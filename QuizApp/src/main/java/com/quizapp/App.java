package com.quizapp;

import com.quizapp.Actions.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static double sceneWidth = 0;
    public static String username;
    public static String leaderBoard = "src/main/resources/leaderboard/leader.txt";
    public static final String LOGO_PATH = "/images/logo.png";
    public static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    public static final String BACKGROUND_Main_IMAGE_PATH = "/images/Student-Studying.jpg";




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/LoginPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
        sceneWidth = scene.getWidth();
    }

    public static void main(String[] args) {
        launch();
    }
}