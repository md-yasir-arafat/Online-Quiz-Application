package com.quizapp.Controllers;

import com.quizapp.Controllers.QuizEditPage;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static com.quizapp.Controllers.QuizListTeacher.openCourseListTeacher;

public class TeacherMainPage {
    public Button addCourses;

    @FXML
    private ImageView logoImage; // Ensure this matches the FXML fx:id

    @FXML
    private ImageView userImage; // Fixed spelling to match FXML fx:id

    @FXML
    private GridPane numberGrid;


    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    private String filename = "src/main/resources/teacherInfo/Rabbi.csv";
    private String common = "src/main/resources/Questions/";


    @FXML
    public void initialize() {
        try {
            // Initialize the logo
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));

            // Set the user image (background)
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }


        addCourses.setOnAction(e-> {
            try {
                com.quizapp.Controllers.AddQuizPage.openAddQuizPage();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        currentCourses(filename);
    }

    private void currentCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            int column = 0;

            // Create a GridPane to hold the course squares
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10); // Horizontal spacing between squares
            gridPane.setVgap(10); // Vertical spacing between squares
            gridPane.setPadding(new Insets(100)); // Padding around the grid
            gridPane.setAlignment(Pos.TOP_LEFT);

            // Reading the CSV file line by line, skipping the header
            while ((line = reader.readLine()) != null) {
                // Split the line to extract details
                String[] courseData = line.split(",");
                String subject = courseData[0];
                String description = courseData[1];
                String enrolled = courseData[2];
                String quizFileName = courseData[3]; // File name for the quiz

                // Create a VBox for each course (to display as a square)
                VBox courseBox = new VBox(10); // Add some spacing between elements
                courseBox.setAlignment(Pos.CENTER);
                courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                courseBox.setPrefWidth(200); // Set a preferred width for consistent size

                // Add course details to the VBox
                Label subjectLabel = new Label(subject);
                subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label descriptionLabel = new Label(description);
                descriptionLabel.setStyle("-fx-font-size: 12px;");

                Label enrolledLabel = new Label("Enrolled: " + enrolled);
                enrolledLabel.setStyle("-fx-font-size: 12px;");

                // Add a button to check files starting with "common"
                Button checkFilesButton = new Button("Edit");
                checkFilesButton.setOnAction(e -> {
                    try {
                        // Open the Quiz Editor dynamically with the quiz file name
                        openCourseListTeacher();
//                        quizEditPage.openQuizEditor(common + "Rabbi_Math_quiz1.csv"); // Use dynamic file path
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                // Add all elements to the course box
                courseBox.getChildren().addAll(subjectLabel, descriptionLabel, enrolledLabel, checkFilesButton);

                // Add the VBox to the GridPane
                gridPane.add(courseBox, column, row);

                // Update column and row for the next course
                column++;
                if (column == 4) { // Move to the next row after 4 columns
                    column = 0;
                    row++;
                }
            }

            // Add the gridPane to the main layout (numberGrid is assumed to be a container)
            numberGrid.getChildren().clear();
            numberGrid.getChildren().add(gridPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
