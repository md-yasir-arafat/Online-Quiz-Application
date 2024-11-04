package com.quizapp.StudentWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private static final String USER_PATH = "/images/user.png";
    private static final String STYLESHEET_PATH = "/css/style.css";

    public void show() {
        HBox header = createHeader();
        VBox sideBar = sideBar();

        // Main content placeholder
        GridPane content = new GridPane();
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        // Add a label to indicate the page is under development
        Label underDevelopmentLabel = new Label("This feature is under development.");
        underDevelopmentLabel.getStyleClass().add("development-label"); // Optional: add style class
        content.add(underDevelopmentLabel, 0, 0); // Add label at position (0, 0)

        // Layout setup using BorderPane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);       // Set header at the top
        mainLayout.setLeft(sideBar);      // Set sidebar on the left
        mainLayout.setCenter(content);    // Placeholder for main content in the center

        Stage studentStage = new Stage();
        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLESHEET_PATH)).toExternalForm());

        studentStage.setScene(scene);
        studentStage.setTitle("Student Dashboard");
        studentStage.setMaximized(true);
        studentStage.show();
    }

    // Create header with logo and user icon
    public HBox createHeader() {
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

        // User icon
        ImageView userIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(USER_PATH))));
        userIcon.setFitWidth(LOGO_WIDTH);
        userIcon.setFitHeight(LOGO_HEIGHT);

        HBox header = new HBox(SPACING, gridLogo, userIcon);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15, 150, 40, 50)); // top, right, bottom, left

        return header;
    }

    // Sidebar with navigation buttons
    private VBox sideBar() {
        VBox vBox = new VBox();

        Button homebtn = new Button("Home");
        homebtn.getStyleClass().add("sideButton");

        Button takebtn = new Button("Take\nQuiz");
        takebtn.getStyleClass().add("sideButton");

        Button enrolledbtn = new Button("Enrolled\nCourse");
        enrolledbtn.getStyleClass().add("sideButton");

        Button leaderboard = new Button("Leaderboard");
        leaderboard.getStyleClass().add("sideButton");

        // Add buttons to vBox
        vBox.getChildren().addAll(homebtn, takebtn, enrolledbtn, leaderboard);

        // Set spacing, padding, and alignment for VBox
        vBox.setSpacing(20);       // Space between buttons
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(15)); // Padding around sidebar
        vBox.getStyleClass().add("sidebar");

        // Main sidebar VBox
        VBox sidebar = new VBox();

        // Bind vBox height to 70% of sidebar height
        vBox.prefHeightProperty().bind(sidebar.heightProperty().multiply(0.7));
        sidebar.getChildren().add(vBox);

        // Spacer for remaining 30% of the height
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);

        sidebar.setAlignment(Pos.TOP_LEFT);

        return sidebar; // Return sidebar instead of vBox
    }

}
