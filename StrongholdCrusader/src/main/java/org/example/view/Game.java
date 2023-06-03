package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.controller.GameController;
import org.example.controller.GameGraphicalController;
import org.example.model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Game extends Application {

    @Override
    public void start( Stage stage ) throws Exception {
        Pane pane = FXMLLoader.load( getClass().getResource( "/fxml/Game.fxml" ) ) ;
        Scene scene = new Scene( pane ) ;
        stage.setScene( scene ) ;
        stage.show() ;
        // for test
        Account acc1 = new Account( "Danial" , "dani_gang" , "DanialManial@gmail.com" , 12345678 , 0 , "we shall conquer" , 2 ,2 ) ;
        Account acc2 = new Account( "Amin" , "amin_gang" , "AminMamin@gmail.com" , 12345678 , 0 , "amin is the true king" , 0 ,0 ) ;
        GameController gameController = (new GameController( new ArrayList <Account>( Arrays.asList( acc1 , acc2 ) ) )) ;
        GameGraphicalController.init( stage , pane , gameController ) ;
    }

}
