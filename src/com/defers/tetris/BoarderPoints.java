package com.defers.tetris;

public class BoarderPoints {

    public static Point[][] points = new Point[Settings.ROWS][Settings.COLUMNS];

    public BoarderPoints() {
    }

    public void createPointsArray(){

        int cellSize = Settings.CELL_SIZE;

        int x = 0;
        int y = 0;

        for (int row = 0; row <= Settings.ROWS - 1; row++) {

            for (int column = 0; column <= Settings.COLUMNS - 1; column++) {

                points[row][column] = new Point(x, y, row, column);

                x += cellSize;
            }

            y += cellSize;
            x = 0;
        }
    }

    public static Point findPointByCoordinates(int row, int column) {

        if (row > points.length || column > points[row].length) {
            return new Point(0,0, -1, -1);
        }

        return points[row][column];
    }

}
