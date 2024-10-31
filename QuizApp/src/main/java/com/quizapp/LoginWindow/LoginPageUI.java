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

    // Constants for layout
    private static final double LOGO_WIDTH = 70;
    private static final double LOGO_HEIGHT = 50;
    private static final double IMAGE_SCALE_RATIO = 0.4;
    private static final int SPACING = 10;
    private static final int FORM_GAP = 10;
    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/Student-Studying.jpg";
    private static final String STYLESHEET_PATH = "/css/style.css";

    @Override
    public void start(Stage primaryStage) {
        // Header Section using HBox
        HBox header = createHeader();

        // Main image of the login page
        GridPane gridImage = frontPageImage(primaryStage);

        // Layout using GridPane for the form
        GridPane formVbox = createForm();


        // body grid
        GridPane body = new GridPane();
        body.setAlignment(Pos.CENTER);
        body.setHgap(200);                       // this thing must be improved
        body.add(header,0, 0);
        body.add(gridImage, 0, 2);
        body.add(formVbox, 1, 2);

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
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLESHEET_PATH)).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page");

        // Maximize the window to take full screen space
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    // Create header with logo and sign-up button
    private HBox createHeader() {
        // Logo and Label
        ImageView logoImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
        logoImage.setFitWidth(LOGO_WIDTH);
        logoImage.setFitHeight(LOGO_HEIGHT);
        Label lblLogo = new Label("Quize");
        lblLogo.getStyleClass().add("logo-text");

        // logo and app name aligning
        HBox gridLogo = new HBox(SPACING, logoImage, lblLogo);
        gridLogo.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(gridLogo, Priority.ALWAYS);

        // Sign Up button
        Button signUpButton = new Button("Sign up");
        signUpButton.setId("signUpBtn");  // Set ID for lookup
        signUpButton.getStyleClass().add("button");

        SignupAction signupAction = new SignupAction(signUpButton);

        HBox header = new HBox(SPACING, gridLogo, signUpButton);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15, 50, 2, 50));
        return header;
    }

    private GridPane frontPageImage(Stage primaryStage){
        GridPane gridImage = new GridPane();
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            imageView.setFitWidth(newVal.doubleValue() * 0.4); // Set ImageView width to 40% of the screen width
        });
        imageView.setPreserveRatio(true);
        gridImage.add(imageView, 0, 0);
        gridImage.setAlignment(Pos.CENTER);

        return gridImage;
    }

    // Create login form with fields and buttons
    private GridPane createForm() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setId("usernameField");  // Set ID for lookup
        usernameField.getStyleClass().add("text-field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setId("passwordField");  // Set ID for lookup
        passwordField.getStyleClass().add("text-field");

        Label forgotPassword = new Label("Forgot Password?");
        forgotPassword.setAlignment(Pos.CENTER_LEFT);

        Button loginBtn = new Button("Login");
        loginBtn.setId("loginBtn");  // Set ID for lookup
        loginBtn.getStyleClass().add("button");

        Label lblMessage = new Label();
        lblMessage.setId("lblMessage");  // Set ID for lookup

        // Form layout
        GridPane formLayout = new GridPane();
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setHgap(FORM_GAP);
        formLayout.setVgap(FORM_GAP);
        formLayout.add(usernameField, 0, 0);
        formLayout.add(passwordField, 0, 1);
        formLayout.add(forgotPassword, 0, 2);
        formLayout.add(loginBtn, 0, 3);
        formLayout.add(lblMessage, 0, 4);
        formLayout.getStyleClass().add("grid-pane");

        // Delegate the event handling to LoginActions class
        LoginAction loginActions = new LoginAction(usernameField, passwordField, loginBtn, lblMessage);
        loginActions.setupActions();  // Call the method to set up the actions

        GridPane outerVBox = new GridPane();
        // using outerVBox to center the innerVBox
        outerVBox.add(formLayout, 0 , 0);
        outerVBox.setAlignment(Pos.CENTER);


        return outerVBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}