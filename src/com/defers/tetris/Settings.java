package com.defers.tetris;

import java.awt.*;

public class Settings {

    public static final int ROWS = 18;
    public static final int COLUMNS = 12;
    public static final Color BTN_COLOR = new Color(221, 205, 143);
    public static final Color FIGURE_CELL_COLOR = Color.black;
    public static final Color FIGURE_ACTIVE_CELL_COLOR = Color.blue;
    public static final Color FIGURE_DEACTIVATE_CELL_COLOR = Color.darkGray;
    public static final int SPEED = 800;
    public static final int INCREASE_SPEED_FOR_LEVEL = 100;
    public static final int SCORE_FOR_WIN = 10000;
    public static final int ACCELERATION = 2;
    public static final int CELL_SIZE = 40;
    public static final int MAIN_CANVAS_WIDTH = CELL_SIZE * COLUMNS;
    public static final int MAIN_CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int QUANTITY_NEXT_FIGURES = 2;

}
