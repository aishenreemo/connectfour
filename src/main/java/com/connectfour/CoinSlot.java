package com.connectfour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JButton;

public class CoinSlot extends JButton {
    public boolean isHovered;
    public boolean isHoveredOnMenu;
    public CoinVariant variant;

    public Color randomColor;

    public int col;
    public int row;

    private static Random coinSlotRng = new Random();

    public CoinSlot(int col, int row) {
        super();
        this.isHovered = false;
        this.isHoveredOnMenu = false;
        this.variant = CoinVariant.NONE;
        this.col = col;
        this.row = row;

        this.setBackground(Palette.BACKGROUND.getColor());
        this.setForeground(Palette.FOREGROUND.getColor());
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.addMouseMotionListener(new CoinSlotMouseMotionListener());
    }

    private void setRandomColor() {
        this.randomColor = Palette.array[coinSlotRng.nextInt(Palette.array.length)];
    }

    @Override
    public boolean contains(Point p) {
        int width = this.getWidth();
        int height = this.getHeight();
        int radius = Math.min(width, height) / 2;
        double dx = p.getX() - width / 2.0;
        double dy = p.getY() - height / 2.0;
        return dx * dx + dy * dy <= radius * radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height);

        if (this.isHovered) {
            g.setColor(Palette.BLUE.getColor());
            g.fillOval(0, 0, diameter - 1, diameter - 1);
        } else if (this.isHoveredOnMenu && this.randomColor == null) {
            this.setRandomColor();
        }

        if (this.randomColor != null) {
            g.setColor(this.randomColor);
            g.fillOval(0, 0, diameter - 1, diameter - 1);
        }

        g.setColor(Palette.FOREGROUND.getColor());
        g.drawOval(0, 0, diameter - 1, diameter - 1);
    }
}

class CoinSlotMouseMotionListener implements MouseMotionListener {
    @Override
    public void mouseMoved(MouseEvent e) {
        CoinSlot slot = (CoinSlot) e.getSource();

        if (!slot.contains(e.getPoint())) {
            GamePanel.getInstance(null).hoverColumn(-1);
            slot.isHoveredOnMenu = false;
            return;
        }

        if (App.getInstance().state == App.MENU_STATE && !slot.isHoveredOnMenu) {
            slot.isHoveredOnMenu = true;
            slot.randomColor = null;
            slot.repaint();
        } else if (App.getInstance().state == App.PLAYING_STATE) {
            GamePanel.getInstance(null).hoverColumn(slot.col);
        }

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

