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

public class LoginActions {

    private final TextField Username;
    private final PasswordField Password;
    private final Button Login;
    private final Label Message;
    private final Button signup;  // Added signup button

    // Constructor to pass the references of UI components
    public LoginActions(TextField Username, PasswordField Password, Button Login, Label Message, Button signup) {
        this.Username = Username;
        this.Password = Password;
        this.Login = Login;
        this.Message = Message;
        this.signup = signup;  // Initialize signup button
    }

    // Method to set up the actions
    public void setupActions() {
        // Handle pressing "Enter" in username field to move to password
        Username.setOnAction(e -> Password.requestFocus());

        // Handle pressing "Enter" in password field to trigger login
        Password.setOnAction(e -> Login.fire());

        // Handle login button action
        Login.setOnAction(e -> handleLoginAction());

        // Handle signup button action
        signup.setOnAction(e -> openSignUpWindow());
    }

    // Handle the login logic
    private void handleLoginAction() {
        String username = Username.getText();
        String password = Password.getText();
        int isSuccess = handleLogin(username, password);

        if (isSuccess == 1) {  // Teacher login
            Message.setTextFill(Color.GREEN);
            Message.setText("Login successful!\nYou are a teacher.");
            new TeacherWindow().show();  // Open teacher window
            closeCurrentWindow();
        } else if (isSuccess == 2) {  // Student login
            Message.setTextFill(Color.GREEN);
            Message.setText("Login successful!\nYou are a student.");
            new StudentWindow().show();  // Open student window
            closeCurrentWindow();
        } else {  // Invalid login
            Message.setTextFill(Color.RED);
            Message.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public int handleLogin(String username, String password) {
        // Simple validation for demonstration purposes
        if (username.equals("teacher") && password.equals("password")) {
            return 1;
        } else if (username.equals("student") && password.equals("password")) {
            return 2;
        } else {
            return 0;
        }
    }

    // Method to open the sign-up window
    private void openSignUpWindow() {
        SignUpWindow signUpWindow = new SignUpWindow();
        Stage signUpStage = new Stage();
        try {
            signUpWindow.start(signUpStage);  // Open sign-up window
            closeCurrentWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Close the current login window
    private void closeCurrentWindow() {
        Stage stage = (Stage) Login.getScene().getWindow();
        stage.close();  // Close the current stage (login window)
    }
}
