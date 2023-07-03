package org.example.controller;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.example.Main;
import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.awt.datatransfer.Clipboard;
import java.net.URL;
import java.util.ArrayList;

public class GameGraphicalController {

    private static Stage stage;
    public static GameController gameController;
    private static Pane pane;
    private static Polygon[][] map;
    private static int width;
    private static int height;
    public static ArrayList<Polygon> selectedBuildingsShapes = new ArrayList<>();
    public static int SCREEN_HEIGHT = 700;
    public static int SCREEN_WIDTH = 1000;
    private static final int TILE_HEIGHT = 22;
    private static final int TILE_WIDTH = TILE_HEIGHT * 2;
    public static Camera camera;
    public static Polygon mouse;
    public static int mouseWidth = 1;
    public static int mouseHeight = 1;
    private static int mouseRow = 0;
    private static int mouseColumn = 0;
    public static Minimap minimap;
    public static ArrayList<Node> reservedShapes;
    public static Rectangle attackingMouse = new Rectangle( 0 , 0 , 40 , 40 ) ;
    public static Rectangle copyingMouse = new Rectangle( 0 , 0 , 40 , 40 ) ;
    public static String chosenBuildingName = null ;
    public static String selectedBuildingName = null ;
    public static String copiedBuildingName = null ;
    public static Group lastNode = new Group() ;
    public static Group firstNode = new Group() ;
    public static Group weaponsNode = new Group() ;
    public static Group middleNode = new Group() ;
    public static Scale scale = new Scale() ;

    public static void init(Stage stage, Pane pane, GameController gameController) {
        GameGraphicalController.pane = pane;
        pane.getChildren().add(firstNode) ;
        pane.getChildren().add(middleNode) ;
        pane.getChildren().add(weaponsNode) ;
        pane.getChildren().add(lastNode) ;
        GameGraphicalController.stage = stage;
        GameGraphicalController.gameController = gameController;
        reservedShapes = new ArrayList<>();
        width = 400;
        height = 400;
        pane.requestFocus();
        // initTestMode() ;
        instantiate() ;
        initGraphicalMenu();
        initMap(height, width);
        initMouse() ;
        initKeyboardControlKeys() ;
    }

    public static void updateMouse() {
        int[] arr = {0, 0, mouseHeight * TILE_WIDTH / 2, mouseHeight * TILE_HEIGHT / 2, (mouseHeight - mouseWidth) * TILE_WIDTH / 2, (mouseHeight + mouseWidth) * TILE_HEIGHT / 2, -mouseWidth * TILE_WIDTH / 2, mouseWidth * TILE_HEIGHT / 2};
        for (int i = 0; i < 8; i++)
            mouse.getPoints().set(i, (double) arr[i]);
    }

    public static Pane getPane() {
        return pane;
    }

    public static Player getPlayer() {
        return gameController.getPlayers().get(0);
    }


    public static void zoomIn(){
        if( pane.getTransforms().isEmpty() ) pane.getTransforms().add( scale ) ;
        if( scale.getX() < 1.05 ) return ;
        scale.setX( scale.getX() - 0.1 );
        scale.setY( scale.getY() - 0.1 );
    }

    public static void zoomOut(){
        if( pane.getTransforms().isEmpty() ) pane.getTransforms().add( scale ) ;
        scale.setX( scale.getX() + 0.1 );
        scale.setY( scale.getY() + 0.1 );
    }

    public static void putMangonel(){
        chosenBuildingName = "mangonel" ;
        mouse.setFill( Color.RED ) ;
        mouseWidth = 2 ;
        mouseHeight = 2 ;
        updateMouse() ;
        mouse.setVisible( true ) ;
    }

    private static void initMouse(){
        attackingMouse.setFill( new ImagePattern( new Image( Main.class.getResource( "/images/icons/sword.png" ).toExternalForm() ) ) ) ;
        lastNode.getChildren().add( attackingMouse ) ;
        attackingMouse.setVisible( false ) ;
        mouse.setOpacity( 0.5 );
        copyingMouse.setFill( new ImagePattern( new Image( Main.class.getResource( "/images/icons/copy.png" ).toExternalForm() ) ) ) ;
        copyingMouse.setVisible( false ) ;
        lastNode.getChildren().add( copyingMouse ) ;
        pane.setOnMouseMoved( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                copyingMouse.setX( mouseEvent.getX() - copyingMouse.getWidth() - 1 ) ;
                copyingMouse.setY( mouseEvent.getY() - copyingMouse.getHeight() - 1 ) ;

            }
        } ) ;
        pane.setOnScroll( new EventHandler <ScrollEvent>() {
            @Override
            public void handle( ScrollEvent scrollEvent ){
                if( scrollEvent.getDeltaY() > 0 )
                    zoomOut() ;
                else
                    zoomIn() ;
            }
        } );
        for(int i = 0 ; i < 400 ; i++)
            for(int j = 0 ; j < 400 ; j++){
                Polygon cell = camera.getMap()[i][j] ;
                int finalI = i ;
                int finalJ = j ;
                cell.setOnMouseMoved( new EventHandler <MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouse.setLayoutX(cell.getLayoutX());
                        mouse.setLayoutY(cell.getLayoutY());
                        mouseRow = finalI;
                        mouseColumn = finalJ;
                        boolean green = true;
                        outer:
                        for (int k = finalI; k < finalI + mouseHeight; k++)
                            for (int m = finalJ; m < finalJ + mouseWidth; m++)
                                if (gameController.getGameMap().getCell(k, m).getBuilding() != null ||
                                        !gameController.getGameMap().getCell(k, m).getUnits().isEmpty() ||
                                        gameController.getGameMap().getCell(k, m).getCellType() == CellType.SEA) {
                                    green = false;
                                    break outer;
                                }
                        if (green)
                            mouse.setFill(Color.GREEN);
                        else
                            mouse.setFill(Color.RED);
                    }
                });
            }
        mouse.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                if(mouseEvent.getButton() == MouseButton.SECONDARY){
                    mouse.setVisible(false) ;
                } else if ( mouse.getFill() == Color.GREEN ) {
                    gameController.putBuildingInPlace( Building.createBuildingByName( chosenBuildingName, gameController.getPlayers().get(0) , mouseRow , mouseColumn ) ) ;
                    addBuildingImage( mouseRow , mouseColumn , chosenBuildingName ) ;
                }
            }
        });
        reservedShapes.add(mouse);
        mouse.setVisible(false);
        pane.getChildren().add(mouse);
    }

    public static void initGraphicalMenu() {
        BottomMenu.initGraphicalMenu();
    }

    private static void instantiate() {
        map = new Polygon[height][width];
        camera = new Camera(map, pane, gameController);
        mouse = new Polygon(0, 0, mouseHeight * TILE_WIDTH / 2, mouseHeight * TILE_HEIGHT / 2, (mouseHeight - mouseWidth) * TILE_WIDTH / 2, (mouseHeight + mouseWidth) * TILE_HEIGHT / 2, -mouseWidth * TILE_WIDTH / 2, mouseWidth * TILE_HEIGHT / 2);
    }

    private static void initMap(int height, int width) {
        camera.draw();
        camera.move(0);
    }

    public static void initKeyboardControlKeys() {
        pane.requestFocus();
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    camera.move(0);
                }
                if (keyEvent.getCode().equals(KeyCode.UP)) {
                    camera.move(1);
                }
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    camera.move(2);
                }
                if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    camera.move(3);
                }
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    BottomMenu.pauseGame();
                }
                if (keyEvent.getCode().equals(KeyCode.K)) {
                    debug();
                }
                if (keyEvent.getCode().equals(KeyCode.M)) {
                    minimap.setVisible(!minimap.isVisible());
                }
                if (keyEvent.getCode().equals(KeyCode.S)) {
                    SoundController.setMusicMute(!SoundController.isMusicMute);
                }
                if( keyEvent.getCode().equals(KeyCode.C) ){
                    copyingMouse.setVisible( true ) ;
                    mouse.setVisible( true ) ;
                    String buildingName = camera.getHoveredBuilding() ;
                    if( null == buildingName ){
                        copyingMouse.setVisible( false ); ;
                        mouse.setVisible( false ) ;
                    } else {
                        copiedBuildingName = buildingName ;
                    }

                }
                if( keyEvent.getCode().equals(KeyCode.V) ){
                    if( copyingMouse.isVisible() ){
                        Building pastedBuilding = Building.createBuildingByName( copiedBuildingName , getPlayer() , mouseRow , mouseColumn ) ;
                        gameController.putBuildingInPlace( pastedBuilding );
                        addBuildingImage( mouseRow , mouseColumn , copiedBuildingName );
                    }
                    copyingMouse.setVisible( false ) ;
                    mouse.setVisible( false ) ;
                }
                if( keyEvent.getCode().equals(KeyCode.A) ){
                    for( Unit unit : Unit.getUnits() ){
                        Player player = unit.getOwner() ;
                        if( player != gameController.getPlayers().get(0) ) continue ;
                        player.selectUnit( unit );
                        Glow glow = new Glow() ;
                        glow.setLevel( 200 );
                        unit.getShape().setEffect( glow ) ;

                    }
                }
                if (keyEvent.getCode().equals(KeyCode.TAB)) {
                    PlayerList.add(gameController.getPlayers(), pane);
                }
            }
        });

        pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.TAB) {
                    PlayerList.remove();
                }
            }
        });

    }

    private static void addBuildingImage(int row, int column, String name) {
        Polygon buildingShape = camera.addBuilding(row, column, name);
        mouse.setVisible(false);
        buildingShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    selectedBuildingName = name ;
                    Glow glow = new Glow();
                    glow.setLevel(100);
                    buildingShape.setEffect(glow);
                    selectedBuildingsShapes.add(buildingShape);
                    System.out.println( name ) ;
                } else {
                    BottomMenu.deSelect();
                    buildingShape.setEffect(null);
                    selectedBuildingsShapes.remove(buildingShape);
                    selectedBuildingName = null ;
                    return;
                }
                if (mouseEvent.getButton() == MouseButton.PRIMARY && !lastNode.getChildren().contains(BottomMenu.back)) {
                    switch (selectedBuildingName) {
                        case "market" -> BottomMenu.preInitShop();
                        case "engineerguild" -> BottomMenu.initEngineerGuild();
                        case "barracks" -> BottomMenu.initBarracks();
                    }
                }
            }
        });
    }


    public static ImagePattern loadImagePattern(URL url) {
        ImagePattern imagePattern;
        imagePattern = new ImagePattern(new Image(url.toExternalForm()));
        return imagePattern;
    }


    private static void initTestMode(){
        System.out.println( "you are running the test mode" ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "archer" , 1 , 13 , 13 ) ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 13 , 15 ) ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 13 , 17 ) ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 13 , 20 ) ) ;
        //System.out.println( "GameGraphicalController : " + gameController.dropUnit( "operator" , 1 , 20 , 20 ) ) ;
        gameController.nextTurn() ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 20 , 13 ) ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 20 , 17 ) ) ;
        System.out.println( "GameGraphicalController : " + gameController.dropUnit( "swordsman" , 1 , 20 , 22 ) ) ;

    }

    private static void debug() {
        getPlayer().setGold( getPlayer().getGold() + 1000 ) ;
        System.out.println( selectedBuildingName ) ;
        BottomMenu.updateScribeNotes();
        for (Unit unit : Unit.getUnits()) {
            System.out.println(unit.getOwner().getNickname());
        }
    }

}
