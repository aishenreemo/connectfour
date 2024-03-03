package com.connectfour;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private static GamePanel instance;

    public CoinSlot slots[][];
    public CoinVariant currentTurn;
    public int hoveredColumn;

    public final static int COLUMNS = 7;
    public final static int ROWS = 6;
    public int totalMoves;

    public GamePanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.80);

        this.totalMoves = 0;
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

        this.totalMoves += 1;

        if (this.totalMoves >= GamePanel.COLUMNS * GamePanel.ROWS){
            InfoPanel info = InfoPanel.getInstance(null);
            App app = App.getInstance();

            app.state = App.ENDING_STATE;
            info.setWinner(CoinVariant.NONE); 
            return;
        }

        this.currentTurn = this.currentTurn.type == CoinVariant.BLUE.type 
            ? CoinVariant.RED 
            : CoinVariant.BLUE;

        this.hoverColumn(column);
        this.repaint();
    }

    public boolean isWinner() {
        for (int i = 0; i < ROWS * (COLUMNS - 3); i++) {
            int col = i % (COLUMNS - 3);
            int row = i / (COLUMNS - 3);

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col + 1][row].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 2][row].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 3][row].variant.type
            ) {
                return true;
            }
        }

        for (int i = 0; i < (ROWS - 3) * COLUMNS; i++) {
            int col = i % COLUMNS;
            int row = i / COLUMNS;

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col][row + 1].variant.type &&
                this.slots[col][row].variant.type == this.slots[col][row + 2].variant.type &&
                this.slots[col][row].variant.type == this.slots[col][row + 3].variant.type
            ) {
                return true;
            }
        }

        for (int i = 0; i < (ROWS - 3) * (COLUMNS - 3); i++) {
            int col = i % (COLUMNS - 3);
            int row = i / (COLUMNS - 3);

            if (
                this.slots[col][row].variant.type != CoinVariant.NONE.type &&
                this.slots[col][row].variant.type == this.slots[col + 1][row + 1].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 2][row + 2].variant.type &&
                this.slots[col][row].variant.type == this.slots[col + 3][row + 3].variant.type
            ) {
                return true;
            }
        }

        for (int i = 0; i < (ROWS - 3) * (COLUMNS - 3); i++) {
            int col = i % (COLUMNS - 3);
            int row = i / (COLUMNS - 3) + 3;

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
        this.totalMoves = 0;
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
