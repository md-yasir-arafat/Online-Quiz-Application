package com.quizapp;

import com.quizapp.Actions.Login;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App extends Application {
    public static double sceneWidth = 0;
    public static String username;
    public static String leaderBoard = "src/main/resources/leaderboard/leader.txt";
    public static final String LOGO_PATH = "/images/logo.png";
    public static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    public static final String BACKGROUND_Main_IMAGE_PATH = "/images/Student-Studying.jpg";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/LoginPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
        sceneWidth = scene.getWidth();

        // Load leaderboard data in a background thread
        loadLeaderboardInBackground();
    }

    private void loadLeaderboardInBackground() {
        // Create a Task for background processing
        Task<Void> loadLeaderboardTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulate a long-running task (e.g., reading the leaderboard file)
                if (Files.exists(Path.of(leaderBoard))) {
                    System.out.println("Loading leaderboard...");
                    String data = Files.readString(Path.of(leaderBoard));
                    System.out.println("Leaderboard data loaded: \n" + data);
                } else {
                    System.out.println("Leaderboard file not found.");
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Leaderboard loaded successfully.");
            }

            @Override
            protected void failed() {
                super.failed();
                System.err.println("Failed to load leaderboard: " + getException().getMessage());
            }
        };

        // Run the task in a separate thread
        Thread backgroundThread = new Thread(loadLeaderboardTask);
        backgroundThread.setDaemon(true); // Ensures the thread stops when the application exits
        backgroundThread.start();
    }

    // Close the current login window
    public static void closeCurrentWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();  // Close the current stage (login window)
    }

    public static void main(String[] args) {
        launch();
    }
}
