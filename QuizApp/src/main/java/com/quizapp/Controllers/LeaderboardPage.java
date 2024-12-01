package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaderboardPage {

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    @FXML
    private TableView<Player> leaderboardTable;

    @FXML
    private TableColumn<Player, Integer> rankColumn;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, Integer> scoreColumn;

    @FXML
    private void initialize() {
        // Set up table columns
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Add sample data (replace with real data fetching logic)
        leaderboardTable.getItems().addAll(
                new Player(1, "Alice", 1500),
                new Player(2, "Bob", 1400),
                new Player(3, "Charlie", 1300)
        );
    }

    // Helper class for leaderboard data
    public static class Player {
        private final int rank;
        private final String name;
        private final int score;

        public Player(int rank, String name, int score) {
            this.rank = rank;
            this.name = name;
            this.score = score;
        }

        public int getRank() {
            return rank;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }

    public static void openLeaderBoard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TakeQuizPage.class.getResource("/com/quizapp/LeaderBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setMaximized(true);
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}