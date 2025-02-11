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

public class QuizListTeacher extends App {
    public Map<String, String> quizMap = new HashMap<>();
    protected static String quizDir = "src/main/resources/Courses/";
    protected static String quizFile = "";

    // Method to check how many files start with a specific prefix and read the first line (quiz title)
    protected void courseList(String commonPrefix) {
        quizDir = quizDir+ quizFile;
        File directory = new File(quizDir); // Directory containing quiz files
        if (!directory.isDirectory()) {
            System.out.println("Not a valid directory.");
            return;
        }

        // Get all files starting with the common prefix
        File[] matchingFiles = directory.listFiles((dir, name) -> name.startsWith(commonPrefix));
        if (matchingFiles == null || matchingFiles.length == 0) {
            System.out.println("No files found starting with prefix: " + commonPrefix);
            return;
        }

        // Map to store file name and quiz title


        System.out.println("Files starting with prefix '" + commonPrefix + "': " + matchingFiles.length);

        int count = 0;
        for (File file : matchingFiles) {
            System.out.println(" - " + file.getName());

            // Read the first line (quiz title) from the file
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String title = reader.readLine(); // Read the first line as title
                if (title == null || title.trim().isEmpty()) {
                    title = "Untitled Quiz";
                }

                // Add file name and title to the map
                quizMap.put(file.getName(), title);

                count++;
            } catch (IOException e) {
                System.out.println("   Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }

        System.out.println(count + " quizzes loaded.");

        // Debugging: Print the map
        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            System.out.println("File: " + entry.getKey() + ", Title: " + entry.getValue());
        }
    }

    public static void openCourseListTeacher(String quizFileName) throws IOException {
        quizFile = quizFileName;
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/QuizListTeacher.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Course List");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
