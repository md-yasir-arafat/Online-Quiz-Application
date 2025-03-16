package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginPage {

    // FXML Components
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView mainImage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label lblMessage;

    @FXML
    private Button signUpButton;

    @FXML
    public void initialize() {
        // Initialize the logo
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));

        // Initialize the main image
        Image mainBgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_Main_IMAGE_PATH)));
        mainImage.setImage(mainBgImage);


        // Set up key event handlers to move focus to the next field on ENTER key press
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                loginButton.requestFocus();
                loginButton.fire();
            }
        });

        // Add functionality to buttons (Sign Up and Login)
        signUpButton.setOnAction(e -> {
            try {
                handleSignUp();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginButton.setOnAction(e -> {
            try {
                handleLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleSignUp() throws IOException {
        // Handle sign-up logic here
        Login.openSignUpWindow();
        lblMessage.setText("Sign up button clicked!");
        closeCurrentWindow();
    }

    private void handleLogin() throws IOException {
        // Validate login credentials
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Delegate the event handling to LoginActions class
        Login login = new Login(usernameField, passwordField, loginButton, lblMessage);
        login.handleLoginAction(username,password);  // Call the method to set up the actions
    }

    // Close the current login window
    public void closeCurrentWindow() {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();  // Close the current stage (login window)
    }
}
