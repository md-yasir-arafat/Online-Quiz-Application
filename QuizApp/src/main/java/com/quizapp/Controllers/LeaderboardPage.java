package com.quizapp.Controllers;

import com.quizapp.Actions.Leaderboard;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.quizapp.Actions.Enroll.openEnrollPage;
import static com.quizapp.Actions.Login.openStudentMain;

public class LeaderboardPage extends Leaderboard {
    @FXML
    private Button home;
    @FXML
    private Button enroll;
    @FXML
    private TableView<Player> leaderboardTable;

    @FXML
    private TableColumn<Player, Integer> rankColumn;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, Integer> scoreColumn;

    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;

    private static final String LEADERBOARD_FILE = "src/main/resources/leaderboard/leader.txt";

    @FXML
    private void initialize() {

        try {
            // Initialize the logo
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));

            // Set the user image (background)
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        // Set up table columns
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Populate the leaderboard with data from the file
        leaderboardTable.getItems().setAll(loadLeaderboardData());

        home.setOnAction(e -> {
            try {
                openStudentMain();
                closeCurrentWindow(home);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        enroll.setOnAction(e -> {
            try {
                openEnrollPage();
                closeCurrentWindow(enroll);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Reads leaderboard data from the leader.txt file.
     *
     * @return A list of Player objects.
     */
    private List<Player> loadLeaderboardData() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(LEADERBOARD_FILE))) {
            String line;
            int rank = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2); // Split by the first space
                if (parts.length == 2) {
                    int score = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    players.add(new Player(rank++, name, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading leaderboard data: " + e.getMessage());
        }
        return players;
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
}
