package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.Objects;

public class CourseInfoPage {
    @FXML
    private Button addCourses;

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    @FXML
    private GridPane numberGrid;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    private String filename = "src/main/resources/teacherInfo/Rabbi.csv";
    private String common = "src/main/resources/Questions/";

    @FXML
    public void initialize() {
        try {
            // Initialize the logo and user images
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        addCourses.setOnAction(e -> {
            try {
                com.quizapp.Actions.TeacherMain.openAddCourses();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Initialize courses based on the files available
        checkFilesWithPrefix("Rabbi_Math_");
    }

    private void courses(String title, String quizFileName) {
        int row = 0;
        int column = 0;

        // Create a GridPane to hold the course squares
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal spacing between squares
        gridPane.setVgap(10); // Vertical spacing between squares
        gridPane.setPadding(new Insets(100)); // Padding around the grid
        gridPane.setAlignment(Pos.TOP_LEFT);

        // Create a VBox for each course (to display as a square)
        VBox courseBox = new VBox(10); // Add some spacing between elements
        courseBox.setAlignment(Pos.CENTER);
        courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        courseBox.setPrefWidth(200); // Set a preferred width for consistent size

        // Add course details to the VBox
        Label quizTitle = new Label(title);
        quizTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Add buttons to the VBox
        Button checkFilesButton = new Button("Check Files");
        checkFilesButton.setOnAction(e -> System.out.println("Files checked for prefix!")); // Action for checking files

        Button editFileButton = new Button("Edit File");
        editFileButton.setOnAction(e -> {
            try {
                editQuizFile(quizFileName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }); // Pass the file name for editing

        // Add all elements to the course box
        courseBox.getChildren().addAll(quizTitle, checkFilesButton, editFileButton);

        // Add the VBox to the GridPane
        gridPane.add(courseBox, column, row);

        // Add the GridPane to the main layout
        numberGrid.getChildren().clear();
        numberGrid.getChildren().add(gridPane);
    }

    // Method to edit the quiz file
    private void editQuizFile(String quizFileName) throws IOException {
        if (quizFileName == null || quizFileName.trim().isEmpty()) {
            System.out.println("No quiz file available to edit.");
            return;
        }

        System.out.println("Editing quiz file: " + quizFileName);
        // Implement logic to open the Quiz Edit Page, passing the file name
        QuizEditPage quizEditPage = new QuizEditPage();
        quizEditPage.openQuizEditor(quizFileName);
    }

    // Method to check how many files start with a specific prefix and read the first line (quiz title)
    private void checkFilesWithPrefix(String commonPrefix) {
        File directory = new File(common); // Directory containing quiz files
        if (!directory.isDirectory()) {
            System.out.println("Not a valid directory.");
            return;
        }

        // Get all files starting with the common prefix
        File[] matchingFiles = directory.listFiles((dir, name) -> name.startsWith(commonPrefix));
        if (matchingFiles == null || matchingFiles.length == 0) {
            System.out.println("No files found starting with prefix: " + commonPrefix);
        } else {
            System.out.println("Files starting with prefix '" + commonPrefix + "': " + matchingFiles.length);

            // Display courses dynamically
            int count = 0;
            for (File file : matchingFiles) {
                System.out.println(" - " + file.getName());

                // Read the first line (quiz title) from the file
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String title = reader.readLine(); // Read the first line as title
                    if (title == null || title.trim().isEmpty()) {
                        title = "Untitled Quiz";
                    }
                    courses(title, file.getAbsolutePath()); // Pass title and file path for UI creation
                    count++;
                } catch (IOException e) {
                    System.out.println("   Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }
            System.out.println(count + " quizzes loaded.");
        }
    }
}
