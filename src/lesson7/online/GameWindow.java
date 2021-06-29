package lesson7.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private final int WIN_WIDTH = 500;
    private final int WIN_HEIGHT = 550;
    private final int WIN_POS_X = 450;
    private final int WIN_HJS_Y = 100;
    private SettingsWindow settingsWindow;
    private GameMap gameMap;


    GameWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocation(WIN_POS_X, WIN_HJS_Y);
        setTitle("The Game");
        setResizable(false);

        settingsWindow = new SettingsWindow(this);
        gameMap = new GameMap();



        JButton butStartGame = new JButton("Start New Game");
        butStartGame.addActionListener(e -> settingsWindow.setVisible(true));

        JButton butExitGame = new JButton("Exit");
        butExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));


        buttonPanel.add(butStartGame);
        buttonPanel.add(butExitGame);
        add(buttonPanel, BorderLayout.SOUTH);
        add(gameMap);

        setVisible(true);
    }

    void startNewGame (int gameMode, int fieldSizeX, int fieldSizeY, int winLength, Color colorMap){
    gameMap.start(gameMode, fieldSizeX, fieldSizeY, winLength, colorMap);
    }




}
