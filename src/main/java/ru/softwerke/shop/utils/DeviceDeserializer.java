package ru.softwerke.shop.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Device;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DeviceDeserializer extends JsonDeserializer<Device> {
    @Override
    public Device deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode json = oc.readTree(jsonParser);

        JsonNode name = json.get(Device.NAME_FIELD);
        if (name == null) {
            throw new RequestException("Required field \"" + Device.NAME_FIELD + "\" missed");
        }

        JsonNode company = json.get(Device.COMPANY_FIELD);
        if (company == null) {
            throw new RequestException("Required field \"" + Device.COMPANY_FIELD + "\" missed");
        }

        JsonNode price = json.get(Device.PRICE_FIELD);
        if (price == null) {
            throw new RequestException("Required field \"" + Device.PRICE_FIELD + "\" missed");
        }

        JsonNode color = json.get(Device.COLOR_FIELD);
        if (color == null) {
            throw new RequestException("Required field \"" + Device.COLOR_FIELD + "\" missed");
        }

        JsonNode type = json.get(Device.TYPE_FIELD);
        if (type == null) {
            throw new RequestException("Required field \"" + Device.TYPE_FIELD + "\" missed");
        }

        JsonNode released = json.get(Device.RELEASED_FIELD);
        if (released == null) {
            throw new RequestException("Required field \"" + Device.RELEASED_FIELD + "\" missed");
        }
        LocalDate date = Utils.parseDate(released.asText());

        return new Device(company.asText(), name.asText(), color.asText(), type.asText(), date,
                Utils.parseNumber(price.asText(), BigDecimal::new));
    }
}
