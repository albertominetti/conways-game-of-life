package org.example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;

public class UserInterface {
    private static final int MAX_HEIGHT = 1600;
    private static final int MAX_WIDTH = 1600;

    private final Stage stage;

    private Board board;
    private GraphicsContext gc;
    private int cellSize;

    public UserInterface(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Game of Life");
    }

    public void bind(Board board) {
        this.board = board;

        int cellHeight = MAX_HEIGHT / board.getHeight();
        int cellWidth = MAX_WIDTH / board.getWidth();

        cellSize = Math.min(cellHeight, cellWidth);

        Canvas canvas = new Canvas(board.getWidth() * cellSize, board.getHeight() * cellSize);

        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void display() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                gc.setFill(GRAY);
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                if (board.isAlive(x, y)) {
                    gc.setFill(DEEPSKYBLUE);
                    gc.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 1, cellSize - 1);
                } else {
                    gc.setFill(AZURE);
                    gc.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 1, cellSize - 1);
                }
            }
        }
    }

}
