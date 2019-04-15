package ru.softwerke.shop.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.service.ServiceUtils;

import java.io.IOException;
import java.time.LocalDate;

public class DateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return ServiceUtils.parseDate(date);
        } catch (RequestException ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
