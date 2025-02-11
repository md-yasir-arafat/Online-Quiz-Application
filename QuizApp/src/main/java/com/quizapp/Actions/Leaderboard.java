package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.Controllers.LeaderboardPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Leaderboard extends App {
    public static void openLeaderBoard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LeaderboardPage.class.getResource("/com/quizapp/LeaderBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Leaderboard");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
