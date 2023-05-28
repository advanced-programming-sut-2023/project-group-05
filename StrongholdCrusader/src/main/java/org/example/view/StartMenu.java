package org.example.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartMenu extends Application {
    public static Stage stage;
    private Text welcomeText = new Text("Welcome To Strongholds Game");

    @Override
    public void start(Stage stage) throws Exception {
        StartMenu.stage = stage;
        BorderPane borderPane = FXMLLoader.load(StartMenu.class.getResource("/fxml/StartMenu.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(StartMenu.stage);
    }

    public void signup(MouseEvent mouseEvent) throws Exception {
        new SignupMenu().start(StartMenu.stage);
    }

}
