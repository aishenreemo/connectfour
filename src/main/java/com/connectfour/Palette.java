package com.connectfour;

import java.awt.Color;

public enum Palette {
    BACKGROUND("#0B0F10"),
    FOREGROUND("#C5C8C9"),
    BLACK("#131718"),
    RED("#DF5B61"),
    GREEN("#87C7A1"), 
    ORANGE("#DE8F78"),
    BLUE("#6791C9"),
    VIOLET("#BC83E3"),
    CYAN("#70B9CC"),
    WHITE("#C4C4C4");

    public static final Color array[] = {
        Palette.RED.getColor(),
        Palette.BLUE.getColor(),
        Palette.ORANGE.getColor(),
        Palette.CYAN.getColor(),
        Palette.VIOLET.getColor(),
        Palette.GREEN.getColor(),
    };

    private String hexcode;
    private Palette(String hexcode) {
        this.hexcode = hexcode;
    }

    public Color getColor() {
        return Color.decode(this.hexcode);
    }
}

