module com.quizapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.quizapp to javafx.fxml;
    exports com.quizapp;
    exports com.quizapp.Controllers;
    opens com.quizapp.Controllers to javafx.fxml;
}