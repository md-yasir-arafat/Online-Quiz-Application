package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.Controllers.TakeQuizPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TakeQuiz {
    protected static String filePath;
    protected static String courseName;

    protected void updateLeaderFile(int scoreToAdd) throws IOException {
        // Read the leaderboard file
        List<String[]> leaders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.leaderBoard))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                leaders.add(data);
            }
        }

        // Update or add the user score
        boolean userFound = false;
        for (String[] leader : leaders) {
            if (leader[1].equals(App.username)) {
                int currentScore = Integer.parseInt(leader[0].trim());
                leader[0] = String.valueOf(currentScore + scoreToAdd);
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            leaders.add(new String[]{String.valueOf(scoreToAdd), App.username});
        }

        // Sort the leaderboard by scores in descending order
        leaders.sort((a, b) -> Integer.parseInt(b[0]) - Integer.parseInt(a[0]));

        // Write back to the leaderboard file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(App.leaderBoard))) {
            for (String[] leader : leaders) {
                bw.write(String.join(" ", leader));
                bw.newLine();
            }
        }
    }

    protected void updateQuizTakenCount(String courseName) {
        String file = "src/main/resources/studentInfo/" + App.username + ".csv";
        List<String[]> rows = new ArrayList<>();

        try {
            // Step 1: Read the CSV file
            System.out.println("Debug: Reading CSV file at " + file);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("Debug: Read line: " + line);
                    rows.add(line.split(","));
                }
            }

            // Step 2: Update quiz count
            boolean courseFound = false;
            for (String[] row : rows) {
                if (row[0].equals(courseName)) {
                    int quizTakenCount = Integer.parseInt(row[2].trim());
                    quizTakenCount++;
                    row[2] = String.valueOf(quizTakenCount);
                    System.out.println("Debug: Updated course " + courseName + " to " + quizTakenCount);
                    courseFound = true;
                    break;
                }
            }

            if (!courseFound) {
                System.err.println("Error: Course " + courseName + " not found in CSV.");
                return;
            }

            // Step 3: Write back to the CSV file
            System.out.println("Debug: Writing updates to CSV file at " + file);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String[] row : rows) {
                    String line = String.join(",", row);
                    System.out.println("Debug: Writing line: " + line);
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void openTakeQuizPage(String quizDir, AtomicReference<String> fileName) throws IOException {
        filePath = quizDir+ String.valueOf(fileName);
        courseName = quizDir;
        courseName = courseName.endsWith("/") ? courseName.substring(0, courseName.length() - 1) : courseName;
        // Extract the folder name
        courseName = courseName.substring(courseName.lastIndexOf("/") + 1);


        FXMLLoader fxmlLoader = new FXMLLoader(TakeQuizPage.class.getResource("/com/quizapp/TakeQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setMaximized(true);
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
