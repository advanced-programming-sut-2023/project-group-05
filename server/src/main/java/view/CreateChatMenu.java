package view;

import controller.URLFinder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Chat;
import model.ChatLog;
import model.DataBase;

import java.io.DataOutputStream;

public class CreateChatMenu extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Create Chat Between 2 Users");
        BorderPane borderPane = FXMLLoader.load(URLFinder.run("/fxml/CreateChatMenu.fxml"));

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        TextField first = new TextField();
        first.setPromptText("Enter First User Name");

        TextField second = new TextField();
        second.setPromptText("Enter Second User Name");

        first.setAlignment(Pos.CENTER);
        second.setAlignment(Pos.CENTER);

        first.setMaxWidth(200);
        second.setMaxWidth(200);

        Button button = new Button("Create Chat Room");

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if(DataBase.getFromDataBase("userName", first.getText()) == null || DataBase.getFromDataBase("userName", second.getText()) == null)
                {
                    try {
                        new ErrorWindow("Invalid Users", "Let Me Try Again").start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                if(!ChatLog.AddChat(new Chat(first.getText(), second.getText())))
                {
                    try {
                        new ErrorWindow("Chat Already Exists", "Oh Fuck!").start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    try {
                        new ErrorWindow("Chat Created Successfully", "Nice!").start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        vBox.getChildren().addAll(first, second, button);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);
        stage.setScene(new Scene(borderPane));
        stage.show();
    }
}
