package ru.softwerke.shop.model;

import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.service.DeviceDataService;

import java.math.BigDecimal;

public class ModelUtils {
    public static void checkClient(Client client) throws RequestException {
        if (client == null) {
            throw new RequestException("No request body");
        }

        if (client.getName() == null) {
            throw new RequestException("name field is empty");
        }

        if (client.getSecondName() == null) {
            throw new RequestException("secondName field is empty");
        }

        if (client.getPatronymic() == null) {
            throw new RequestException("patronymic field is empty");
        }

        if (client.getBirthday() == null) {
            throw new RequestException("birthday field is empty");
        }
    }

    public static void checkDevice(Device device) throws RequestException {
        if (device == null) {
            throw new RequestException("No request body");
        }

        if (device.getCompany() == null) {
            throw new RequestException("company field is empty");
        }

        if (device.getName() == null) {
            throw new RequestException("name field is empty");
        }

        if (device.getPrice() == null) {
            throw new RequestException("price field is empty");
        }

        if (device.getType() == null) {
            throw new RequestException("type field is empty");
        }

        if (device.getColor() == null) {
            throw new RequestException("color field is empty");
        }

        if (device.getReleased() == null) {
            throw new RequestException("released field is empty");
        }

        if (!DeviceDataService.colors.containsKey(device.getColor())) {
            throw new RequestException("Illegal color: " + device.getColor() );
        }

        if (!DeviceDataService.types.contains(device.getType())) {
            throw new RequestException("Illegal type: " + device.getType() );
        }

        if (device.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new RequestException("price can not be negative");
        }
    }
}
