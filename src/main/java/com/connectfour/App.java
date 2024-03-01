package com.connectfour;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class App extends JFrame {
    private static App instance;

    public final static int PLAYING_STATE = 0;
    public final static int MENU_STATE = 1;

    public int state;

    public App() {
        this.state = App.MENU_STATE;

        this.setDefaultCloseOperation(App.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        this.setLayout(new BorderLayout(1, 1));
        this.setBackground(Palette.FOREGROUND.getColor());

        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.add(GamePanel.getInstance(this.getSize()), BorderLayout.CENTER);
        this.add(InfoPanel.getInstance(this.getSize()), BorderLayout.EAST);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        App.getInstance();
    }

    public static App getInstance() {
        if (App.instance != null) {
            return App.instance;
        }

        synchronized (App.class) {
            App.instance = new App();
        }

        return App.instance;
    }
}

