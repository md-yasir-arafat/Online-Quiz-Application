package com.quizapp.Controllers;

import com.quizapp.Actions.StudentMain;
import com.quizapp.App;
import com.quizapp.Controllers.QuizListStudent;

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
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static com.quizapp.Controllers.QuizListStudent.openCourseListStudent;
import static com.quizapp.Controllers.QuizListTeacher.openCourseListTeacher;

public class StudentMainPage {
    @FXML
    private Button Courses;
    @FXML
    private Button leaderBoard;
    @FXML
    private GridPane courseGrid; // Use courseGrid defined in FXML
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    private final String fileName = "src/main/resources/studentInfo/" + App.username + ".csv";

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

        populateCourses(fileName);

        Courses.setOnAction(e -> {
            try {
                StudentMain.openCourses();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void populateCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            int column = 0;

            // Clear the existing content in the courseGrid
            courseGrid.getChildren().clear();
            courseGrid.setHgap(10);
            courseGrid.setVgap(10);
            courseGrid.setPadding(new Insets(20));
            courseGrid.setAlignment(Pos.TOP_LEFT);

            int quizCount = 1;

            while ((line = reader.readLine()) != null) {
                // Split the line to extract details
                String[] courseData = line.split(",");
                String subject = courseData[0];
                String faculty = "";
                String description = courseData[1];
                int quizTaken = Integer.parseInt(courseData[2]);
                String quizFileName = courseData[0];

                // Create a VBox for each course
                VBox courseBox = new VBox(10);
                courseBox.setAlignment(Pos.CENTER);
                courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                courseBox.setPrefWidth(200);

                try {
                    String[] parts = subject.split("by", 2); // Split into at most 2 parts
                    if (parts.length == 2) {
                        subject = parts[0].trim().replace("_"," "); // Trim to remove extra spaces
                        faculty = parts[1].trim().replace("_"," ");
                    } else {
                        throw new IllegalArgumentException("String does not contain 'by' or is not in the expected format.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error processing subject string: " + subject);
                }
                Label subjectLabel = new Label(subject);
                subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label facultyLabel = new Label(faculty);
                subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label descriptionLabel = new Label(description);
                descriptionLabel.setStyle("-fx-font-size: 12px;");

                Label enrolledLabel = new Label("Quiz Taken: " + quizTaken);
                enrolledLabel.setStyle("-fx-font-size: 12px;");

                Button takeQuizButton = new Button("Take Quiz");
                takeQuizButton.setOnAction(e -> {
                    try {
                        openCourseListStudent(quizFileName);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                courseBox.getChildren().addAll(subjectLabel,facultyLabel, descriptionLabel, enrolledLabel, takeQuizButton);

                // Add the course box to the grid
                courseGrid.add(courseBox, column, row);

                column++;
                if (column == 4) { // Move to the next row after 4 columns
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
