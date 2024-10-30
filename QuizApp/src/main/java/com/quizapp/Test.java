package com.quizapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Test {

    public void show() {
        Stage test = new Stage();

        // Load the FXML file
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/hello-view.fxml")));
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();  // Optional: Log full stack trace for debugging
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // Optional: Log full stack trace for debugging
        }

        // If root is not null, create and show the scene
        if (root != null) {
            // Set the scene with the loaded FXML
            Scene scene = new Scene(root, 300, 300);

            // Set the stage (window) title
            test.setScene(scene);
            test.setTitle("Student Dashboard");

            // Show the stage
            test.show();
        } else {
            // Handle the case where the root is null (e.g., show an error message or take corrective action)
            System.err.println("Failed to load the FXML file, stage will not be shown.");
        }
    }
}
