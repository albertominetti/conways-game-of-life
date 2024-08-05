package org.example;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static javafx.scene.paint.Color.*;

public class DisplayBoard {
    private static final int MAX_HEIGHT = 1600;
    private static final int MAX_WIDTH = 1600;


    private final Board board;
    private int cellSize;

    private Canvas canvas;
    private boolean wasRunning;
    private boolean[][] snapshotGrid;

    public DisplayBoard(Board board) {
        this.board = board;
    }

    public void bind(Timeline timeline) {
        int cellHeight = MAX_HEIGHT / board.getHeight();
        int cellWidth = MAX_WIDTH / board.getWidth();

        cellSize = Math.min(cellHeight, cellWidth);

        canvas = new Canvas(board.getWidth() * cellSize + 1, board.getHeight() * cellSize + 1);

        initDragging(timeline);
        initMouseClick();
    }

    public void display() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.isAlive(x, y)) {
                    gc.setFill(DEEPSKYBLUE);
                } else {
                    gc.setFill(AZURE);
                }
                gc.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 1, cellSize - 1);
            }
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void initDragging(Timeline timeline) {
        canvas.setOnDragDetected(mouseEvent -> {
            wasRunning = timeline.getStatus() == Animation.Status.RUNNING;
            timeline.pause();
            canvas.startFullDrag();
            snapshotGrid = board.getGridSnapshot();
        });

        canvas.setOnMouseDragOver(mouseEvent -> {

            if (mouseEvent.getX() >= canvas.getWidth() || mouseEvent.getY() >= canvas.getHeight()) {
                return;
            }

            int x = (int) ((mouseEvent.getX() - 1) / cellSize);
            int y = (int) ((mouseEvent.getY() - 1) / cellSize);
            board.setCell(x, y, !snapshotGrid[x][y]);
            display();

        });

        canvas.setOnMouseDragReleased(mouseEvent -> {
            if (wasRunning) {
                timeline.play();
            }
        });
    }

    private void initMouseClick() {
        canvas.setOnMouseClicked(mouseEvent -> {
            if (!mouseEvent.isStillSincePress()) {
                return;
            }

            int x = (int) ((mouseEvent.getX() - 1) / cellSize);
            int y = (int) ((mouseEvent.getY() - 1) / cellSize);
            board.setCell(x, y, !board.isAlive(x, y));
            display();
        });
    }

    public Board getBoard() {
        return board;
    }
}
