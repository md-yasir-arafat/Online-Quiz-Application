module com.quizapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.quizapp to javafx.fxml;
    exports com.quizapp;
}