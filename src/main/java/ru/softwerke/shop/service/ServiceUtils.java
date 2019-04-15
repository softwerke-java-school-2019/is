package ru.softwerke.shop.service;

import ru.softwerke.shop.controller.RequestException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class ServiceUtils {
    public static <T> T parseNumber(String numberStr, Function<String, T> converter) throws RequestException {
        try {
            return converter.apply(numberStr);
        } catch (NumberFormatException ex ) {
            throw new RequestException("Number expected, instead: " + numberStr);
        }
    }

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public static LocalDate parseDate(String dateStr) throws RequestException {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeException ex) {
            throw new RequestException("Invalid date format: " + dateStr);
        }
    }
}
