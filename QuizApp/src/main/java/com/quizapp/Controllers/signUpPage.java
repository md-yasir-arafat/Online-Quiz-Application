package com.quizapp.Controllers;

import com.quizapp.Actions.SignUp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class signUpPage {

    public ImageView mainImage;
    private static final String BACKGROUND_IMAGE_PATH = "/images/Student-Studying.jpg";
    @FXML
    private ImageView logoImage;

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

        // Set up key event handlers to move focus to the next field on ENTER key press
        fullNameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                nickNameField.requestFocus();
            }
        });

        nickNameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                usernameField.requestFocus();
            }
        });

        usernameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                confirmPasswordField.requestFocus();
            }
        });

        confirmPasswordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                signUpButton.requestFocus();
            }
        });

        // Set up sign-up action (optional: custom class or lambda)
        signUpButton.setOnAction(event -> handleSignUp());

        signInButton.setOnAction(e -> {
            try {
                SignUp.start();
                closeCurrentWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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

    public void closeCurrentWindow() {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.close();  // Close the current stage (sign-up window)
    }
}

