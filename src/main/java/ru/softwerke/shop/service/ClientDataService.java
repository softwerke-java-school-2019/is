package ru.softwerke.shop.service;

import org.eclipse.jetty.util.StringUtil;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.utils.Utils;
import ru.softwerke.shop.model.Client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientDataService extends DataService<Client> {

    public static final String BY_NAME = Client.NAME_FIELD;
    public static final String BY_SECOND_NAME = Client.SECONDNAME_FIELD;
    public static final String BY_PATRONYMIC = Client.PATRONYMIC_FIELD;
    public static final String BY_BIRTHDATE_FROM = Client.BIRTHDAY_FIELD + "From";
    public static final String BY_BIRTHDATE_TO = Client.BIRTHDAY_FIELD + "To";
    public static final String BY_BIRTHDATE = Client.BIRTHDAY_FIELD;

    public ClientDataService() {
        predicates = new ConcurrentHashMap<>();
        comparators = new ConcurrentHashMap<>();
        items = new CopyOnWriteArrayList<>();

        predicates.put(BY_NAME, term -> client -> client.getName().equals(term));
        predicates.put(BY_SECOND_NAME, term -> client -> client.getSecondName().equals(term));
        predicates.put(BY_PATRONYMIC, term -> client -> client.getPatronymic().equals(term));
        predicates.put(BY_BIRTHDATE_FROM, term -> {
            LocalDate date = Utils.parseDate(term);

            return client -> client.getBirthday().compareTo(date) >= 0;
        });
        predicates.put(BY_BIRTHDATE_TO, term -> {
            LocalDate date = Utils.parseDate(term);

            return client -> client.getBirthday().compareTo(date) <= 0;
        });
        predicates.put(BY_BIRTHDATE, term -> {
            LocalDate date = Utils.parseDate(term);

            return client -> client.getBirthday().compareTo(date) == 0;
        });

        comparators.put(BY_ID, Comparator.comparing(Client::getId));
        comparators.put(BY_NAME, Comparator.comparing(Client::getName));
        comparators.put(BY_SECOND_NAME, Comparator.comparing(Client::getSecondName));
        comparators.put(BY_PATRONYMIC, Comparator.comparing(Client::getPatronymic));
        comparators.put(BY_BIRTHDATE, Comparator.comparing(Client::getBirthday));
    }

    @Override
    public void checkItem(Client item) throws IOException {
        if (StringUtil.isBlank(item.getName())) {
            throw new RequestException("name field is incorrect");
        }

        if (StringUtil.isBlank(item.getSecondName())) {
            throw new RequestException("secondName field is incorrect");
        }

        if (StringUtil.isBlank(item.getPatronymic())) {
            throw new RequestException("patronymic field is incorrect");
        }
    }
}
