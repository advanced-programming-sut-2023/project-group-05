package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.ChatConnection;
import org.example.controller.GameController;
import org.example.controller.ProfileMenuController;
import org.example.controller.SignupLoginMenuController;
import org.example.model.animations.PoopAnimation;
import org.example.model.enums.Icons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class MainMenu extends Application {

    public static String username ;
    public static Stage stage;
    private Button back = new Button();
    private VBox buttons;

    @Override
    public void start(Stage _stage) throws Exception
    {
        String currentUserName = GameController.currentUsername ;
        String buttonStyle = "-fx-background-color:\n" +
                "            #3c7fb1,\n" +
                "            linear-gradient(#fafdfe, #e8f5fc),\n" +
                "            linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-background-radius: 3,2,1;\n" +
                "    -fx-padding: 3 30 3 30;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 15px;\n" +
                "    -fx-border-radius: 5px;";
        stage = _stage;
        stage.setTitle("Main Menu");
        BorderPane borderPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/MainMenu.fxml"));
        VBox vBox = new VBox();
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        Button goToChat = new Button("Go To Chats");
        /// goToChat.setAlignment(Pos.CENTER);
        goToChat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    ChatConnection.Run(currentUserName);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        Button goToProfile = new Button("View Profile!");
        /// goToChat.setAlignment(Pos.CENTER);
        goToProfile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    stage.close();
                    new ProfileMenu().start(stage) ;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        TextField roomName = new TextField();
        roomName.setPromptText("Enter Room Name");

        TextField password = new TextField();
        password.setPromptText("Enter Room Password");

        roomName.setMaxWidth(200);
        password.setMaxWidth(200);

        Button joinRoom = new Button("Join Room");
        joinRoom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameRoomMenu gameRoomMenu = new GameRoomMenu() ;
                if( SignupLoginMenuController.joinRoom( roomName.getText() , password.getText() , gameRoomMenu ) )
                    try{gameRoomMenu.start( stage ) ;}catch( Exception e ){ e.printStackTrace(); }

            }
        });

        Button search = new Button("Search Users");
        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                search(borderPane);
            }
        });

        Button viewInvites = new Button("View Friend Invites");
        viewInvites.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                viewInvites(borderPane);
            }
        });
        back.setAlignment(Pos.BASELINE_LEFT);
        back.setPrefHeight(50);
        back.setPrefWidth(50);
        back.setBackground(new Background(new BackgroundFill(Icons.BACK.getImagePattern(),null,null)));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new LoginMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        vBox.getChildren().addAll(goToChat, goToProfile, roomName, password, joinRoom,search,viewInvites);
        for (Node node : vBox.getChildren())
            node.setStyle(buttonStyle);
        borderPane.setBottom(back);
        borderPane.setCenter(vBox);
        this.buttons = vBox;

        goToChat.setAlignment(Pos.TOP_RIGHT);
        goToProfile.setAlignment(Pos.CENTER);
        roomName.setAlignment(Pos.BASELINE_CENTER);
        password.setAlignment(Pos.BASELINE_CENTER);
        joinRoom.setAlignment(Pos.CENTER);
        borderPane.setBackground(new Background(new BackgroundFill(Icons.MAIN_MENU_BG.getImagePattern(), null,null)));
        stage.setScene(new Scene(borderPane));
        stage.show();
    }

    private void setBackButton(BorderPane pane)
    {
        Button back = new Button();
        back.setAlignment(Pos.TOP_LEFT);
        back.setPrefHeight(50);
        back.setPrefWidth(50);
        back.setBackground(new Background(new BackgroundFill(Icons.BACK.getImagePattern(),null,null)));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().clear();
                pane.setBackground(new Background(new BackgroundFill(Icons.MAIN_MENU_BG.getImagePattern(),null,null)));
                pane.getChildren().addAll(buttons,back);
                back.setOnMouseClicked( new EventHandler <MouseEvent>() {
                    @Override
                    public void handle( MouseEvent mouseEvent ){
                        try {
                            new LoginMenu().start( stage );
                        } catch(Exception e) {
                            throw new RuntimeException( e );
                        }
                    }
                } );
            }
        });
        this.back = back;
        pane.setBottom(back);
    }

    public ArrayList<String> invites = new ArrayList<>();

    private void viewInvites(BorderPane pane)
    {
        //todo : i suppose i have an arraylist of invites that have username called invites
        pane.getChildren().clear();
        setBackButton(pane);
        pane.setBackground(new Background(new BackgroundFill(Icons.WHITE.getImagePattern(),null,null)));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new MainMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        String style = "-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #18cb2b;";
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        Scene scene1 = new Scene(scrollPane);
        stage.setScene(scene1);
        stage.close();
        pane.setCenter(vBox);
        HBox hBox = new HBox();
        for (String string : invites){
            Text text = new Text("User : "+string);
            text.setStyle(style);
            text.setFill(Color.LIGHTBLUE);
            hBox.getChildren().clear();
            Button accept = new Button();
            accept.setBackground(new Background(new BackgroundFill(Icons.APPROVE.getImagePattern(),null,null)));
            accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //todo : what should we do here
                }
            });
            Button reject = new Button();
            reject.setBackground(new Background(new BackgroundFill(Icons.DENY.getImagePattern(), null,null)));
            reject.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // todo : what should we do here
                }
            });
            hBox.getChildren().addAll(reject,accept,text);
            vBox.getChildren().add(hBox);
        }
        stage.show();
    }

    private void search (BorderPane pane)
    {
        pane.getChildren().clear();
        pane.setBackground(new Background(new BackgroundFill(Icons.FRIEND_BG.getImagePattern(),null,null)));
        setBackButton(pane);
        String style = "-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #18cb2b;";
        TextField find = new TextField();
        find.setStyle("-fx-prompt-text-fill: #19e0a3;\n" +
                "    -fx-text-fill: #0da2ef;");
        find.setAlignment(Pos.CENTER);
        find.setPrefHeight(60);
        find.setPromptText("Find User Information");
        find.setPrefWidth(pane.getPrefWidth()*0.6);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Button submit = new Button();
        submit.setStyle("-fx-background-color: #4CAF50; \n" +
                "    -fx-text-fill: white; \n" +
                "    -fx-font-size: 14px; \n" +
                "    -fx-padding: 8px 16px; \n" +
                "    -fx-border-radius: 4px; \n" +
                "    -fx-cursor: hand; ");
        submit.setText("SUBMIT");
        VBox template = new VBox();
        template.setAlignment(Pos.CENTER);
        template.setSpacing(5);
        String username=null;
        String nickname=null;
        String email=null;
        Text user = new Text("Username : ");
        Text nick = new Text("Nickname : ");
        Text mail = new Text("Email : ");
        Text noFound = new Text("Not Found");
        user.setStyle(style);nick.setStyle(style);mail.setStyle(style);noFound.setStyle(style);
        noFound.setStyle("-fx-font-size: 20px");
        noFound.setFill(Color.RED);
        Button sendFriendShip = new Button();
        sendFriendShip.setPrefHeight(80);
        sendFriendShip.setPrefWidth(80);
        sendFriendShip.setBackground(new Background(new BackgroundFill(Icons.FRIEND.getImagePattern(),null,null)));
        find.textProperty().addListener((observable, oldValue, newValue) -> {
            template.getChildren().clear();
        });
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // todo : how to find
                if (false && !template.getChildren().contains(noFound))
                    template.getChildren().add(noFound);
                else if (!template.getChildren().contains(user)){
                    user.setText("Username : "+username);
                    nick.setText("Nickname : "+nickname);
                    mail.setText("Email : "+email);
                    sendFriendShip.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            // todo : how to invite him ?
                        }
                    });
                    template.getChildren().addAll(user,nick,mail,sendFriendShip);
                }
            }
        });
        vBox.getChildren().addAll(find,submit,template);
        vBox.setSpacing(30);
        pane.setTop(vBox);
    }

}
