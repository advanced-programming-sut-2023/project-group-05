package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.GameConnectionReader;
import org.example.controller.GameConnectionWriter;
import org.example.controller.GameController;
import org.example.controller.SignupLoginMenuController;
import org.example.model.enums.Icons;

public class GameRoomMenu extends Application {

    public VBox userList;
    public Button leaveButton;
    public Button spectatorButton;

    public void start( Stage stage ) throws Exception {
        BorderPane borderPane = FXMLLoader.load( MainMenu.class.getResource( "/fxml/GameRoomMenu.fxml" ) ) ;
        InviteOthers(borderPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Room Menu - " + GameController.currentUsername);
        stage.show();
        GameController.gcw = new GameConnectionWriter(GameController.currentUsername) ;
        GameController.gcr = new GameConnectionReader(GameController.currentUsername,stage) ;
        GameController.gcr.play() ;
    }

    public void setUsernamesAndNicknames( String[] usernamesAndNicknames ){
        for(int i = 0 ; i < usernamesAndNicknames.length / 2 ; i++){
            String username = usernamesAndNicknames[2*i] ;
            String nickname = usernamesAndNicknames[2*i+1] ;
            Button button = new Button() ;
            button.setText( username + "(" + nickname + ")" ) ;
            button.setStyle( "-fx-background-color: grey ; -fx-text-fill: blue ;" ) ;
            userList.getChildren().add( button ) ;
        }
    }

    private void InviteOthers(BorderPane pane)
    {
        //pane.getChildren().clear();
        pane.setBackground(new Background(new BackgroundFill(Icons.FRIEND_BG.getImagePattern(),null,null)));
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
        VBox buffer = new VBox();
        buffer.setAlignment(Pos.CENTER);
        VBox error = new VBox();
        error.setAlignment(Pos.CENTER);
        buffer.setSpacing(5);
        String username=null;
        Text user = new Text("Username : ");
        Text noFound = new Text("Not Found");
        noFound.setStyle("-fx-font-size: 20px");
        noFound.setFill(Color.RED);
        find.textProperty().addListener((observable, oldValue, newValue) -> {
            buffer.getChildren().clear();
        });
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // todo : how to find
                if (!SignupLoginMenuController.userExists( find.getText() ) && !error.getChildren().contains(noFound)) {
                    error.getChildren().add(noFound);
                    new Alert(Alert.AlertType.ERROR,"User Not Found");
                }
                else if (!buffer.getChildren().contains(user)){
                    user.setText("Username : "+username);
                    // how to send invitation
                    buffer.getChildren().add(user);
                    new Alert(Alert.AlertType.INFORMATION,"User Invited Successfully");
                }
            }
        });
        vBox.getChildren().addAll(find,submit,error);
        vBox.setSpacing(30);
        pane.setTop(vBox);
    }


}
