package com.defers.tetris;

import java.awt.*;

public class GameObject {

    public int x;
    public int y;
    public int width;
    public int height;
    public Color color;

    public GameObject(int x, int y, int width, int height, Color color) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

    }

    public void update() {

    }

    public void render(Graphics2D g2d) {

        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

    }

    public void render(Graphics2D g2d, int x , int y) {

        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

    }

}
