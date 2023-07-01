package org.example.controller;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.model.Player;

import java.util.ArrayList;

public class PlayerList {

    private static Group group = new Group() ;
    private static Pane pane ;
    private static boolean isShowing = false ;

    public static void add( ArrayList <Player> players , Pane pane ){
        if( isShowing ) return ;
        isShowing = true ;
        PlayerList.pane = pane ;
        int n = 0 ;
        for( Player player : players ){
            double width = 100 ;
            double height = 30 ;
            Rectangle rect = new Rectangle( GameGraphicalController.SCREEN_WIDTH / 2 - width / 2 , GameGraphicalController.SCREEN_HEIGHT / 4 + n * (height + 3) , width , height ) ;
            Text text = new Text( player.getNickname() ) ;
            text.setY(n * ( height + 3 ) + height / 2 + GameGraphicalController.SCREEN_HEIGHT / 4) ;
            text.setX( GameGraphicalController.SCREEN_WIDTH / 2 - width / 2 + 10 ) ;
            group.getChildren().addAll(rect , text) ;
            rect.setFill( Color.GREEN );
            text.setFill( Color.WHITE ) ;
            rect.setOpacity( 0.5 ) ;
            n++ ;
        }
        pane.getChildren().add( group ) ;
    }

    public static void remove(){
        isShowing = false ;
        pane.getChildren().remove( group ) ;
    }

}
