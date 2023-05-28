module AA {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json.simple;
    requires junit;

    exports org.example.model;
    exports org.example.view;
    opens org.example.view to javafx.fxml;
}