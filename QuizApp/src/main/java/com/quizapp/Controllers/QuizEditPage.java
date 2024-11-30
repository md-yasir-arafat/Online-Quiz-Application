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

    private String filePath; // Dynamic file path
    private String title; // Quiz title
    private List<Question> questions = new ArrayList<>(); // List of questions

    public  void openQuizEditor(String quizFileName) throws IOException {
        setFilePath(quizFileName);
        openEditQuizPage();

    }

    // Method to set the file path (called externally)
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        loadQuizData(); // Load the quiz data after setting the path
    }

    // Method to load quiz data from the file
    private void loadQuizData() {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("File path is not set or invalid.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

    // Method to display the title in the titleGrid
    private void displayTitle(String title) {
        titleGrid.getChildren().clear();
        TextField titleField = new TextField(title);
        titleField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        titleField.textProperty().addListener((observable, oldValue, newValue) -> this.title = newValue);
        titleGrid.add(titleField, 0, 0);
    }

    // Method to display questions in the questionGrid
    private void displayQuestions() {
        questionGrid.getChildren().clear();

        int row = 0;
        for (Question question : questions) {
            // Question text (editable)
            TextField questionField = new TextField(question.getText());
            questionField.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            questionField.textProperty().addListener((observable, oldValue, newValue) -> question.setText(newValue));
            questionGrid.add(questionField, 0, row);

            // Answer and options in a VBox (editable)
            VBox optionsBox = new VBox(5);
            optionsBox.getChildren().addAll(
                    createEditableOption("Answer: ", question.getAnswer(), question::setAnswer),
                    createEditableOption("Option 2: ", question.getOption2(), question::setOption2),
                    createEditableOption("Option 3: ", question.getOption3(), question::setOption3),
                    createEditableOption("Option 4: ", question.getOption4(), question::setOption4)
            );
            questionGrid.add(optionsBox, 1, row);

            row++;
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
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("File path is not set or invalid.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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

    public static void openEditQuizPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/QuizEdit.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage editQuiz = new Stage();
        editQuiz.setMaximized(true);
        editQuiz.setTitle("Edit Quiz");
        editQuiz.setScene(scene);
        editQuiz.show();
    }

    // Functional interface for setting properties
    @FunctionalInterface
    private interface Setter {
        void set(String value);
    }
}
