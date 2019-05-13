package ru.softwerke.shop.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.softwerke.shop.model.Client;

import java.io.IOException;

public class ClientSerializer extends JsonSerializer<Client> {
    @Override
    public void serialize(Client client, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(Client.ID_FILED, client.getId());
        jsonGenerator.writeStringField(Client.NAME_FIELD, client.getName());
        jsonGenerator.writeStringField(Client.SECONDNAME_FIELD, client.getSecondName());
        jsonGenerator.writeStringField(Client.PATRONYMIC_FIELD, client.getPatronymic());
        jsonGenerator.writeStringField(Client.BIRTHDAY_FIELD, client.getBirthday().format(ServiceUtils.dateFormatter));
        jsonGenerator.writeEndObject();
    }
}
