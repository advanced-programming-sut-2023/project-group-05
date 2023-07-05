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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.ChatConnection;
import org.example.controller.PlayerList;
import org.example.controller.URLFinder;
import org.example.model.Chat;
import org.example.model.ChatPacket;

import java.util.ArrayList;

public class ChatMenu extends Application
{
    public ChatConnection connection;

    public static Stage stage;
    public static String userName = "Danial";
    public static ArrayList < String > publicChatNames = new ArrayList<>();
    public static ArrayList < String > privateChatNames = new ArrayList<>();
    public static ArrayList < Long > publicChatNumber = new ArrayList<>();
    public static ArrayList < Long > privateChatNumber = new ArrayList<>();
    public static ArrayList < String > privateChatStatus = new ArrayList<>();
    public static Tab publicChatTab;
    public static Tab privateChatTab;
    public static void updatePublicChat()
    {
        ScrollPane publicChats = new ScrollPane();
        VBox pChats = new VBox();
        pChats.setSpacing(20);
        for(int i = 0; i < publicChatNames.size(); i ++)
        {
            Text text = new Text(publicChatNames.get(i) + " with " + publicChatNumber.get(i) + " messages");
            pChats.getChildren().add(text);
        }

        for(Node now : pChats.getChildren())
        {
            Text text = (Text) now;
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Enter the public chat with " + text.getText());
                    try {
                        String other = text.getText().split(" with ")[0];
                        ChatConnection.getChatWith(other);
                        new ChatPage().start(ChatMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        publicChats.setContent(pChats);
        if(publicChatTab != null) publicChatTab.setContent(publicChats);
    }

    public static void updatePrivateChat()
    {
        ScrollPane privateChats = new ScrollPane();
        VBox pChats = new VBox();
        pChats.setSpacing(20);

        for(int i = 0; i < privateChatNames.size(); i ++)
        {
            Text text = new Text();
            text.setText(privateChatNames.get(i) + " with " + privateChatNumber.get(i) + " messages, status: " + privateChatStatus.get(i));
            pChats.getChildren().add(text);
        }

        for(Node text2 : pChats.getChildren())
        {
            Text text = (Text) text2;
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    String need = text.getText();
                    System.out.println("Enter the private chat with " + need);
                    try {
                        String other = text.getText().split(" with ")[0];
                        ChatConnection.getChatWith(other);
                        new ChatPage().start(ChatMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        privateChats.setContent(pChats);
        if(privateChatTab != null) privateChatTab.setContent(privateChats);
    }

    @Override
    public void start(Stage _stage) throws Exception
    {
        stage = _stage;
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
