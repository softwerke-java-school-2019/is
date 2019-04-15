package ru.softwerke.shop.service;

import org.eclipse.jetty.util.StringUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;

public class ServiceUtils {
    public static <T> T parseNumber(String numberStr, Function<String, T> converter) throws QueryParamsException {
        try {
            return converter.apply(numberStr);
        } catch (NumberFormatException ex ) {
            throw new QueryParamsException("Number expected, instead: " + numberStr);
        }
    }

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public static LocalDate parseDate(String dateStr) throws QueryParamsException {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeException ex) {
            throw new QueryParamsException("Invalid date format: " + dateStr);
        }
    }
}
