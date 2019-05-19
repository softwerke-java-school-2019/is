package ru.softwerke.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.softwerke.shop.model.Device;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceDataServiceTest {
    private DeviceDataService data = new DeviceDataService();
    private List<Device> deviceList = new ArrayList<>();

    @BeforeEach
    void init() throws IOException {
        deviceList.add(new Device("Xiaomi", "mi a1", "red", "smartphone",
                LocalDate.of(2016, 10,9), BigDecimal.valueOf(10000)));
        deviceList.add(new Device("Samsung", "s10", "red", "smartphone",
                LocalDate.of(2018, 6,2), BigDecimal.valueOf(60000)));
        deviceList.add(new Device("Iphone", "X", "gray", "smartphone",
                LocalDate.of(2018, 7,21), BigDecimal.valueOf(80000)));
        deviceList.add(new Device("Samsung", "s9", "black", "smartphone",
                LocalDate.of(2015, 9,1), BigDecimal.valueOf(40000)));
        deviceList.add(new Device("Dell", "G3", "green", "laptop",
                LocalDate.of(2018, 4,1), BigDecimal.valueOf(60000)));
        deviceList.add(new Device("Acer", "nitro 5", "green", "laptop",
                LocalDate.of(2018, 2,10), BigDecimal.valueOf(60000)));
        deviceList.add(new Device("Lenovo", "Legion Y530", "red", "laptop",
                LocalDate.of(2018, 2,10), BigDecimal.valueOf(60000)));
        deviceList.add(new Device("Asus", "X504L", "black", "Laptop",
                LocalDate.of(2014, 1,1), BigDecimal.valueOf(20000)));

        for (Device device : deviceList) {
            data.addItem(device);
        }
    }

    @Test
    void testFilterString() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.BY_COMPANY, "Samsung");

        List<Device> list = data.getList(queryParams);

        assertTrue(list.contains(deviceList.get(1)));

        assertTrue(list.contains(deviceList.get(3)));

        assertEquals(list.size(), 2);
    }

    @Test
    void testFilterPriceEquals() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.BY_PRICE, "60000");

        List<Device> list = data.getList(queryParams);

        assertTrue(list.contains(deviceList.get(1)));

        assertTrue(list.contains(deviceList.get(4)));

        assertTrue(list.contains(deviceList.get(5)));

        assertTrue(list.contains(deviceList.get(6)));

        assertEquals(list.size(), 4);
    }

    @Test
    void testFilterPriceInterval() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.BY_PRICE_FROM, "10000");
        queryParams.add(DeviceDataService.BY_PRICE_TO, "40000");

        List<Device> list = data.getList(queryParams);

        assertTrue(list.contains(deviceList.get(0)));

        assertTrue(list.contains(deviceList.get(3)));

        assertTrue(list.contains(deviceList.get(7)));


        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterDateEquals() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.BY_RELEASED, "10.02.2018");

        List<Device> list = data.getList(queryParams);

        assertTrue(list.contains(deviceList.get(5)));

        assertTrue(list.contains(deviceList.get(6)));

        assertEquals(list.size(), 2);
    }

    @Test
    void testFilterDateInterval() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.BY_RELEASED_FROM, "01.04.2018");
        queryParams.add(DeviceDataService.BY_RELEASED_TO, "21.07.2018");

        List<Device> list = data.getList(queryParams);

        assertTrue(list.contains(deviceList.get(1)));

        assertTrue(list.contains(deviceList.get(2)));

        assertTrue(list.contains(deviceList.get(4)));

        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterPaging() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.PAGE, "2");
        queryParams.add(DeviceDataService.COUNT, "3");

        List<Device> list = data.getList(queryParams);

        assertTrue(list.contains(deviceList.get(3)));

        assertTrue(list.contains(deviceList.get(4)));

        assertTrue(list.contains(deviceList.get(5)));

        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterSorting() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(DeviceDataService.ORDER_BY, DeviceDataService.BY_PRICE);
        queryParams.add(DeviceDataService.ORDER_BY, "-" + DeviceDataService.BY_RELEASED + ", - " + DeviceDataService.BY_COMPANY);

        List<Device> list = data.getList(queryParams);

        assertEquals(list.get(0), deviceList.get(0));

        assertEquals(list.get(1), deviceList.get(7));

        assertEquals(list.get(2), deviceList.get(3));

        assertEquals(list.get(3), deviceList.get(1));

        assertEquals(list.get(4), deviceList.get(4));

        assertEquals(list.get(5), deviceList.get(6));

        assertEquals(list.get(6), deviceList.get(5));

        assertEquals(list.get(7), deviceList.get(2));
    }

}
