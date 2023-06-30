module com.example.servermenu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires json.simple;


    exports view;
    opens view to javafx.fxml;
}