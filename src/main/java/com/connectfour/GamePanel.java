package com.connectfour;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private static GamePanel instance;

    public CoinSlot slots[][];
    public int hoveredColumn;

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
            CoinSlot slot = this.slots[this.hoveredColumn][i];
            slot.isHovered = false;
            slot.repaint();
        }

        if (column == -1) {
            return;
        }

        for (int i = 0; i < 6; i++) {
            CoinSlot slot = this.slots[column][i];
            slot.isHovered = true;
            slot.repaint();
        }

        this.hoveredColumn = column;
    }

    public void clearRandomColors() {
        for (int i = 0; i < 7 * 6; i++) {
            CoinSlot slot = this.slots[i % 7][i / 7];
            slot.randomColor = null;
            slot.isHoveredOnMenu = false;
            slot.isHovered = false;
            slot.repaint();
        }
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

