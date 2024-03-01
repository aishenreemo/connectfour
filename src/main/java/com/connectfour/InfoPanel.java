package com.connectfour;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    private static InfoPanel instance;

    public InfoPanel(Dimension windowSize) {
        int width = (int) (windowSize.width * 0.30);

        this.setPreferredSize(new Dimension(width, windowSize.height));
        this.setBackground(Palette.BACKGROUND.getColor());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new ConnectFourLabel());
        this.add(new EmptyGap(10, 10, 10, 10));
        this.add(new PlayButton());
        this.add(new EmptyGap(10, 10, 10, 10));
        this.add(new ClearButton());
        this.add(new EmptyGap(10, 10, 10, 10));
        this.add(new QuitButton());
    }

    public static InfoPanel getInstance(Dimension windowSize) {
        if (InfoPanel.instance != null) {
            return InfoPanel.instance;
        }

        synchronized (GamePanel.class) {
            InfoPanel.instance = new InfoPanel(windowSize);
        }

        return InfoPanel.instance;
    }
}

class ConnectFourLabel extends JLabel {
    public ConnectFourLabel() {
        super("CONNECT FOUR");
        this.setForeground(Palette.FOREGROUND.getColor());
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setFont(new Font("Arial", Font.PLAIN, 18));
        this.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
    }
}

class EmptyGap extends JLabel {
    public EmptyGap(int top, int left, int bottom, int right) {
        this.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }
}

class PlayButton extends JButton {
    public PlayButton() {
        super("PLAY");
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addActionListener(new PlayActionListener());
    }
}

class PlayActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        App.getInstance().state = App.PLAYING_STATE;

        GamePanel game = GamePanel.getInstance(null);
        game.clearRandomColors();
    }
}

class ClearButton extends JButton {
    public ClearButton() {
        super("CLEAR");
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addActionListener(new ClearActionListener());
    }
}

class ClearActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        GamePanel game = GamePanel.getInstance(null);
        game.clearRandomColors();
    }
}

class QuitButton extends JButton {
    public QuitButton() {
        super("QUIT");
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addActionListener(new QuitActionListener());
    }
}

class QuitActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0); 
    }
}

