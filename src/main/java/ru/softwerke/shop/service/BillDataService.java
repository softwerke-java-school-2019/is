package ru.softwerke.shop.service;

import ru.softwerke.shop.controller.NotFoundException;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Device;
import ru.softwerke.shop.utils.Utils;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.model.BillItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BillDataService extends DataService<Bill> {

    public static final String BY_CLIENT_ID = Bill.CLIENT_ID_FIELD;
    public static final String BY_TOTAL_PRICE_FROM = Bill.TOTAL_PRICE_FIELD + "From";
    public static final String BY_TOTAL_PRICE_TO = Bill.TOTAL_PRICE_FIELD + "To";
    public static final String BY_TOTAL_PRICE = Bill.TOTAL_PRICE_FIELD;
    public static final String BY_PURCHASE_DATE_TIME_FROM = Bill.PURCHASE_DATE_TIME_FIELD + "From";
    public static final String BY_PURCHASE_DATE_TIME_TO = Bill.PURCHASE_DATE_TIME_FIELD + "To";
    public static final String BY_PURCHASE_DATE_TIME = Bill.PURCHASE_DATE_TIME_FIELD;
    public static final String BY_DEVICE_ID = BillItem.DEVICE_ID_FIELD;
    public static final String BY_PRICE_FROM = BillItem.PRICE_FIELD + "From";
    public static final String BY_PRICE_TO = BillItem.PRICE_FIELD + "To";
    public static final String BY_PRICE = BillItem.PRICE_FIELD ;
    public static final String BY_QUANTITY_FROM = BillItem.QUANTITY_FIELD + "From";
    public static final String BY_QUANTITY_TO = BillItem.QUANTITY_FIELD + "To";
    public static final String BY_QUANTITY = BillItem.QUANTITY_FIELD;


    private final ClientDataService clientData;
    private final DeviceDataService deviceData;


    public BillDataService (DeviceDataService deviceData, ClientDataService clientData) {
        this.deviceData = deviceData;
        this.clientData = clientData;

        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_CLIENT_ID, term -> {
            long id = Utils.parseNumber(term, Long::parseLong);

            return bill -> bill.getClientId() == id;
        });
        predicates.put(BY_TOTAL_PRICE_FROM, term -> {
            BigDecimal priceFrom = Utils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getTotalPrice().compareTo(priceFrom) >= 0;
        });
        predicates.put(BY_TOTAL_PRICE_TO, term -> {
            BigDecimal priceTo = Utils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getTotalPrice().compareTo(priceTo) <= 0;
        });
        predicates.put(BY_TOTAL_PRICE, term -> {
            BigDecimal price = Utils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getTotalPrice().compareTo(price) == 0;
        });
        predicates.put(BY_PURCHASE_DATE_TIME_FROM, term -> {
            LocalDateTime date = Utils.parseDateTime(term);

            return bill -> bill.getPurchaseDateTime().compareTo(date) >= 0;
        });
        predicates.put(BY_PURCHASE_DATE_TIME_TO, term -> {
            LocalDateTime date = Utils.parseDateTime(term);

            return bill -> bill.getPurchaseDateTime().compareTo(date) <= 0;
        });
        predicates.put(BY_PURCHASE_DATE_TIME, term -> {
            LocalDateTime date = Utils.parseDateTime(term);

            return bill -> bill.getPurchaseDateTime().compareTo(date) == 0;
        });
        predicates.put(BY_DEVICE_ID, term -> {
            long id = Utils.parseNumber(term, Long::parseLong);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getDeviceId() == id);
        });
        predicates.put(BY_PRICE_FROM, term -> {
            BigDecimal priceFrom = Utils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getPrice().compareTo(priceFrom) >= 0);
        });
        predicates.put(BY_PRICE_TO, term -> {
            BigDecimal priceTo = Utils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getPrice().compareTo(priceTo) <= 0);
        });
        predicates.put(BY_PRICE, term -> {
            BigDecimal price = Utils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getPrice().compareTo(price) == 0);
        });
        predicates.put(BY_QUANTITY_FROM, term -> {
           long quantityFrom = Utils.parseNumber(term, Long::new);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getQuantity() >= quantityFrom);
        });
        predicates.put(BY_QUANTITY_TO, term -> {
            long quantityTo = Utils.parseNumber(term, Long::new);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getQuantity() <= quantityTo);
        });
        predicates.put(BY_QUANTITY, term -> {
            long quantity = Utils.parseNumber(term, Long::new);

            return bill -> bill.getItems().stream().anyMatch(item -> item.getQuantity() == quantity);
        });

        comparators.put(BY_ID, Comparator.comparing(Bill::getId));
        comparators.put(BY_CLIENT_ID, Comparator.comparing(Bill::getClientId));
        comparators.put(BY_PURCHASE_DATE_TIME, Comparator.comparing(Bill::getPurchaseDateTime));
        comparators.put(BY_TOTAL_PRICE, Comparator.comparing(Bill::getTotalPrice));
    }

    @Override
    public void checkItem(Bill item) throws IOException {
        if (clientData.getItemById(item.getClientId()) == null) {
            throw new NotFoundException("No client with id: " + item.getClientId());
        }

        for (BillItem billItem : item.getItems()) {
            Device device = deviceData.getItemById(billItem.getDeviceId());
            if (device == null) {
                throw new NotFoundException("No device with id: " + billItem.getDeviceId());
            }

            if (billItem.getQuantity() < 1) {
                throw new RequestException("Wrong quantity: " + billItem.getQuantity());
            }

            billItem.setPrice(device.getPrice());
        }

        item.setTotalPrice();
    }
}
