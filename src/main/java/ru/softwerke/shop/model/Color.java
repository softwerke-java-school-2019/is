package ru.softwerke.shop.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Color {
    private static byte colorID = 0;

    private byte id;
    private String name;
    private Color rgb;

    public Color(String name, Color rgb) {
        this.id = colorID++;
        this.name = name;
        this.rgb = rgb;
    }

    public byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getRgb() {
        return rgb;
    }

    public void setRgb(Color rgb) {
        this.rgb = rgb;
    }
}
