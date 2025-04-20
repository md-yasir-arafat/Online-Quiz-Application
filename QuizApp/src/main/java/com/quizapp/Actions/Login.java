package com.quizapp.Actions;

import com.quizapp.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class Login extends  App{

    // Handle the login logic
    public void handleLoginAction(String username, String password, Label messageLabel, Button loginButton) throws IOException {
        int loginResult = handleLogin(username, password);

        if (loginResult == 1) {  // Teacher login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a teacher.");
            openTeacherMain();  // Open teacher window
            closeCurrentWindow(loginButton);
        } else if (loginResult == 2) {  // Student login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a student.");
            openStudentMain();
            closeCurrentWindow(loginButton);
        } else {  // Invalid login
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public int handleLogin(String username, String password) {
        if (verifyCredentials(username, password, "teacher.csv")) {
            App.username = username;
            return 1;
        } else if (verifyCredentials(username, password, "student.csv")) {
            App.username = username;
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

    public static void openStudentMain() throws IOException {
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
}

