package com.quizapp.LoginWindow;

import com.quizapp.SignUpWindow.SignUpWindow;
import com.quizapp.StudentWindows.StudentWindow;
import com.quizapp.TeacherWindows.TeacherWindow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class LoginAction {

    private final TextField Username;
    private final PasswordField Password;
    private final Button Login;
    private final Label Message;

    // Constructor to pass the references of UI components
    public LoginAction(TextField Username, PasswordField Password, Button Login, Label Message) {
        this.Username = Username;
        this.Password = Password;
        this.Login = Login;
        this.Message = Message;
    }

    // Method to set up the actions
    public void setupActions() {

        // Handle pressing "Enter" in username field to move to password
        Username.setOnAction(e -> Password.requestFocus());

        // Handle pressing "Enter" in password field to trigger login
        Password.setOnAction(e -> Login.fire());

        // Handle login button action
        Login.setOnAction(e -> handleLoginAction());
    }

    // Handle the login logic
    private void handleLoginAction() {
        String username = Username.getText();
        String password = Password.getText();
        int isSuccess = handleLogin(username, password);

        if (isSuccess == 1) {  // Teacher login
            Message.setTextFill(Color.GREEN);
            Message.setText("Login successful!\nYou are a teacher.");
            new TeacherWindow().show();  // Open teacher window
            closeCurrentWindow();
        } else if (isSuccess == 2) {  // Student login
            Message.setTextFill(Color.GREEN);
            Message.setText("Login successful!\nYou are a student.");
            new StudentWindow().show();  // Open student window
            closeCurrentWindow();
        } else {  // Invalid login
            Message.setTextFill(Color.RED);
            Message.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public int handleLogin(String username, String password) {
        // Simple validation for demonstration purposes
        if (SignupAction.verifyCredentials(username,password,"/credential/teacher.csv")) {
            return 1;
        } else if (SignupAction.verifyCredentials(username,password,"/credential/student.csv")) {
            return 2;
        } else {
            return 0;
        }
    }
    // Close the current login window
    private void closeCurrentWindow() {
        Stage stage = (Stage) Login.getScene().getWindow();
        stage.close();  // Close the current stage (login window)
    }
}


class SignupAction{
    private final Button signup;

    SignupAction(Button signup) {
        this.signup = signup;
        signup.setOnAction(e -> openSignUpWindow());
    }

    // Method to open the sign-up window
    private void openSignUpWindow() {
        SignUpWindow signUpWindow = new SignUpWindow();
        Stage signUpStage = new Stage();
        try {
            signUpWindow.start(signUpStage);  // Open sign-up window
            //closeCurrentWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Stuck with file but moving on signup page

    public static boolean verifyCredentials(String username, String password, String filePath) {
        String line;

        File file = new File("credential/test.csv");
        try {
            // Attempt to create the file
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());

                // Optionally, write some content to the new file
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("username,password,id,full name,display name\n");
                    writer.write("yasir,password,2412558042,Md. Yasir Arafat,Yasir\n");
                    writer.write("alvee,password,241323,Alvee Ifteshum,Alvee\n");
                    System.out.println("Content written to the file.");
                }
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            while ((line = br.readLine()) != null) {
//                // Splitting line into values
//                String[] values = line.split(",");
//
//                // Assuming username is in the first position and password in the second
//                String fileUsername = values[0].trim();
//                String filePassword = values[1].trim();
//
//                // Checking if username and password match
//                if (fileUsername.equals(username) && filePassword.equals(password)) {
//                    return true; // Valid credentials
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred while reading the file.");
//            e.printStackTrace();
//        }

        return false; // Invalid credentials
    }
}

