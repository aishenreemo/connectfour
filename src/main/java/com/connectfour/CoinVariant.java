package com.connectfour;

import java.awt.Color;

public enum CoinVariant {
    NONE(0, Palette.FOREGROUND.getColor()),
    RED(1, Palette.RED.getColor()),
    BLUE(2, Palette.BLUE.getColor());

    public int type;
    public Color color;

    private CoinVariant(int type, Color color) {
        this.type = type;
        this.color = color;
    }

    public String getWinningMessage(){
        if (this.type == 0) {
            return "IT'S A DRAW!";
        }

        else if (this.type == 1) {
            return "RED WON!";
        }

        else { 
            return "BLUE WON!";
        }
    }

}

