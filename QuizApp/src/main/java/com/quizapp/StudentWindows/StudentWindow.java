package com.quizapp.StudentWindows;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class StudentWindow {
    private static final String LOGO_PATH = "/images/logo.png";
    private static final double LOGO_WIDTH = 70;
    private static final double LOGO_HEIGHT = 50;
    private static final int SPACING = 10;

    public void show() {
        Stage studentStage = new Stage();
        VBox vbox = new VBox(new Label("Welcome, Student!"));
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 300, 200);

        studentStage.setScene(scene);
        studentStage.setTitle("Student Dashboard");
        studentStage.setMaximized(true);
        studentStage.show();
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
        signUpButton.getStyleClass().add("button");


        HBox header = new HBox(SPACING, gridLogo, signUpButton);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15, 150, 2, 50)); //top, right, bottom, left
        return header;
    }
}
