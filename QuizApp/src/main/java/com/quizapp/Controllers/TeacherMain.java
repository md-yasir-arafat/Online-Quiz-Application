package com.quizapp.Controllers;

import javafx.fxml.FXML;
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

public class TeacherMain {
    public Button addCourses;

    @FXML
    private ImageView logoImage; // Ensure this matches the FXML fx:id

    @FXML
    private ImageView userImage; // Fixed spelling to match FXML fx:id

    @FXML
    private GridPane numberGrid;


    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";


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


        addCourses.setOnAction(e-> {
            try {
                com.quizapp.Actions.TeacherMain.openAddCourses();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }


}
