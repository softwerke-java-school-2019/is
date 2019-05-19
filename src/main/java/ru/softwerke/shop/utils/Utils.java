package ru.softwerke.shop.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import ru.softwerke.shop.controller.RequestException;

import java.io.IOException;
import java.io.StringWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Utils {
    public static <T> T parseNumber(String numberStr, Function<String, T> converter) throws RequestException {
        try {
            return converter.apply(numberStr);
        } catch (NumberFormatException ex ) {
            throw new RequestException("Number expected, instead: " + numberStr);
        }
    }

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter dateWithTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


    public static LocalDate parseDate(String dateStr) throws RequestException {
        try {
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeException ex) {
            throw new RequestException("Invalid date format: " + dateStr);
        }
    }

    public static LocalDateTime parseDateTime(String dateStr) throws RequestException {
        try {
            return LocalDateTime.parse(dateStr, dateWithTimeFormatter);
        } catch (DateTimeException ex) {
            throw new RequestException("Invalid date format: " + dateStr);
        }
    }

    public static StringWriter exceptionToJson(String type, String message) {
        try {
            JsonFactory factory = new JsonFactory();
            StringWriter w = new StringWriter();
            JsonGenerator json = factory.createGenerator(w);
            json.writeStartObject();
            json.writeStringField("type", type);
            json.writeStringField("message", message);
            json.writeEndObject();
            json.close();
            return w;
        } catch (IOException ex) {
            // exception never thrown
            return null;
        }
    }

}
