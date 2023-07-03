package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.controller.GameConnectionReader;
import org.example.controller.GameConnectionWriter;
import org.example.controller.GameController;
import org.example.controller.GameGraphicalController;
import org.example.model.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Game extends Application {
    // TODO : check if kiani is removing from pane or setting visible !!!
    @Override
    public void start( Stage stage ) throws Exception {
        /*
        GameConnectionReader gcr = new GameConnectionReader(username) ;
        GameConnectionWriter gcw = new GameConnectionWriter(username) ;
        gcr.start() ;
        */
        Player player1 = new Player( "username1" , "nickname1" ) ;
        Player player2 = new Player( "username2" , "nickname2" ) ;

        Pane pane = FXMLLoader.load( getClass().getResource( "/fxml/Game.fxml" ) ) ;
        Scene scene = new Scene( pane ) ;
        stage.setScene( scene ) ;
        stage.show() ;
        GameController gameController = (new GameController( new ArrayList <>( Arrays.asList(player1 , player2)) )) ;
        GameGraphicalController.init( stage , pane , gameController ) ;
    }

}
