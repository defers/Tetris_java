package com.defers.tetris;

public class Main {

    public static void main(String[] args) {
        GameWindow gm = new GameWindow();
        GameApplication ga = new GameApplication(gm.canvasMain, gm.canvasMap);
        KL kl = new KL();
        gm.addKeyListener(kl);

        Thread mainThread = new Thread(ga);
        mainThread.start();

        gm.createButtons(ga);
        gm.setVisible(true);

    }
}
