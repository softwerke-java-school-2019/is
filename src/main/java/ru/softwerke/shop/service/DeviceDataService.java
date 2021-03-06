package ru.softwerke.shop.service;

import org.eclipse.jetty.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import ru.softwerke.shop.controller.NotFoundException;
import ru.softwerke.shop.utils.Utils;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Device;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeviceDataService extends DataService<Device> {
    public static final String BY_COMPANY = Device.COMPANY_FIELD;
    public static final String BY_NAME = Device.NAME_FIELD;
    public static final String BY_RELEASED = Device.RELEASED_FIELD;
    public static final String BY_RELEASED_FROM = Device.RELEASED_FIELD + "From";
    public static final String BY_RELEASED_TO = Device.RELEASED_FIELD + "To";
    public static final String BY_COLOR= Device.COLOR_FIELD;
    public static final String BY_TYPE = Device.TYPE_FIELD;
    public static final String BY_PRICE = Device.PRICE_FIELD;
    public static final String BY_PRICE_FROM = Device.PRICE_FIELD + "From";
    public static final String BY_PRICE_TO = Device.PRICE_FIELD + "To";

    public static Map<String, Color> colors = new ConcurrentHashMap<>();
    public static CopyOnWriteArrayList<String> types = new CopyOnWriteArrayList<>();

    static {
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

        predicates.put(BY_ID, term -> {
            Long id = Utils.parseNumber(term, Long::parseLong);

            return device -> term.equals("") || device.getId() == id;
        });
        predicates.put(BY_NAME, term -> device -> term.equals("") || device.getName().equals(term));
        predicates.put(BY_COMPANY, term -> device -> term.equals("") || device.getCompany().equals(term));
        predicates.put(BY_COLOR, term -> device -> term.equals("") || device.getColor().equals(term.toLowerCase()));
        predicates.put(BY_TYPE, term -> device -> term.equals("") || device.getType().equals(term.toLowerCase()));
        predicates.put(BY_RELEASED_FROM, term ->  {
            LocalDate date = Utils.parseDate(term);

            return device -> term.equals("") || device.getReleased().compareTo(date) >= 0;
        });
        predicates.put(BY_RELEASED_TO, term -> {
            LocalDate date = Utils.parseDate(term);

            return device -> term.equals("") || device.getReleased().compareTo(date) <= 0;
        });
        predicates.put(BY_RELEASED, term -> {
            LocalDate date = Utils.parseDate(term);

            return device -> term.equals("") || device.getReleased().compareTo(date) == 0;
        });
        predicates.put(BY_PRICE_FROM, term ->  {
            BigDecimal priceFrom = Utils.parseNumber(term, BigDecimal::new);

            return device -> term.equals("") || device.getPrice().compareTo(priceFrom) >= 0;
        });
        predicates.put(BY_PRICE_TO, term ->  {
            BigDecimal priceTo = Utils.parseNumber(term, BigDecimal::new);

            return device -> term.equals("") || device.getPrice().compareTo(priceTo) <= 0;
        });
        predicates.put(BY_PRICE, term ->  {
            BigDecimal price = Utils.parseNumber(term, BigDecimal::new);

            return device -> term.equals("") || device.getPrice().compareTo(price) == 0;
        });

        comparators.put(BY_ID, Comparator.comparing(Device::getId));
        comparators.put(BY_NAME, Comparator.comparing(Device::getName));
        comparators.put(BY_RELEASED, Comparator.comparing(Device::getReleased));
        comparators.put(BY_COMPANY, Comparator.comparing(Device::getCompany));
        comparators.put(BY_PRICE, Comparator.comparing(Device::getPrice));
        comparators.put(BY_COLOR, Comparator.comparing(Device::getColor));
        comparators.put(BY_TYPE, Comparator.comparing(Device::getType));
    }

    public Device patchDevice(String jsonDevice) throws IOException {
        try {
            JSONObject json = new JSONObject(jsonDevice);

            Set<String> keys = json.keySet();

            if (!keys.contains("id")) {
                throw new RequestException("Required field \'id\' missed");
            }

            long id = json.getLong("id");

            Device device = getItemById(id);

            if (device == null) {
                throw new NotFoundException("No device with id: " + id);
            }

            if (keys.contains(Device.PRICE_FIELD)) {
                BigDecimal price = Utils.parseNumber(json.get(Device.PRICE_FIELD).toString(), BigDecimal::new);

                if (price.compareTo(BigDecimal.valueOf(0)) <= 0) {
                    throw new RequestException("price can not be negative");
                }

                device.setPrice(price);
            }

            if (keys.contains(Device.COLOR_FIELD)) {

                String color = json.getString(Device.COLOR_FIELD);

                if (!DeviceDataService.colors.containsKey(color.toLowerCase())) {
                    throw new NotFoundException("Illegal color: " + color );
                }

                device.setColor(color);
            }

            if (keys.contains(Device.TYPE_FIELD)) {

                String type = json.getString(Device.TYPE_FIELD);

                if (!DeviceDataService.types.contains(type.toLowerCase())) {
                    throw new NotFoundException("Illegal type: " + type );
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
            String rgb = json.getString("rgb");

            if (colors.keySet().contains(name.toLowerCase())) {
                throw new RequestException("Color " + name + " already exists");
            }
            int r = Integer.valueOf( rgb.substring( 1, 3 ), 16 );
            int g = Integer.valueOf( rgb.substring( 3, 5 ), 16 );
            int b = Integer.valueOf( rgb.substring( 5, 7 ), 16 );

            colors.put(name.toLowerCase(), new Color(r, g, b));

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

    @Override
    public void checkItem(Device item) throws IOException {

        if (StringUtil.isBlank(item.getCompany())) {
            throw new RequestException("company field is incorrect");
        }

        if (StringUtil.isBlank(item.getName())) {
            throw new RequestException("name field is incorrect");
        }

        if (StringUtil.isBlank(item.getType())) {
            throw new RequestException("type field is incorrect");
        }

        if (StringUtil.isBlank(item.getColor())) {
            throw new RequestException("color field is incorrect");
        }

        if (!DeviceDataService.colors.containsKey(item.getColor())) {
            throw new NotFoundException("Illegal color: " + item.getColor() );
        }

        if (!DeviceDataService.types.contains(item.getType())) {
            throw new NotFoundException("Illegal type: " + item.getType() );
        }

        if (item.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new RequestException("price can not be negative");
        }
    }
}
