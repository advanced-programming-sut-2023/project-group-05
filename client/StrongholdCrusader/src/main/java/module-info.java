module AA {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json.simple;
    requires junit;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.datatransfer;

    exports org.example.model;
    exports org.example.view;
    exports org.example.controller;
    opens org.example.view to javafx.fxml;
    opens org.example.model to javafx.fxml;
    opens org.example.controller to javafx.fxml;
    exports org.example.controller.graphicalMenuController;
    opens org.example.controller.graphicalMenuController to javafx.fxml;
    exports org.example.model.enums;
    opens org.example.model.enums to javafx.fxml;
}