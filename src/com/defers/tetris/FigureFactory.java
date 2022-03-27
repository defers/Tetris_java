package com.defers.tetris;

import java.util.LinkedList;

public class FigureFactory {

    public static LinkedList<Figure> nextFigures = new LinkedList<>();

    public static void createNextFigure() {

        int figuresQuantity = Figure.getAllFigures().size();
        int figureType = (int) (Math.random() * figuresQuantity);

        Figure figure = new Figure(0,0,0,0,
                                    Settings.FIGURE_CELL_COLOR, Figure.getFigurePattern(figureType), true);

        nextFigures.add(figure);
    }

    public static Figure getNextFigure() {

        Figure nextFigure = null;

        if (nextFigures.size() > 0) {
            nextFigure = nextFigures.removeFirst();
        }

        return nextFigure;
    }


}
