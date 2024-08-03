package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.strategies.CommercialStrategy;

import static javafx.animation.Animation.INDEFINITE;
import static javafx.util.Duration.seconds;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board(200, 100, new CommercialStrategy());
        board.randomize(15);
        board.fillColumn(6);

        UserInterface userInterface = new UserInterface(primaryStage);
        userInterface.bind(board);
        userInterface.display();

        Timeline timeline = new Timeline(new KeyFrame(seconds(0.1), e -> {
            board.nextGeneration();
            userInterface.display();
        }));
        timeline.setCycleCount(INDEFINITE);
        timeline.play();

    }
}