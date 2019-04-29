package ru.softwerke.shop.Utils;

import org.eclipse.jetty.util.StringUtil;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.model.BillItem;
import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.model.Device;
import ru.softwerke.shop.service.ClientDataService;
import ru.softwerke.shop.service.DeviceDataService;

import java.math.BigDecimal;

/**
 * Class contains utils for checking items validity
 */
public class ModelUtils {
    public static void checkClient(Client client) throws RequestException {
        if (StringUtil.isBlank(client.getName()) || client.getName().startsWith(" ")) {
            throw new RequestException("name field is incorrect");
        }

        if (StringUtil.isBlank(client.getSecondName()) || client.getSecondName().startsWith(" ")) {
            throw new RequestException("secondName field is incorrect");
        }

        if (StringUtil.isBlank(client.getPatronymic()) || client.getPatronymic().startsWith(" ")) {
            throw new RequestException("patronymic field is incorrect");
        }
    }

    public static void checkDevice(Device device) throws RequestException {

        if (StringUtil.isBlank(device.getCompany()) || device.getCompany().startsWith(" ")) {
            throw new RequestException("company field is incorrect");
        }

        if (StringUtil.isBlank(device.getName()) || device.getCompany().startsWith(" ")) {
            throw new RequestException("name field is incorrect");
        }

        if (StringUtil.isBlank(device.getType()) || device.getType().startsWith(" ")) {
            throw new RequestException("type field is incorrect");
        }

        if (StringUtil.isBlank(device.getColor()) || device.getColor().startsWith(" ")) {
            throw new RequestException("color field is incorrect");
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

    public static void checkBill(Bill bill, ClientDataService clientData, DeviceDataService deviceData) throws RequestException {
        if (clientData.getItemById(bill.getClientId()) == null) {
            throw new RequestException("No client with " + bill.getClientId() + " id");
        }

        for (BillItem item : bill.getItems()) {
            Device device = deviceData.getItemById(item.getDeviceId());
            if (device == null) {
                throw new RequestException("No device with " + item.getDeviceId() + " id");
            }

            if (item.getQuantity() < 1) {
                throw new RequestException("Wrong quantity: " + item.getQuantity());
            }

            item.setPrice(device.getPrice());
        }

        bill.setTotalPrice();
    }
}
