package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Item {


    public static final String ID_FILED = "id";

    long id;

    public long getId() {
        return id;
    }
}
