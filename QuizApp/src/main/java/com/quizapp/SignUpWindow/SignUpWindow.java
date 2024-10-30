package com.quizapp.SignUpWindow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpWindow extends Application {
    @Override
    public void start(Stage signUpStage) {
        // Create UI elements
        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter username");

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter password");

        Label lblConfirmPassword = new Label("Confirm Password:");
        PasswordField txtConfirmPassword = new PasswordField();
        txtConfirmPassword.setPromptText("Confirm password");

        Button btnSignUp = new Button("Sign Up");
        btnSignUp.getStyleClass().add("button");

        // Layout using GridPane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(lblUsername, 0, 0);
        gridPane.add(txtUsername, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(txtPassword, 1, 1);
        gridPane.add(lblConfirmPassword, 0, 2);
        gridPane.add(txtConfirmPassword, 1, 2);

        // Sign-up button positioned at the bottom
        VBox vbox = new VBox(10, gridPane, btnSignUp);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Scene setup
        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        // Set up stage
        signUpStage.setTitle("Sign Up");
        signUpStage.setScene(scene);
        signUpStage.setMaximized(true);
        signUpStage.show();

        // Add functionality for sign-up button (can be expanded to add validation, etc.)
        btnSignUp.setOnAction(event -> {
            // Example action: print the input values
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();

            if (password.equals(confirmPassword)) {
                System.out.println("Sign-up successful for: " + username);
                // Logic for sign-up action (e.g., save user to database)
            } else {
                System.out.println("Passwords do not match!");
            }
        });
    }
}

