package ru.softwerke.shop.service;

import ru.softwerke.shop.Utils.ServiceUtils;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.model.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BillDataService extends DataService<Bill> {

    private static final String BY_CLIENT_ID = "clientId";
    private static final String BY_TOTAL_PRICE_FROM = "totalPriceFrom";
    private static final String BY_TOTAL_PRICE_TO = "totalPriceTo";
    private static final String BY_TOTAL_PRICE = "totalPrice";
    private static final String BY_PURCHASE_DATE_TIME_FROM = "dateFrom";
    private static final String BY_PURCHASE_DATE_TIME_TO = "dateTo";
    private static final String BY_PURCHASE_DATE_TIME = "date";


    public BillDataService () {
        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_CLIENT_ID, term -> {
            long id = ServiceUtils.parseNumber(term, Long::parseLong);

            return bill -> bill.getClientId() == id;
        });
        predicates.put(BY_TOTAL_PRICE_FROM, term -> {
            BigDecimal priceFrom = ServiceUtils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getTotalPrice().compareTo(priceFrom) >= 0;
        });
        predicates.put(BY_TOTAL_PRICE_TO, term -> {
            BigDecimal priceTo = ServiceUtils.parseNumber(term, BigDecimal::new);

            return bill -> bill.getTotalPrice().compareTo(priceTo) <= 0;
        });
        predicates.put(BY_PURCHASE_DATE_TIME_FROM, term -> {
            LocalDateTime date = ServiceUtils.parseDateTime(term, ServiceUtils.dateWithTimeFormatter);

            return bill -> bill.getPurchaseDateTime().compareTo(date) >= 0;
        });
        predicates.put(BY_PURCHASE_DATE_TIME_TO, term -> {
            LocalDateTime date = ServiceUtils.parseDateTime(term, ServiceUtils.dateWithTimeFormatter);

            return bill -> bill.getPurchaseDateTime().compareTo(date) <= 0;
        });

        comparators.put(BY_ID, Comparator.comparing(Bill::getId));
        comparators.put(BY_CLIENT_ID, Comparator.comparing(Bill::getClientId));
        comparators.put(BY_PURCHASE_DATE_TIME, Comparator.comparing(Bill::getPurchaseDateTime));
        comparators.put(BY_TOTAL_PRICE, Comparator.comparing(Bill::getTotalPrice));

    }
}
