package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizEditPage {

    @FXML
    private GridPane titleGrid; // For the quiz title
    @FXML
    private GridPane questionGrid; // For quiz questions
    @FXML
    private Button saveButton; // Save button (can be added in FXML)

    private String title; // Quiz title
    private List<Question> questions = new ArrayList<>(); // List of questions
    private static String dir = "";

    /**
     * Initialize method called after the FXML is loaded.
     * It sets up event listeners and loads the quiz data.
     */
    @FXML
    private void initialize() {
        // Add action for the save button
        if (saveButton != null) {
            saveButton.setOnAction(event -> saveQuizToFile());
        }

        // Load quiz data if a file path is provided
        if (dir != null && !dir.trim().isEmpty()) {
            loadQuizData();
        }
    }

    // Method to load quiz data from the file
    private void loadQuizData() {
        if (dir == null || dir.trim().isEmpty()) {
            System.out.println("File path is not set or invalid.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dir))) {
            // Read the title
            title = reader.readLine();
            if (title != null && !title.trim().isEmpty()) {
                displayTitle(title);
            } else {
                System.out.println("Title is missing in the quiz file.");
            }

            // Read and store questions
            String line;
            questions.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5); // Split into 5 parts: question, answer, option2, option3, option4
                if (parts.length == 5) {
                    questions.add(new Question(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }

            displayQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayTitle(String title) {
        titleGrid.getChildren().clear(); // Clear existing nodes
        TextField titleField = new TextField(title);
        titleField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        titleField.textProperty().addListener((observable, oldValue, newValue) -> this.title = newValue);
        titleGrid.add(titleField, 0, 0); // Add title field to grid
    }

    private void displayQuestions() {
        questionGrid.getChildren().clear(); // Clear existing nodes

        int row = 0;
        for (Question question : questions) {
            // Question field
            TextField questionField = new TextField(question.getText());
            questionField.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            questionField.textProperty().addListener((observable, oldValue, newValue) -> question.setText(newValue));

            // Create a container for the question
            HBox questionBox = new HBox(5);
            questionBox.getChildren().add(questionField);
            questionGrid.add(questionBox, 0, row);

            // Options
            VBox optionsBox = new VBox(5);
            optionsBox.getChildren().addAll(
                    createEditableOption("Answer: ", question.getAnswer(), question::setAnswer),
                    createEditableOption("Option 2: ", question.getOption2(), question::setOption2),
                    createEditableOption("Option 3: ", question.getOption3(), question::setOption3),
                    createEditableOption("Option 4: ", question.getOption4(), question::setOption4)
            );
            questionGrid.add(optionsBox, 0, ++row); // Add options below the question

            row++; // Increment row for the next question
        }
    }


    // Helper method to create an editable option field
    private HBox createEditableOption(String prefix, String value, Setter setter) {
        Label label = new Label(prefix);
        label.setStyle("-fx-font-size: 12px;");
        TextField textField = new TextField(value);
        textField.textProperty().addListener((observable, oldValue, newValue) -> setter.set(newValue));
        HBox box = new HBox(5, label, textField);
        return box;
    }

    // Method to save changes back to the file
    @FXML
    private void saveQuizToFile() {
        if (dir == null || dir.trim().isEmpty()) {
            System.out.println("File path is not set or invalid.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dir))) {
            // Write the title
            writer.write(title);
            writer.newLine();

            // Write all questions
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

    public static void openEditQuizPage(String file) throws IOException {
        dir = file;
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/QuizEdit.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage editQuiz = new Stage();
        editQuiz.setMaximized(true);
        editQuiz.setTitle("Edit Quiz");
        editQuiz.setScene(scene);
        editQuiz.setMaximized(true);
        editQuiz.show();
    }

    // Functional interface for setting properties
    @FunctionalInterface
    private interface Setter {
        void set(String value);
    }
}
