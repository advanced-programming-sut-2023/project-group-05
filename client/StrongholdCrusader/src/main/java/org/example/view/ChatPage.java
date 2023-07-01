package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.URLFinder;
import org.example.model.Chat;
import org.example.model.Message;

import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends Application {

    public static Chat chat;
    public static VBox vBox;
    public ChatPage(Chat _chat)
    {
        chat = _chat;
    }
    public static void updatePane()
    {
        vBox.getChildren().clear();

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
                chat.AddMessage(new Message(chat.getFirst(), chat.getSecond(), textField.getText(), (new Date()).toString()));
                textField.setText("");
            }
        });

        hBox.getChildren().addAll(textField, sendMessage);

        vBox.getChildren().add(hBox);

        for(int i = chat.getMessages().size() - 1; i >= 0; i --)
        {
            Message message = chat.getMessages().get(i);
            String T = (message.isSeen()? "S" : "NS");
            Label label = new Label(message.getSender() + ": " + message.getText() + "(" + message.getDate() + ") " + T);
            vBox.getChildren().add(label);
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        ScrollPane scrollPane = FXMLLoader.load(URLFinder.run("/fxml/ChatPage.fxml"));
        VBox vBox = new VBox();
        updatePane();
        scrollPane.setContent(vBox);
        stage.setScene(new Scene(scrollPane));
        stage.show();
    }
}
