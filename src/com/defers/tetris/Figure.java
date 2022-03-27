package com.defers.tetris;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Figure extends GameObject implements Moveable{

    int[][] pattern;
    ElementCell[][] elementCells;
    boolean isActiveFigure = true;
    boolean isNewFigure;
    long prevTime = System.nanoTime();

    public Figure(int x, int y, int width, int height, Color color, int[][] pattern, boolean isNewFigure) {

        super(x, y, width, height, color);

        this.pattern = pattern;
        this.isNewFigure = isNewFigure;

        createCellFigure();
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(Graphics2D g2d) {

        for (ElementCell[] row : elementCells) {

            for (ElementCell elementCell : row) {

                if (elementCell != null) elementCell.render(g2d);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d, int x, int y) {

        int xCell;
        int yCell = y;

        for (ElementCell[] row : elementCells) {

            xCell = x;

            for (ElementCell elementCell : row) {

                if (elementCell != null) {
                    elementCell.render(g2d, xCell, yCell);
                }

                xCell += Settings.CELL_SIZE;
            }

            yCell += Settings.CELL_SIZE;
        }
    }
    public void createCellFigure() {

        elementCells = new ElementCell[pattern.length][pattern.length];

        for (int numRow = 0; numRow <= pattern.length-1 ; numRow++) {

            int[] row = pattern[numRow];

            for (int numColumn = 0; numColumn <= row.length-1; numColumn++) {

                int value = row[numColumn];

                if (value == 1) {

                    ElementCell elementCell = new ElementCell(0,0, Settings.CELL_SIZE,
                                                                Settings.CELL_SIZE, Settings.FIGURE_ACTIVE_CELL_COLOR, this);
                    elementCells[numRow][numColumn] = elementCell;
                }
            }
        }
    }

    public static HashMap<Integer, int[][]> getAllFigures() {

        HashMap<Integer, int[][]> allFigures = new HashMap<>();

        int[][] figureArray0 ={
                {0, 1, 0},
                {1, 1, 1},
                {0, 0, 0}
        };

        allFigures.put(0, figureArray0);

        int[][] figureArray1 = {
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0}
        };

        allFigures.put(1, figureArray1);

        int[][] figureArray2 = {
                {1, 0, 0},
                {1, 1, 1},
                {0, 0, 1}
        };

        allFigures.put(2, figureArray2);

        int[][] figureArray3 = {
                {1, 1},
                {1, 1}
        };

        allFigures.put(3, figureArray3);

        int[][] figureArray4 = {
                {1, 1, 0},
                {0, 1, 0},
                {0, 1, 0}
        };

        allFigures.put(4, figureArray4);

        int[][] figureArray5 = {
                {0, 1, 1},
                {0, 1, 0},
                {0, 1, 0}
        };

        allFigures.put(5, figureArray5);

        int[][] figureArray6 = {
                {1, 1, 0},
                {0, 1, 1},
                {0, 0, 0}
        };

        allFigures.put(6, figureArray6);

        int[][] figureArray7 = {
                {0, 1, 1},
                {1, 1, 0},
                {0, 0, 0}
        };

        allFigures.put(7, figureArray7);

        return allFigures;

    }

    public static int[][] getFigurePattern(int figureType) {

        return getAllFigures().get(figureType);
    }

    public void rotateFigure() {

        HashMap<ElementCell, Point> nextPointsMap = new HashMap<>();

        ElementCell[][] tempElementCells = new ElementCell[elementCells.length][elementCells.length];

        for (int numRow = 0; numRow <= elementCells.length - 1 ; numRow++) {

            ElementCell[] row = elementCells[numRow];

            int columnPlus = elementCells.length-1 - numRow;
            int rowPlus = -1*(numRow + 1);

            for (int numColumn = 0; numColumn <= row.length - 1; numColumn++) {

                ElementCell elementCell = row[numColumn];

                if (elementCell == null) {
                    rowPlus = rowPlus + 1;
                    columnPlus = columnPlus - 1;
                    continue;
                }

                Point currentPoint = elementCell.getPoint();
                int currentRow = currentPoint.row;
                int currentColumn = currentPoint.column;

                int nextRow = currentRow + 1 + rowPlus;
                int nextColumn = currentColumn + columnPlus;

                if (BoarderPoints.points.length-1 >= nextRow
                        && nextColumn >= 0
                        && BoarderPoints.points[nextRow].length - 1 >= nextColumn) {
                    Point nextPoint = BoarderPoints.findPointByCoordinates(nextRow, nextColumn);
                    nextPointsMap.put(elementCell, nextPoint);
                    tempElementCells[numRow + 1 + rowPlus][numColumn + columnPlus] = elementCell;
                }
                rowPlus = rowPlus + 1;
                columnPlus = columnPlus - 1;
            }
        }

        boolean isCollision = checkCollisions(nextPointsMap);

        if (isCollision) {
        }
        else {

            for (ElementCell cell : nextPointsMap.keySet()) {
                Point nextPoint = nextPointsMap.get(cell);
                Board.moveCell(cell, nextPoint);
                cell.setPoint(nextPoint);

                elementCells = tempElementCells;
            }
        }

    }

    private void moveCells(int directionY, int directionX) {

        HashMap<ElementCell, Point> nextPointsMap = new HashMap<>();

        for (int numRow = 0; numRow <= elementCells.length-1 ; numRow++) {

            ElementCell[] row = elementCells[numRow];

            for (int numColumn = 0; numColumn <= row.length-1; numColumn++) {

                ElementCell elementCell = row[numColumn];

                if (elementCell == null) continue;

                Point currentPoint = elementCell.getPoint();
                int currentRow = currentPoint.row;
                int currentColumn = currentPoint.column;

                int nextRow = currentRow + directionY;
                int nextColumn = currentColumn + directionX;

                if (BoarderPoints.points.length-1 >= nextRow
                        && nextColumn >= 0
                        && BoarderPoints.points[nextRow].length - 1 >= nextColumn) {

                    Point nextPoint = BoarderPoints.points[nextRow][nextColumn];
                    nextPointsMap.put(elementCell, nextPoint);
                } else {
                    nextPointsMap.put(elementCell, new Point(0,0, -1, -1));
                }
            }
        }

        boolean isCollision = checkCollisions(nextPointsMap);

        if (isCollision & directionY > 0) {

            deactivateFigure();

        }
        else if (isCollision) {
        }
        else {

            for (ElementCell cell : nextPointsMap.keySet()) {
                Point nextPoint = nextPointsMap.get(cell);

                Board.moveCell(cell, nextPoint);
                cell.setPoint(nextPoint);

            }
        }
    }

    private boolean checkCollisions(HashMap<ElementCell, Point> nextPointsMap) {

        boolean isCollision = false;
        ElementCell[][] board = Board.boarderCells;

        for (ElementCell cell : nextPointsMap.keySet()) {

            Point nextPoint = nextPointsMap.get(cell);

            if (nextPoint.row < 0 || nextPoint.column < 0 ) {
                isCollision = true;
            }
            else {
                ElementCell currentCellValue = board[nextPoint.row][nextPoint.column];

                if (currentCellValue != null && currentCellValue.figure != cell.figure) {
                    isCollision = true;
                }
            }
        }

        return isCollision;
    }

    public static void removeCell(ElementCell cell) {

        Figure figure = cell.figure;

        for (int numRow = 0; numRow <= figure.elementCells.length-1 ; numRow++) {

            ElementCell[] row = figure.elementCells[numRow];

            for (int numColumn = 0; numColumn <= row.length - 1; numColumn++) {

                ElementCell elementCell = row[numColumn];

                if (elementCell == cell) figure.elementCells[numRow][numColumn] = null;
            }
        }
    }

    private void checkFullRow() {

        ArrayList<ElementCell[]> fullRows = Board.getFullRows();

        if (fullRows.size() > 0) {
            deleteFullRows(fullRows);
        }
    }

    private void deleteFullRows(ArrayList<ElementCell[]> rows) {

        int numRowsDeleted = 0;
        int firstRowNum = -1;

        for (ElementCell[] row: rows) {

            for (ElementCell cell: row) {

                if (cell == null) continue;

                Point point = cell.getPoint();

                if (firstRowNum == -1) firstRowNum = point.row;

                Board.boarderCells[point.row][point.column] = null;
                Figure.removeCell(cell);
                GameApplication.gameObjects.remove(cell);

            }
            numRowsDeleted += 1;
        }

        rows.clear();

        int scorePlus = 0;

        if (numRowsDeleted == 1) {
            scorePlus = 200;
        } else if (numRowsDeleted == 2) {
            scorePlus = 600;
        } else if (numRowsDeleted == 3) {
            scorePlus = 1200;
        } else if (numRowsDeleted >= 4) {
            scorePlus = 1600;
        }

        Stats.score += scorePlus;

        moveRowsAfterDeleting(firstRowNum, numRowsDeleted);

    }

    private void moveRowsAfterDeleting(int firstRowNum, int numRowsDeleted) {

        for (int numRow=firstRowNum; numRow >- 0; numRow--) {

            ElementCell[] row = Board.boarderCells[numRow];

            for (int numColumn = 0; numColumn <= row.length-1; numColumn++) {

                ElementCell elementCell = row[numColumn];

                if (elementCell == null) continue;

                Point currentPoint = elementCell.getPoint();
                int currentRow = currentPoint.row;
                int currentColumn = currentPoint.column;
                int nextRow = currentRow + numRowsDeleted;

                Point nextPoint = BoarderPoints.findPointByCoordinates(nextRow, currentColumn);
                Board.moveCell(elementCell, nextPoint);
                elementCell.setPoint(nextPoint);
            }
        }
    }

    private void deactivateFigure() {

        isActiveFigure = false;
        for (ElementCell[] row : elementCells) {
            Arrays.stream(row)
                    .filter((cell) -> cell != null)
                    .forEach(cell -> cell.setColor(Settings.FIGURE_DEACTIVATE_CELL_COLOR));
        }

        checkFullRow();

        if (isNewFigure) GameApplication.loose = true;
    }

    @Override
    public void move() {

        if (!isActiveFigure) {
            return;
        }

        double deltaTime;

        long currentTime = System.nanoTime();
        deltaTime = (currentTime - prevTime) / 1.0E06f;

        if (deltaTime >= GameApplication.gameSpeed) {
            moveCells(1, 0);
            if (isNewFigure) isNewFigure = false;
            prevTime = currentTime;
        }

        if (KL.isKeypressed(KeyEvent.VK_DOWN)) {

            for (int i = 0; i < Settings.ACCELERATION ; i++) {
                moveCells(1, 0);

                if (isNewFigure) isNewFigure = false;
            }

        }
        else if (KL.isKeypressed(KeyEvent.VK_RIGHT)) {
            moveCells(0, 1);
        }
        else if (KL.isKeypressed(KeyEvent.VK_LEFT)) {
            moveCells(0, -1);
        }
        else if (KL.isKeyTyped(KeyEvent.VK_SPACE)) {
            rotateFigure();
        }
    }
}
