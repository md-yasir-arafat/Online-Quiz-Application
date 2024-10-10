package com.quizapp;

import com.quizapp.LoginActions;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPageUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        txtUsername.setMaxWidth(Double.MAX_VALUE);

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setMaxWidth(Double.MAX_VALUE);

        Button btnLogin = new Button("Login");
        Label lblMessage = new Label();

        // Layout using GridPane for the form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(lblUsername, 0, 0);
        gridPane.add(txtUsername, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(txtPassword, 1, 1);
        gridPane.add(btnLogin, 1, 2);
        gridPane.add(lblMessage, 1, 3);

        // Use VBox for vertical alignment and stretchability
        VBox vBox = new VBox(20, gridPane);
        vBox.setAlignment(Pos.CENTER);

        // Use BorderPane to center everything
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 600, 400); // Initial size
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page with Background");

        // Maximize the window to take full screen space
        primaryStage.setMaximized(true);

        // Delegate the event handling to LoginActions class
        LoginActions loginActions = new LoginActions(txtUsername, txtPassword, btnLogin, lblMessage);
        loginActions.setupActions();  // Call the method to set up the actions

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
