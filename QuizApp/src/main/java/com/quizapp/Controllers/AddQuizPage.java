package com.quizapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddQuizPage {

    @FXML
    private TextField quizTitleField; // Field for quiz title
    @FXML
    private GridPane questionGrid; // Grid for questions
    @FXML
    private Button saveButton; // Save button

    private List<Question> questions = new ArrayList<>(); // List to store questions

    private static final int MAX_QUESTIONS = 10; // Fixed number of questions

    @FXML
    public void initialize() {
        // Automatically generate question fields
        for (int i = 0; i < MAX_QUESTIONS; i++) {
            addNewQuestion(i);
        }

        // Save the quiz when the "Save Quiz" button is clicked
        saveButton.setOnAction(e -> saveQuizToFile());
    }

    // Method to add a new question row dynamically
    private void addNewQuestion(int index) {
        // Create fields for a new question
        TextField questionField = new TextField();
        questionField.setPromptText("Enter question " + (index + 1));
        TextField answerField = new TextField();
        answerField.setPromptText("Enter correct answer");
        TextField option2Field = new TextField();
        option2Field.setPromptText("Enter option 2");
        TextField option3Field = new TextField();
        option3Field.setPromptText("Enter option 3");
        TextField option4Field = new TextField();
        option4Field.setPromptText("Enter option 4");

        // Add question field in the first line
        questionGrid.add(new Label("Q" + (index + 1)), 0, index * 2);
        questionGrid.add(questionField, 1, index * 2, 5, 1);

        // Add answer and options fields in the second line
        HBox answerOptionsBox = new HBox(10, answerField, option2Field, option3Field, option4Field);
        questionGrid.add(answerOptionsBox, 1, index * 2 + 1, 5, 1);

        // Add a new question object to the list
        Question question = new Question("", "", "", "", "");
        questions.add(question);

        // Bind the fields to the question object
        questionField.textProperty().addListener((obs, oldVal, newVal) -> question.setText(newVal));
        answerField.textProperty().addListener((obs, oldVal, newVal) -> question.setAnswer(newVal));
        option2Field.textProperty().addListener((obs, oldVal, newVal) -> question.setOption2(newVal));
        option3Field.textProperty().addListener((obs, oldVal, newVal) -> question.setOption3(newVal));
        option4Field.textProperty().addListener((obs, oldVal, newVal) -> question.setOption4(newVal));
    }

    // Method to save the quiz to a file
    private void saveQuizToFile() {
        String title = quizTitleField.getText().trim();
        if (title.isEmpty()) {
            System.out.println("Quiz title is required.");
            return;
        }

        if (questions.stream().allMatch(q -> q.getText().isEmpty())) {
            System.out.println("At least one question must be filled.");
            return;
        }

        // Create the file in the "Questions" directory
        File file = new File("src/main/resources/Questions/" + title.replaceAll("\\s+", "_") + ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
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

            System.out.println("Quiz saved successfully: " + file.getAbsolutePath());
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

    // Method to open the AddQuiz page
    public static void openAddQuizPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddQuizPage.class.getResource("/com/quizapp/AddQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage addQuizStage = new Stage();
        addQuizStage.setTitle("Add New Quiz");
        addQuizStage.setMaximized(true);
        addQuizStage.setScene(scene);
        addQuizStage.show();
    }
}
