package org.example.controller;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import org.example.model.*;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class bottomMenu {
    public static ArrayList<? extends Node> current;
    public static ArrayList<Circle> buttons = new ArrayList<>();
    public static HashMap<Circle, ArrayList<Rectangle>> buttonMap = new HashMap<>();
    public static ArrayList<ArrayList<Rectangle>> menuMap = new ArrayList<>();
    private static final int SCREEN_WIDTH = GameGraphicalController.SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT = GameGraphicalController.SCREEN_HEIGHT;
    public static ArrayList<Image> armouries = new ArrayList<>();
    public static ArrayList<Image> foods = new ArrayList<>();
    public static ArrayList<Image> houseAndStorage = new ArrayList<>();
    public static ArrayList<Image> militaryBuildings = new ArrayList<>();
    public static ArrayList<Image> goodThings = new ArrayList<>();
    public static ArrayList<Image> badThings = new ArrayList<>();
    public static ArrayList<Image> towerAndWalls = new ArrayList<>();
    public static ArrayList<Image> industries = new ArrayList<>();
    private static Rectangle graphicalMenu = new Rectangle(0, SCREEN_HEIGHT * 0.7, SCREEN_WIDTH, SCREEN_HEIGHT * 0.3);
    ;
    public static ArrayList<Node> reservedShapes = GameGraphicalController.reservedShapes;
    private static GameController gameController = GameGraphicalController.gameController;
    private static Camera camera = GameGraphicalController.camera;
    private static Pane pane = GameGraphicalController.getPane();
    //Refactoring
    private static Minimap minimap = new Minimap(camera, gameController);
    private static ArrayList<Rectangle> armoury = new ArrayList<>();
    private static ArrayList<Rectangle> food = new ArrayList<>();
    private static ArrayList<Rectangle> houseAndStorages = new ArrayList<>();
    private static ArrayList<Rectangle> industry = new ArrayList<>();
    private static ArrayList<Rectangle> militaryBuilding = new ArrayList<>();
    private static ArrayList<Rectangle> goodThing = new ArrayList<>();
    private static ArrayList<Rectangle> badThing = new ArrayList<>();
    private static ArrayList<Rectangle> towerAndWall = new ArrayList<>();


    static {
        minimap.setLayoutX(minimap.getLayoutX() + SCREEN_WIDTH - minimap.getSize());
        reservedShapes.add(minimap);
        graphicalMenu.setFill(new ImagePattern(BuildingImages.MENU.getImage()));
        reservedShapes.add(graphicalMenu);

        ArrayList<String> armouryNames = new ArrayList<>(Arrays.asList("armourer", "blacksmith", "fletcher", "poleturner", "tanner"));
        initArray(armoury, armouries, 40, 40, armouryNames);

        ArrayList<String> foodNames = new ArrayList<>(Arrays.asList("appleorchard", "bakery", "brewer", "dairyfarmer", "hopsfarmer", "huterpost", "inn", "wheatfarmer", "food9"));
        initArray(food, foods, 30, 30, foodNames);

        ArrayList<String> houseAndStorageNames = new ArrayList<>(Arrays.asList("granary", "market", "stockpile", "hovel", "has5", "has6", "has7"));
        initArray(houseAndStorages, houseAndStorage, 30, 30, houseAndStorageNames);

        ArrayList<String> industryNames = new ArrayList<>(Arrays.asList("ironmine", "oxtether", "pitchrig", "quarry", "woodcutter"));
        initArray(industry, industries, 40, 40, industryNames);

        ArrayList<String> militaryBuildingsNames = new ArrayList<>(Arrays.asList("armoury", "barracks", "mercenarypost", "engineerguild", "siegetent", "stable", "tunnelerguild"));
        initArray(militaryBuilding, militaryBuildings, 40, 40, militaryBuildingsNames);

        ArrayList<String> goodNames = new ArrayList<>(Arrays.asList("gt1", "gt2", "gt3", "gt4", "gt5", "gt6", "gt7", "gt8"));
        initArray(goodThing, goodThings, 40, 40, goodNames);

        ArrayList<String> badNames = new ArrayList<>(Arrays.asList("bt1", "bt2", "bt3", "bt4", "bt5", "bt6", "bt7", "bt8"));
        initArray(badThing, badThings, 40, 40, badNames);

        ArrayList<String> towerAndWallNames = new ArrayList<>(Arrays.asList("taw1", "taw2", "taw3", "taw4", "taw5", "squaretower", "roundtower", "taw8", "taw9"));
        initArray(towerAndWall, towerAndWalls, 20, 40, towerAndWallNames);
    }

    public static void initGraphicalMenu() {
        // adding the minimap
        pane.getChildren().add(minimap);
        pane.getChildren().add(graphicalMenu);
        for (Rectangle rectangle : houseAndStorages)
            pane.getChildren().add(rectangle);
        current = houseAndStorages;
        setMenuButtons(pane);
    }

    public static void setMenuButtons(Pane pane) {
        Circle house = new Circle(SCREEN_HEIGHT * 0.95, SCREEN_HEIGHT * 0.95 - 90, 15);
        house.setFill(Color.BLUE);
        buttonMap.put(house, menuMap.get(2));
        Circle food = new Circle(SCREEN_HEIGHT * 0.95 - 30, SCREEN_HEIGHT * 0.95 - 90, 15);
        food.setFill(Color.RED);
        buttonMap.put(food, menuMap.get(1));
        Circle armoury = new Circle(SCREEN_HEIGHT * 0.95 - 60, SCREEN_HEIGHT * 0.95 - 90, 15);
        armoury.setFill(Color.YELLOW);
        buttonMap.put(armoury, menuMap.get(0));
        Circle military = new Circle(SCREEN_HEIGHT * 0.95 - 90, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(military, menuMap.get(4));
        Circle good = new Circle(SCREEN_HEIGHT * 0.95 - 120, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(good, menuMap.get(5));
        Circle bad = new Circle(SCREEN_HEIGHT * 0.95 - 150, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(bad, menuMap.get(6));
        Circle towers = new Circle(SCREEN_HEIGHT * 0.95 - 180, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(towers, menuMap.get(7));
        Circle industry = new Circle(SCREEN_HEIGHT * 0.95 - 210, SCREEN_HEIGHT * 0.95 - 90, 15);
        buttonMap.put(industry, menuMap.get(3));
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
                    alterMenu(current, buttonMap.get(circle), pane);
                    current = buttonMap.get(circle);
                }
            });
        }
        alterButton(true);
    }

    private static void alterButton(boolean show) {
        if (show) {
            for (Circle button : buttons)
                pane.getChildren().add(button);
            return;
        }
        for (Circle button : buttons)
            pane.getChildren().remove(button);
    }

    public static <T, K> void alterMenu(ArrayList<T> first, ArrayList<K> second, Pane pane) {
        for (T t : first) ((Node) t).setVisible(false);
        for (K k : second) pane.getChildren().add((Node) k);
    }

    private static void addToPane(ArrayList<Node> buffer)
    {
        pane.getChildren().addAll(buffer);
    }

    private static void removeFromPane(ArrayList<Node>buffer)
    {
        pane.getChildren().removeAll(buffer);
    }

    private static Polygon mouse = GameGraphicalController.mouse;

    public static void initArray(ArrayList<Rectangle> array, ArrayList<Image> images, int height, int width, ArrayList<String> buildingNames) {
        int size = images.size();
        for (int i = 0; i < size; ++i) {
            Rectangle rectangle = new Rectangle(150 + SCREEN_WIDTH * ((double) i / size) * 0.5, SCREEN_HEIGHT * 0.85, height * 2, width * 2.5);
            rectangle.setFill(new ImagePattern(images.get(i)));
            int finalI = i;
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameGraphicalController.chosenBuildingName = buildingNames.get(finalI);
                    System.out.println("name : " + GameGraphicalController.chosenBuildingName);
                    GameGraphicalController.mouseHeight = BuildingEnum.getBuildingHeightByName(buildingNames.get(finalI));
                    GameGraphicalController.mouseWidth = BuildingEnum.getBuildingWidthByName(buildingNames.get(finalI));
                    GameGraphicalController.updateMouse();
                    mouse.setVisible( true ) ;
                }
            });
            array.add(rectangle);
        }
        menuMap.add(array);
        reservedShapes.addAll(array);
    }

    //Pause Menu

    public static ArrayList<Image> menuImages = new ArrayList<>();
    private static Rectangle pauseMenu = new Rectangle(SCREEN_HEIGHT * 0.85, SCREEN_WIDTH * 0.85);
    private static Rectangle resume = new Rectangle(SCREEN_HEIGHT * 0.15 + 20, SCREEN_WIDTH * 0.5);
    private static Circle sound = new Circle(SCREEN_HEIGHT * 0.15 + 30, SCREEN_WIDTH * 0.15 + 30, 20);
    private static Rectangle exit = new Rectangle(SCREEN_HEIGHT * 0.85 - 20, SCREEN_WIDTH * 0.5);
    private static boolean toggle = false;
    private static boolean paused = false;

    static {
        pauseMenu.setFill(MenuImages.PAUSE_MENU.getImagePattern());
        pauseMenu.setOpacity(0.8);
        sound.setFill(MenuImages.SOUND_ON.getImagePattern());
        sound.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sound.setFill(toggle ? MenuImages.SOUND_ON.getImagePattern() : MenuImages.SOUND_OFF.getImagePattern());
                toggle = !toggle;
                //TODO : WHERE IS THE BOOLEAN
            }
        });
        exit.setFill(MenuImages.EXIT.getImagePattern());
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO : EXIT
            }
        });
        resume.setFill(MenuImages.RESUME.getImagePattern());
        resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pauseGame();
            }
        });
    }

    public static void pauseGame() {
        if (!paused)
            pane.getChildren().addAll(pauseMenu, sound, exit, resume);
        else
            pane.getChildren().removeAll(pauseMenu, sound, exit, resume);
        paused = !paused;
    }

    //Shop Menu

    private static ArrayList<Shape> shopKeys = new ArrayList<>();
    private static int currentDepth = 0;
    private static int currentShopMode = 0;
    private static boolean infoToggle = false;

    static {
        Circle back = new Circle(SCREEN_HEIGHT * 0.95, SCREEN_WIDTH * 0.1, 15);
        back.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/back.png").toExternalForm())));
        shopKeys.add(back);
        Rectangle buy = new Rectangle(SCREEN_HEIGHT * 0.8, SCREEN_WIDTH * 0.5, 100, 10);
        buy.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/buy.png").toString())));
        Rectangle sell = new Rectangle(SCREEN_HEIGHT * 0.8 + 20, SCREEN_WIDTH * 0.5, 100, 10);
        sell.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/sell.png").toString())));
        shopKeys.add(buy);
        shopKeys.add(sell);
        Circle info = new Circle(SCREEN_HEIGHT * 0.95, SCREEN_WIDTH - 10, 15);
        info.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/info.png").toExternalForm())));
        Text text = new Text("Stone : To Build Buildings\n" +
                "Wood : To Build Buildings And Bows\n" +
                "Pitch : To Build Fire Traps\n" +
                "Iron : To Build Armor\n" +
                "Foods : To Make People Happy\n" +
                "Bow And CrossBow : Suitable For Distant Attack\n" +
                "Spear And Pike : Suitable For Attacking By Spearmen And Pikemen\n" +
                "Sword : Suitable For Strong Swordsmen");
        text.setX(450);
        text.setY(450);
        Rectangle information = new Rectangle(50, 50, SCREEN_WIDTH * 0.8, SCREEN_HEIGHT * 0.8);
        information.setOpacity(0.8);
        info.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (infoToggle) {
                    pane.getChildren().add(text);
                    pane.getChildren().add(information);
                } else {
                    pane.getChildren().remove(text);
                    pane.getChildren().remove(information);
                }
                infoToggle = !infoToggle;
            }
        });
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (currentDepth) {
                    case 0 -> {
                        ArrayList<Shape> buffer = new ArrayList<>();
                        buffer.addAll(houseAndStorages);
                        buffer.addAll(buttons);
                        alterMenu(current, buffer, pane);
                        current = houseAndStorages;
                    }
                    case 1 -> {
                        ArrayList<Shape> buffer = new ArrayList<>();
                        buffer.addAll(ShopImages.shopMap.get(0));
                        buffer.add(back);
                        alterMenu(current, buffer, pane);
                        current = buffer;
                        currentDepth = 0;
                    }
                    case 2 -> {
                        ArrayList<Shape> buffer = new ArrayList<>();
                        buffer.addAll(ShopImages.shopMap.get(currentShopMode));
                        buffer.add(back);
                        alterMenu(current, buffer, pane);
                        current = buffer;
                        currentDepth = 1;
                    }
                }
            }
        });
        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO : WHERE IS ACCOUNT DETAILS?
            }
        });
        sell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO : WHERE IS ACCOUNT DETAILS ?
            }
        });
    }

    public static void initShopMenu(Rectangle rectangle, int mode, int identity) {
        if (rectangle == null) {
            HashMap <Integer,ArrayList<Rectangle>> map = ShopImages.shopMap;
            buffer.clear();
            buffer.addAll(map.get(mode));
            //TODO : Inventory
            for (int i =0;i<ShopImages.shopMap.size();++i) {
                Text bff = new Text(map.get(mode).get(i).getX(), map.get(mode).get(i).getY(), "0");
                bff.setFill(Color.WHITE);
                bff.setStyle("-fx-font: 10");
                buffer.add(bff);
            }
            alterMenu(current, buffer, pane);
            currentDepth = (mode == 0) ? 0 : 1;
            current = buffer;
            return;
        }
        Rectangle rectangle1 = new Rectangle(rectangle.getLayoutX() - 20, rectangle.getLayoutY(), 20, 20);
        Text buyPrice = new Text(Integer.toString(identity));
        buyPrice.setX(shopKeys.get(1).getLayoutX() + 20);
        Text sellPrice = new Text(Integer.toString(identity / 2));
        sellPrice.setX(shopKeys.get(2).getLayoutX() + 20);
        ArrayList<Node> buffer = new ArrayList<>(Arrays.asList(rectangle1, buyPrice, sellPrice, shopKeys.get(1), shopKeys.get(2), shopKeys.get(3)));
        alterMenu(current, buffer, pane);
        current = buffer;
        currentShopMode = mode;
        currentDepth = 2;
    }

    //Trade

    private static Rectangle tradeFirstMenu = new Rectangle(SCREEN_HEIGHT * 0.1, SCREEN_HEIGHT * 0.1, SCREEN_HEIGHT * 0.7, SCREEN_WIDTH * 0.7);
    private static Rectangle sendTrade = new Rectangle(tradeFirstMenu.getLayoutX() + 200, tradeFirstMenu.getLayoutY() + 200, 50, 50);
    private static Rectangle receiveTrade = new Rectangle(sendTrade.getLayoutX() + 100, sendTrade.getLayoutY(), 50, 50);
    private static Circle closeTrade = new Circle(tradeFirstMenu.getLayoutX() + 20, tradeFirstMenu.getLayoutY() + 20, 15);

    static {
        tradeFirstMenu.setOpacity(0.8);
        tradeFirstMenu.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/shop/tradeBackground.png").toExternalForm())));;
        sendTrade.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/shop/sendTrade.png").toExternalForm())));
        receiveTrade.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/shop/receiveTrade.png").toExternalForm())));
        closeTrade.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/shop/close.png").toExternalForm())));
        receiveTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tradeMenuHandler(false);
                receiveTrade();
            }
        });
        sendTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tradeMenuHandler(false);
                sendTrade();
            }
        });
        closeTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tradeMenuHandler(false);
                pane.getChildren().remove(closeTrade);
            }
        });
    }

    private static void tradeMenuHandler(boolean wtd) {
        if (!wtd) {
            pane.getChildren().remove(tradeFirstMenu);
            pane.getChildren().remove(sendTrade);
            pane.getChildren().remove(receiveTrade);
            return;
        }
        pane.getChildren().add(tradeFirstMenu);
        pane.getChildren().add(sendTrade);
        pane.getChildren().add(receiveTrade);
        pane.getChildren().add(closeTrade);
    }

    private static ArrayList<Node> buffer = new ArrayList<>();

    public static void initTrade() {
        tradeMenuHandler(true);
    }

    private static void sendTrade() {
        HashMap <Integer, Integer> letter = new HashMap<>();
        //  todo : handle all players , this is after that
        int cnt = 1;
        buffer.add(tradeFirstMenu);
        buffer.add(closeTrade);
        ArrayList<Rectangle> items = new ArrayList<>();
        ArrayList<Text> values = new ArrayList<>();
        ImagePattern minus = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/minus.png").toExternalForm()));
        ImagePattern plus = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/plus.png").toString()));
        for (ShopImages item : ShopImages.values()) {
            if (item.getCategory() > 0) {
                Rectangle rectangle = new Rectangle(SCREEN_HEIGHT*0.1+cnt*20,SCREEN_WIDTH*0.1+cnt*20,20,20);
                rectangle.setFill(new ImagePattern(item.getImage()));
                Circle lower = new Circle(rectangle.getLayoutX(),rectangle.getLayoutY()+50,15);
                Circle upper = new Circle(lower.getCenterX(),lower.getCenterY()+50,15);
                lower.setFill(minus);
                upper.setFill(plus);
                Text zeroText = new Text("0");zeroText.setFill(Color.WHITE);zeroText.setStyle("-fx-font: 15");
                lower.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int a;
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if ((a = Integer.parseInt(zeroText.getText())) > -250 )
                            zeroText.setText(Integer.toString(a-25));
                    }
                });
                upper.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int a;
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if ((a = Integer.parseInt(zeroText.getText())) < 450 )
                            zeroText.setText(Integer.toString(a+25));
                    }
                });
                values.add(zeroText);
                items.add(rectangle);
                buffer.add(zeroText);buffer.add(rectangle);buffer.add(lower);buffer.add(upper);
            }
        }
        Circle send = new Circle(closeTrade.getCenterX(),closeTrade.getCenterY()+SCREEN_HEIGHT*0.8,20);
        buffer.add(send);
        addToPane(buffer);
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0;i<values.size();++i)
                    letter.put(i+1,Integer.parseInt(values.get(i).getText()));
                //TODO : HOW TO RETURN TO THE ACCOUNT ?
                removeFromPane(buffer);
            }
        });
    }

    private static void receiveTrade() {
        buffer.clear();
        buffer.add(closeTrade);
        buffer.add(tradeFirstMenu);
        Circle sent = new Circle(tradeFirstMenu.getLayoutX()+SCREEN_WIDTH*0.1,tradeFirstMenu.getLayoutY()+SCREEN_HEIGHT*0.1,25);
        sent.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/sent.png").toExternalForm())));
        Circle receive = new Circle(sent.getCenterX()+SCREEN_WIDTH*0.2,sent.getCenterY(),25);
        receive.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/receive.png").toString())));
        buffer.add(sent);
        buffer.add(receive);
        sent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().remove(sent);
                pane.getChildren().remove(receive);
                pane.getChildren().addAll(myPastTrades());
            }
        });
        receive.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().remove(sent);
                pane.getChildren().remove(receive);
                pane.getChildren().addAll(requestsToMe());
            }
        });
        addToPane(buffer);
    }

    private static ArrayList<Node> myPastTrades()
    {
        return null;
    }

    private static ArrayList<Node> requestsToMe()
    {
        return null;
    }

}