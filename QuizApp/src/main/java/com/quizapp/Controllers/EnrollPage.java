package com.quizapp.Controllers;

import com.quizapp.Actions.Enroll;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.Objects;

import static com.quizapp.Actions.Login.openStudentMain;
import static com.quizapp.Controllers.LeaderboardPage.openLeaderBoard;

public class EnrollPage extends Enroll {

    @FXML
    private Button home;
    @FXML
    private Button leaderBoard;
    @FXML
    private GridPane courseGrid;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String USER_IMAGE_PATH = "/images/temp.jpg";
    private static final String COURSES_FILE = "/Courses/all.csv";

    @FXML
    private void initialize() {
        try {
            // Load logo and user images
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(LOGO_PATH)).toExternalForm()));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(USER_IMAGE_PATH)).toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        leaderBoard.setOnAction(e -> {
            try {
                openLeaderBoard();
                closeCurrentWindow(leaderBoard);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        home.setOnAction(e -> {
            try {
                openStudentMain();
                closeCurrentWindow(home);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Populate the course grid
        populateCourses(COURSES_FILE);
    }

    private void populateCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(fileName))))) {

            String line;
            int row = 0;
            int column = 0;

            // Configure the course grid layout
            courseGrid.getChildren().clear();
            courseGrid.setHgap(10);
            courseGrid.setVgap(10);
            courseGrid.setPadding(new Insets(20));
            courseGrid.setAlignment(Pos.TOP_LEFT);

            while ((line = reader.readLine()) != null) {
                String[] courseData = line.split(",");
                String courseName = courseData[0].trim().replace("_", " ");
                String description = courseData[1].trim();

                VBox courseBox = createCourseBox(courseName, description);
                courseGrid.add(courseBox, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
    }
}
