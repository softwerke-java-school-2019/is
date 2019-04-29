package ru.softwerke.shop.model;

import java.math.BigDecimal;

public class BillItem {
    public static String DEVICE_ID_FIELD = "deviceId";
    public static String QUANTITY_FIELD = "quantity";
    public static String PRICE_FIELD = "price";

    private long deviceId;
    private long quantity;
    private BigDecimal price;

    public BillItem(long deviceId, long quantity, BigDecimal price) {
        this.deviceId = deviceId;
        this.quantity = quantity;
        this.price = price;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
