package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.ChatConnection;
import org.example.controller.ProfileMenuController;
import org.example.controller.URLFinder;
import org.example.model.Commands;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class MainMenu extends Application {

    public static Stage stage;

    @Override
    public void start(Stage _stage) throws Exception
    {
        Scanner scanner = new Scanner(System.in);

        String currentUserName = scanner.next();

        stage = _stage;
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
                try {
                    ChatConnection.Run(currentUserName);
                } catch (Exception e) {
                    e.printStackTrace();
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
        port.setPromptText("Enter Room Port Number");
        /// port.setAlignment(Pos.CENTER);

        TextField password = new TextField();
        password.setPromptText("Enter Room Password");
        /// password.setAlignment(Pos.CENTER);

        port.setMaxWidth(200);
        password.setMaxWidth(200);

        Button joinRoom = new Button("Join Room");
        /// joinRoom.setAlignment(Pos.CENTER);
        joinRoom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Join the room and start the game");
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
