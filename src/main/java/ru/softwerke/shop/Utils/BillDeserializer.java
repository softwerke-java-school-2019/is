package ru.softwerke.shop.Utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.model.BillItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BillDeserializer extends JsonDeserializer<Bill> {
    @Override
    public Bill deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode json = oc.readTree(jsonParser);

        JsonNode clientIdNode = json.get(Bill.CLIENT_ID_FIELD);
        if (clientIdNode == null) {
            throw new RequestException("Required field\"" + Bill.CLIENT_ID_FIELD + "\" missed");
        }

        JsonNode dateNode = json.get(Bill.PURCHASE_DATE_TIME_FIELD);
        if (dateNode == null) {
            throw new RequestException("Required field \"" + Bill.PURCHASE_DATE_TIME_FIELD + "\" missed");
        }

        JsonNode jsonItemsNode = json.get(Bill.ITEMS_FIELD);
        if (jsonItemsNode == null) {
            throw new RequestException("Required field \"" + Bill.ITEMS_FIELD + "\" missed");
        }

        List<BillItem> items = new CopyOnWriteArrayList<>();

        for (JsonNode jsonItem : jsonItemsNode) {
            JsonNode deviceIdNode = jsonItem.get(BillItem.DEVICE_ID_FIELD);
            if (deviceIdNode == null) {
                throw new RequestException("Required field \"" + BillItem.DEVICE_ID_FIELD + "\" missed");
            }

            JsonNode quantityNode = jsonItem.get(BillItem.QUANTITY_FIELD);
            if (quantityNode == null) {
                throw new RequestException("Required field \"" + BillItem.QUANTITY_FIELD + "\" missed");
            }

            items.add(new BillItem(ServiceUtils.parseNumber(deviceIdNode.asText(), Long::parseLong),
                    ServiceUtils.parseNumber(quantityNode.asText(), Long::parseLong)));
        }

        long clientId = ServiceUtils.parseNumber(clientIdNode.asText(), Long::parseLong);

        return new Bill(clientId, ServiceUtils.parseDateTime(dateNode.asText(), ServiceUtils.dateWithTimeFormatter), items);
    }
}
