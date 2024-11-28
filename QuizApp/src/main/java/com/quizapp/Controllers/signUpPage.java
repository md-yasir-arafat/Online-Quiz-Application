package com.quizapp.Controllers;


import com.quizapp.Actions.SignUp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class signUpPage {

    public ImageView mainImage;
    private static final String BACKGROUND_IMAGE_PATH = "/images/Student-Studying.jpg";
    @FXML
    private ImageView logoImage, frontImage;

    @FXML
    private Button signInButton, signUpButton;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField fullNameField, nickNameField, usernameField;

    @FXML
    private PasswordField passwordField, confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        // Load the logo image
        logoImage.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));

        // Initialize the main image and resize it dynamically
        Image mainBgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH)));
        mainImage.setImage(mainBgImage);

        // Add items to the role combo box
        roleComboBox.getItems().addAll("Teacher", "Student");

        // Set up sign-up action (optional: custom class or lambda)
        signUpButton.setOnAction(event -> handleSignUp());
    }

    private void handleSignUp() {
        String role = roleComboBox.getValue();
        String fullName = fullNameField.getText();
        String nickName = nickNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        SignUp signUp = new SignUp(
                fullNameField,
                nickNameField,
                usernameField,
                passwordField,
                confirmPasswordField,
                signUpButton,
                messageLabel,
                roleComboBox
        );
        signUp.setUpActions();
    }
}
