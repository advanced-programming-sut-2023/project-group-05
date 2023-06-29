package org.example.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.model.*;
import org.example.model.building.Building;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameGraphicalController {

    private static Stage stage;

    public static GameController gameController ;
    private static Pane pane;
    private static Polygon[][] map;
    private static int width;

    public static Stage getStage() {
        return stage;
    }

    public static Pane getPane() {
        return pane;
    }

    private static int height;

    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    private static final int TILE_HEIGHT = 22;
    private static final int TILE_WIDTH = TILE_HEIGHT * 2;
    public static Camera camera;
    public static Polygon mouse ;
    public static int mouseWidth = 1 ;
    public static int mouseHeight = 1 ;
    private static int mouseRow = 0 ;
    private static int mouseColumn = 0 ;
    public static String chosenBuilding = "" ;
    public static ArrayList<Node> reservedShapes;

    private static bottomMenu bottom = new bottomMenu();

    public static String selectedBuildingType = null ;

    public static void init(Stage stage, Pane pane , GameController gameController) {
        GameGraphicalController.pane = pane;
        GameGraphicalController.stage = stage;
        GameGraphicalController.gameController = gameController ;
        reservedShapes = new ArrayList<>();
        width = 400;
        height = 400;
        SCREEN_HEIGHT = 700;
        SCREEN_WIDTH = 1000;
        pane.requestFocus();
        initTestMode() ;
        initMap(height, width);
        initImages();
        initKeyboardControlKeys();
        bottomMenu.initGraphicalMenu();
        initMouse() ;
    }

    private static void initImages(){
        // init images
    }

    public static void updateMouse(){
        int[] arr = { 0 , 0 , mouseHeight * TILE_WIDTH / 2 , mouseHeight * TILE_HEIGHT / 2 , ( mouseHeight - mouseWidth ) * TILE_WIDTH / 2 , ( mouseHeight + mouseWidth ) * TILE_HEIGHT / 2 , -mouseWidth * TILE_WIDTH / 2 , mouseWidth * TILE_HEIGHT / 2 } ;
        for(int i = 0 ; i < 8 ; i++)
            mouse.getPoints().set(i , (double)arr[i] ) ;
    }

    private static void initMouse(){
        mouse = new Polygon( 0 , 0 , mouseHeight * TILE_WIDTH / 2 , mouseHeight * TILE_HEIGHT / 2 , ( mouseHeight - mouseWidth ) * TILE_WIDTH / 2 , ( mouseHeight + mouseWidth ) * TILE_HEIGHT / 2 , -mouseWidth * TILE_WIDTH / 2 , mouseWidth * TILE_HEIGHT / 2 ) ;
        mouse.setOpacity( 0.5 );
        for(int i = 0 ; i < 400 ; i++)
            for(int j = 0 ; j < 400 ; j++){
                Polygon cell = camera.getMap()[i][j] ;
                int finalI = i ;
                int finalJ = j ;
                cell.setOnMouseMoved( new EventHandler <MouseEvent>() {
                    @Override
                    public void handle( MouseEvent mouseEvent ){
                        mouse.setLayoutX( cell.getLayoutX() ) ;
                        mouse.setLayoutY( cell.getLayoutY() ) ;
                        mouseRow = finalI ;
                        mouseColumn = finalJ ;
                        boolean green = true;
                        outer :
                            for(int k = finalI ; k < finalI + mouseHeight ; k++)
                                for(int m = finalJ ; m < finalJ + mouseWidth ; m++)
                                    if(gameController.getGameMap().getCell(k,m).getBuilding() != null ||
                                            !gameController.getGameMap().getCell(k,m).getUnits().isEmpty() ||
                                            gameController.getGameMap().getCell(k,m).getCellType() == CellType.SEA ){
                                        green = false;
                                        break outer;
                                    }
                        if(green)
                            mouse.setFill( Color.GREEN ) ;
                        else
                            mouse.setFill( Color.RED ) ;
                    }
                } );
            }
        mouse.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                if(mouseEvent.getButton() == MouseButton.SECONDARY){
                    pane.getChildren().remove( mouse ) ;
                    reservedShapes.remove( mouse ) ;
                } else if ( mouse.getFill() == Color.GREEN ) {
                    // TODO : owner
                    gameController.putBuildingInPlace( Building.createBuildingByName( chosenBuilding , gameController.getPlayers().get(0) , mouseRow , mouseColumn ) ) ;
                    addBuildingImage( mouseRow , mouseColumn ) ;
                } else {
                    System.out.println( "Bro you can't place that shit here" ) ;
                }
            }
        } );
    }

    private static void initMap( int height , int width ){
        map = new Polygon[height][width] ;
        camera = new Camera( map , pane , gameController ) ;
        camera.draw() ;
        camera.move(0) ;
    }

    public static void initKeyboardControlKeys() {
        pane.requestFocus();
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle( KeyEvent keyEvent ){
                if( keyEvent.getCode().equals( KeyCode.DOWN ) ){
                    camera.move( 0 );
                }
                if( keyEvent.getCode().equals( KeyCode.UP ) ){
                    camera.move( 1 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.LEFT ) ){
                    camera.move( 2 ) ;
                }
                if( keyEvent.getCode().equals( KeyCode.RIGHT ) ){
                    camera.move( 3 ) ;
                }
            }
        });
    }

    private static void addBuildingImage(int row, int column){
        ImagePattern imagePattern = BuildingImages.getImagePattern( chosenBuilding ) ;
        Rectangle rectangle = new Rectangle( 0 , 0 , 101 , 101 ) ;
        pane.getChildren().add(rectangle) ;
        rectangle.setFill( imagePattern ) ;
    }




    public static void alterMenu(ArrayList<Rectangle> first, ArrayList<Rectangle> second, Pane pane) {
        for (Rectangle rectangle : first) pane.getChildren().remove(rectangle);
        for (Rectangle rectangle : second) pane.getChildren().add(rectangle);
    }


    public static ImagePattern loadImagePattern(URL url) {
        ImagePattern imagePattern;
        imagePattern = new ImagePattern(new Image(url.toExternalForm()));
        return imagePattern;
    }

    private static void initTestMode(){
        System.out.println( "you are running the test mode" ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "archer" , 1 , 13 , 13 ) ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 25 , 20 )) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "operator" , 1 , 20 , 20 )) ;
    }

}
