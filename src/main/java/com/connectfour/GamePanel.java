package com.connectfour;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private static GamePanel instance;

    public CoinSlot slots[][];
    public CoinVariant currentTurn;
    public int hoveredColumn;

    public final static int COLUMNS = 7;
    public final static int ROWS = 6;

    public GamePanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.80);

        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
        this.setLayout(new GridLayout(ROWS, COLUMNS, 5, 5));
        this.setBorder(BorderFactory.createEmptyBorder(65, 25, 65, 25));

        this.slots = new CoinSlot[COLUMNS][ROWS];
        this.resetBoard();

        this.currentTurn = CoinVariant.NONE;
    }

    public void hoverColumn(int column) {
        for (int i = 0; i < ROWS; i++) {
            CoinSlot slot = this.slots[this.hoveredColumn][i];

            if (!slot.isHovered) {
                continue;
            }

            slot.isHovered = false;
            slot.repaint();
        }

        if (column == -1) {
            return;
        }

        for (int i = ROWS - 1; i >= 0; i--) {
            CoinSlot slot = this.slots[column][i];

            if (slot.variant.type != CoinVariant.NONE.type) {
                continue;
            }

            slot.isHovered = true;
            slot.repaint();
            break;
        }

        this.hoveredColumn = column;
    }

    public void makeMoveColumn(int column) {
        boolean isFull = true;

        for (int i = 5; i >= 0; i--) {
            CoinSlot slot = this.slots[column][i];

            if (slot.variant.type != CoinVariant.NONE.type) {
                continue;
            }

            isFull = false;
            break;
        }

        if (isFull) {
            return;
        }

        for (int i = 5; i >= 0; i--) {
            CoinSlot slot = this.slots[column][i];

            if (slot.variant.type != CoinVariant.NONE.type) {
                continue;
            }

            slot.variant = this.currentTurn;
            slot.isHovered = false;
            slot.randomColor = null;
            slot.repaint();
            break;
        }

        if (this.isWinner()) {
            InfoPanel info = InfoPanel.getInstance(null);
            App app = App.getInstance();

            app.state = App.ENDING_STATE;
            info.setWinner(this.currentTurn);
            return;
        }

        this.currentTurn = this.currentTurn.type == CoinVariant.BLUE.type 
            ? CoinVariant.RED 
            : CoinVariant.BLUE;

        this.hoverColumn(column);
        this.repaint();
    }

    public boolean isWinner() {
        int rows = this.slots[0].length;
        int cols = this.slots.length;

        for (int i = 0; i < rows * (cols - 3); i++) {
            int col = i % (cols - 3);
            int row = i / (cols - 3);

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col + 1][row].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 2][row].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 3][row].variant.type
            ) {
                return true;
            }
        }

        for (int i = 0; i < (rows - 3) * cols; i++) {
            int col = i % cols;
            int row = i / cols;

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col][row + 1].variant.type &&
                this.slots[col][row].variant.type == this.slots[col][row + 2].variant.type &&
                this.slots[col][row].variant.type == this.slots[col][row + 3].variant.type
            ) {
                return true;
            }
        }

        for (int i = 0; i < (rows - 3) * (cols - 3); i++) {
            int col = i % (cols - 3);
            int row = i / (cols - 3);

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col + 1][row + 1].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 2][row + 2].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 3][row + 3].variant.type
            ) {
                return true;
            }
        }

        for (int i = 0; i < (rows - 3) * (cols - 3); i++) {
            int col = i % (cols - 3);
            int row = i / (cols - 3) + 3;

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col + 1][row - 1].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 2][row - 2].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 3][row - 3].variant.type
            ) {
                return true;
            }
        }

        return false;
    }

    public void clearRandomColors() {
        for (int i = 0; i < COLUMNS * ROWS; i++) {
            CoinSlot slot = this.slots[i % COLUMNS][i / COLUMNS];
            slot.randomColor = null;
            slot.isHoveredOnMenu = false;
            slot.isHovered = false;
            slot.repaint();
        }
    }

    public void resetBoard() {
        this.removeAll();
        for (int i = 0; i < COLUMNS * ROWS; i++) {
            int col = i % COLUMNS;
            int row = i / COLUMNS;

            this.slots[col][row] = new CoinSlot(col, row);
            this.add(this.slots[col][row]);
        }

        this.revalidate();
        this.repaint();
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
