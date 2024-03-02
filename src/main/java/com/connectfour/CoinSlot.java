package com.connectfour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.addActionListener(new CoinSlotActionListener());
    }

    private void setRandomColor() {
        this.randomColor = Palette.array[coinSlotRng.nextInt(Palette.array.length)];
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color currentTurnColor = GamePanel.getInstance(null).currentTurn.color;

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height);

        if (this.variant.type != CoinVariant.NONE.type) {
            g.setColor(this.variant.color);
            g.fillOval(0, 0, diameter - 1, diameter - 1);
        } else if (this.isHovered) {
            g.setColor(Palette.FOREGROUND.getColor());
            g.fillOval(0, 0, diameter - 1, diameter - 1);
        } else if (this.isHoveredOnMenu && this.randomColor == null) {
            this.setRandomColor();
        }

        if (this.randomColor != null) {
            g.setColor(this.randomColor);
            g.fillOval(0, 0, diameter - 1, diameter - 1);
        }

        g.setColor(currentTurnColor);
        g.drawOval(0, 0, diameter - 1, diameter - 1);
    }
}

class CoinSlotActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (App.getInstance().state == App.MENU_STATE) {
            return;
        };

        CoinSlot slot = (CoinSlot) e.getSource();
        GamePanel.getInstance(null).makeMoveColumn(slot.col);
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

        int appState = App.getInstance().state;
        if (appState == App.MENU_STATE && !slot.isHoveredOnMenu) {
            slot.isHoveredOnMenu = true;
            slot.randomColor = null;
            slot.repaint();
        } else if (appState == App.PLAYING_STATE) {
            GamePanel.getInstance(null).hoverColumn(slot.col);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}

