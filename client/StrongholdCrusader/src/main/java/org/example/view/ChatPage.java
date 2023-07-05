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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.ChatConnection;
import org.example.controller.ChatUpdator;
import org.example.controller.URLFinder;
import org.example.model.Chat;
import org.example.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends Application
{
    public static String userName = null;
    public static String other = null;
    public static Chat chat = null;
    public static ScrollPane scrollPane = null;
    public static VBox vBox = null;

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Chat Page (" + userName + ")");
        BorderPane borderPane = FXMLLoader.load(URLFinder.run("/fxml/ChatPage.fxml"));

        stage.setScene(new Scene(borderPane));
        stage.show();

        scrollPane = (ScrollPane) borderPane.getCenter();

        vBox = new VBox();

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

        vBox.getChildren().add( hBox );

        new ChatUpdator().play() ;

        /*scrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.R)
                {
                    try {
                        ChatPage.updatePane();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });*/
        updatePane();
    }

    public static void updatePane() throws IOException {
        if(scrollPane == null || chat == null)
        {
            return;
        }

        while( vBox.getChildren().size() > 1 )
        {
            vBox.getChildren().remove( vBox.getChildren().size() - 1 );
        }

        boolean shouldUPD = false;
        for(int i = 0; i < chat.getMessages().size(); i ++)
        {
            Message cur = chat.getMessages().get(i);
            if(!cur.sender.equals(ChatConnection.userName))
            {
                if(!cur.isSeen())
                {
                    shouldUPD = true;
                    cur.setSeen(true);
                }
            }
        }
        if(shouldUPD)
        {
            String other = ChatPage.other;
            ChatConnection.updateChatWith(other, ChatPage.chat);
        }

        for(int i = chat.getMessages().size() - 1; i >= 0; i --)
        {
            Message message = chat.getMessages().get(i);
            String T = (message.isSeen()? "S" : "NS");
            Text text = new Text(message.getSender() + ": " + message.getText() + "(" + message.getDate() + ") " + T);
            vBox.getChildren().add(text);
        }

        for(int i = chat.getMessages().size() - 1; i >= 0; i --)
        {
            Text text = (Text) vBox.getChildren().get(i + 1);
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    if(mouseEvent.getButton() == MouseButton.SECONDARY)
                    {
                        System.out.println("here in edit Scene");
                        int j = 0;
                        for(; j < chat.getMessages().size(); j ++)
                        {
                            Message message = chat.getMessages().get(j);
                            String start = message.getSender() + ": " + message.getText() + "(" + message.getDate() + ") ";
                            if(text.getText().startsWith(start))
                            {
                                /// chat.getMessages().remove(j);
                                break;
                            }
                        }
                        System.out.println(j);

                        HBox EDITHBOX = new HBox();

                        Button deleteText = new Button("Delete");
                        int finalJ = j;
                        deleteText.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent)
                            {
                                chat.getMessages().remove(finalJ);
                                try {
                                    ChatConnection.updateChatWith(other, chat);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    updatePane();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        Button editText = new Button("Edit Message");
                        int finalJ1 = j;
                        int finalJ2 = j;
                        editText.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent)
                            {
                                HBox curHBOX = (HBox) vBox.getChildren().get(0);
                                curHBOX.setSpacing(5);
                                TextField textField1 = (TextField) curHBOX.getChildren().get(0);
                                Button button1 = (Button) curHBOX.getChildren().get(1);
                                textField1.setText(chat.getMessages().get(finalJ2).text);
                                button1.setText("Edit");
                                button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent)
                                    {
                                        chat.getMessages().get(finalJ1).setText(textField1.getText());
                                        try {
                                            ChatConnection.updateChatWith(other, chat);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        try {
                                            updatePane();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });
                            }
                        });
                        EDITHBOX.getChildren().add(deleteText);
                        EDITHBOX.getChildren().add(editText);
                        vBox.getChildren().add(chat.getMessages().size() - j, EDITHBOX);
                        int size = vBox.getChildren().size();

                    }
                }
            });
        }
        scrollPane.setContent(vBox);
    }

}
