package ru.softwerke.shop.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Device {
    static long currentID = 0;

    long ID;
    String company, name;
    Date released;
    byte color, type;
    BigDecimal price;

    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public Device(String company, String name, byte color, byte type, Date released, BigDecimal price) {
        ID = ++currentID;
        this.company = company;
        this.name = name;
        this.released = released;
        this.color = color;
        this.type = type;
        this.price = price;
    }

    public long getID() {
        return ID;
    }

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public Date getReleased() {
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
                "id=" + ID +
                ", company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", released=" + df.format(released) +
                ", color=" + color +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
