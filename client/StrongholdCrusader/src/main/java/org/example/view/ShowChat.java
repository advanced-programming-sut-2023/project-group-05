package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.ChatConnection;
import org.example.model.Chat;
import org.example.model.Message;

import java.io.IOException;

public class ShowChat extends Application {

    public Chat chat;

    public String userName;

    public String other;
    public ShowChat(Chat chat, String userName, String other)
    {
        this.chat = chat;
        this.userName = userName;
        this.other = other;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
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

    }
}
