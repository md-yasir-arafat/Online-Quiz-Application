package com.quizapp.Controllers;

import com.quizapp.Actions.Login;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class EnrollPage {

    @FXML
    private GridPane courseGrid;
    static String line = "";

    @FXML
    private void initialize() {
        populateCourses("src/main/resources/Courses/all.csv");
    }

    private void populateCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            int column = 0;

            // Clear the existing content in the courseGrid
            courseGrid.getChildren().clear();
            courseGrid.setHgap(10);
            courseGrid.setVgap(10);
            courseGrid.setPadding(new Insets(20));
            courseGrid.setAlignment(Pos.TOP_LEFT);

            while ((line = reader.readLine()) != null) {
                // Split the line to extract details
                String[] courseData = line.split(",");
                String subject = courseData[0];
                String faculty = "";
                String description = courseData[1];
                String courseFileName = courseData[0];

                // Process subject string to split into subject and faculty
                try {
                    String[] parts = subject.split("by", 2); // Split into at most 2 parts
                    if (parts.length == 2) {
                        subject = parts[0].trim().replace("_", " ");
                        faculty = parts[1].trim().replace("_", " ");
                    } else {
                        throw new IllegalArgumentException("String does not contain 'by' or is not in the expected format.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error processing subject string: " + subject);
                }

                // Create a VBox for each course
                VBox courseBox = new VBox(10);
                courseBox.setAlignment(Pos.CENTER);
                courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                courseBox.setPrefWidth(200);

                Label subjectLabel = new Label(subject);
                subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label facultyLabel = new Label(faculty);
                facultyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label descriptionLabel = new Label(description);
                descriptionLabel.setWrapText(true);
                descriptionLabel.setStyle("-fx-font-size: 12px;");

                Button enrollButton = new Button("Enroll");
                String finalFaculty = faculty;
                enrollButton.setOnAction(e -> {
                    enrollCourse(courseFileName, description, finalFaculty);
                });

                courseBox.getChildren().addAll(subjectLabel, facultyLabel, descriptionLabel, enrollButton);

                // Add the course box to the grid
                courseGrid.add(courseBox, column, row);

                column++;
                if (column == 4) { // Move to the next row after 4 columns
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enrollCourse(String courseFileName, String courseInfo,String faculty) {
        boolean isCourseFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/studentInfo/" + App.username + ".csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into course details
                String[] courseDetails = line.split(",", 2);
                if (courseDetails.length > 0) {
                    String courseName = courseDetails[0].trim();
                    if (courseName.equalsIgnoreCase(courseFileName)) {
                        isCourseFound = true;
                        System.out.println("Already Enrolled in course");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the course file: " + e.getMessage());
        }

        if (!isCourseFound) {
            System.out.println("You can Enroll");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/studentInfo/" + App.username + ".csv", true))) {
                int progress = 0; // Default progress value

                // Write the course details
                bw.write(courseFileName + "," + courseInfo + "," + progress);
                bw.newLine(); // Add a new line for the next entry
                System.out.println("Enrolled successfully in course: " + courseFileName);
                updateCourseEnrollmentCount(courseFileName,faculty);
            } catch (IOException e) {
                System.out.println("Error saving the course: " + e.getMessage());
            }
        }
    }

    private void updateCourseEnrollmentCount(String courseFileName, String faculty) {
        // Remove any unintended spaces from the file path
        String filePath = "src/main/resources/teacherInfo/" + faculty.trim() + ".csv";
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] courseDetails = line.split(",", 4); // Expecting 4 fields: Name, Description, Count, FileName

                if (courseDetails.length == 4) {
                    String fileName = courseDetails[3].trim();
                    if (fileName.equalsIgnoreCase(courseFileName)) {
                        // Increment the enrolled student count
                        int enrolledCount = Integer.parseInt(courseDetails[2].trim());
                        enrolledCount++;
                        // Reconstruct the line with updated count
                        line = courseDetails[0] + "," + courseDetails[1] + "," + enrolledCount + "," + fileName;
                    }
                }

                updatedContent.append(line).append("\n"); // Append updated or unchanged line
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Message: " + e.getMessage());
            return;
        }

        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(updatedContent.toString());
            System.out.println("Updated enrolled count for course: " + courseFileName + " in file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath + ". Message: " + e.getMessage());
        }
    }

    public static void openEnrollPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/com/quizapp/Enroll.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage enrollStage = new Stage();
        enrollStage.setTitle("Enroll in a Course");
        enrollStage.setMaximized(true);
        enrollStage.setScene(scene);
        enrollStage.show();
    }
}
