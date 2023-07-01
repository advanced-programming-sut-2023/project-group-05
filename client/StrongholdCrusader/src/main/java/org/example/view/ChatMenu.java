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

import java.util.ArrayList;

public class ChatMenu extends Application
{
    public static ArrayList < String > publicChatNames = new ArrayList<>();
    public static ArrayList < String > privateChatNames = new ArrayList<>();
    public static ArrayList < Long > publicChatNumber = new ArrayList<>();
    public static ArrayList < Long > privateChatNumber = new ArrayList<>();
    public static Tab publicChatTab;
    public static Tab privateChatTab;
    public static void updatePublicChat()
    {
        ScrollPane publicChats = new ScrollPane();
        VBox pChats = new VBox();

        for(int i = 0; i < publicChatNames.size(); i ++)
        {
            Label label = new Label(publicChatNames.get(i) + " with " + publicChatNumber.get(i) + " total number of messages");
            pChats.getChildren().add(label);
        }

        for(Node now : pChats.getChildren())
        {
            Label label = (Label) now;
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Enter the public chat with " + label.toString());

                }
            });
        }
        publicChats.setContent(pChats);
    }

    public static void updatePrivateChat()
    {
        ScrollPane privateChats = new ScrollPane();
        VBox pChats = new VBox();

        for(int i = 0; i < privateChatNames.size(); i ++)
        {
            Label label = new Label(privateChatNames.get(i) + " with " + privateChatNumber.get(i) + " total number of messages");
            pChats.getChildren().add(label);
        }

        for(Node label : pChats.getChildren())
        {
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Enter the private chat with " + label.toString());

                }
            });
        }
        privateChats.setContent(pChats);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chat Room");
        TabPane tabPane = FXMLLoader.load(URLFinder.run("/fxml/ChatMenu.fxml"));

        publicChatTab = tabPane.getTabs().get(0);
        privateChatTab = tabPane.getTabs().get(1);

        updatePrivateChat();
        updatePublicChat();

        stage.setScene(new Scene(tabPane));
        stage.show();
    }
}
