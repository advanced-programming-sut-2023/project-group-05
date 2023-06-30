module com.example.servermenu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports View;
    opens View to javafx.fxml;
}