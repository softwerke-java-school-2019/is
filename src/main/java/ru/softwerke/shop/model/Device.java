package ru.softwerke.shop.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.softwerke.shop.utils.DeviceDeserializer;
import ru.softwerke.shop.utils.DeviceSerializer;
import ru.softwerke.shop.service.DeviceDataService;
import ru.softwerke.shop.utils.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@JsonSerialize(using = DeviceSerializer.class)
@JsonDeserialize(using = DeviceDeserializer.class)
public class Device extends Item {
    private static AtomicLong currentID = new AtomicLong(0);

    public static final String COMPANY_FIELD = "manufacturer";
    public static final String NAME_FIELD = "modelName";
    public static final String RELEASED_FIELD = "manufactureDate";
    public static final String COLOR_FIELD = "colorName";
    public static final String COLOR_HEX_FIELD = "colorHex";
    public static final String TYPE_FIELD = "deviceType";
    public static final String PRICE_FIELD = "price";

    private String company;

    private String name;

    private LocalDate released;

    private String color;

    private String type;

    private BigDecimal price;

    public Device(String company,
                  String name,
                  String color,
                  String type,
                  LocalDate released,
                  BigDecimal price) {
        this.id = currentID.getAndIncrement();
        this.company = company;
        this.name = name;
        this.released = released;
        this.color = color.toLowerCase();
        this.type = type.toLowerCase();
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleased() {
        return released;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getColorHex() {
        return Utils.toHexString(DeviceDataService.colors.get(color));
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Device other = (Device) o;
        return id == other.id &&
                name.equals(other.name) &&
                company.equals(other.company) &&
                color.equals(other.color) &&
                type.equals(other.type) &&
                price.equals(other.price) &&
                released.equals(other.released);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, company, released, color, type, price);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", released=" + released +
                ", color=" + color +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
