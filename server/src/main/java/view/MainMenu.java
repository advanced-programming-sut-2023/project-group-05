package view;

import controller.ChatController;
import controller.GameMaster.GameRoom;
import controller.GameRoomController;
import controller.URLFinder;
import model.Chat;
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

import java.util.Map;

public class MainMenu extends Application
{
    public VBox roomVbox;
    public VBox chatVbox;
    public static Stage stage;
    public void updateRoomVbox()
    {
        for( Map.Entry x : GameRoom.gameRoomHashMap.entrySet() ){
            Button button = new Button() ;
            button.setText( "room name : " + x.getKey() + " , user count : " + ((GameRoom)x.getValue()).getUsernames().size() + "/" + ((GameRoom)x.getValue()).getCapacity() );
            roomVbox.getChildren().add( button ) ;
        }
    }

    public void updateChatVbox()
    {
        VBox vBox = chatVbox;
        vBox.getChildren().clear();
        vBox.setSpacing(20);
        vBox.getChildren().add(new Label("Chat Rooms' List"));
        for(int i = 0; i < ChatController.Chats.size(); i ++)
        {
            Chat chat = ChatController.Chats.get(i);
            Label label = new Label("chat number " + (i + 1) + " between " + chat.getFirst() + " and " + chat.getSecond() + " with total " + chat.getMessages().size() + " messages");
            vBox.getChildren().add(label);
        }
        Button createChatButton = new Button("Create new Chat");
        createChatButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("create Chat Menu should be Enabled");
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
