package ru.softwerke.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.softwerke.shop.model.Client;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ClientDataServiceTest {
    private ClientDataService data = new ClientDataService();
    private List<Client> clientsList = new ArrayList<>();

    @BeforeEach
    void init() throws IOException {
        clientsList.add(new Client("Саяхов", "Ильфат", "Раилевич", LocalDate.of(1998, 4, 6)));
        clientsList.add(new Client("Башаров", "Ильфат", "Раилевич", LocalDate.of(1999, 1, 1)));
        clientsList.add(new Client("Цвык", "Илья", "Игоревич", LocalDate.of(1995, 7, 5)));
        clientsList.add(new Client("Иванов", "Иван", "Иванович", LocalDate.of(1995, 7, 5)));
        clientsList.add(new Client("Сидоров", "Константин", "Алексеевич", LocalDate.of(1990, 2, 2)));
        clientsList.add(new Client("Богданов", "Михаил", "Константинович", LocalDate.of(1998, 7, 5)));
        clientsList.add(new Client("Мазитов", "Марсель", "Михайлович", LocalDate.of(1998, 10, 1)));
        clientsList.add(new Client("Сунгатуллин", "Нияз", "Игоревич", LocalDate.of(1995, 7, 5)));
        clientsList.add(new Client("Абрамов", "Нияз", "Игоревич", LocalDate.of(1995, 7, 5)));


        for (Client client : clientsList) {
            data.addItem(client);
        }
    }

    @Test
    void testFilterString() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(ClientDataService.BY_NAME, "Ильфат");

        List<Client> list = data.getList(queryParams);

        assertTrue(list.contains(clientsList.get(0)));

        assertTrue(list.contains(clientsList.get(1)));

        assertEquals(list.size(), 2);
    }

    @Test
    void testFilterDateEquals() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(ClientDataService.BY_BIRTHDATE, "01.01.1999");

        List<Client> list = data.getList(queryParams);

        assertTrue(list.contains(clientsList.get(1)));

        list = data.getList(queryParams);

        assertEquals(list.size(), 1);
    }

    @Test
    void testFilterDateInterval() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(ClientDataService.BY_BIRTHDATE_FROM, "06.04.1998");
        queryParams.add(ClientDataService.BY_BIRTHDATE_TO, "01.01.1999");

        List<Client> list = data.getList(queryParams);

        assertTrue(list.contains(clientsList.get(0)));

        assertTrue(list.contains(clientsList.get(1)));

        assertTrue(list.contains(clientsList.get(5)));

        assertTrue(list.contains(clientsList.get(6)));

        assertEquals(list.size(), 4);
    }

    @Test
    void testFilterPaging() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(ClientDataService.PAGE, "2");
        queryParams.add(ClientDataService.COUNT, "3");

        List<Client> list = data.getList(queryParams);

        assertTrue(list.contains(clientsList.get(3)));

        assertTrue(list.contains(clientsList.get(4)));

        assertTrue(list.contains(clientsList.get(5)));

        assertEquals(list.size(), 3);
    }

    @Test
    void testFilterSorting() throws IOException {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add(ClientDataService.ORDER_BY, ClientDataService.BY_BIRTHDATE);
        queryParams.add(ClientDataService.ORDER_BY, "-" + ClientDataService.BY_NAME + ", " + ClientDataService.BY_SECOND_NAME);

        List<Client> list = data.getList(queryParams);

        assertEquals(list.get(0), clientsList.get(4));

        assertEquals(list.get(1), clientsList.get(8));

        assertEquals(list.get(2), clientsList.get(7));

        assertEquals(list.get(3), clientsList.get(2));

        assertEquals(list.get(4), clientsList.get(3));

        assertEquals(list.get(5), clientsList.get(0));

        assertEquals(list.get(6), clientsList.get(5));

        assertEquals(list.get(7), clientsList.get(6));

        assertEquals(list.get(8), clientsList.get(1));

        assertEquals(list.size(), 9);
    }
}
