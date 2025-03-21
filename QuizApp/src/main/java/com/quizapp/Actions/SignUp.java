package com.quizapp.Actions;

import com.quizapp.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class SignUp extends App{
    private final TextField name;
    private final TextField nickname;
    private final TextField username;
    private final PasswordField password;
    private final PasswordField confirmPassword;
    private final Button signUpButton;
    private final Label message;
    private final ComboBox<String> roleComboBox;
    private final String leaderBoard = "src/main/resources/leaderboard/leader.txt";

    // Constructor to pass the references of UI components
    public SignUp(TextField name, TextField nickname, TextField username,
                  PasswordField password, PasswordField confirmPassword,
                  Button signUpButton, Label message, ComboBox<String> roleComboBox) {
        this.name = name;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.signUpButton = signUpButton;
        this.message = message;
        this.roleComboBox = roleComboBox;
    }

    // Method to set up the actions
    public void setUpActions() {
        // Focus traversal with Enter key
        name.setOnAction(e -> nickname.requestFocus());
        nickname.setOnAction(e -> username.requestFocus());
        username.setOnAction(e -> password.requestFocus());
        password.setOnAction(e -> confirmPassword.requestFocus());
        confirmPassword.setOnAction(e -> signUpButton.fire());

        // Sign-up button action
        signUpButton.setOnAction(e -> handleSignUpAction());
    }

    // Handle the sign-up logic with specific error messages for each empty field
    private void handleSignUpAction() {
        String fullName = name.getText();
        String nickName = nickname.getText();
        String usernameText = username.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();
        String role = roleComboBox.getValue();

        // Validate each field individually and set a specific error message
        if (fullName.isEmpty()) {
            message.setTextFill(Color.RED);
            message.setText("Please enter your full name.");
            name.requestFocus();
            return;
        }

        if (nickName.isEmpty()) {
            message.setTextFill(Color.RED);
            message.setText("Please enter your nickname.");
            nickname.requestFocus();
            return;
        }

        if (usernameText.isEmpty()) {
            message.setTextFill(Color.RED);
            message.setText("Please enter a username.");
            username.requestFocus();
            return;
        } else {
            App.username = usernameText;
        }

        if (passwordText.isEmpty()) {
            message.setTextFill(Color.RED);
            message.setText("Please enter a password.");
            password.requestFocus();
            return;
        }

        if (confirmPasswordText.isEmpty()) {
            message.setTextFill(Color.RED);
            message.setText("Please confirm your password.");
            confirmPassword.requestFocus();
            return;
        }

        if (role == null) {
            message.setTextFill(Color.RED);
            message.setText("Please select a role.");
            roleComboBox.requestFocus();
            return;
        }

        // Check if passwords match
        if (!passwordText.equals(confirmPasswordText)) {
            message.setTextFill(Color.RED);
            message.setText("Passwords do not match.");
            confirmPassword.requestFocus();
            return;
        }

        // Save the user's information
        boolean isSaved = saveUserCredentials(usernameText, passwordText, fullName, nickName, role);
        saveUserFile(role, App.username);
        if (isSaved) {
            message.setTextFill(Color.GREEN);
            message.setText("Sign-up successful! You can now log in.");
        } else {
            message.setTextFill(Color.RED);
            message.setText("An error occurred. Please try again.");
        }
        System.out.println("Selected Role: " + role);
        if ("Student".equals(role)){
            String leaderFilePath = "src/main/resources/leaderboard/leader.txt";
            String contentToAppend = "0," + App.username;

            try (FileWriter writer = new FileWriter(leaderFilePath, true)) { // Open in append mode
                writer.append(contentToAppend).append("\n"); // Append the content followed by a new line
                System.out.println("Appended to leader.csv: " + contentToAppend);
            } catch (IOException e) {
                System.err.println("An error occurred while writing to leader.csv:");
                e.printStackTrace(); // Log the error for debugging
            }
        }
    }
    // Method to create a file if it does not already exist
    private void createFileIfNotExists(String directoryPath, String fileName) throws IOException {
        File directory = new File(directoryPath);
        File file = new File(directoryPath, fileName);

        // Ensure the directory exists
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn’t exist
        }

        // Create the file only if it doesn’t exist
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            }
        }
    }

    private boolean saveUserFile(String role, String username) {
        String userDirectory = role.equals("Teacher")
                ? "src/main/resources/teacherInfo/"
                : "src/main/resources/studentInfo/";
        String userFilePath = userDirectory + username + ".csv";

        try {
            createFileIfNotExists(userDirectory, username + ".csv");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to save user credentials to a file
    private boolean saveUserCredentials(String username, String password, String fullName, String nickName, String role) {
        // Determine the filename based on the role
        String fileName = role.equals("Teacher") ? "teacher.csv" : "student.csv";
        String filePath = "src/main/resources/credential/" + fileName;
        File directory = new File("src/main/resources/credential");

        try {
            if (!directory.exists()) {
                directory.mkdirs();  // Create the directory if it doesn’t exist
            }

            File csvFile = new File(directory, fileName);
            if (!csvFile.exists()) {
                csvFile.createNewFile();  // Create the file if it doesn’t exist
            }

            try (FileWriter writer = new FileWriter(csvFile, true)) {
                // Writing the data in CSV format
                writer.append(escapeForCSV(username)).append(',')
                        .append(escapeForCSV(password)).append(',')
                        .append(escapeForCSV(fullName)).append(',')
                        .append(escapeForCSV(nickName)).append(',')
                        .append(escapeForCSV(role)).append('\n');
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/LoginPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    // Helper method to escape strings for CSV
    private String escapeForCSV(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = "\"" + value.replace("\"", "\"\"") + "\""; // Escape double quotes by doubling them
        }
        return value;
    }
}
