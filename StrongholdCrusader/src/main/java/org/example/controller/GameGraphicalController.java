package org.example.controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.example.Main;
import org.example.model.BuildingImages;

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


    private static Pane pane;
    private static Polygon[][] map;
    private static int width;
    private static int height;
    private static Rectangle graphicalMenu;
    private static ArrayList<Rectangle> graphicalMenuButton;
    private static ArrayList<Rectangle> buildings;
    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    private static final int TILE_HEIGHT = 20;
    private static final int TILE_WIDTH = TILE_HEIGHT * 2;
    private static Camera camera;
    public static ArrayList<Shape> reservedShapes;

    public static void init(Stage stage, Pane pane) {
        GameGraphicalController.pane = pane;
        GameGraphicalController.stage = stage;
        reservedShapes = new ArrayList<>();
        width = 400;
        height = 400;
        SCREEN_HEIGHT = 700;
        SCREEN_WIDTH = 1000;
        pane.requestFocus();
        initMap(height, width);
        initImages();
        initKeyboardControlKeys();
        initGraphicalMenu();
    }

    private static void initImages() {
        // init images
    }

    private static void initMap(int height, int width) {
        map = new Polygon[height][width];
        camera = new Camera(map, pane);
        camera.draw();
        camera.move(0);
    }

    public static void initKeyboardControlKeys() {
        pane.requestFocus();
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    camera.move(1);
                }
                if (keyEvent.getCode().equals(KeyCode.UP)) {
                    camera.move(0);
                }
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    camera.move(3);
                }
                if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    camera.move(2);
                }
            }
        });
    }

    private static int selectedButton = 0;
    public static ArrayList<Rectangle> current;
    public static ArrayList<Circle> buttons = new ArrayList<>();
    public static HashMap<Circle, ArrayList<Rectangle>> buttonMap = new HashMap<>();
    public static ArrayList<ArrayList<Rectangle>> menuMap = new ArrayList<>();

    public static void initGraphicalMenu() {
        graphicalMenu = new Rectangle(0, SCREEN_HEIGHT * 0.7, SCREEN_WIDTH, SCREEN_HEIGHT * 0.3);
        graphicalMenu.setFill(new ImagePattern(BuildingImages.MENU.getImage()));
        reservedShapes.add(graphicalMenu);
        pane.getChildren().add(graphicalMenu);
        ArrayList<Rectangle> armoury = new ArrayList<>();
        initArray(armoury, armouries, 40, 40);
        ArrayList<Rectangle> food = new ArrayList<>();
        initArray(food, foods, 30, 30);
        ArrayList<Rectangle> houseAndStorages = new ArrayList<>();
        initArray(houseAndStorages, houseAndStorage, 30, 30);
        ArrayList<Rectangle> industry = new ArrayList<>();
        initArray(industry, industries, 40, 40);
        ArrayList<Rectangle> militaryBuilding = new ArrayList<>();
        initArray(militaryBuilding, militaryBuildings, 40, 40);
        ArrayList<Rectangle> goodThing = new ArrayList<>();
        initArray(goodThing, goodThings, 40, 40);
        ArrayList<Rectangle> badThing = new ArrayList<>();
        initArray(badThing, badThings, 40, 40);
        ArrayList<Rectangle> towerAndWall = new ArrayList<>();
        initArray(towerAndWall, towerAndWalls, 20, 40);
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

    public static void initArray(ArrayList<Rectangle> array, ArrayList<Image> images, int height, int width) {
        int size = images.size();
        for (int i = 0; i < size; ++i) {
            Rectangle rectangle = new Rectangle(150 + SCREEN_WIDTH * ((double) i / size) * 0.5, SCREEN_HEIGHT * 0.85, height * 2, width * 2.5);
            rectangle.setFill(new ImagePattern(images.get(i)));
            //TODO : SETON CLICK ON RECTANGLE
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

}
