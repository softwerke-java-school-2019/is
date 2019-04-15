package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Device extends Item {

    private static final String COMPANY_FIELD = "company";
    private static final String NAME_FIELD = "name";
    private static final String RELEASED_FIELD = "released";
    private static final String COLOR_FIELD = "color";
    private static final String TYPE_FIELD = "type";
    private static final String PRICE_FIELD = "price";

    @JsonProperty(COMPANY_FIELD)
    private String company;

    @JsonProperty(NAME_FIELD)
    private String name;

    @JsonProperty(RELEASED_FIELD)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate released;

    @JsonProperty(COLOR_FIELD)
    private String color;

    @JsonProperty(TYPE_FIELD)
    private String type;

    @JsonProperty(PRICE_FIELD)
    private BigDecimal price;

    public Device(@JsonProperty(COMPANY_FIELD) String company,
                  @JsonProperty(NAME_FIELD) String name,
                  @JsonProperty(COLOR_FIELD) String color,
                  @JsonProperty(TYPE_FIELD) String type,
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

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
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
