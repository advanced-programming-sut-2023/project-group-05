package org.example.view;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.GameConnectionReader;
import org.example.controller.GameConnectionWriter;
import org.example.model.Player;

public class Game extends Application {
    // TODO : check if kiani is removing from pane or setting visible !!!
    @Override
    public void start( Stage stage ) throws Exception {
        GameConnectionReader gcr = new GameConnectionReader() ;
        GameConnectionWriter gcw = new GameConnectionWriter() ;

        gcr.start() ;

        Player player1 = new Player( "username1" , "nickname1" ) ;
        Player player2 = new Player( "username2" , "nickname2" ) ;

        /*Pane pane = FXMLLoader.load( getClass().getResource( "/fxml/Game.fxml" ) ) ;
        Scene scene = new Scene( pane ) ;
        stage.setScene( scene ) ;
        stage.show() ;
        GameController gameController = (new GameController( new ArrayList<>(Arrays.asList(player1 , player2)) )) ;
        GameGraphicalController.init( stage , pane , gameController ) ;
    */}

}
