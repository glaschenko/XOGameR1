package lesson7.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsColor extends JFrame {

    private final int COLOR_WIDTH = 350;
    private final int COLOR_HEIGHT = 300;




    private SettingsWindow settingsWindow;

    SettingsColor(SettingsWindow settingsWindow){
        this.settingsWindow = settingsWindow;
        setSize(COLOR_WIDTH, COLOR_HEIGHT);

        Rectangle settingsWindowBounds = settingsWindow.getBounds();
        int PosX = (int) settingsWindowBounds.getX();
        int PosY = (int) settingsWindowBounds.getY();

        setLocation(PosX, PosY);
        setResizable(false);
        setTitle("Field Color");


        JButton butOk = new JButton("Ok");
        butOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });
        add(butOk, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));



        JButton butWhite = new JButton();
        butWhite.setBackground(Color.WHITE);
        butWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Color colorId = Color.WHITE;
                settingsWindow.colorButton(colorId);
                butOk.setBackground(colorId);

            }
        });

        JButton butBlue = new JButton();
        butBlue.setBackground(Color.BLUE);
        butBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color colorId = Color.BLUE;
                settingsWindow.colorButton(colorId);
                butOk.setBackground(colorId);
            }
        });
        JButton butGreen = new JButton();
        butGreen.setBackground(Color.GREEN);
        butGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color colorId = Color.GREEN;
                settingsWindow.colorButton(colorId);
                butOk.setBackground(colorId);
            }
        });

        JButton butRed = new JButton();
        butRed.setBackground(Color.RED);
        butRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color colorId = Color.RED;
                settingsWindow.colorButton(colorId);
                butOk.setBackground(colorId);
            }
        });
        buttonPanel.add(butWhite);
        buttonPanel.add(butBlue);
        buttonPanel.add(butGreen);
        buttonPanel.add(butRed);

        add(buttonPanel);



    }



}
