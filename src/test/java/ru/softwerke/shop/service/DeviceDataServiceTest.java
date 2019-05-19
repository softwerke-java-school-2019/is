package ru.softwerke.shop.service;

import org.junit.Before;
import org.junit.Test;
import ru.softwerke.shop.model.Device;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeviceDataServiceTest {
    private DeviceDataService data = new DeviceDataService();
    private List<Device> deviceList = new ArrayList<>();

    @Before
    public void init() throws IOException {
        deviceList.add(new Device("Xiaomi", "mi a1", "red", "smartphone",
                LocalDate.of(2016, 10,9), BigDecimal.valueOf(100)));
        deviceList.add(new Device("Samsung", "s10", "red", "smartphone",
                LocalDate.of(2016, 10,9), BigDecimal.valueOf(100)));

        for (Device device : deviceList) {
            data.addItem(device);
        }
    }

    @Test
    public void testFilterString() {

    }
}
