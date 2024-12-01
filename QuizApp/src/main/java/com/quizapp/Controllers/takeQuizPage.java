package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class TakeQuizPage {

    @FXML
    private GridPane questionGrid; // Grid for displaying questions and options
    @FXML
    private Button submitButton; // Button for submitting the quiz
    private static String filePath;

    private List<Question> questions = new ArrayList<>(); // List to store questions and options
    private Map<Integer, ToggleGroup> toggleGroups = new HashMap<>(); // Toggle groups for radio buttons

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
        });
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
    public static void openTakeQuizPage(String quizDir) throws IOException {

        filePath = quizDir;
        FXMLLoader fxmlLoader = new FXMLLoader(TakeQuizPage.class.getResource("/com/quizapp/TakeQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setMaximized(true);
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
