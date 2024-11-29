package com.quizapp.Actions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class Login {

    private final TextField usernameField;
    private final PasswordField passwordField;
    private static Button loginButton = null;
    private final Label messageLabel;

    // Constructor to pass the references of UI components
    public Login(TextField usernameField, PasswordField passwordField, Button loginButton, Label messageLabel) {
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
        loginButton.setOnAction(e -> {
            try {
                handleLoginAction();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // Handle the login logic
    private void handleLoginAction() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
//        int loginResult = handleLogin(username, password);
        int loginResult = 2;

        if (loginResult == 1) {  // Teacher login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a teacher.");
            openTeacherMain();  // Open teacher window
            closeCurrentWindow();
        } else if (loginResult == 2) {  // Student login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a student.");
            openStudentMain();
            closeCurrentWindow();
        } else {  // Invalid login
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public int handleLogin(String username, String password) {
        if (verifyCredentials(username, password, "teacher.csv")) {
            return 1;
        } else if (verifyCredentials(username, password, "student.csv")) {
            return 2;
        } else {
            return 0; // Invalid credentials
        }
    }

    // Method to open the sign-up window
    public static void openTeacherMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/TeacherMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage signUpStage = new Stage();
        signUpStage.setMaximized(true);
        signUpStage.setTitle("Teacher Main Page");
        signUpStage.setScene(scene);

        signUpStage.show(); // Open sign-up window
    }


    // Method to open the sign-up window
    public static void openSignUpWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/SignUpPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage signUpStage = new Stage();
        signUpStage.setMaximized(true);
        signUpStage.setTitle("SignUp Page");
        signUpStage.setScene(scene);

        signUpStage.show(); // Open sign-up window
    }

    public void openStudentMain() throws IOException {
        // Ensure the path to FXML is correct and matches runtime packaging
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/StudentMain.fxml"));

        // Load the FXML file and create the scene
        Scene scene = new Scene(fxmlLoader.load());
        Stage studentMainStage = new Stage();
        studentMainStage.setMaximized(true);
        studentMainStage.setTitle("Student Main Page");
        studentMainStage.setScene(scene);
        studentMainStage.show();
    }

    public boolean verifyCredentials(String username, String password, String fileName) {
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

    // Close the current login window
    public void closeCurrentWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();  // Close the current stage (login window)
    }
}

