package ru.softwerke.shop.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.softwerke.shop.service.QueryParamsException;
import ru.softwerke.shop.service.ServiceUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return ServiceUtils.parseDate(date);
        } catch (QueryParamsException ex) {
            throw new IOException(ex);
        }
    }
}
