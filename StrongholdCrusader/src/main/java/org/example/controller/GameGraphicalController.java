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
    public static ArrayList<Image> armouries = new ArrayList<>();
    public static ArrayList<Image> foods = new ArrayList<>();
    public static ArrayList<Image> houseAndStorage = new ArrayList<>();
    public static ArrayList<Image> militaryBuildings = new ArrayList<>();
    public static ArrayList<Image> goodThings = new ArrayList<>();
    public static ArrayList<Image> badThings = new ArrayList<>();
    public static ArrayList<Image> towerAndWalls = new ArrayList<>();
    public static ArrayList<Image> industries = new ArrayList<>();
    public static GameController gameController ;
    private static Pane pane;
    private static Polygon[][] map;
    private static int width;
    private static int height;
    private static Rectangle graphicalMenu;
    private static ArrayList<Rectangle> graphicalMenuButton;
    private static ArrayList<Rectangle> buildings;
    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    private static final int TILE_HEIGHT = 22;
    private static final int TILE_WIDTH = TILE_HEIGHT * 2;
    public static Camera camera;
    public static Polygon mouse ;
    public static int mouseWidth = 1 ;
    private static int mouseHeight = 1 ;
    private static int mouseRow = 0 ;
    private static int mouseColumn = 0 ;
    public static ArrayList<Node> reservedShapes;
    private static String chosenBuildingName = "" ;
    public static ArrayList<Rectangle> current;
    public static ArrayList<Circle> buttons = new ArrayList<>();
    public static HashMap<Circle, ArrayList<Rectangle>> buttonMap = new HashMap<>();
    public static ArrayList<ArrayList<Rectangle>> menuMap = new ArrayList<>();

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
        initKeyboardControlKeys();
        initGraphicalMenu();
        initMouse() ;
    }

    private static void updateMouse(){
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
                    gameController.putBuildingInPlace( Building.createBuildingByName( chosenBuildingName, gameController.getPlayers().get(0) , mouseRow , mouseColumn ) ) ;
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
        ImagePattern imagePattern = BuildingImages.getImagePattern( chosenBuildingName ) ;
        int width = BuildingEnum.getBuildingWidthByName( chosenBuildingName ) ;
        int height = BuildingEnum.getBuildingHeightByName( chosenBuildingName ) ;
        /*
        *  TODO:  read this:
        *         It's 23:53 and I'm already tired...
        *         28-JUNE-2023
        *         -Danial
        */
        // fact : every building is in shape of a hexagon-like.
        int size = width ;
        Polygon hexagon = new Polygon(
                0, 0,
                size * TILE_WIDTH / 2 , size * TILE_HEIGHT / 2,
                size * TILE_WIDTH / 2 , 3 * size * TILE_HEIGHT / 2,
                0 , 4 * size * TILE_HEIGHT / 2,
                -size * TILE_WIDTH / 2 , 3 * size * TILE_HEIGHT / 2,
                -size * TILE_WIDTH / 2 , size * TILE_HEIGHT / 2
        ) ;
        pane.getChildren().add(hexagon) ;
        double x = map[row][column].getLayoutX() ;
        double y = map[row][column].getLayoutY() - TILE_HEIGHT * size ;
        hexagon.setLayoutX(x) ;
        hexagon.setLayoutY(y) ;
        hexagon.setFill( imagePattern ) ;
        pane.getChildren().remove( mouse ) ;
        reservedShapes.remove( mouse ) ;
    }

    public static void initGraphicalMenu() {
        graphicalMenu = new Rectangle(0, SCREEN_HEIGHT * 0.7, SCREEN_WIDTH, SCREEN_HEIGHT * 0.3);
        graphicalMenu.setFill(new ImagePattern(BuildingImages.MENU.getImage()));
        reservedShapes.add(graphicalMenu);

        // adding the minimap
        Minimap minimap = new Minimap(camera, gameController) ;
        minimap.setLayoutX( minimap.getLayoutX() + SCREEN_WIDTH - minimap.getSize() );
        reservedShapes.add( minimap ) ;
        pane.getChildren().add( minimap ) ;

        pane.getChildren().add(graphicalMenu);
        ArrayList<Rectangle> armoury = new ArrayList<>();
        ArrayList<String> armouryNames = new ArrayList<>( Arrays.asList( "armourer" , "blacksmith" , "fletcher" , "poleturner" , "tanner" ) ) ;
        initArray(armoury, armouries, 40, 40, armouryNames);

        ArrayList<Rectangle> food = new ArrayList<>();
        ArrayList<String> foodNames = new ArrayList<>( Arrays.asList( "appleorchard" , "bakery" , "brewer" , "dairyfarmer" , "hopsfarmer" , "huterpost" , "inn" , "wheatfarmer", "food9" ) ) ;
        initArray(food, foods, 30, 30, foodNames);

        ArrayList<Rectangle> houseAndStorages = new ArrayList<>();
        ArrayList<String> houseAndStorageNames = new ArrayList<>( Arrays.asList( "granary" , "market" , "stockpile" , "hovel" , "has5" , "has6" , "has7" ) ) ;
        initArray(houseAndStorages, houseAndStorage, 30, 30, houseAndStorageNames);

        ArrayList<Rectangle> industry = new ArrayList<>();
        ArrayList<String> industryNames = new ArrayList<>( Arrays.asList( "ironmine" , "oxtether" , "pitchrig" , "quarry" , "woodcutter" ) ) ;
        initArray(industry, industries, 40, 40, industryNames);

        ArrayList<Rectangle> militaryBuilding = new ArrayList<>();
        ArrayList<String> militaryBuildingsNames = new ArrayList<>( Arrays.asList( "armoury" , "barracks" , "mercenarypost" , "engineerguild" , "siegetent" , "stable" , "tunnelerguild" ) ) ;
        initArray(militaryBuilding, militaryBuildings, 40, 40, militaryBuildingsNames);

        ArrayList<Rectangle> goodThing = new ArrayList<>();
        ArrayList<String> goodNames = new ArrayList<>( Arrays.asList( "gt1" , "gt2" , "gt3" , "gt4" , "gt5" , "gt6", "gt7" , "gt8") ) ;
        initArray(goodThing, goodThings, 40, 40, goodNames);

        ArrayList<Rectangle> badThing = new ArrayList<>();
        ArrayList<String> badNames = new ArrayList<>( Arrays.asList( "bt1" , "bt2" , "bt3" , "bt4" , "bt5" , "bt6" , "bt7" , "bt8" ) ) ;
        initArray(badThing, badThings, 40, 40, badNames);

        ArrayList<Rectangle> towerAndWall = new ArrayList<>();
        ArrayList<String> towerAndWallNames = new ArrayList<>( Arrays.asList( "taw1" , "taw2" , "taw3" , "taw4" , "taw5" , "squaretower" , "roundtower" , "taw8" , "taw9" ) ) ;
        initArray(towerAndWall, towerAndWalls, 20, 40, towerAndWallNames);

        for (Rectangle rectangle : houseAndStorages)
            pane.getChildren().add(rectangle);
        current = houseAndStorages;
        setMenuButtons(pane);
    }

    public static void setMenuButtons(Pane pane) {
        Circle house = new Circle(SCREEN_HEIGHT * 0.95, SCREEN_HEIGHT * 0.95 - 90, 15);
        house.setFill(Color.BLUE);
        buttonMap.put(house,menuMap.get(2));
        Circle food = new Circle(SCREEN_HEIGHT * 0.95-30, SCREEN_HEIGHT * 0.95 - 90, 15);
        food.setFill(Color.RED);
        buttonMap.put(food,menuMap.get(1));
        Circle armoury = new Circle(SCREEN_HEIGHT * 0.95-60, SCREEN_HEIGHT * 0.95 - 90, 15);
        armoury.setFill(Color.YELLOW);
        buttonMap.put(armoury,menuMap.get(0));
        Circle military = new Circle(SCREEN_HEIGHT * 0.95-90, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(military,menuMap.get(4));
        Circle good = new Circle(SCREEN_HEIGHT * 0.95-120, SCREEN_HEIGHT * 0.95- 90, 15);
        buttonMap.put(good,menuMap.get(5));
        Circle bad = new Circle(SCREEN_HEIGHT * 0.95-150, SCREEN_HEIGHT * 0.95- 90, 15);
        buttonMap.put(bad,menuMap.get(6));
        Circle towers = new Circle(SCREEN_HEIGHT * 0.95-180, SCREEN_HEIGHT * 0.95- 90, 15);
        buttonMap.put(towers,menuMap.get(7));
        Circle industry = new Circle(SCREEN_HEIGHT * 0.95-210, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(industry,menuMap.get(3));
        pane.getChildren().addAll(house, food, armoury, military, good, bad, towers, industry);
        buttons.add(house);
        buttons.add(food);
        buttons.add(armoury);
        buttons.add(military);
        buttons.add(good);
        buttons.add(bad);
        buttons.add(towers);
        buttons.add(industry);
        for (Circle circle : buttons) {
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    alterMenu(current,buttonMap.get(circle),pane);
                    current = buttonMap.get(circle);
                }
            });
        }

    }

    public static void alterMenu(ArrayList<Rectangle> first, ArrayList<Rectangle> second, Pane pane) {
        for (Rectangle rectangle : first) pane.getChildren().remove(rectangle);
        for (Rectangle rectangle : second) pane.getChildren().add(rectangle);
    }

    public static void initArray(ArrayList<Rectangle> array, ArrayList<Image> images, int height, int width, ArrayList<String> buildingNames) {
        int size = images.size();
        for (int i = 0; i < size; ++i) {
            Rectangle rectangle = new Rectangle(150 + SCREEN_WIDTH * ((double) i / size) * 0.5, SCREEN_HEIGHT * 0.85, height * 2, width * 2.5);
            rectangle.setFill(new ImagePattern(images.get(i)));
            int finalI = i ;
            rectangle.setOnMouseClicked( new EventHandler <MouseEvent>() {
                @Override
                public void handle( MouseEvent mouseEvent ){
                    chosenBuildingName = buildingNames.get(finalI) ;
                    System.out.println( "name : " + chosenBuildingName ) ;
                    mouseHeight = BuildingEnum.getBuildingHeightByName( buildingNames.get(finalI) ) ;
                    mouseWidth = BuildingEnum.getBuildingWidthByName( buildingNames.get(finalI) ) ;
                    updateMouse() ;
                    if( !pane.getChildren().contains( mouse ) ){
                        pane.getChildren().add( mouse );
                        reservedShapes.add( mouse );
                    }
                }
            } );
            array.add(rectangle);
        }
        menuMap.add(array);
        reservedShapes.addAll(array);
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
