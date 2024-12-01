package com.quizapp.Controllers;

import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class EnrollPage {

    @FXML
    private GridPane courseGrid;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String USER_IMAGE_PATH = "/images/temp.jpg";
    private static final String COURSES_FILE = "/Courses/all.csv";

    @FXML
    private void initialize() {
        try {
            // Load logo and user images
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(LOGO_PATH)).toExternalForm()));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(USER_IMAGE_PATH)).toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        // Populate the course grid
        populateCourses(COURSES_FILE);
    }

    private void populateCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(fileName))))) {

            String line;
            int row = 0;
            int column = 0;

            // Configure the course grid layout
            courseGrid.getChildren().clear();
            courseGrid.setHgap(10);
            courseGrid.setVgap(10);
            courseGrid.setPadding(new Insets(20));
            courseGrid.setAlignment(Pos.TOP_LEFT);

            while ((line = reader.readLine()) != null) {
                String[] courseData = line.split(",");



                String courseName = courseData[0].trim().replace("_", " ");
                String description = courseData[1].trim();


                VBox courseBox = createCourseBox(courseName, description);
                courseGrid.add(courseBox, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
    }

    private VBox createCourseBox(String courseName, String description) {
        VBox courseBox = new VBox(10);
        courseBox.setAlignment(Pos.CENTER);
        courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        courseBox.setPrefWidth(200);

        Label nameLabel = new Label(courseName);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 12px;");

        Button enrollButton = new Button("Enroll");

        String faculty = extractFaculty(courseName);
        String courseFileName = courseName.split("by", 2)[0].trim();

        enrollButton.setOnAction(e -> enrollCourse(courseFileName, description, faculty));

        courseBox.getChildren().addAll(nameLabel, descriptionLabel, enrollButton);
        return courseBox;
    }

    private String extractFaculty(String courseName) {
        try {
            String[] parts = courseName.split("by", 2);
            return (parts.length == 2) ? parts[1].trim().replace("_", " ") : "";
        } catch (Exception e) {
            System.err.println("Error extracting faculty from courseName: " + e.getMessage());
            return "";
        }
    }

    public void enrollCourse(String courseFileName, String courseInfo, String faculty) {
        boolean isCourseFound = false;

        String userFilePath = "src/main/resources/studentInfo/" + App.username + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] courseDetails = line.split(",", 2);
                if (courseDetails.length > 0 && courseDetails[0].trim().equalsIgnoreCase(courseFileName)) {
                    isCourseFound = true;
                    showAlert("Already Enrolled", "You are already enrolled in: " + courseFileName);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }

        if (!isCourseFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFilePath, true))) {
                int progress = 0;
                bw.write(courseFileName + "," + courseInfo + "," + progress);
                bw.newLine();
                System.out.println("Enrolled successfully in course: " + courseFileName);
                updateCourseEnrollmentCount(courseFileName, faculty);
                showAlert("Enrollment Successful", "You have successfully enrolled in: " + courseFileName);
            } catch (IOException e) {
                System.err.println("Error writing to user file: " + e.getMessage());
            }
        }
    }

    private void updateCourseEnrollmentCount(String courseFileName, String faculty) {
        String filePath = "src/main/resources/teacherInfo/" + faculty.trim() + ".csv";
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] courseDetails = line.split(",", 4);

                if (courseDetails.length == 4) {
                    String fileName = courseDetails[3].trim();
                    if (fileName.equalsIgnoreCase(courseFileName)) {
                        int enrolledCount = Integer.parseInt(courseDetails[2].trim());
                        enrolledCount++;
                        line = courseDetails[0] + "," + courseDetails[1] + "," + enrolledCount + "," + fileName;
                    }
                }

                updatedContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + ". Message: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(updatedContent.toString());
            System.out.println("Updated enrolled count for course: " + courseFileName + " in file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath + ". Message: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void openEnrollPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EnrollPage.class.getResource("/com/quizapp/Enroll.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(EnrollPage.class.getResource("/css/style.css")).toExternalForm());

        Stage enrollStage = new Stage();
        enrollStage.setTitle("Enroll in a Course");
        enrollStage.setMaximized(true);
        enrollStage.setScene(scene);
        enrollStage.show();
    }
}
