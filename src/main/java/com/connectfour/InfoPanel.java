package com.connectfour;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
        int width = (int) (windowSize.width * 0.20);

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

    public void setWinner(CoinVariant winner) {
        this.add(new EmptyGap(10, 10, 10, 10));
        this.add(new WinnerLabel(winner));
        this.add(new EmptyGap(10, 10, 10, 10));
        this.add(new RestartButton());
        this.revalidate();
        this.repaint();
    }

    public void setDraw(){

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
        this.setBackground(Palette.FOREGROUND.getColor());
        this.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g); 
    }
}

class PlayActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        App app = App.getInstance();
        InfoPanel info = InfoPanel.getInstance(null);
        GamePanel game = GamePanel.getInstance(null);

        app.state = App.PLAYING_STATE;
        game.clearRandomColors();
        game.currentTurn = CoinVariant.BLUE;

        info.removeAll();
        info.add(new ConnectFourLabel());
        info.add(new EmptyGap(20, 10, 10, 10));
        info.add(new MenuButton());
        info.add(new EmptyGap(10, 10, 10, 10));
        info.add(new QuitButton());
        info.revalidate();
        info.repaint();
    }
}

class ClearButton extends JButton {
    public ClearButton() {
        super("CLEAR");

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addActionListener(new ClearActionListener());
        this.setBackground(Palette.FOREGROUND.getColor());
        this.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g); 
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
        this.setBackground(Palette.FOREGROUND.getColor());
        this.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g); 
    }
}

class QuitActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0); 
    }
}

class MenuButton extends JButton {
    public MenuButton() {
        super("BACK TO MENU");
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addActionListener(new MenuActionListener());
        this.setBackground(Palette.FOREGROUND.getColor());
        this.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g); 
    }
}

class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        App app = App.getInstance();
        InfoPanel info = InfoPanel.getInstance(null);
        GamePanel game = GamePanel.getInstance(null);

        app.state = App.MENU_STATE;
        game.clearRandomColors();
        game.resetBoard();
        game.currentTurn = CoinVariant.NONE;

        info.removeAll();
        info.add(new ConnectFourLabel());
        info.add(new EmptyGap(10, 10, 10, 10));
        info.add(new PlayButton());
        info.add(new EmptyGap(10, 10, 10, 10));
        info.add(new ClearButton());
        info.add(new EmptyGap(10, 10, 10, 10));
        info.add(new QuitButton());
        info.revalidate();
        info.repaint();
    }
}

class WinnerLabel extends JLabel {
    public WinnerLabel(CoinVariant winner) {
        super(winner.getWinningMessage());
        this.setForeground(winner.color);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setFont(new Font("Arial", Font.PLAIN, 18));
        this.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
    }
}

class RestartButton extends JButton {
    public RestartButton() {
        super("RESTART");
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addActionListener(new MenuActionListener());
        this.setBackground(Palette.FOREGROUND.getColor());
        this.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g); 
    }
}

class RestartActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        App app = App.getInstance();
        InfoPanel info = InfoPanel.getInstance(null);
        GamePanel game = GamePanel.getInstance(null);

        app.state = App.PLAYING_STATE;
        game.clearRandomColors();
        game.resetBoard();
        game.currentTurn = CoinVariant.BLUE;

        info.removeAll();
        info.add(new ConnectFourLabel());
        info.add(new EmptyGap(20, 10, 10, 10));
        info.add(new MenuButton());
        info.add(new EmptyGap(10, 10, 10, 10));
        info.add(new QuitButton());
        info.revalidate();
        info.repaint();
    }
}
