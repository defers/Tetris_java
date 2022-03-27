package com.defers.tetris;

import java.awt.*;

public class ElementCell extends GameObject {

    Point point;
    Figure figure;

    public ElementCell(int x, int y, int width, int height, Color color, Figure figure) {
        super(x, y, width, height, color);

        this.figure = figure;
    }

    @Override
    public void render(Graphics2D g2d) {

        g2d.setColor(color);
        g2d.fillRect(point.x, point.y, width, height);

        g2d.setColor(Color.black);
        g2d.drawRect(point.x, point.y, width, height);

    }

    @Override
    public void render(Graphics2D g2d, int x , int y) {

        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.black);
        g2d.drawRect(x, y, width, height);

    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
