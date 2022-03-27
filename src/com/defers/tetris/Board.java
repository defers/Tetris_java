package com.defers.tetris;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    Point[][] points;
    public static ElementCell[][] boarderCells = new ElementCell[Settings.ROWS][Settings.COLUMNS];

    public Board(Point[][] points) {

        this.points = points;

    }

    public static void moveCell(ElementCell cell, Point newPoint) {

        Point currentPoint = cell.getPoint();
        boarderCells[newPoint.row][newPoint.column] = cell;

        ElementCell oldBoardCellValue = boarderCells[currentPoint.row][currentPoint.column];
        if (oldBoardCellValue == cell) {
            boarderCells[currentPoint.row][currentPoint.column] = null;
        }

    }

    public static ArrayList<ElementCell[]> getFullRows() {

        ArrayList<ElementCell[]> fullRows = new ArrayList<>();

        for (int numRow = 0; numRow <= boarderCells.length-1 ; numRow++) {

            ElementCell[] row = boarderCells[numRow];
            boolean fullRow = true;

            for (int numColumn = 0; numColumn <= row.length-1; numColumn++) {

                ElementCell elementCell = row[numColumn];

                if (elementCell == null || elementCell.figure.isActiveFigure) {
                    fullRow = false;
                }
            }

            if (fullRow) {
                fullRows.add(Arrays.copyOf(row, row.length));
            }
        }

        return fullRows;
    }

    public void addFigureOnBoard(Figure figure) {

        ElementCell[][] elementCells = figure.elementCells;

        for (int numRow = 0; numRow <= elementCells.length-1 ; numRow++) {

            ElementCell[] row = elementCells[numRow];

            for (int numColumn = 0; numColumn <= row.length-1; numColumn++) {

                ElementCell elementCell = row[numColumn];
                if (elementCell != null) {
                    elementCell.setPoint(points[numRow][Settings.COLUMNS/2 + numColumn]);
                }

                boarderCells[numRow][Settings.COLUMNS/2 + numColumn] = elementCell;
            }
        }
    }
}
