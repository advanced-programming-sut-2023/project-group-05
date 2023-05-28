package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.controller.GameGraphicalController;

import java.io.IOException;

public class Game extends Application {

    @Override
    public void start( Stage stage ) throws IOException{
        Pane pane = FXMLLoader.load( getClass().getResource( "/fxml/Game.fxml" ) ) ;
        Scene scene = new Scene( pane ) ;
        stage.setScene( scene ) ;
        stage.show() ;
        GameGraphicalController.init( stage , pane ) ;
    }

}
