package com.quizapp.SignUpWindow;

import com.quizapp.LoginWindow.LoginPageUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class SignUpWindow extends Application {

    private static final String LOGO_PATH = "/images/logo.png";
    private static final double LOGO_WIDTH = 70;
    private static final double LOGO_HEIGHT = 50;
    private static final int SPACING = 10;

    @Override
    public void start(Stage signUpStage) {
        // Header
        HBox header = createHeader();

        GridPane gridImage = LoginPageUI.frontPageImage(signUpStage);
        GridPane imageForm = new GridPane();

        imageForm.add(gridImage, 0, 0);
        imageForm.add(outerBox(), 1, 0);
        imageForm.setHgap(200);
        imageForm.setAlignment(Pos.CENTER);

        // Layout using GridPane
        GridPane body = new GridPane();
        body.setHgap(10);
        body.setVgap(10);
        body.add(header, 0, 0);
        body.add(imageForm, 0, 1);
        body.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(header);
        pane.setCenter(body);

        // Scene setup
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        // Set up stage
        signUpStage.setTitle("Sign Up");
        signUpStage.setScene(scene);
        signUpStage.setMaximized(true);
        signUpStage.show();
    }

    // Create header with logo and app name
    private HBox createHeader() {
        // Logo and Label
        ImageView logoImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
        logoImage.setFitWidth(LOGO_WIDTH);
        logoImage.setFitHeight(LOGO_HEIGHT);
        Label lblLogo = new Label("Quize");
        lblLogo.getStyleClass().add("logo-text");

        // Logo and app name alignment
        HBox gridLogo = new HBox(SPACING, logoImage, lblLogo);
        gridLogo.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(gridLogo, Priority.ALWAYS);

        // Sign In button
        Button signInButton = new Button("Sign In");
        signInButton.getStyleClass().add("button");

        HBox header = new HBox(SPACING, gridLogo, signInButton);
        header.setPadding(new Insets(15, 150, 2, 50)); // top, right, bottom, left
        return header;
    }

    private HBox pageTitle() {
        Label lbl = new Label("Welcome to Quize");
        lbl.getStyleClass().add("title-text"); // Assuming this CSS class styles the title text

        HBox titleBox = new HBox(lbl);
        titleBox.setAlignment(Pos.CENTER); // Center the title within the HBox

        return titleBox;
    }

    private GridPane createSignUpForm() {
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        // Row 1: Role Selection (Are you a Teacher or Student?)
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Teacher", "Student");
        roleComboBox.setPromptText("Are you a Teacher or Student?");
        formGrid.add(roleComboBox, 0, 0, 2, 1);  // Span across 2 columns

        // Row 2: Full Name
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Your Full Name");
        formGrid.add(fullNameField, 0, 1, 2, 1);

        // Row 3: Nickname
        TextField nickNameField = new TextField();
        nickNameField.setPromptText("Your Nickname");
        formGrid.add(nickNameField, 0, 2, 2, 1);

        // Row 4: Username
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        formGrid.add(usernameField, 0, 3, 2, 1);

        // Row 5: Password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        formGrid.add(passwordField, 0, 4, 2, 1);

        // Row 6: Confirm Password
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        formGrid.add(confirmPasswordField, 0, 5, 2, 1);

        // Row 7: Sign Up Button
        Button signUpButton = new Button("Sign Up");
        HBox buttonBox = new HBox(signUpButton);
        buttonBox.setAlignment(Pos.CENTER);
        formGrid.add(buttonBox, 0, 6, 2, 1);

        // Row 8: Message Label for feedback
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);  // Default color for error
        formGrid.add(messageLabel, 0, 7, 2, 1);  // Span across 2 columns

        // Instantiate SignUpAction to handle signup logic, passing messageLabel
        SignUpAction signUpAction = new SignUpAction(
                fullNameField,
                nickNameField,
                usernameField,
                passwordField,
                confirmPasswordField,
                signUpButton,
                messageLabel,
                roleComboBox
        );
        signUpAction.setupActions();  // Set up Enter key actions and sign-up button

        return formGrid;
    }

    private GridPane outerBox() {
        GridPane formLayout = new GridPane();

        formLayout.add(pageTitle(), 0, 0);
        formLayout.add(createSignUpForm(), 0, 1);
        formLayout.getStyleClass().add("signUpForm");

        GridPane outerBox = new GridPane();
        outerBox.add(formLayout, 0, 0);

        outerBox.setAlignment(Pos.CENTER);

        return outerBox;
    }
}
