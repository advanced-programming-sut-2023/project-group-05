package view;


import controller.GameMaster.GameRoom;
import controller.GameRoomController;
import controller.URLFinder;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.Chat;
import model.ChatLog;
import model.GameRoomConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        // TODO : button
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
        createRoom( roomVbox ) ;
        createRoom.setContent(roomVbox);
        createChat.setContent(chatVbox);
        stage.setScene(new Scene(current));
        stage.show();
    }

    private void createRoom( VBox vBox )
    {
        vBox.setAlignment( Pos.CENTER);
        vBox.setSpacing(50);

        TextField roomNumber = new TextField();
        roomNumber.setAlignment(Pos.CENTER);
        roomNumber.setPromptText("Room Number");
        roomNumber.setPrefHeight(60);

        TextField roomPass = new TextField();
        roomPass.setAlignment(Pos.CENTER);
        roomPass.setPromptText("Room Password");
        roomPass.setPrefHeight(60);

        Button create = new Button("Create Room");
        create.setStyle("-fx-background-color: #29ecd4; \n" +
                "    -fx-text-fill: #ffffff; \n" +
                "    -fx-font-size: 14px; \n" +
                "    -fx-padding: 8px 16px; \n" +
                "    -fx-border-radius: 4px; \n" +
                "    -fx-cursor: hand; ");

        vBox.getChildren().addAll(roomNumber,roomPass,create);

        create.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if ( GameRoom.gameRoomHashMap.get( roomNumber.getText() ) != null )
                    new Alert(Alert.AlertType.ERROR,"Room Already Exists").showAndWait();
                else {
                    GameRoom gameRoom = new GameRoom( roomNumber.getText() , roomPass.getText() , 4  ) ;
                    new Alert(Alert.AlertType.INFORMATION,"Room Created Successfully").showAndWait();
                    Label text = new Label( "name = " + roomNumber.getText() + " " + "pass = " + roomPass.getText() ) ;
                    vBox.getChildren().add( text ) ;
                    text.setOnMouseClicked( new EventHandler <MouseEvent>() {
                        @Override
                        public void handle( MouseEvent mouseEvent ){
                            System.out.println( "number of users : " + gameRoom.getUsernames().size() );
                            for( String username : gameRoom.getUsernames() ){
                                System.out.print( " user : " + username ) ;
                            }
                        }
                    } );
                }
            }
        });
    }

}
