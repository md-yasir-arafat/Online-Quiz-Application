package com.quizapp.Controllers;

import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class EnrollPage extends com.quizapp.Actions.Enroll{

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
