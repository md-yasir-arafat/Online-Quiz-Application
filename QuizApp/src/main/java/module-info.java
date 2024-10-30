module com.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.quizapp to javafx.fxml;
    exports com.quizapp;
    exports com.quizapp.StudentWindows;
    opens com.quizapp.StudentWindows to javafx.fxml;
    exports com.quizapp.TeacherWindows;
    opens com.quizapp.TeacherWindows to javafx.fxml;
    exports com.quizapp.LoginWindow;
    opens com.quizapp.LoginWindow to javafx.fxml;
}