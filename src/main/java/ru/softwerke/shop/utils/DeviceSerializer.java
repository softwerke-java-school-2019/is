package ru.softwerke.shop.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.softwerke.shop.model.Device;

import java.io.IOException;

public class DeviceSerializer extends JsonSerializer<Device> {

    @Override
    public void serialize(Device device, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(Device.ID_FILED, device.getId());
        jsonGenerator.writeStringField(Device.COMPANY_FIELD, device.getCompany());
        jsonGenerator.writeStringField(Device.NAME_FIELD, device.getName());
        jsonGenerator.writeStringField(Device.TYPE_FIELD, device.getType());
        jsonGenerator.writeStringField(Device.COLOR_FIELD, device.getColor());
        jsonGenerator.writeNumberField(Device.COLOR_RGB_FIELD, device.getColorRGB());
        jsonGenerator.writeNumberField(Device.PRICE_FIELD, device.getPrice());
        jsonGenerator.writeStringField(Device.RELEASED_FIELD, device.getReleased().format(ServiceUtils.dateFormatter));
        jsonGenerator.writeEndObject();
    }
}
