package com.quizapp.Actions;

import com.quizapp.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuizListStudent extends App {
    public Map<String, String> quizMap = new HashMap<>();
    protected static String quizDir = "src/main/resources/Courses/";

    // Method to list and read all files in the specified folder
    protected void courseList() {
        System.out.println("Debug: quizDir = " + quizDir);

        // Step 1: Check if the directory exists and is valid
        File directory = new File(quizDir); // Directory containing quiz files
        if (!directory.exists()) {
            System.out.println("Error: quizDir does not exist. Please verify the path.");
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("Error: quizDir is not a directory. Please verify the path.");
            return;
        }

        // Step 2: List all files in the directory
        File[] allFiles = directory.listFiles();
        if (allFiles == null || allFiles.length == 0) {
            System.out.println("Error: No files found in quizDir.");
            return;
        }

        System.out.println("Debug: Found " + allFiles.length + " files in quizDir:");

        // Step 3: Process each file
        int count = 0;
        for (File file : allFiles) {
            System.out.println("Debug: Processing file - " + file.getName());

            // Attempt to read the first line (quiz title)
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String title = reader.readLine(); // Read the first line as title
                if (title == null || title.trim().isEmpty()) {
                    title = "Untitled Quiz";
                }

                // Add file name and title to the map
                quizMap.put(file.getName(), title);
                System.out.println("Debug: Added to quizMap -> File: " + file.getName() + ", Title: " + title);

                count++;
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }

        // Step 4: Summary
        System.out.println("Debug: Total quizzes loaded: " + count);

        // Debugging: Print the map
        System.out.println("Debug: Current quizMap contents:");
        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            System.out.println("  File: " + entry.getKey() + ", Title: " + entry.getValue());
        }
    }

    public static void openCourseListStudent(String quizFileName) throws IOException {
        quizDir = "src/main/resources/Courses/" + quizFileName + "/";
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/QuizListStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Quiz List");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
