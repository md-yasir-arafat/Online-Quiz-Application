package com.quizapp.Controllers;


import com.quizapp.Actions.QuizListStudent;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.quizapp.Controllers.TakeQuizPage.openTakeQuizPage;

public class QuizListStudentPage extends com.quizapp.Actions.QuizListStudent{
    public Button startQuiz;
    @FXML
    public Button leaderBoard;
    @FXML
    public Button enroll;
    public GridPane CourseGrid;
    public ScrollPane scrollPane;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    @FXML
    private Label courseName;

    @FXML
    private Label facultyName;

    @FXML
    private Label courseDescription;




    @FXML
    public void initialize() {
        try {
            // Initialize the logo and user images
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        setCourseDetails(QuizListStudent.subject,QuizListStudent.faculty, String.valueOf(QuizListStudent.description));
        courseList();
        addCoursesFromMap();
    }

    private void addCoursesFromMap() {
        // Clear the existing content in the GridPane to avoid duplication
//        CourseGrid.getChildren().clear();

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

    public void setCourseDetails(String name, String faculty, String description) {
        courseName.setText(name);
        facultyName.setText(faculty);
        courseDescription.setText(description);
    }

}
