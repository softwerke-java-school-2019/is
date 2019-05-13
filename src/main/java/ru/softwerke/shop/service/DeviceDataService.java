package ru.softwerke.shop.service;

import org.json.JSONException;
import org.json.JSONObject;
import ru.softwerke.shop.utils.ServiceUtils;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Device;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeviceDataService extends DataService<Device> {
    private static final String BY_COMPANY = Device.COMPANY_FIELD;
    private static final String BY_NAME = Device.NAME_FIELD;
    private static final String BY_RELEASED = Device.RELEASED_FIELD;
    private static final String BY_RELEASED_FROM = Device.RELEASED_FIELD + "From";
    private static final String BY_RELEASED_TO = Device.RELEASED_FIELD + "To";
    private static final String BY_COLOR= Device.COLOR_FIELD;
    private static final String BY_TYPE = Device.TYPE_FIELD;
    private static final String BY_PRICE = Device.PRICE_FIELD;
    private static final String BY_PRICE_FROM = Device.PRICE_FIELD + "From";
    private static final String BY_PRICE_TO = Device.PRICE_FIELD + "To";

    public static Map<String, Color> colors = new ConcurrentHashMap<>();
    public static CopyOnWriteArrayList<String> types = new CopyOnWriteArrayList<>();

    static {
        colors.put("red", Color.RED);
        colors.put("green", Color.GREEN);
        colors.put("blue", Color.BLUE);
        colors.put("yellow", Color.YELLOW);
        colors.put("black", Color.BLACK);
        colors.put("white", Color.WHITE);
        colors.put("gray", Color.GRAY);
        colors.put("красный", Color.RED);
        colors.put("зеленый", Color.GREEN);
        colors.put("синий", Color.BLUE);
        colors.put("желтый", Color.YELLOW);
        colors.put("черный", Color.BLACK);
        colors.put("белый", Color.WHITE);
        colors.put("серый", Color.GRAY);

        types.addAll(Arrays.asList("smartphone", "laptop", "smart watches", "tablet"));
    }

    public DeviceDataService(){

        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_NAME, term -> device -> device.getName().equals(term));
        predicates.put(BY_COMPANY, term -> device -> device.getCompany().equals(term));
        predicates.put(BY_COLOR, term -> device -> device.getColor().equals(term.toLowerCase()));
        predicates.put(BY_TYPE, term -> device -> device.getType().equals(term.toLowerCase()));
        predicates.put(BY_RELEASED_FROM, term ->  {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return device -> device.getReleased().compareTo(date) >= 0;
        });
        predicates.put(BY_RELEASED_TO, term -> {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return device -> device.getReleased().compareTo(date) <= 0;
        });
        predicates.put(BY_RELEASED, term -> {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return device -> device.getReleased().compareTo(date) == 0;
        });
        predicates.put(BY_PRICE_FROM, term ->  {
            BigDecimal priceFrom = ServiceUtils.parseNumber(term, BigDecimal::new);

            return device -> device.getPrice().compareTo(priceFrom) >= 0;
        });
        predicates.put(BY_PRICE_TO, term ->  {
            BigDecimal priceTo = ServiceUtils.parseNumber(term, BigDecimal::new);

            return device -> device.getPrice().compareTo(priceTo) <= 0;
        });
        predicates.put(BY_PRICE, term ->  {
            BigDecimal price = ServiceUtils.parseNumber(term, BigDecimal::new);

            return device -> device.getPrice().compareTo(price) == 0;
        });

        comparators.put(BY_ID, Comparator.comparing(Device::getId));
        comparators.put(BY_NAME, Comparator.comparing(Device::getName));
        comparators.put(BY_RELEASED, Comparator.comparing(Device::getReleased));
        comparators.put(BY_COMPANY, Comparator.comparing(Device::getCompany));
        comparators.put(BY_PRICE, Comparator.comparing(Device::getPrice));
        comparators.put(BY_COLOR, Comparator.comparing(Device::getColor));
        comparators.put(BY_TYPE, Comparator.comparing(Device::getType));
    }

    public Device patchDevice(String jsonDevice) throws RequestException {
        try {
            JSONObject json = new JSONObject(jsonDevice);

            Set<String> keys = json.keySet();

            if (!keys.contains("id")) {
                throw new RequestException("Required field \'id\' missed");
            }

            long id = json.getLong("id");

            Device device = getItemById(id);

            if (device == null) {
                throw new RequestException("No device with id: " + id);
            }

            if (keys.contains(Device.PRICE_FIELD)) {
                BigDecimal price = ServiceUtils.parseNumber(json.get(Device.PRICE_FIELD).toString(), BigDecimal::new);

                if (price.compareTo(BigDecimal.valueOf(0)) <= 0) {
                    throw new RequestException("price can not be negative");
                }

                device.setPrice(price);
            }

            if (keys.contains(Device.COLOR_FIELD)) {

                String color = json.getString(Device.COLOR_FIELD);

                if (!DeviceDataService.colors.containsKey(color.toLowerCase())) {
                    throw new RequestException("Illegal color: " + color );
                }

                device.setColor(color);
            }

            if (keys.contains(Device.TYPE_FIELD)) {

                String type = json.getString(Device.TYPE_FIELD);

                if (!DeviceDataService.types.contains(type.toLowerCase())) {
                    throw new RequestException("Illegal type: " + type );
                }

                device.setType(type);
            }

            return device;
        } catch (JSONException ex) {
            throw new RequestException("Wrong json format");
        }
    }

    public static void addColor(String jsonStr) throws RequestException {
        try {
            JSONObject json = new JSONObject(jsonStr);

            Set<String> keys = json.keySet();
            if (!keys.contains("name")) {
                throw new RequestException("Required field \'name\' missed");
            }
            if (!keys.contains("rgb")) {
                throw new RequestException("Required field \'rgb\' missed");
            }

            String name = json.getString("name");
            int rgb = json.getInt("rgb");

            if (colors.keySet().contains(name.toLowerCase())) {
                throw new RequestException("Color " + name + " already exists");
            }
            colors.put(name.toLowerCase(), new Color(rgb));

        } catch (JSONException ex) {
            throw new RequestException("Wrong json format");
        }
    }

    public static void addType(String jsonStr) throws RequestException {
        try {
            JSONObject json = new JSONObject(jsonStr);

            if (!json.keySet().contains("name")) {
                throw new RequestException("Required field \'name\' missed");
            }

            String type = json.getString("name");

            if (types.contains(type.toLowerCase())) {
                throw new RequestException("Type " + type + " already exists");
            }
            types.add(type.toLowerCase());
        } catch (JSONException ex) {
            throw new RequestException("Wrong json format");
        }
    }
}
