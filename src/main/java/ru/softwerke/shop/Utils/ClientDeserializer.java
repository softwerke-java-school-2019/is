package ru.softwerke.shop.Utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Client;

import java.io.IOException;
import java.time.LocalDate;

public class ClientDeserializer extends JsonDeserializer<Client> {
    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode json = oc.readTree(jsonParser);

        JsonNode name = json.get(Client.NAME_FIELD);
        if (name == null) {
            throw new RequestException("Required field \"name\" missed");
        }
        JsonNode secondName = json.get(Client.SECONDNAME_FIELD);
        if (secondName == null) {
            throw new RequestException("Required field \"secondName\" missed");
        }
        JsonNode patronymic = json.get(Client.PATRONYMIC_FIELD);
        if (patronymic == null) {
            throw new RequestException("Required field \"patronymic\" missed");
        }
        JsonNode dateStr = json.get(Client.BIRTHDAY_FIELD);
        if (dateStr == null) {
            throw new RequestException("Required field \"birthday\" missed");
        }
        LocalDate date = ServiceUtils.parseDate(dateStr.asText(), ServiceUtils.dateFormatter);

        return new Client(secondName.asText(), name.asText(), patronymic.asText(), date);
    }
}
