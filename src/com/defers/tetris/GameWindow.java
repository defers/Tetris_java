package com.defers.tetris;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GameWindow extends JFrame {

    public JPanel canvasMain;
    public JPanel canvasMap;
    public JPanel panelMenu;

    public GameWindow(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createPanels();
        this.setResizable(false);
        this.setUndecorated(true);
        this.setFocusable(true);
        this.pack();
    }

    private void createPanels() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.DARK_GRAY);

        FlowLayout flowLayout = new FlowLayout();
        mainPanel.setLayout(flowLayout);

        this.add(mainPanel);

        canvasMain = new JPanel();

        canvasMain.setPreferredSize(new Dimension(Settings.CELL_SIZE * Settings.COLUMNS,
                Settings.CELL_SIZE * Settings.ROWS));
        canvasMain.setBackground(Color.lightGray);
        canvasMain.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        mainPanel.add(canvasMain);

        JPanel rightPanel = new JPanel();

        BoxLayout boxLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
        rightPanel.setLayout(boxLayout);

        rightPanel.setPreferredSize(new Dimension(Settings.CELL_SIZE * 6,
                Settings.CELL_SIZE * Settings.ROWS));
        rightPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        rightPanel.setBackground(Color.ORANGE);
        mainPanel.add(rightPanel);

        canvasMap = new JPanel();
        canvasMap.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        canvasMap.setBackground(Color.ORANGE);
        rightPanel.add(canvasMap);

        panelMenu = new JPanel();

        GridLayout gridLayout = new GridLayout(8, 1);

        panelMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelMenu.setBackground(Color.ORANGE);
        panelMenu.setLayout(gridLayout);

        rightPanel.add(panelMenu);

    }

    public void createButtons(GameApplication ga) {

        Color btnColor = Settings.BTN_COLOR;

        JButton btnStart = new JButton("New game");
        btnStart.setBackground(btnColor);
        btnStart.addActionListener((e) -> {
            Stats.gameSpeed = Settings.SPEED;
            Stats.score = 0;
            ga.loose = true;
            ga.init();
            this.requestFocus();
        });

        panelMenu.add(btnStart);

        JButton btnPause = new JButton("Pause");
        btnPause.setBackground(btnColor);
        btnPause.addActionListener((e) -> {
            if (!ga.paused) {
                ga.paused = true;
            }
            else {
                ga.paused = false;
            }

            this.requestFocus();
        });

        panelMenu.add(btnPause);

        JButton btnExit = new JButton("Exit");
        btnExit.setBackground(btnColor);
        btnExit.addActionListener((e) -> System.exit(0));

        panelMenu.add(btnExit);
    }

}
