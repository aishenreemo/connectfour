package com.connectfour;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GamePanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.80);
        int columns = 7;
        int rows = 6;

        GridLayout grid = new GridLayout(rows, columns, 5, 5);
        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
        this.setLayout(grid);
        this.setBorder(BorderFactory.createEmptyBorder(65, 25, 65, 25));

        for (int i = 0; i < rows * columns; i++){
            CoinSlot slot = new CoinSlot();
            this.add(slot);
        }
    }
}

class CoinSlot extends JButton {
    public CoinSlot() {
        super();
        this.setBackground(Palette.BACKGROUND.getColor());
        this.setForeground(Palette.FOREGROUND.getColor());
        this.setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Palette.BLUE.getColor());
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height);
        g.drawOval(0, 0, diameter - 1, diameter - 1);
    }

    @Override
    public boolean contains(Point p) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        double dx = p.getX() - width / 2.0;
        double dy = p.getY() - height / 2.0;
        return dx * dx + dy * dy <= radius * radius;
    }
}

