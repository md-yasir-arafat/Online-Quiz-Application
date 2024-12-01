package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;

public class AddCourse {

    @FXML
    private TextField courseTitleField;
    @FXML
    private TextArea courseDescriptionField;
    @FXML
    private Button createCourseButton;

    @FXML
    public void createCourse() {
        String courseTitle = courseTitleField.getText().trim();
        String courseDescription = courseDescriptionField.getText().trim();

        // Replace spaces with underscores
        courseTitle = courseTitle.replace(" ", "_");

        if (courseTitle.isEmpty() || courseDescription.isEmpty()) {
            showAlert("Error", "Both title and description are required.");
            return;
        }

        // Define the directory path (creating inside 'src/main/resources/Courses/')
        Path courseDirectory = Paths.get("src", "main", "resources", "Courses", courseTitle);
        try {
            if (!Files.exists(courseDirectory)) {
                Files.createDirectories(courseDirectory);  // Create the directory
                showAlert("Success", "Course directory created successfully.");

                // Optionally, save the course description to a file
                Path courseFile = courseDirectory.resolve(courseTitle + ".txt");
                try (BufferedWriter writer = Files.newBufferedWriter(courseFile)) {
                    writer.write("Course Title: " + courseTitle);
                    writer.newLine();
                    writer.write("Description: " + courseDescription);
                }

            } else {
                showAlert("Error", "A course with this title already exists.");
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to create the course directory: " + e.getMessage());
        }
    }


    // Utility method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void openAddCoursePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/CreateNewCourse.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage enrollStage = new Stage();
        enrollStage.setTitle("Enroll in a Course");
        enrollStage.setMaximized(true);
        enrollStage.setScene(scene);
        enrollStage.show();
    }
}
