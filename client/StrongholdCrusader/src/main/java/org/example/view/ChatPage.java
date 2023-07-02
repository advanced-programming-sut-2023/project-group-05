package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.ChatConnection;
import org.example.controller.URLFinder;
import org.example.model.Chat;
import org.example.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends Application
{
    public static String userName;
    public static String other;
    public static Chat chat;
    public static ScrollPane scrollPane;

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Chat Page");
        BorderPane borderPane = FXMLLoader.load(URLFinder.run("/fxml/ChatPage.fxml"));

        stage.setScene(new Scene(borderPane));
        stage.show();

        scrollPane = (ScrollPane) borderPane.getCenter();
        updatePane();
    }

    public static void updatePane(){
        VBox vBox = new VBox();
        vBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        TextField textField = new TextField();
        textField.setPromptText("Send Message");
        textField.setAlignment(Pos.CENTER);

        Button sendMessage = new Button("Send");
        sendMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chat.AddMessage(new Message(userName, textField.getText()));
                try {
                    ChatConnection.updateChatWith(other, chat);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                textField.setText("");
            }
        });

        hBox.getChildren().addAll(textField, sendMessage);

        vBox.getChildren().add(hBox);

        for(int i = chat.getMessages().size() - 1; i >= 0; i --)
        {
            Message message = chat.getMessages().get(i);
            String T = (message.isSeen()? "S" : "NS");
            Text text = new Text(message.getSender() + ": " + message.getText() + "(" + message.getDate() + ") " + T);
            vBox.getChildren().add(text);
        }
        scrollPane.setContent(vBox);
    }

}
