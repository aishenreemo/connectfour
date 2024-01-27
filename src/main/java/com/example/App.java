package com.example;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Canvas Window with Hex Color");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawColoredCanvas(g);
            }
        };

        // Set the background color using a hex value
        canvas.setBackground(Color.decode(Pallete.CYAN)); // Replace with your desired hex color

        frame.add(canvas);

        frame.setVisible(true);
    }

    private static void drawColoredCanvas(Graphics g) {
        // Custom drawing code can be added here if needed
    }
}

class Pallete {
    public static final String BACKGROUND = "#0B0F10";
    public static final String FOREGROUND = "#C5C8C9";
    public static final String BLACK = "#131718";
    public static final String RED = "#DF5B61";
    public static final String GREEN = "#87C7A1"; 
    public static final String ORANGE = "#DE8F78";
    public static final String BLUE = "#6791C9";
    public static final String VIOLET = "#BC83E3";
    public static final String CYAN = "#70B9CC";
    public static final String WHITE = "#C4C4C4";
}
