package ru.softwerke.shop.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Color extends Item {

    private String name;
    private Color rgb;

    public Color(String name, Color rgb) {
        super();
        this.name = name;
        this.rgb = rgb;
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
