package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board(150, 70);
        board.randomize(15);

        DisplayBoard displayBoard = new DisplayBoard(board);

        ControlPanel controlPanel = new ControlPanel(displayBoard);

        displayBoard.bind(controlPanel.getTimeline());
        displayBoard.display();

        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        root.getChildren()
                .addAll(controlPanel.getPanel(), displayBoard.getCanvas());

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}