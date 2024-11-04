package com.quizapp.LoginWindow;

import com.quizapp.SignUpWindow.SignUpWindow;
import com.quizapp.StudentWindows.StudentWindow;
import com.quizapp.TeacherWindows.TeacherWindow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class LoginAction {

    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button loginButton;
    private final Label messageLabel;

    // Constructor to pass the references of UI components
    public LoginAction(TextField usernameField, PasswordField passwordField, Button loginButton, Label messageLabel) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginButton = loginButton;
        this.messageLabel = messageLabel;
    }

    // Method to set up the actions
    public void setupActions() {
        // Handle pressing "Enter" in username field to move to password
        usernameField.setOnAction(e -> passwordField.requestFocus());

        // Handle pressing "Enter" in password field to trigger login
        passwordField.setOnAction(e -> loginButton.fire());

        // Handle login button action
        loginButton.setOnAction(e -> handleLoginAction());
    }

    // Handle the login logic
    private void handleLoginAction() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        int loginResult = handleLogin(username, password);

        if (loginResult == 1) {  // Teacher login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a teacher.");
            new TeacherWindow().show();  // Open teacher window
            closeCurrentWindow();
        } else if (loginResult == 2) {  // Student login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a student.");
            new StudentWindow().show();  // Open student window
            closeCurrentWindow();
        } else {  // Invalid login
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public int handleLogin(String username, String password) {
        if (SignupAction.verifyCredentials(username, password, "teacher.csv")) {
            return 1;
        } else if (SignupAction.verifyCredentials(username, password, "student.csv")) {
            return 2;
        } else {
            return 0; // Invalid credentials
        }
    }

    // Close the current login window
    private void closeCurrentWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();  // Close the current stage (login window)
    }
}


class SignupAction {
    private final Button signupButton;

    SignupAction(Button signupButton) {
        this.signupButton = signupButton;
        signupButton.setOnAction(e -> openSignUpWindow());
    }

    // Method to open the sign-up window
    private void openSignUpWindow() {
        SignUpWindow signUpWindow = new SignUpWindow();
        Stage signUpStage = new Stage();
        try {
            signUpWindow.start(signUpStage);  // Open sign-up window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyCredentials(String username, String password, String fileName) {
        String line;
        String filePath = "src/main/resources/credential/" + fileName;  // Use the correct file path

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // Splitting line into values
                String[] values = line.split(",");

                // Assuming username is in the first position and password in the second
                String fileUsername = values[0].trim().replace("\"", ""); // Remove quotes if present
                String filePassword = values[1].trim().replace("\"", ""); // Remove quotes if present

                // Checking if username and password match
                if (fileUsername.equals(username) && filePassword.equals(password)) {
                    return true; // Valid credentials
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        return false; // Invalid credentials
    }
}
