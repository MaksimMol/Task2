module com.example.task5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task5 to javafx.fxml;
    exports com.example.task5;
}