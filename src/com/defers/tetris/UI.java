package com.defers.tetris;

import java.awt.*;

public class UI extends GameObject{

    public UI(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics2D g2d) {

        g2d.setColor(Color.BLACK);
        Font font = new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, (Settings.CELL_SIZE / 2));
        g2d.setFont(font);
        g2d.drawString("Score: " + Stats.score + " / " + Stats.scoreWorWin, (int) (Settings.CELL_SIZE / 1.5), (int) 10 * Settings.CELL_SIZE/2);
        g2d.drawString("Level: " + Stats.level, (int) (Settings.CELL_SIZE / 1.5), (int) 11 * Settings.CELL_SIZE / 2);
        g2d.drawString("Speed: " + Stats.gameSpeed, (int) (Settings.CELL_SIZE / 1.5), (int) 12 * Settings.CELL_SIZE / 2);

    }

    @Override
    public void render(Graphics2D g2d, int x, int y) {
    }
}
