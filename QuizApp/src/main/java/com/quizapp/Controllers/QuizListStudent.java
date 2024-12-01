package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.quizapp.Controllers.TakeQuizPage.openTakeQuizPage;

public class QuizListStudent {
    public Button startQuiz;
    public GridPane CourseGrid;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    public Map<String, String> quizMap = new HashMap<>();
    private static String quizDir = "src/main/resources/Courses/";
    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";

    @FXML
    public void initialize() {
        try {
            // Initialize the logo and user images
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        courseList();
        addCoursesFromMap();
    }

    private void addCoursesFromMap() {
        // Clear the existing content in the GridPane to avoid duplication
        CourseGrid.getChildren().clear();

        int row = 0;

        // Iterate through the quizMap to add titles and buttons
        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            AtomicReference<String> fileName = new AtomicReference<>(entry.getKey()); // File name
            String title = entry.getValue(); // Title of the quiz

            // Create a Label for the title
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

            // Create a Take Quiz button
            Button takeQuizButton = new Button("Take Quiz");
            takeQuizButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");

            // Add an action listener to the button to handle quiz-taking
            takeQuizButton.setOnAction(e -> {
                try {
                    openTakeQuizPage(quizDir,fileName); // Call the method to take the quiz
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            // Add the Label and Button to the GridPane in the current row
            CourseGrid.add(titleLabel, 0, row); // Add the title to the first column
            CourseGrid.add(takeQuizButton, 1, row); // Add the button to the second column

            row++; // Move to the next row
        }
    }

    // Method to list and read all files in the specified folder
    private void courseList() {
        System.out.println("Debug: quizDir = " + quizDir);

        // Step 1: Check if the directory exists and is valid
        File directory = new File(quizDir); // Directory containing quiz files
        if (!directory.exists()) {
            System.out.println("Error: quizDir does not exist. Please verify the path.");
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("Error: quizDir is not a directory. Please verify the path.");
            return;
        }

        // Step 2: List all files in the directory
        File[] allFiles = directory.listFiles();
        if (allFiles == null || allFiles.length == 0) {
            System.out.println("Error: No files found in quizDir.");
            return;
        }

        System.out.println("Debug: Found " + allFiles.length + " files in quizDir:");

        // Step 3: Process each file
        int count = 0;
        for (File file : allFiles) {
            System.out.println("Debug: Processing file - " + file.getName());

            // Attempt to read the first line (quiz title)
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String title = reader.readLine(); // Read the first line as title
                if (title == null || title.trim().isEmpty()) {
                    title = "Untitled Quiz";
                }

                // Add file name and title to the map
                quizMap.put(file.getName(), title);
                System.out.println("Debug: Added to quizMap -> File: " + file.getName() + ", Title: " + title);

                count++;
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }

        // Step 4: Summary
        System.out.println("Debug: Total quizzes loaded: " + count);

        // Debugging: Print the map
        System.out.println("Debug: Current quizMap contents:");
        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            System.out.println("  File: " + entry.getKey() + ", Title: " + entry.getValue());
        }
    }

    public static void openCourseListStudent(String quizFileName) throws IOException {
        quizDir = "src/main/resources/Courses/" + quizFileName + "/";
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/QuizListStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Quiz List");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
