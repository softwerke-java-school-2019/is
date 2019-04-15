package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Item {
    private static AtomicLong currentID = new AtomicLong(0);

    private static final String ID_FILED = "id";

    @JsonProperty(ID_FILED)
    long id;

    Item() {
        this.id = currentID.getAndIncrement();
    }

    public long getId() {
        return id;
    }
}
