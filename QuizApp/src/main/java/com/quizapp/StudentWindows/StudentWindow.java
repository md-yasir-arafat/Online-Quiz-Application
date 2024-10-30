package com.quizapp.StudentWindows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentWindow {

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
}
