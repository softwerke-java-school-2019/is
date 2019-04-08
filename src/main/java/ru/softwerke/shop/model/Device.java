package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Device extends Item {

    public static final String COMPANY_FIELD = "company";
    public static final String NAME_FIELD = "name";
    public static final String RELEASED_FIELD = "released";
    public static final String COLOR_FIELD = "color";
    public static final String TYPE_FIELD = "type";
    public static final String PRICE_FIELD = "price";

    @JsonProperty(COMPANY_FIELD)
    private String company;

    @JsonProperty(NAME_FIELD)
    private String name;

    @JsonProperty(RELEASED_FIELD)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate released;

    @JsonProperty(COLOR_FIELD)
    private byte color;

    @JsonProperty(TYPE_FIELD)
    private byte type;

    @JsonProperty(PRICE_FIELD)
    private BigDecimal price;

    public Device(@JsonProperty(COMPANY_FIELD) String company,
                  @JsonProperty(NAME_FIELD) String name,
                  @JsonProperty(COLOR_FIELD) byte color,
                  @JsonProperty(TYPE_FIELD) byte type,
                  @JsonProperty(RELEASED_FIELD) LocalDate released,
                  @JsonProperty(PRICE_FIELD) BigDecimal price) {
        super();
        this.company = company;
        this.name = name;
        this.released = released;
        this.color = color;
        this.type = type;
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

    public byte getColor() {
        return color;
    }

    public byte getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
