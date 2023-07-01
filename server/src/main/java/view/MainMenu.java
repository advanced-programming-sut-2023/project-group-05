package view;

import controller.GameRoomController;
import controller.URLFinder;
import model.Chat;
import model.ChatLog;
import model.GameRoomConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application
{
    public VBox roomVbox;
    public VBox chatVbox;
    public static Stage stage;
    public void updateRoomVbox()
    {
        VBox vBox = roomVbox;
        vBox.getChildren().clear();
        vBox.setSpacing(20);
        vBox.getChildren().add(new Label("Game Rooms' List:"));
        for(int i = 0; i < GameRoomController.Rooms.size(); i ++)
        {
            GameRoomConnection gameRoomConnection = GameRoomController.Rooms.get(i);
            Label label = new Label("Room number " + (i + 1) + " name: " + gameRoomConnection.getServerName() + " port: " + gameRoomConnection.getServerPort() + " Capacity: " + gameRoomConnection.getClients().size() + "/" + gameRoomConnection.getMaxCapacity());
            vBox.getChildren().add(label);
        }
        Button createRoomButton = new Button("Create New Room");
        createRoomButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new CreateGameRoomMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        vBox.getChildren().add(createRoomButton);
    }

    public void updateChatVbox()
    {
        VBox vBox = chatVbox;
        vBox.getChildren().clear();
        vBox.setSpacing(20);
        vBox.getChildren().add(new Label("Chat Rooms' List"));
        for(int i = 0; i < ChatLog.chatLog.size(); i ++)
        {
            Chat chat = ChatLog.chatLog.get(i);
            Label label = new Label("chat number " + (i + 1) + " between " + chat.getFirst() + " and " + chat.getSecond() + " with total " + chat.getMessages().size() + " messages");
            vBox.getChildren().add(label);
        }
        Button createChatButton = new Button("Create new Chat");
        createChatButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                System.out.println("create Chat Menu should be Enabled");
                try {
                    new CreateChatMenu().start(new Stage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        vBox.getChildren().add(createChatButton);
    }
    @Override
    public void start(Stage _stage) throws Exception
    {
        stage = _stage;
        TabPane current = FXMLLoader.load(URLFinder.run("/fxml/MainMenu.fxml"));
        Tab createRoom =  current.getTabs().get(0);
        Tab createChat = current.getTabs().get(1);
        roomVbox = new VBox();
        chatVbox = new VBox();
        updateRoomVbox();
        updateChatVbox();
        createRoom.setContent(roomVbox);
        createChat.setContent(chatVbox);
        stage.setScene(new Scene(current));
        stage.show();
    }
}
