package com.quizapp.Controllers;

import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizEditPage {

    @FXML
    private GridPane titleGrid; // Grid for displaying the quiz title
    @FXML
    private GridPane questionGrid; // Grid for displaying questions and options
    @FXML
    private Button saveButton; // Button for saving the quiz

    private String quizTitle = ""; // Title of the quiz
    private final List<Question> questions = new ArrayList<>(); // List to store questions
    private static String filePath; // File path for the quiz file

    @FXML
    public void initialize() {
        if (filePath != null && !filePath.trim().isEmpty()) {
            loadQuizData();
            displayTitle();
            displayQuestions();
            setupSaveAction();
        } else {
            System.out.println("File path not provided.");
        }
    }

    // Method to load quiz data from a file
    private void loadQuizData() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Quiz file not found: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            quizTitle = reader.readLine(); // Read the first line as the title
            String line;

            // Parse questions from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5); // Split line into question, answer, and options
                if (parts.length < 5) continue; // Skip invalid lines

                questions.add(new Question(parts[0], parts[1], parts[2], parts[3], parts[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to display the quiz title
    private void displayTitle() {
        titleGrid.getChildren().clear();
        TextField titleField = new TextField(quizTitle);
        titleField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        titleField.setPrefWidth(Double.MAX_VALUE); // Allow full width
        titleField.textProperty().addListener((observable, oldValue, newValue) -> quizTitle = newValue);

        titleGrid.add(titleField, 0, 0);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
    }

    // Method to display questions and their options in the grid
    private void displayQuestions() {
        questionGrid.getChildren().clear();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            // Display question text
            TextField questionField = new TextField(question.getText());
            questionField.setStyle("-fx-font-size: 14px;");
            questionField.setPrefWidth(Double.MAX_VALUE);
            questionField.textProperty().addListener((observable, oldValue, newValue) -> question.setText(newValue));

            HBox questionBox = new HBox(5, questionField);
            HBox.setHgrow(questionField, Priority.ALWAYS);

            questionGrid.add(questionBox, 0, i * 2);

            // Display options
            VBox optionsBox = new VBox(5);
            optionsBox.getChildren().addAll(
                    createEditableOption("Answer: ", question.getAnswer(), question::setAnswer),
                    createEditableOption("Option 2: ", question.getOption2(), question::setOption2),
                    createEditableOption("Option 3: ", question.getOption3(), question::setOption3),
                    createEditableOption("Option 4: ", question.getOption4(), question::setOption4)
            );

            questionGrid.add(optionsBox, 0, i * 2 + 1);
        }
    }

    // Helper method to create an editable option field
    private HBox createEditableOption(String prefix, String value, Setter setter) {
        Label label = new Label(prefix);
        TextField optionField = new TextField(value);
        optionField.textProperty().addListener((observable, oldValue, newValue) -> setter.set(newValue));
        return new HBox(5, label, optionField);
    }

    // Method to set up save button action
    private void setupSaveAction() {
        saveButton.setOnAction(e -> saveQuizToFile());
    }

    // Method to save the quiz data back to the file
    private void saveQuizToFile() {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("File path not set.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Save title
            writer.write(quizTitle);
            writer.newLine();

            // Save all questions
            for (Question question : questions) {
                writer.write(String.format("%s,%s,%s,%s,%s",
                        question.getText(),
                        question.getAnswer(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()));
                writer.newLine();
            }

            System.out.println("Quiz saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to represent a Question
    private static class Question {
        private String text;
        private String answer;
        private String option2;
        private String option3;
        private String option4;

        public Question(String text, String answer, String option2, String option3, String option4) {
            this.text = text;
            this.answer = answer;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }
    }

    // Functional interface for setting properties
    @FunctionalInterface
    private interface Setter {
        void set(String value);
    }

    // Method to open the edit quiz page
    public static void openEditQuizPage(String file) throws IOException {
        filePath = file;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/quizapp/QuizEdit.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Edit Quiz");
        stage.setMaximized(true);
        stage.show();
    }
}
