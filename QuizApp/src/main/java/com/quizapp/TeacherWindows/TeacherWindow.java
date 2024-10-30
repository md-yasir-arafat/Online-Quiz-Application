package com.quizapp.TeacherWindows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TeacherWindow {

    public void show() {
        Stage teacherWindow = new Stage();

        // Sidebar
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(200);

        Label titleLabel = new Label("Quiz");
        titleLabel.getStyleClass().add("title-label");

        Button homeButton = new Button("Home");
        Button takeQuizButton = new Button("Take Quiz");
        Button responseButton = new Button("Responses");

        // Set button styling
        for (Button button : new Button[]{homeButton, takeQuizButton, responseButton}) {
            button.getStyleClass().add("button");
        }

        sidebar.getChildren().addAll(titleLabel, homeButton, takeQuizButton, responseButton);

        // Header
        HBox header = new HBox();
        header.getStyleClass().add("header");

        Label userLabel = new Label("User");
        userLabel.getStyleClass().add("user-label");
        header.getChildren().add(userLabel);

        // Main content area
        VBox mainContent = new VBox(10);
        mainContent.getStyleClass().add("main-content");

        Label myCoursesLabel = new Label("My Courses");
        myCoursesLabel.getStyleClass().add("course-label");

        HBox courseBox = new HBox(20);
        courseBox.setPadding(new Insets(20, 0, 0, 0));

        // Example course card
        VBox courseCard1 = createCourseCard("CSE 215", "Students Enrolled: 50", "Rating: 90", "Exams Taken: 5");
        VBox courseCard2 = createCourseCard("", "", "", ""); // Empty card
        VBox courseCard3 = createCourseCard("", "", "", ""); // Empty card

        courseBox.getChildren().addAll(courseCard1, courseCard2, courseCard3);
        mainContent.getChildren().addAll(myCoursesLabel, courseBox);

        // Layout for the entire app
        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setTop(header);
        root.setCenter(mainContent);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);
        // Attach the external stylesheet
        scene.getStylesheets().add(getClass().getResource("/css/teacherWindowHome.css").toExternalForm());

        teacherWindow.setScene(scene);
        teacherWindow.setTitle("Teacher Window - Quiz Application");
        teacherWindow.show();
    }

    // Helper method to create a course card
    private VBox createCourseCard(String courseName, String students, String rating, String exams) {
        VBox courseCard = new VBox(10);
        courseCard.getStyleClass().add("course-card");

        Label courseLabel = new Label(courseName);
        Label studentsLabel = new Label(students);
        Label ratingLabel = new Label(rating);
        Label examsLabel = new Label(exams);
        Label detailsButton = new Label("Details");
        detailsButton.getStyleClass().add("details-button");

        courseCard.getChildren().addAll(courseLabel, studentsLabel, ratingLabel, examsLabel, detailsButton);
        return courseCard;
    }
}
