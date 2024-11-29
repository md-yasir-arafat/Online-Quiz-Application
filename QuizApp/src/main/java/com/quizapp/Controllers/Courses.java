package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class Courses {
    public Button Home;
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

        Home.setOnAction(e -> {
            try {
                Login.openTeacherMain();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


}
