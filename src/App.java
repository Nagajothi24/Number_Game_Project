import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class App extends Application {
    private int randomNumber;
    private int attemptsLeft;
    private int score;
    private final int maxAttempts = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Number Guessing Game");

        // Initialize game state
        initializeGame();

        // Create the UI
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels and Input Fields
        Label promptLabel = new Label("Enter your guess (1 to 100):");
        GridPane.setConstraints(promptLabel, 0, 0);
        TextField guessInput = new TextField();
        GridPane.setConstraints(guessInput, 1, 0);
        Button guessButton = new Button("Guess");
        GridPane.setConstraints(guessButton, 2, 0);

        Label feedbackLabel = new Label();
        GridPane.setConstraints(feedbackLabel, 0, 1, 3, 1);

        Label attemptsLabel = new Label("Attempts left: " + attemptsLeft);
        GridPane.setConstraints(attemptsLabel, 0, 2);

        Label scoreLabel = new Label("Score: " + score);
        GridPane.setConstraints(scoreLabel, 1, 2);

        Button playAgainButton = new Button("Play Again");
        GridPane.setConstraints(playAgainButton, 2, 2);
        playAgainButton.setDisable(true);

        guessButton.setOnAction(e -> {
            try {
                int guess = Integer.parseInt(guessInput.getText());
                attemptsLeft--;
                if (guess == randomNumber) {
                    feedbackLabel.setText("Correct! The number was " + randomNumber);
                    score += maxAttempts - attemptsLeft;
                    guessButton.setDisable(true);
                    playAgainButton.setDisable(false);
                } else if (guess < randomNumber) {
                    feedbackLabel.setText("Too low! Try again.");
                } else {
                    feedbackLabel.setText("Too high! Try again.");
                }
                attemptsLabel.setText("Attempts left: " + attemptsLeft);
                if (attemptsLeft == 0 && guess != randomNumber) {
                    feedbackLabel.setText("Out of attempts! The number was " + randomNumber);
                    guessButton.setDisable(true);
                    playAgainButton.setDisable(false);
                }
                guessInput.clear();
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        });

        playAgainButton.setOnAction(e -> {
            initializeGame();
            guessButton.setDisable(false);
            playAgainButton.setDisable(true);
            feedbackLabel.setText("");
            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            scoreLabel.setText("Score: " + score);
        });

        // Add components to the grid
        grid.getChildren().addAll(promptLabel, guessInput, guessButton, feedbackLabel, attemptsLabel, scoreLabel, playAgainButton);

        // Create the scene
        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGame() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attemptsLeft = maxAttempts;
    }
}

