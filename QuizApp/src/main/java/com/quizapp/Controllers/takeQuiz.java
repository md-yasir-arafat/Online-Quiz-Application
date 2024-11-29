package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

public class takeQuiz {
    @FXML
    private ImageView logoImage; // Ensure this matches the FXML fx:id

    @FXML
    private ImageView userImage; // Fixed spelling to match FXML fx:id

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";

    public void initialize(){
        try {
            // Initialize the logo
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));

            // Set the user image (background)
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

    }

    public class FileCounter {
        public static void main(String[] args) {
            // Path to the directory containing the files
            File folder = new File("path/to/Questions");

            // Ensure the folder exists
            if (folder.exists() && folder.isDirectory()) {
                // List all files in the directory
                File[] files = folder.listFiles();

                // Counter for files starting with "Rabbi_Math"
                int count = 0;

                if (files != null) {
                    for (File file : files) {
                        // Check if the file starts with "Rabbi_Math"
                        if (file.isFile() && file.getName().startsWith("Rabbi_Math")) {
                            count++;
                        }
                    }
                }

                System.out.println("Number of files starting with 'Rabbi_Math': " + count);
            } else {
                System.out.println("The folder does not exist or is not a directory.");
            }
        }
    }
}
