package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import com.quizapp.App;
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

import static com.quizapp.Controllers.AddQuizPage.openAddQuizPage;
import static com.quizapp.Controllers.QuizEditPage.openEditQuizPage;

public class QuizListTeacher {
    public Button addQuiz;
    public GridPane CourseGrid;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    public Map<String, String> quizMap = new HashMap<>();
    private static String quizDir = "src/main/resources/Courses/";
    private static String quizFile = "";

    @FXML
    public void initialize() {
        try {
            // Initialize the logo and user images
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        addQuiz.setOnAction(e -> {
            try {
                openAddQuizPage(quizDir);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        courseList( "quiz");
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

            // Create an Edit button
            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #90ee90; -fx-text-fill: white; -fx-font-weight: bold;");

            // Add an action listener to the button to handle editing
            editButton.setOnAction(e -> {
                try {
                    fileName.set(quizDir + "/" + fileName);
                    openEditQuizPage(fileName.get()); // Call the method to edit the file
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            // Add the Label and Button to the GridPane in the current row
            CourseGrid.add(titleLabel, 0, row); // Add the title to the first column
            CourseGrid.add(editButton, 1, row); // Add the button to the second column

            row++; // Move to the next row
        }
    }


    // Method to check how many files start with a specific prefix and read the first line (quiz title)
    private void courseList(String commonPrefix) {
        quizDir = quizDir+ quizFile;
        File directory = new File(quizDir); // Directory containing quiz files
        if (!directory.isDirectory()) {
            System.out.println("Not a valid directory.");
            return;
        }

        // Get all files starting with the common prefix
        File[] matchingFiles = directory.listFiles((dir, name) -> name.startsWith(commonPrefix));
        if (matchingFiles == null || matchingFiles.length == 0) {
            System.out.println("No files found starting with prefix: " + commonPrefix);
            return;
        }

        // Map to store file name and quiz title


        System.out.println("Files starting with prefix '" + commonPrefix + "': " + matchingFiles.length);

        int count = 0;
        for (File file : matchingFiles) {
            System.out.println(" - " + file.getName());

            // Read the first line (quiz title) from the file
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String title = reader.readLine(); // Read the first line as title
                if (title == null || title.trim().isEmpty()) {
                    title = "Untitled Quiz";
                }

                // Add file name and title to the map
                quizMap.put(file.getName(), title);

                count++;
            } catch (IOException e) {
                System.out.println("   Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }

        System.out.println(count + " quizzes loaded.");

        // Debugging: Print the map
        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            System.out.println("File: " + entry.getKey() + ", Title: " + entry.getValue());
        }
    }

    public static void openCourseListTeacher(String quizFileName) throws IOException {
        quizFile = quizFileName;
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/QuizListTeacher.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Course List");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
