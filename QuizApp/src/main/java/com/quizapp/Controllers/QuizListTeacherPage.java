package com.quizapp.Controllers;

import com.quizapp.App;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.quizapp.Controllers.AddQuizPage.openAddQuizPage;
import static com.quizapp.Controllers.QuizEditPage.openEditQuizPage;

public class QuizListTeacherPage extends com.quizapp.Actions.QuizListTeacher {
    public Button addQuiz;
    public GridPane CourseGrid;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;



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



}
