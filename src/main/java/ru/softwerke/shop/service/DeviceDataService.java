package ru.softwerke.shop.service;

import ru.softwerke.shop.Utils.ServiceUtils;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Device;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeviceDataService extends DataService<Device> {
    private static final String BY_COMPANY = "manufacturer";
    private static final String BY_NAME = "modelName";
    private static final String BY_RELEASED = "manufactureDate";
    private static final String BY_RELEASED_FROM = "manufactureDateFrom";
    private static final String BY_RELEASED_TO = "manufactureDateTo";
    private static final String BY_COLOR= "colorName";
    private static final String BY_TYPE = "deviceType";
    private static final String BY_PRICE = "price";
    private static final String BY_PRICE_FROM = "priceFrom";
    private static final String BY_PRICE_TO = "priceTo";

    public static Map<String, Color> colors = new ConcurrentHashMap<>();
    public static CopyOnWriteArrayList<String> types = new CopyOnWriteArrayList<>();

    static {
        colors.put("Red", Color.RED);
        colors.put("Green", Color.GREEN);
        colors.put("Blue", Color.BLUE);
        colors.put("Yellow", Color.YELLOW);
        colors.put("Black", Color.BLACK);
        colors.put("White", Color.WHITE);
        colors.put("Gray", Color.GRAY);

        types.addAll(Arrays.asList("Smartphone", "Laptop", "Smart Watches", "Tablet"));
    }

    public DeviceDataService(){

        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_NAME, term -> device -> device.getName().equals(term));
        predicates.put(BY_COMPANY, term -> device -> device.getCompany().equals(term));
        predicates.put(BY_COLOR, term -> device -> device.getColor().equals(term));
        predicates.put(BY_TYPE, term -> device -> device.getType().equals(term));
        predicates.put(BY_RELEASED_FROM, term ->  {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return device -> device.getReleased().compareTo(date) >= 0;
        });
        predicates.put(BY_RELEASED_TO, term -> {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return device -> device.getReleased().compareTo(date) <= 0;
        });
        predicates.put(BY_PRICE_FROM, term ->  {
            BigDecimal priceFrom = ServiceUtils.parseNumber(term, BigDecimal::new);

            return device -> device.getPrice().compareTo(priceFrom) >= 0;
        });
        predicates.put(BY_PRICE_TO, term ->  {
            BigDecimal priceTo = ServiceUtils.parseNumber(term, BigDecimal::new);

            return device -> device.getPrice().compareTo(priceTo) <= 0;
        });

        comparators.put(BY_ID, Comparator.comparing(Device::getId));
        comparators.put(BY_NAME, Comparator.comparing(Device::getName));
        comparators.put(BY_RELEASED, Comparator.comparing(Device::getReleased));
        comparators.put(BY_COMPANY, Comparator.comparing(Device::getCompany));
        comparators.put(BY_PRICE, Comparator.comparing(Device::getPrice));
        comparators.put(BY_COLOR, Comparator.comparing(Device::getColor));
        comparators.put(BY_TYPE, Comparator.comparing(Device::getType));
    }

    public static void addColor(String name, Color color) throws RequestException {
        if (colors.keySet().contains(name)) {
            throw new RequestException("Color " + name + " already exists");
        }
        colors.put(name, color);
    }

    public static void addType(String type) throws RequestException {
        if (types.contains(type)) {
            throw new RequestException("Type " + type + " already exists");
        }
        types.add(type);
    }
}
