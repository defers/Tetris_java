package com.defers.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL implements KeyListener {

    public static boolean[] pressedKeys = new boolean[155];
    private static int typedKey;

    @Override
    public void keyTyped(KeyEvent e) {
        typedKey = e.getKeyChar();
        System.out.println("space");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
    }

    public static boolean isKeypressed(int key) {
        return pressedKeys[key];
    }

    public static boolean isKeyTyped(int key) {

        boolean isTyped = key == typedKey;
        typedKey = 0;

        return isTyped;
    }
}
