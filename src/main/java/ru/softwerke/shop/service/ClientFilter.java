package ru.softwerke.shop.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.Nullable;
import ru.softwerke.shop.model.Client;

import java.time.LocalDate;

public class ClientFilter {

    String name;
    String secondName;
    String patronymic;
    LocalDate dateFrom;
    LocalDate dateTo;

    public ClientFilter (@Nullable String name,
                         @Nullable String secondName,
                         @Nullable String patronymic,
                         @Nullable LocalDate dateFrom,
                         @Nullable LocalDate dateTo) {
        this.name = name;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
