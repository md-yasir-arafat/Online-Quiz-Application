package com.quizapp.Controllers;

import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class TakeQuizPage {

    @FXML
    private GridPane questionGrid; // Grid for displaying questions and options
    @FXML
    private Button submitButton; // Button for submitting the quiz
    private static String filePath;
    private static String courseName;

    private List<Question> questions = new ArrayList<>(); // List to store questions and options
    private Map<Integer, ToggleGroup> toggleGroups = new HashMap<>(); // Toggle groups for radio buttons
    private final String fileName = "src/main/resources/studentInfo/" + App.username + ".csv";
    private final String leaderBoard = "src/main/resources/leaderboard/leader.txt";
    @FXML
    public void initialize() {
        System.out.println(filePath);
        try {
            loadQuestionsFromFile();
            displayQuestions();
            setupSubmitAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load questions from the CSV file
    private void loadQuestionsFromFile() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Quiz file not found: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String title = reader.readLine(); // Read the first line (title, ignore for now)
            String line;

            // Parse questions from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5); // Split line into question, answer, and options
                if (parts.length < 5) continue; // Skip invalid lines

                String text = parts[0];
                String correctAnswer = parts[1];
                List<String> options = Arrays.asList(parts[1], parts[2], parts[3], parts[4]);

                // Randomize options
                Collections.shuffle(options);

                // Create and store question
                questions.add(new Question(text, correctAnswer, options));
            }
        }
    }

    // Method to display questions and options in the GridPane
    private void displayQuestions() {
        questionGrid.getChildren().clear();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            // Display question text in one row
            Label questionLabel = new Label((i + 1) + ". " + question.getText());
            questionGrid.add(questionLabel, 0, i * 2); // Add to the first column of the current row

            // Create a ToggleGroup for the options
            ToggleGroup group = new ToggleGroup();
            toggleGroups.put(i, group);

            // Create an HBox for the options to arrange them horizontally
            HBox optionsBox = new HBox(10); // Add spacing of 10 between options
            for (int j = 0; j < question.getOptions().size(); j++) {
                RadioButton optionButton = new RadioButton(question.getOptions().get(j));
                optionButton.setToggleGroup(group);
                optionsBox.getChildren().add(optionButton); // Add option button to the HBox
            }

            // Add the options HBox to the next row
            questionGrid.add(optionsBox, 0, i * 2 + 1, 2, 1); // Span across 2 columns
        }
    }

    // Method to set up the submit button action
    private void setupSubmitAction() {
        submitButton.setOnAction(e -> {
            int correctCount = 0;

            // Calculate the score
            for (int i = 0; i < questions.size(); i++) {
                ToggleGroup group = toggleGroups.get(i);
                if (group != null && group.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) group.getSelectedToggle();
                    if (selected.getText().equals(questions.get(i).getCorrectAnswer())) {
                        correctCount++;
                    }
                }
            }

            // Calculate marks out of 100
            double score = ((double) correctCount / questions.size()) * 100;

            // Display result
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Results");
            alert.setHeaderText("Quiz Completed!");
            alert.setContentText("You scored: " + String.format("%.2f", score) + " out of 100");
            alert.showAndWait();

            updateQuizTakenCount(courseName);

            try {
                updateLeaderFile((int) score);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
    }

    private void updateLeaderFile(int scoreToAdd) throws IOException {
        // Read the leaderboard file
        List<String[]> leaders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(leaderBoard))) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(leaderBoard))) {
            for (String[] leader : leaders) {
                bw.write(String.join(" ", leader));
                bw.newLine();
            }
        }
    }

    private void updateQuizTakenCount(String courseName) {
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


    // Inner class to represent a Question
    private static class Question {
        private final String text;
        private final String correctAnswer;
        private final List<String> options;

        public Question(String text, String correctAnswer, List<String> options) {
            this.text = text;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getText() {
            return text;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public List<String> getOptions() {
            return options;
        }
    }

    // Method to open the TakeQuiz page
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
