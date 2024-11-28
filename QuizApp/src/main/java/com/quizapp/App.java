package com.quizapp;

import com.quizapp.Actions.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/LoginPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}