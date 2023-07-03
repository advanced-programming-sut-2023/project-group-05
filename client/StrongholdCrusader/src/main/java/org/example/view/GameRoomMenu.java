package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameRoomMenu extends Application {

    public VBox userList;
    public Button leaveButton;
    public Button spectatorButton;

    public void start( Stage stage ) throws Exception {
        BorderPane borderPane = FXMLLoader.load( MainMenu.class.getResource( "/fxml/GameRoomMenu.fxml" ) ) ;
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

}
