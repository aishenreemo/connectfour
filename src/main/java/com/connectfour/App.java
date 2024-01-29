package com.connectfour;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JFrame {
    public App() {
        this.setDefaultCloseOperation(App.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        this.setLayout(new BorderLayout(1, 1));
        this.setBackground(Palette.FOREGROUND.getColor());

        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.add(new GamePanel(this.getSize()), BorderLayout.CENTER);
        this.add(new InfoPanel(this.getSize()), BorderLayout.EAST);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}

class GamePanel extends JPanel {
    public GamePanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.70);

        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
    }
}

class InfoPanel extends JPanel {
    public InfoPanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.30);
        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
    }
}

enum Palette {
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

    private String hexcode;
    private Palette(String hexcode) {
        this.hexcode = hexcode;
    }

    public Color getColor() {
        return Color.decode(this.hexcode);
    }
}
