package com.quizapp.Actions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentMain {
    public static void openTakeQuiz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/TakeQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }

    public static void openCourses() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/Courses.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("My Courses");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
