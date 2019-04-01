package ru.softwerke.shop.service;

import ru.softwerke.shop.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDataService {
    static List<Client> clients = new ArrayList<>();

    public Client addClient(Client client) {
        clients.add(client);
        return client;
    }

    public Client getClientByID(long id) {
        for (Client client : clients) {
            if (client.getID() == id) {
                return client;
            }
        }
        return null;
    }

    public List<Client> getClientsList() {
        return clients;
    }

    public List<Client> filter(ClientFilter filter) {
        return clients.stream().filter(client -> filterClient(filter, client)).collect(Collectors.toList());
    }

    private boolean filterClient(ClientFilter filter, Client client) {
        return (filterParameter(filter.getName(), client.getName())
        && filterParameter(filter.getSecondName(), client.getSecondName())
        && filterParameter(filter.getPatronymic(), client.getPatronymic())
        && (filter.getDateFrom() == null || client.getBirthday().compareTo(filter.getDateFrom()) >= 0)
        && (filter.getDateTo() == null || client.getBirthday().compareTo(filter.getDateTo()) <= 0));
    }

    private boolean filterParameter(String filter, String client) {
        if (filter == null) {
            return true;
        }

        return client.contains(filter);
    }
}
