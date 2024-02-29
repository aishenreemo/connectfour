package com.connectfour;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public CoinSlot slots[][];
    public int hoveredColumn;

    private static GamePanel instance;

    public GamePanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.80);
        int columns = 7;
        int rows = 6;

        GridLayout grid = new GridLayout(rows, columns, 5, 5);
        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
        this.setLayout(grid);
        this.setBorder(BorderFactory.createEmptyBorder(65, 25, 65, 25));

        this.slots = new CoinSlot[7][6];

        for (int i = 0; i < columns * rows; i++) {
            int col = i % columns;
            int row = i / columns;

            this.slots[col][row] = new CoinSlot(col, row);
            this.add(this.slots[col][row]);
        }
    }

    public void hoverColumn(int column) {
        for (int i = 0; i < 6; i++) {
            this.slots[this.hoveredColumn][i].isHovered = false;
            this.slots[this.hoveredColumn][i].repaint();
        }

        if (column == -1) {
            return;
        }

        for (int i = 0; i < 6; i++) {
            this.slots[column][i].isHovered = true;
            this.slots[column][i].repaint();
        }

        this.hoveredColumn = column;
    }

    public static GamePanel getInstance(Dimension windowSize) {
        if (GamePanel.instance != null) {
            return GamePanel.instance;
        }

        synchronized (GamePanel.class) {
            GamePanel.instance = new GamePanel(windowSize);
        }

        return GamePanel.instance;
    }
}

class CoinSlot extends JButton {
    public boolean isHovered;
    public CoinVariant variant;
    public int col;
    public int row;

    public CoinSlot(int col, int row) {
        super();
        this.isHovered = false;
        this.variant = CoinVariant.NONE;
        this.col = col;
        this.row = row;

        this.setBackground(Palette.BACKGROUND.getColor());
        this.setForeground(Palette.FOREGROUND.getColor());
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.addMouseMotionListener(new CoinSlotListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Palette.BLUE.getColor());
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height);

        if (this.isHovered) {
            g.fillOval(0, 0, diameter - 1, diameter - 1);
            return;
        }

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

class CoinSlotListener implements MouseMotionListener {
    @Override
    public void mouseMoved(MouseEvent e) {
        CoinSlot slot = (CoinSlot) e.getSource();

        GamePanel
            .getInstance(null)
            .hoverColumn(!slot.isHovered || slot.contains(e.getPoint()) ? slot.col : -1);

        slot.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

enum CoinVariant {
    NONE(0),
    RED(1),
    BLUE(2);

    public int type;
    private CoinVariant(int type) {
        this.type = type;
    }
}
