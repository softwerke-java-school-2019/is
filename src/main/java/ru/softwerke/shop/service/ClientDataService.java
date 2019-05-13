package ru.softwerke.shop.service;

import ru.softwerke.shop.Utils.ServiceUtils;
import ru.softwerke.shop.model.Client;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientDataService extends DataService<Client> {

    private static final String BY_NAME = Client.NAME_FIELD;
    private static final String BY_SECOND_NAME = Client.SECONDNAME_FIELD;
    private static final String BY_PATRONYMIC = Client.PATRONYMIC_FIELD;
    private static final String BY_BIRTHDATE_FROM = Client.BIRTHDAY_FIELD + "From";
    private static final String BY_BIRTHDATE_TO = Client.BIRTHDAY_FIELD + "To";
    private static final String BY_BIRTHDATE = Client.BIRTHDAY_FIELD;

    public ClientDataService() {
        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_NAME, term -> client -> client.getName().equals(term));
        predicates.put(BY_SECOND_NAME, term -> client -> client.getSecondName().equals(term));
        predicates.put(BY_PATRONYMIC, term -> client -> client.getPatronymic().equals(term));
        predicates.put(BY_BIRTHDATE_FROM, term -> {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return client -> client.getBirthday().compareTo(date) >= 0;
        });
        predicates.put(BY_BIRTHDATE_TO, term -> {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return client -> client.getBirthday().compareTo(date) <= 0;
        });
        predicates.put(BY_BIRTHDATE, term -> {
            LocalDate date = ServiceUtils.parseDate(term, ServiceUtils.dateFormatter);

            return client -> client.getBirthday().compareTo(date) == 0;
        });

        comparators.put(BY_ID, Comparator.comparing(Client::getId));
        comparators.put(BY_NAME, Comparator.comparing(Client::getName));
        comparators.put(BY_SECOND_NAME, Comparator.comparing(Client::getSecondName));
        comparators.put(BY_PATRONYMIC, Comparator.comparing(Client::getPatronymic));
        comparators.put(BY_BIRTHDATE, Comparator.comparing(Client::getBirthday));
    }

}
