module com.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires kotlin.stdlib;
    requires jdk.incubator.vector;


    opens com.quizapp to javafx.fxml;
    exports com.quizapp;
    exports com.quizapp.Controllers;
    opens com.quizapp.Controllers to javafx.fxml;
}