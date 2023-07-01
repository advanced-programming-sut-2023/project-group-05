package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.URLFinder;

public class ChatMenu extends Application {
    public static Tab publicChat;
    public static Tab privateChat;
    public static void updatePublicChat()
    {
        ScrollPane publicChats = new ScrollPane();
        VBox pChats = new VBox();
        ///TODO: fill this public and private Chats field with JSON file from server
        for(Node label : pChats.getChildren())
        {
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Enter the public chat with ");

                }
            });
        }
        publicChats.setContent(pChats);
    }

    public static void updatePrivateChat()
    {
        ScrollPane privateChats = new ScrollPane();
        VBox pChats = new VBox();
        ///TODO: fill this public and private Chats field with JSON file from server
        for(Node label : pChats.getChildren())
        {
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Enter the private chat with ");

                }
            });
        }
        privateChats.setContent(pChats);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chat Room");
        TabPane tabPane = FXMLLoader.load(URLFinder.run("/fxml/ChatMenu.fxml"));

        publicChat = tabPane.getTabs().get(0);
        privateChat = tabPane.getTabs().get(1);

        updatePrivateChat();
        updatePublicChat();

        stage.setScene(new Scene(tabPane));
        stage.show();
    }
}
