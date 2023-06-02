package org.example.controller;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.example.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;

public class GameGraphicalController {

    private static Stage stage ;
    private static Pane pane ;
    private static Rectangle[][] map ;
    private static double[][] mapX ;
    private static double[][] mapY ;
    private static int width ;
    private static int height ;
    private static Rectangle graphicalMenu ;
    private static ArrayList <Rectangle> graphicalMenuButton ;
    private static ArrayList <Image> buildingImages ;
    private static ArrayList <Rectangle> buildings ;
    private static int SCREEN_HEIGHT ;
    private static int SCREEN_WIDTH ;

    public static void init( Stage stage , Pane pane ){
        GameGraphicalController.pane = pane ;
        GameGraphicalController.stage = stage ;
        width = 200 ;
        height = 200 ;
        SCREEN_HEIGHT = 700 ;
        SCREEN_WIDTH = 1000 ;
        pane.requestFocus() ;
        initMap( height , width ) ;
        initImages() ;
        initKeyboardControlKeys() ;
        initGraphicalMenu() ;
    }

    private static void initImages(){
        buildingImages = new ArrayList <>() ;
        buildingImages.add( new Image( Main.class.getResource( "/images/tiles/Buildings/Market.png" ).toExternalForm()) ) ;
        buildingImages.add( new Image( Main.class.getResource( "/images/tiles/Buildings/Barracks.png" ).toExternalForm()) ) ;
        buildingImages.add( new Image( Main.class.getResource( "/images/tiles/Buildings/Quarry.png" ).toExternalForm()) ) ;
        buildingImages.add( new Image( Main.class.getResource( "/images/tiles/Buildings/WoodCutter.png" ).toExternalForm()) ) ;
    }

    private static void initMap( int height , int width ){
        map = new Rectangle[height][width] ;
        mapX = new double[height][width] ;
        mapY = new double[height][width] ;
        ImagePattern imagePattern = new ImagePattern( new Image( Main.class.getResource( "/images/tiles/desert_tile.jpg" ).toExternalForm() ) ) ;
        for(int i = 0 ; i < height ; i++){
            for( int j = 0; j < width; j++ ){
                map[i][j] = new Rectangle(0, 0, 40, 40 );
                map[i][j].setX( j * 40 ) ;
                map[i][j].setY( i * 40 ) ;
                map[i][j].setFill( imagePattern );
                pane.getChildren().add( map[i][j] );
            }
        }
        // adjusting the correct angle to look from
        Point3D axis1 = new Point3D( 0 , 0 , 1 ) ;
        Point3D axis2 = new Point3D( 1 , -1 , 0 ) ;

        Rotate rotate1 = new Rotate() ;
        Rotate rotate2 = new Rotate() ;

        rotate1.setAxis(axis1) ;
        rotate2.setAxis(axis2) ;

        rotate1.setAngle(45) ;
        rotate2.setAngle(60) ;


        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width ; j++)
                map[i][j].getTransforms().addAll( new Translate() , rotate1 , rotate2 ) ;

        for(int i = 0 ; i < height ; i++ )
            for(int j = 0 ; j < width ; j++){
                mapX[i][j] = 20 * Math.sqrt ( 2 ) * ( j - i - 1 ) ;
                mapY[i][j] = 20 * Math.sqrt( 2 ) * Math.cos( 60 * Math.PI / 180 ) * ( i + j ) ;
            }

        buildings = new ArrayList <>() ;

        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width ; j++){
                int finalI = i;
                int finalJ = j;
                map[i][j].setOnMouseClicked( new EventHandler <MouseEvent>() {
                    @Override
                    public void handle( MouseEvent mouseEvent ){
                        Rectangle building = new Rectangle(mapX[finalI][finalJ],mapY[finalI][finalJ],40*Math.sqrt(2),40*Math.sqrt(2)*Math.cos(60*Math.PI/180)) ;
                        buildings.add( building ) ;
                        Image image = buildingImages.get(selectedButton) ;
                        building.setFill( new ImagePattern( buildingImages.get(selectedButton) ) ) ;
                        building.setHeight( building.getWidth() * image.getHeight() / image.getWidth() ) ;
                        building.setY( building.getY() - ( building.getHeight() - 40*Math.sqrt(2)*Math.cos(60*Math.PI/180) ) ) ;
                        pane.getChildren().add( building ) ;
                    }
                } );
            }

    }

    public static void moveMap( int dx , int dy ){
        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width ; j++){
                Translate translate = (Translate)map[i][j].getTransforms().get(0) ;
                translate.setX( translate.getX() + dx ) ;
                translate.setY( translate.getY() + dy ) ;
                mapX[i][j] += dx ;
                mapY[i][j] += dy ;
            }
        for(Rectangle building : buildings){
            building.setX( building.getX() + dx );
            building.setY( building.getY() + dy );
        }
    }

    public static void initKeyboardControlKeys(){
        pane.requestFocus();
        pane.setOnKeyPressed( new EventHandler <KeyEvent>() {
            @Override
            public void handle( KeyEvent keyEvent ){
                if( keyEvent.getCode().equals( KeyCode.DOWN ) ){
                    moveMap( 0 , 15 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.UP ) ){
                    moveMap( 0 , -15 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.LEFT ) ){
                    moveMap( -15 , 0 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.RIGHT ) ){
                    moveMap( 15 , 0 ) ;
                }
            }
        } ) ;
    }

    private static int selectedButton = 0 ;

    public static void initGraphicalMenu(){
        graphicalMenu = new Rectangle( 0 , SCREEN_HEIGHT*0.8 , SCREEN_WIDTH , SCREEN_HEIGHT*0.2 ) ;
        //graphicalMenu = new Rectangle( 0 , 0 , 50 , 50 ) ;
        graphicalMenu.setFill( Color.GRAY ) ;
        graphicalMenuButton = new ArrayList <>() ;
        pane.getChildren().add( graphicalMenu ) ;
        for(int i = 0 ; i < 4 ; i++){
            Rectangle newButton = new Rectangle( SCREEN_WIDTH * 0.1 , SCREEN_HEIGHT * 0.825, SCREEN_WIDTH * 0.1, SCREEN_HEIGHT * 0.15 ) ;
            graphicalMenuButton.add( newButton );
            graphicalMenuButton.get( i ).setFill( new ImagePattern(buildingImages.get(i)) ) ;
            int finalI = i;
            graphicalMenuButton.get( i ).setOnMouseClicked( new EventHandler <MouseEvent>() {
                @Override
                public void handle( MouseEvent mouseEvent ){
                    selectedButton = finalI;
                }
            } ) ;
            newButton.setX( newButton.getX() + i*(newButton.getWidth() + 10) ) ;
            pane.getChildren().add( graphicalMenuButton.get(i) ) ;
        }
    }

    public static ImagePattern loadImagePattern( URL url ){
        ImagePattern imagePattern ;
        imagePattern = new ImagePattern( new Image( url.toExternalForm() ) ) ;
        return imagePattern ;
    }

}
