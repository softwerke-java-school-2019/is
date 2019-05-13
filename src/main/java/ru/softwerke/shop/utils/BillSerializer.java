package ru.softwerke.shop.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.model.BillItem;

import java.io.IOException;

public class BillSerializer extends JsonSerializer<Bill> {
    @Override
    public void serialize(Bill bill, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(Bill.ID_FILED, bill.getId());
        jsonGenerator.writeNumberField(Bill.CLIENT_ID_FIELD, bill.getClientId());
        jsonGenerator.writeNumberField(Bill.TOTAL_PRICE_FIELD, bill.getTotalPrice());
        jsonGenerator.writeStringField(Bill.PURCHASE_DATE_TIME_FIELD, bill.getPurchaseDateTime()
                .format(ServiceUtils.dateWithTimeFormatter));
        jsonGenerator.writeArrayFieldStart(Bill.ITEMS_FIELD);
        for (BillItem item : bill.getItems()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField(BillItem.DEVICE_ID_FIELD, item.getDeviceId());
            jsonGenerator.writeNumberField(BillItem.PRICE_FIELD, item.getPrice());
            jsonGenerator.writeNumberField(BillItem.QUANTITY_FIELD, item.getQuantity());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();

    }
}
