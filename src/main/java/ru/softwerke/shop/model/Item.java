package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Item {
    protected static AtomicLong currentID = new AtomicLong(0);

    public static final String ID_FILED = "id";

    @JsonProperty(ID_FILED)
    protected long id;

    public Item() {
        this.id = currentID.getAndIncrement();
    }

    public long getId() {
        return id;
    }
}
