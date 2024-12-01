package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddCourse {

    @FXML
    private TextField courseTitleField; // TextField for course title

    @FXML
    private TextArea courseDescriptionField; // TextArea for course description

    @FXML
    private Button createCourseButton; // Button to create the course

    private static final String COURSES_DIRECTORY = "src/main/resources/Courses/";

    /**
     * Creates a directory for the course based on the title.
     */
    @FXML
    public void createCourse() {
        String courseTitle = courseTitleField.getText().trim();

        // Validate title
        if (courseTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Course title is required.");
            return;
        }

        // Sanitize course title for safe directory naming
        courseTitle = courseTitle.replace(" ", "_");

        // Define the course directory path
        Path courseDirectory = Paths.get(COURSES_DIRECTORY, courseTitle);

        try {
            // Check if the directory already exists
            if (Files.exists(courseDirectory)) {
                showAlert(Alert.AlertType.ERROR, "Error", "A course with this title already exists.");
                return;
            }

            // Create the course directory
            Files.createDirectories(courseDirectory);

            // Notify the user of success
            showAlert(Alert.AlertType.INFORMATION, "Success", "Course created successfully.");

            // Clear the input fields
            courseTitleField.clear();
            courseDescriptionField.clear();
        } catch (IOException e) {
            // Handle errors
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create the course directory: " + e.getMessage());
        }
    }

    /**
     * Utility method to display alert messages to the user.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opens the Add Course page in a new window.
     */
    public static void openAddCoursePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddCourse.class.getResource("/com/quizapp/AddCourse.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Add New Course");
        stage.setScene(scene);
        stage.show();
    }
}
