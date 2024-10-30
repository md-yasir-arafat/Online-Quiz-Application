package com.quizapp.LoginWindow;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginPageUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Header Section using HBox
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(10);  // Adjust spacing between elements

        header.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double paddingLeft = width * 0.09;
            double paddingRight = width * 0.12;
            header.setPadding(new Insets(15, (int) paddingRight, 2, (int) paddingLeft)); // Top, Right, Bottom, Left
        });

        // logo image
        ImageView logoImage = new ImageView();
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png")));
        logoImage.setImage(logo);
        logoImage.setFitWidth(70);
        logoImage.setFitHeight(50);

        // logo name/ app name
        Label lblLogo = new Label("Quize");
        lblLogo.getStyleClass().add("logo-text");

        // Left side of the header with logo and label
        HBox gridLogo = new HBox(10, logoImage, lblLogo);
        gridLogo.setAlignment(Pos.CENTER_LEFT);

        // Stretch this HBox so it fills available space
        HBox.setHgrow(gridLogo, Priority.ALWAYS);

        // Signup button on the right
        Button signBtn = new Button("Sign up");
        signBtn.getStyleClass().add("button");
        HBox logobtn = new HBox(signBtn);
        logobtn.setAlignment(Pos.CENTER_RIGHT);

        // Add left (gridLogo) and right (logobtn) to the header
        header.getChildren().addAll(gridLogo, logobtn);


        // Main image of the login page
        GridPane gridImage = new GridPane();
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Student-Studying.jpg")));
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            imageView.setFitWidth(newVal.doubleValue() * 0.4); // Set ImageView width to 40% of the screen width
        });
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        gridImage.add(imageView, 0, 0);

        // Main log in section
        TextField Username = new TextField();
        PasswordField Password = new PasswordField();
        Label forgotPassword = new Label("Forgot Password?");
        Button loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("button");
        Label lblMessage = new Label();

        // Layout using GridPane for the form
        GridPane innerBox = new GridPane();
        GridPane outerBox = new GridPane();

        innerBox.setAlignment(Pos.CENTER);
        innerBox.setHgap(10);
        innerBox.setVgap(10);

        // innerBox elements
        innerBox.add(Username, 0, 0);
        innerBox.add(Password, 0, 1);
        innerBox.add(forgotPassword, 0, 2);
        forgotPassword.setAlignment(Pos.CENTER_LEFT);
        innerBox.add(loginBtn, 0, 3);
        innerBox.add(lblMessage, 0, 4);

        // using outerBox to center the innerBox
        outerBox.add(innerBox, 0 , 0);
        outerBox.setAlignment(Pos.CENTER);

        // prompt message
        Username.setPromptText("username");
        Password.setPromptText("password");

        // Adding css
        Username.getStyleClass().add("text-field");
        Password.getStyleClass().add("text-field");
        innerBox.getStyleClass().add("grid-pane");

        // body grid
        GridPane body = new GridPane();
        body.setAlignment(Pos.CENTER);
        body.setHgap(200);
        body.add(header,0, 0);  // Header including gridLogo and logobtn
        body.add(imageView, 0, 2);  // Add image view to the grid
        body.add(outerBox, 1, 2);   // Add form to the grid next to the image

        // Use VBox for vertical alignment and stretchability
        VBox vBox = new VBox(20, body);
        vBox.setAlignment(Pos.CENTER);

        // Use BorderPane to center everything
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(header);  // Place header at the top
        borderPane.setCenter(vBox);
        borderPane.setStyle("-fx-background-color: White;");

        // Create a scene and add the CSS stylesheet
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page with Background");

        // Maximize the window to take full screen space
        primaryStage.setMaximized(true);

        // Delegate the event handling to LoginActions class
        LoginActions loginActions = new LoginActions(Username, Password, loginBtn, lblMessage, signBtn);
        loginActions.setupActions();  // Call the method to set up the actions

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
