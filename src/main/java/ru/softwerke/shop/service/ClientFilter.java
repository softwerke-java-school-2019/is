package ru.softwerke.shop.service;

import java.time.LocalDate;

public class ClientFilter {

    private String name;
    private String secondName;
    private String patronymic;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public ClientFilter (String name,
                         String secondName,
                         String patronymic,
                         LocalDate dateFrom,
                         LocalDate dateTo) {
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
