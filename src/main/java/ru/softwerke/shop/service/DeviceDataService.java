package ru.softwerke.shop.service;

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
    private static final String BY_COMPANY = "company";
    private static final String BY_NAME = "name";
    private static final String BY_RELEASED = "released";
    private static final String BY_RELEASED_FROM = "releasedFrom";
    private static final String BY_RELEASED_TO = "releasedTo";
    private static final String BY_COLOR= "color";
    private static final String BY_TYPE = "type";
    private static final String BY_PRICE = "price";
    private static final String BY_PRICE_FROM = "priceFrom";
    private static final String BY_PRICE_TO = "priceTo";

    public static Map<String, Color> colors = new ConcurrentHashMap<>();
    public static CopyOnWriteArrayList<String> types = new CopyOnWriteArrayList<>();

    static {
        colors.put("red", Color.RED);
        colors.put("green", Color.GREEN);
        colors.put("blue", Color.BLUE);
        colors.put("yellow", Color.YELLOW);
        colors.put("black", Color.BLACK);
        colors.put("white", Color.WHITE);

        types.addAll(Arrays.asList("smartphone", "laptop", "mouse"));
    }

    public DeviceDataService(){
        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_NAME, term -> device -> device.getName().startsWith(term));
        predicates.put(BY_COMPANY, term -> device -> device.getCompany().startsWith(term));
        predicates.put(BY_COLOR, term -> device -> device.getColor().equals(term));
        predicates.put(BY_TYPE, term -> device -> device.getType().equals(term));
        predicates.put(BY_RELEASED_FROM, term ->  {
            LocalDate date = ServiceUtils.parseDate(term);

            return device -> device.getReleased().compareTo(date) >= 0;
        });
        predicates.put(BY_RELEASED_TO, term -> {
            LocalDate date = ServiceUtils.parseDate(term);

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

    public static void addColor(String name, Color color) {
        colors.put(name, color);
    }

    public static void addType(String type) {
        types.add(type);
    }
}
