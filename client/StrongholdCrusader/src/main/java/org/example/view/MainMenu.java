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
import org.example.controller.GameController;
import org.example.controller.ProfileMenuController;
import org.example.controller.SignupLoginMenuController;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class MainMenu extends Application {

    public static String username ;
    public static Stage stage;

    @Override
    public void start(Stage _stage) throws Exception
    {

        String currentUserName = GameController.currentUsername ;

        stage = _stage;
        stage.setTitle("Main Menu");
        BorderPane borderPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/MainMenu.fxml"));
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
                try{
                    stage.close();
                    new ProfileMenu().start(stage) ;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        TextField roomName = new TextField();
        roomName.setPromptText("Enter Room Name");

        TextField password = new TextField();
        password.setPromptText("Enter Room Password");

        roomName.setMaxWidth(200);
        password.setMaxWidth(200);

        Button joinRoom = new Button("Join Room");
        joinRoom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameRoomMenu gameRoomMenu = new GameRoomMenu() ;
                if( SignupLoginMenuController.joinRoom( roomName.getText() , password.getText() , gameRoomMenu ) )
                    try{gameRoomMenu.start( stage ) ;}catch( Exception e ){ e.printStackTrace(); }

            }
        });

        vBox.getChildren().addAll(goToChat, goToProfile, roomName, password, joinRoom);

        borderPane.setCenter(vBox);

        goToChat.setAlignment(Pos.TOP_RIGHT);
        goToProfile.setAlignment(Pos.CENTER);
        roomName.setAlignment(Pos.BASELINE_CENTER);
        password.setAlignment(Pos.BASELINE_CENTER);
        joinRoom.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(borderPane));
        stage.show();
    }
}
