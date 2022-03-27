package com.defers.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameApplication implements Runnable {

    public static boolean gameRunning = false;
    public static boolean paused = false;
    public static boolean win;
    public int level = 1;
    public static boolean loose;
    public static int gameSpeed = Stats.gameSpeed;
    public UI ui;
    JPanel canvasMain;
    JPanel canvasMap;
    Graphics g;
    Graphics gMap;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<GameObject> gameObjectsMap = new ArrayList<>();
    BoarderPoints boarderPoints;
    Board board;
    Figure currentFigure;

    public GameApplication(JPanel canvasMain, JPanel canvasMap) {

        this.canvasMain = canvasMain;
        this.canvasMap = canvasMap;

        init();

    }

    public void init() {

        win = false;
        loose = false;
        currentFigure = null;
        gameSpeed = Stats.gameSpeed;
        Stats.scoreWorWin = Settings.SCORE_FOR_WIN + (level - 1) * Settings.SCORE_FOR_WIN;

        GameApplication.gameObjects = new ArrayList<>();
        GameApplication.gameObjectsMap = new ArrayList<>();
        gameObjects = new ArrayList<>();
        gameObjectsMap = new ArrayList<>();
        Board.boarderCells = new ElementCell[Settings.ROWS][Settings.COLUMNS];
        BoarderPoints.points = new Point[Settings.ROWS][Settings.COLUMNS];
        boarderPoints = new BoarderPoints();
        boarderPoints.createPointsArray();
        Point[][] points = boarderPoints.points;

        board = new Board(points);
        gameRunning = true;
        g = canvasMain.getGraphics();
        gMap = canvasMap.getGraphics();

        GameObject background = new GameObject(0,0, Settings.MAIN_CANVAS_WIDTH, Settings.MAIN_CANVAS_HEIGHT, Color.lightGray);
        gameObjects.add(background);

        for (int numRow = 0; numRow <= points.length-1 ; numRow++) {

            Point[] row = points[numRow];
            Color color = SystemColor.lightGray;

            for (int numColumn = 0; numColumn <= row.length-1; numColumn++) {

                Point point = row[numColumn];

                GameObject figure = new GameObject(point.x, point.y, Settings.CELL_SIZE, Settings.CELL_SIZE, color);
                gameObjects.add(figure);
            }
        }

        for (int i=0; i < Settings.QUANTITY_NEXT_FIGURES; i++){
            FigureFactory.createNextFigure();
        }

        GameObject backgroundMap = new GameObject(0,0, Settings.CELL_SIZE * 8, Settings.CELL_SIZE * (Settings.ROWS/3), Color.ORANGE);
        gameObjectsMap.add(backgroundMap);
        gameObjectsMap.add(FigureFactory.nextFigures.getFirst());

        ui = new UI(Settings.CELL_SIZE, Settings.CELL_SIZE,
                Settings.CELL_SIZE, Settings.CELL_SIZE, Color.BLACK);

        gameObjectsMap.add(ui);
    }

    @Override
    public void run() {

        long prevTime = System.nanoTime();
        double deltaTime;

        while (gameRunning) {

            long currentTime = System.nanoTime();
            deltaTime = (currentTime - prevTime) / 1.0E06f;

            if (deltaTime >= 1000 / 10) {

                if (!paused && !win && !loose) {
                    update();
                }

                render();
                prevTime = currentTime;

                if (win && KL.isKeyTyped(KeyEvent.VK_SPACE)) {
                    nextLevel();
                }
            }
        }
    }

    private void update() {

        checkLoose();
        checkWin();

        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        addCurrentFigure();
    }

    private void checkLoose() {
        if (loose) {
        }
    }

    private void addCurrentFigure() {

        if (currentFigure == null || !currentFigure.isActiveFigure) {

            currentFigure = FigureFactory.getNextFigure();
            gameObjects.add(currentFigure);
            board.addFigureOnBoard(currentFigure);
            gameObjectsMap.remove(currentFigure);

            int numOfRotates = (int) Math.random() * 4;
            for(int i = 0; i <= numOfRotates; i++) {
                currentFigure.rotateFigure();
            }

            FigureFactory.createNextFigure();
            gameObjectsMap.add(FigureFactory.nextFigures.getFirst());

        }
    }

    private void render() {

        renderCanvasMap();

        Image img = canvasMain.createImage(Settings.MAIN_CANVAS_WIDTH, Settings.MAIN_CANVAS_HEIGHT);
        Graphics2D canvasMain2d = (Graphics2D) img.getGraphics();

        for (GameObject gameObject : gameObjects) {
            gameObject.render(canvasMain2d);
        }

        if (GameApplication.win || GameApplication.loose) {

            String message = "";

            if (GameApplication.win) {
                message = "YOU WIN! Press SPACE to next level";
            } else if (GameApplication.loose) {
                message = "YOU LOOSE! Press New game to restart";
            }

            canvasMain2d.setColor(Color.BLACK);
            Font font = new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, (Settings.CELL_SIZE * 65 / 100));
            canvasMain2d.setFont(font);

            canvasMain2d.drawString(message, Settings.CELL_SIZE / 2, Settings.MAIN_CANVAS_HEIGHT / 2);
        }

        g.drawImage(img,0,0, canvasMain);

    }

    private void renderCanvasMap() {

        Image img = canvasMap.createImage(Settings.CELL_SIZE * 8, Settings.CELL_SIZE * (Settings.ROWS/3));
        Graphics2D canvasMap2d = (Graphics2D) img.getGraphics();

        for (GameObject gameObject : gameObjectsMap) {
            if (gameObject instanceof Figure) {

                gameObject.render(canvasMap2d, Settings.CELL_SIZE, 0);
            } else {

                gameObject.render(canvasMap2d);
            }
        }

        gMap.drawImage(img,0,0, canvasMap);
    }

    private void checkWin() {

        if (Stats.score >= Stats.scoreWorWin) {
            win = true;
        }
    }

    public void nextLevel() {
        level++;
        Stats.gameSpeed -= Settings.INCREASE_SPEED_FOR_LEVEL;
        init();
    }

}
