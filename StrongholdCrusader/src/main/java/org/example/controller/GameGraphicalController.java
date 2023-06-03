package org.example.controller;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.example.Main;

import java.net.URL;
import java.util.ArrayList;

public class GameGraphicalController {

    private static Stage stage ;
    private static Pane pane ;
    private static Polygon[][] map ;
    private static int width ;
    private static int height ;
    private static Rectangle graphicalMenu ;
    private static ArrayList <Rectangle> graphicalMenuButton ;
    private static ArrayList <Rectangle> buildings ;
    private static int SCREEN_HEIGHT ;
    private static int SCREEN_WIDTH ;
    private static final int TILE_HEIGHT = 20 ;
    private static final int TILE_WIDTH = TILE_HEIGHT * 2 ;
    private static Camera camera ;
    public static ArrayList<Shape> reservedShapes ;

    public static void init( Stage stage , Pane pane ){
        GameGraphicalController.pane = pane ;
        GameGraphicalController.stage = stage ;
        reservedShapes = new ArrayList<>() ;
        width = 400 ;
        height = 400 ;
        SCREEN_HEIGHT = 700 ;
        SCREEN_WIDTH = 1000 ;
        pane.requestFocus() ;
        initMap( height , width ) ;
        initImages() ;
        initKeyboardControlKeys() ;
        initGraphicalMenu() ;
    }

    private static void initImages(){
        // init images
    }

    private static void initMap( int height , int width ){

        map = new Polygon[height][width] ;

        camera = new Camera( map , pane ) ;

        camera.draw() ;
        camera.move(0) ;

    }

    public static void initKeyboardControlKeys(){
        pane.requestFocus();
        pane.setOnKeyPressed( new EventHandler <KeyEvent>() {
            @Override
            public void handle( KeyEvent keyEvent ){
                if( keyEvent.getCode().equals( KeyCode.DOWN ) ){
                    camera.move( 1 );
                }
                if( keyEvent.getCode().equals( KeyCode.UP ) ){
                    camera.move( 0 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.LEFT ) ){
                    camera.move( 3 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.RIGHT ) ){
                    camera.move( 2 ) ;
                }
            }
        } ) ;
    }

    private static int selectedButton = 0 ;

    public static void initGraphicalMenu(){
        graphicalMenu = new Rectangle( 0 , SCREEN_HEIGHT*0.8 , SCREEN_WIDTH , SCREEN_HEIGHT*0.2 ) ;
        reservedShapes.add( graphicalMenu ) ;
        pane.getChildren().add( graphicalMenu ) ;
    }

    public static ImagePattern loadImagePattern( URL url ){
        ImagePattern imagePattern ;
        imagePattern = new ImagePattern( new Image( url.toExternalForm() ) ) ;
        return imagePattern ;
    }

}
