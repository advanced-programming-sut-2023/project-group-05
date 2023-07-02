package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.ProfileMenuController;
import org.example.controller.URLFinder;
import org.example.model.Commands;

import java.util.Scanner;

public class MainMenu extends Application {

    public static String username ;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Main Menu");
        BorderPane borderPane = FXMLLoader.load(URLFinder.run("/fxml/MainMenu.fxml"));
        VBox vBox = new VBox();
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        Button goToChat = new Button("Go To Chats");
        /// goToChat.setAlignment(Pos.CENTER);

        goToChat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("TODO: Go To Client Chat");
                try {
                    new ChatMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button goToProfile = new Button("View Profile!");
        /// goToChat.setAlignment(Pos.CENTER);
        goToProfile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("TODO: Go To Profile Menu");
            }
        });

        TextField port = new TextField();
        port.setPromptText("Enter Room Number");

        TextField password = new TextField();
        password.setPromptText("Enter Room Password");

        port.setMaxWidth(200);
        password.setMaxWidth(200);

        Button joinRoom = new Button("Join Room");
        joinRoom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    (new Game()).start( stage ) ;
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        vBox.getChildren().addAll(goToChat, goToProfile, port, password, joinRoom);

        borderPane.setCenter(vBox);

        goToChat.setAlignment(Pos.TOP_RIGHT);
        goToProfile.setAlignment(Pos.CENTER);
        port.setAlignment(Pos.BASELINE_CENTER);
        password.setAlignment(Pos.BASELINE_CENTER);
        joinRoom.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(borderPane));
        stage.show();
    }
}
