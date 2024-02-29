package com.connectfour;

import java.awt.Dimension;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    public InfoPanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.30);
        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
    }
}
