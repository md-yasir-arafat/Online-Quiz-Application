package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class StudentMainPage {
    public Button takeQuiz;
    public Button Courses;
    public Button addCourses;

    @FXML
    private ImageView logoImage; // Ensure this matches the FXML fx:id

    @FXML
    private ImageView userImage; // Fixed spelling to match FXML fx:id

    @FXML
    private SplitPane splitPane; // SplitPane defined in FXML

    @FXML
    private VBox leftVBox; // Left VBox defined in FXML
    @FXML
    private VBox centerVBox; // Center VBox defined in FXML
    @FXML
    private VBox rightVBox; // Right VBox defined in FXML
    @FXML
    private GridPane numberGrid;
    @FXML
    private GridPane rank;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    private final String fileName = "src/main/resources/studentInfo/yasir.txt";
    private final String leader = "src/main/resources/leaderboard/leader.txt";

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

        // Set the divider positions for the SplitPane
        splitPane.setDividerPositions(0.10, 0.90);

        populateCourses(fileName);
        leaderboard(leader);

        takeQuiz.setOnAction(e-> {
            try {
                com.quizapp.Actions.StudentMain.openTakeQuiz();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Courses.setOnAction(e-> {
            try {
                com.quizapp.Actions.StudentMain.openCourses();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void populateCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String course;
            int row = 0;

            // Reading file line by line and adding Labels to the GridPane
            while ((course = reader.readLine()) != null) {
                Label courseLabel = new Label(course);
                courseLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
                GridPane gridPane = new GridPane();
                gridPane.add(courseLabel, 0,0);
                gridPane.setAlignment(Pos.CENTER_LEFT);
                numberGrid.add(gridPane, 0, row++); // Add each course to a new row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leaderboard(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String course;
            int row = 0;

            // Reading file line by line and adding Labels to the GridPane
            while ((course = reader.readLine()) != null) {
                Label courseLabel = new Label(course);
                courseLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
                rank.add(courseLabel, 0, row++); // Add each course to a new row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
