package ru.softwerke.shop.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.softwerke.shop.Utils.BillDeserializer;
import ru.softwerke.shop.Utils.BillSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@JsonDeserialize(using = BillDeserializer.class)
@JsonSerialize(using = BillSerializer.class)
public class Bill extends Item {
    private static AtomicLong currentID = new AtomicLong(0);

    public static String CLIENT_ID_FIELD = "clientId";
    public static String PURCHASE_DATE_TIME_FIELD = "date";
    public static String TOTAL_PRICE_FIELD = "totalPrice";
    public static String ITEMS_FIELD = "items";

    private long clientId;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private LocalDateTime purchaseDateTime;
    private List<BillItem> items;

    public Bill (long clientId, LocalDateTime purchaseDateTime, List<BillItem> items) {
        this.id = currentID.getAndIncrement();
        this.clientId = clientId;
        this.purchaseDateTime = purchaseDateTime;
        this.items = items;
    }

    public void setTotalPrice() {
        for (BillItem item : items) {
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
    }

    public long getClientId() {
        return clientId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public List<BillItem> getItems() {
        return items;
    }
}
