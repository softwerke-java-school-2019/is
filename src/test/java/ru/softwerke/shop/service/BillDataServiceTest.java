package ru.softwerke.shop.service;

import org.junit.jupiter.api.Test;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.model.BillItem;
import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.model.Device;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BillDataServiceTest {
    private static DeviceDataService deviceData = new DeviceDataService();
    private static ClientDataService clientData = new ClientDataService();
    private static BillDataService data = new BillDataService(deviceData, clientData);
    private static List<Bill> billList = new ArrayList<>();
    private static List<Device> deviceList = new ArrayList<>();
    private static List<Client> clientsList = new ArrayList<>();

    @BeforeAll
    static void init() throws IOException {
        createDevices();
        createClients();
        createBills();
    }

    private static void createDevices() throws IOException {
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

        for (Device device : deviceList) {
            deviceData.addItem(device);
        }
    }

    private static void createClients() throws IOException {
        clientsList.add(new Client("Саяхов", "Ильфат", "Раилевич", LocalDate.of(1998, 4, 6)));
        clientsList.add(new Client("Башаров", "Ильфат", "Раилевич", LocalDate.of(1999, 1, 1)));
        clientsList.add(new Client("Цвык", "Илья", "Игоревич", LocalDate.of(1995, 7, 5)));

        for (Client client : clientsList) {
            clientData.addItem(client);
        }
    }

    private static void createBills() throws IOException {
        List<BillItem> billItems = Arrays.asList(new BillItem(0, 5), new BillItem(3, 3));
        billList.add(new Bill(1, LocalDateTime.of(LocalDate.of(2018, 12, 31),
                LocalTime.of(23,59, 59)), billItems));

        billItems = Arrays.asList(new BillItem(1, 2), new BillItem(2, 3),
                new BillItem(4, 10));
        billList.add(new Bill(2, LocalDateTime.of(LocalDate.of(2019, 2, 2),
                LocalTime.of(0,0, 2)), billItems));

        billItems = Arrays.asList(new BillItem(3, 2));
        billList.add(new Bill(2, LocalDateTime.of(LocalDate.of(2018, 4, 1),
                LocalTime.of(1,2, 2)), billItems));

        billItems = Arrays.asList(new BillItem(2, 3), new BillItem(4, 3));
        billList.add(new Bill(2, LocalDateTime.of(LocalDate.of(2018, 4, 1),
                LocalTime.of(1,2, 2)), billItems));

        billItems = Arrays.asList(new BillItem(0, 1), new BillItem(1, 3));
        billList.add(new Bill(0, LocalDateTime.of(LocalDate.of(2018, 4, 2),
                LocalTime.of(0,0, 2)), billItems));

        for (Bill bill : billList) {
            data.addItem(bill);
        }
    }

    @Test
    void testFilterCustomerId() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(BillDataService.BY_CLIENT_ID, "2");

        List<Bill> list = data.getList(queryParams);

        assertTrue(list.contains(billList.get(1)));

        assertTrue(list.contains(billList.get(2)));

        assertTrue(list.contains(billList.get(3)));

        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterDateEquals() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(BillDataService.BY_PURCHASE_DATE_TIME, "02.02.2019 00:00:02");

        List<Bill> list = data.getList(queryParams);

        assertTrue(list.contains(billList.get(1)));

        assertEquals(list.size(), 1);
    }

    @Test
    void testFilterDateInterval() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(BillDataService.BY_PURCHASE_DATE_TIME_FROM, "02.04.2018 00:00:02");
        queryParams.add(BillDataService.BY_PURCHASE_DATE_TIME_TO, "02.02.2019 01:02:02");

        List<Bill> list = data.getList(queryParams);

        assertTrue(list.contains(billList.get(0)));

        assertTrue(list.contains(billList.get(1)));

        assertTrue(list.contains(billList.get(4)));

        assertEquals(list.size(), 3);
    }



    @Test
    void testFilterQuantityInterval() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(BillDataService.BY_QUANTITY_FROM, "1");
        queryParams.add(BillDataService.BY_QUANTITY_TO, "2");

        List<Bill> list = data.getList(queryParams);

        assertTrue(list.contains(billList.get(1)));

        assertTrue(list.contains(billList.get(2)));

        assertTrue(list.contains(billList.get(4)));

        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterPriceEquals() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(BillDataService.BY_PRICE, "60000");

        List<Bill> list = data.getList(queryParams);

        assertTrue(list.contains(billList.get(1)));

        assertTrue(list.contains(billList.get(3)));

        assertTrue(list.contains(billList.get(4)));

        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterSorting() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(BillDataService.ORDER_BY, BillDataService.BY_CLIENT_ID);
        queryParams.add(BillDataService.ORDER_BY, "-" + BillDataService.BY_PURCHASE_DATE_TIME + ", -" + BillDataService.BY_TOTAL_PRICE);

        List<Bill> list = data.getList(queryParams);

        assertEquals(list.get(0), billList.get(4));

        assertEquals(list.get(1), billList.get(0));

        assertEquals(list.get(2), billList.get(1));

        assertEquals(list.get(3), billList.get(3));

        assertEquals(list.get(4), billList.get(2));
    }


}
