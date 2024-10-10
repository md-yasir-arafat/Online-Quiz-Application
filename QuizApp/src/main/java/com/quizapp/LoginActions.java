package com.quizapp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginActions {

    private final TextField Username;
    private final PasswordField Password;
    private final Button Login;
    private final Label Message;

    // Constructor to pass the references of UI components
    public LoginActions(TextField Username, PasswordField Password, Button Login, Label Message) {
        this.Username = Username;
        this.Password = Password;
        this.Login = Login;
        this.Message = Message;
    }

    // Method to set up the actions
    public void setupActions() {
        // Handle pressing "Enter" in username field to move to password
        Username.setOnAction(e -> Password.requestFocus());

        // Handle pressing "Enter" in password field to trigger login
        Password.setOnAction(e -> Login.fire());

        // Handle login button action
        Login.setOnAction(e -> handleLoginAction());
    }

    // Handle the login logic
    private void handleLoginAction() {
        String username = Username.getText();
        String password = Password.getText();
        boolean isSuccess = handleLogin(username, password);

        if (isSuccess) {
            Message.setTextFill(Color.GREEN);
            Message.setText("Login successful!");
        } else {
            Message.setTextFill(Color.RED);
            Message.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public boolean handleLogin(String username, String password) {
        // Simple validation for demonstration purposes
        return username.equals("user") && password.equals("password");
    }
}
