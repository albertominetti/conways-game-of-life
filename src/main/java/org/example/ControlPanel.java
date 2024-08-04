package org.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.example.strategies.*;
import org.example.strategies.byset.HighLifeBySetStrategy;
import org.example.strategies.byset.StandardBySetStrategy;

import static javafx.animation.Animation.INDEFINITE;
import static javafx.util.Duration.seconds;

public class ControlPanel {
    private final Board board;
    private final DisplayBoard displayBoard;

    private HBox panel;
    private Timeline timeline;
    private Label generationCount;

    public ControlPanel(DisplayBoard displayBoard) {
        this.board = displayBoard.getBoard();
        this.displayBoard = displayBoard;
        init();
    }

    private void init() {
        timeline = createTimeline(displayBoard);

        Button playPause = createPlayPauseButton(timeline);
        Button step = createStepButton();
        Label strategyLabel = new Label("Strategy:");
        generationCount = new Label();
        ChoiceBox<Strategy> strategyChoice = createStrategyChoice();
        Button reset = createResetButton();
        Button clean = createCleanButton();

        panel = new HBox(playPause, step, strategyLabel, strategyChoice, reset, clean, generationCount);
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);

        timeline.play();
    }

    private Timeline createTimeline(DisplayBoard displayBoard) {
        Timeline timeline = new Timeline(new KeyFrame(seconds(0.1), e -> {
            board.nextGeneration();
            displayBoard.display();
            generationCount.setText("Generation: " + board.getGeneration());
        }));
        timeline.setCycleCount(INDEFINITE);
        return timeline;
    }

    private static Button createPlayPauseButton(Timeline timeline) {
        Button playPause = new Button("Play/Pause");
        playPause.setOnAction(l -> {
            switch (timeline.getStatus()) {
                case Animation.Status.RUNNING:
                    timeline.pause();
                    playPause.setText("Play");
                    break;
                case PAUSED:
                case STOPPED:
                    timeline.play();
                    playPause.setText("Pause");
                    break;
            }
        });
        return playPause;
    }

    private ChoiceBox<Strategy> createStrategyChoice() {
        ChoiceBox<Strategy> strategyChoice = new ChoiceBox<>();
        ObservableList<Strategy> strategies = FXCollections.observableArrayList(
                new StandardBySetStrategy(), new HighLifeBySetStrategy(), new WalledCitiesStrategy(),
                new GnarlStrategy(), new EternalStrategy(), new CommercialStrategy()
        );
        strategyChoice.setItems(strategies);
        strategyChoice.setValue(strategyChoice.getItems().getFirst());
        strategyChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Strategy strategy) {
                return strategy.name();
            }

            @Override
            public Strategy fromString(String name) {
                return strategyChoice.getItems().stream()
                        .filter(strategy -> strategy.name().equals(name))
                        .findFirst()
                        .orElse(null);
            }
        });

        strategyChoice.setOnAction(actionEvent -> {
            board.setStrategy(strategyChoice.getValue());
        });

        return strategyChoice;
    }

    private Button createStepButton() {
        Button step = new Button("Step");
        step.setOnAction(l -> {
            board.nextGeneration();
            displayBoard.display();
            generationCount.setText("Generation: " + board.getGeneration());
        });
        return step;
    }

    private Button createResetButton() {
        Button reset = new Button("Reset");
        reset.setOnAction(l -> {
            board.randomize(15);
            displayBoard.display();
        });
        return reset;
    }

    private Button createCleanButton() {
        Button reset = new Button("Clean");
        reset.setOnAction(l -> {
            board.randomize(0);
            displayBoard.display();
        });
        return reset;
    }

    public Pane getPanel() {
        return panel;
    }

    public Timeline getTimeline() {
        return timeline;
    }

}
