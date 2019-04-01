package ru.softwerke.shop.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateDeserializer extends JsonDeserializer<LocalDate> {
    public static DateTimeFormatter formetter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        return LocalDate.parse(date, formetter);
    }
}
