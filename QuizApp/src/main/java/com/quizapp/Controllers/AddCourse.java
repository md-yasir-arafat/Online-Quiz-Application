package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;

public class AddCourse {

    @FXML
    private TextField courseTitleField; // TextField for course title

    @FXML
    private TextArea courseDescriptionField; // TextArea for course description

    @FXML
    private Button createCourseButton; // Button to create the course

    // Method to create a new course
    @FXML
    public void createCourse() {
        String courseTitle = courseTitleField.getText().trim();
        String courseDescription = courseDescriptionField.getText().trim();

        // Ensure the title and description are not empty
        if (courseTitle.isEmpty() || courseDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Both title and description are required.");
            return;
        }

        // Sanitize course title to replace spaces with underscores
        courseTitle = courseTitle.replace(" ", "_");

        // Define the course directory path
        Path courseDirectory = Paths.get("src/main/resources/Courses", courseTitle);

        try {
            // Check if the directory already exists
            if (Files.exists(courseDirectory)) {
                showAlert(Alert.AlertType.ERROR, "Error", "A course with this title already exists.");
                return;
            }

            // Create the course directory
            Files.createDirectories(courseDirectory);

            // Create a description file in the directory
            Path courseFile = courseDirectory.resolve(courseTitle + ".txt");
            try (BufferedWriter writer = Files.newBufferedWriter(courseFile)) {
                writer.write("Course Title: " + courseTitle);
                writer.newLine();
                writer.write("Description: " + courseDescription);
            }

            // Notify the user of success
            showAlert(Alert.AlertType.INFORMATION, "Success", "Course created successfully.");

            // Clear the input fields
            courseTitleField.clear();
            courseDescriptionField.clear();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create the course: " + e.getMessage());
        }
    }

    // Utility method to display alert messages
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to open the Add Course page
    public static void openAddCoursePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddCourse.class.getResource("/com/quizapp/AddCourse.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Add New Course");
        stage.setScene(scene);
        stage.show();
    }
}
