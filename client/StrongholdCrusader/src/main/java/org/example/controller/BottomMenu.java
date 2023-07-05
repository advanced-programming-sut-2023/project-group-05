package org.example.controller;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.example.Main;
import org.example.model.*;
import org.example.model.enums.BuildingImages;
import org.example.model.enums.Icons;
import org.example.model.enums.ShopImages;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class BottomMenu {
    public static ArrayList<Node> current;
    private static Player player = GameGraphicalController.getPlayer();
    public static ArrayList<Circle> buttons = new ArrayList<>();
    public static HashMap<Circle, ArrayList<Node>> buttonMap = new HashMap<Circle, ArrayList<Node>>();
    public static ArrayList<ArrayList<Node>> menuMap = new ArrayList<ArrayList<Node>>();
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
    private static Rectangle graphicalMenu = new Rectangle(0, SCREEN_HEIGHT * 0.78, SCREEN_WIDTH, SCREEN_HEIGHT * 0.22);
    public static ArrayList<Node> reservedShapes = GameGraphicalController.reservedShapes;
    private static GameController gameController = GameGraphicalController.gameController;
    private static Camera camera = GameGraphicalController.camera;
    private static Group pane = GameGraphicalController.lastNode ;
    //Refactoring
    private static Minimap minimap = new Minimap(camera, gameController);
    private static ArrayList<Node> armoury = new ArrayList<Node>();
    private static ArrayList<Node> food = new ArrayList<Node>();
    private static ArrayList<Node> houseAndStorages = new ArrayList<Node>();
    private static ArrayList<Node> industry = new ArrayList<Node>();
    private static ArrayList<Node> militaryBuilding = new ArrayList<Node>();
    private static ArrayList<Node> goodThing = new ArrayList<Node>();
    private static ArrayList<Node> badThing = new ArrayList<Node>();
    private static ArrayList<Node> towerAndWall = new ArrayList<Node>();
    private static Text popularity = new Text(SCREEN_WIDTH*0.725,SCREEN_HEIGHT*0.89,"100");
    private static Text treasure = new Text(SCREEN_WIDTH*0.715,SCREEN_HEIGHT*0.92,"74");
    private static Text labourForce = new Text(SCREEN_WIDTH*0.7,SCREEN_HEIGHT*0.95,"0/64");
    static {
        minimap.setLayoutX(minimap.getLayoutX() + SCREEN_WIDTH - minimap.getSize());
        reservedShapes.add(minimap);
        graphicalMenu.setFill(new ImagePattern(BuildingImages.MENU.getImage()));
        popularity.setFill(Color.GREEN);
        popularity.setRotate(8);
        popularity.setStyle("-fx-font-size: 20px");
        treasure.setFill(Color.GOLD);
        treasure.setRotate(10);
        treasure.setStyle("-fx-font-size: 20px");
        labourForce.setRotate(10);
        labourForce.setStyle("-fx-font-size: 16px");
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

        ArrayList<String> towerAndWallNames = new ArrayList<>(Arrays.asList("smallstonegatehouse", "bigstonegatehouse", "taw3", "lookouttower", "perimetertower", "squaretower", "roundtower", "taw8", "taw9"));
        initArray(towerAndWall, towerAndWalls, 20, 40, towerAndWallNames);
    }

    public static void initGraphicalMenu() {
        // adding the minimap
        currentBuilding = "graphicalMenu";
        pane.getChildren().add(minimap);
        pane.getChildren().add(graphicalMenu);
        Circle shrekFace = new Circle(SCREEN_WIDTH * 0.77 ,graphicalMenu.getY() - 20 ,65) ;
        shrekFace.setFill( new ImagePattern( new Image( Main.class.getResource( "/images/buildings/buildingMenu/menu/face1.png" ).toExternalForm() ) ) ) ;
        pane.getChildren().add(shrekFace) ;
        pane.getChildren().addAll(popularity,treasure,labourForce);
        for (Node rectangle : houseAndStorages)
            buffer.add(rectangle);
        setMenuButtons(pane);
    }

    public static void setActionButtons()
    {
        Circle delete = new Circle(SCREEN_HEIGHT*0.95+20,SCREEN_HEIGHT*0.95-60,10);
        delete.setFill(Icons.DELETE.getImagePattern());
        Circle brief = new Circle(delete.getCenterX(),delete.getCenterY()+23,delete.getRadius());
        brief.setFill(Icons.BRIEF.getImagePattern());
        Circle undo = new Circle(delete.getCenterX(),brief.getCenterY()+23,brief.getRadius());
        undo.setFill(Icons.UNDO.getImagePattern());
        Collections.addAll(buttons,delete,undo,brief);
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //todo :
            }
        });
        brief.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                initFaceAnimations();
            }
        });
    }

    public static void setMenuButtons(Group pane) {
        Circle house = new Circle(SCREEN_HEIGHT * 0.95, SCREEN_HEIGHT * 0.95 - 90, 15);
        house.setFill(Icons.HOUSE.getImagePattern());
        buttonMap.put(house, menuMap.get(2));
        Circle food = new Circle(SCREEN_HEIGHT * 0.95 - 30, SCREEN_HEIGHT * 0.95 - 90, 15);
        food.setFill(Icons.FOOD.getImagePattern());
        buttonMap.put(food, menuMap.get(1));
        Circle armoury = new Circle(SCREEN_HEIGHT * 0.95 - 60, SCREEN_HEIGHT * 0.95 - 90, 15);
        armoury.setFill(Icons.SWORDS.getImagePattern());
        buttonMap.put(armoury, menuMap.get(0));
        Circle military = new Circle(SCREEN_HEIGHT * 0.95 - 90, SCREEN_HEIGHT * 0.95 - 90, 15);
        military.setFill(Icons.MILITARY.getImagePattern());
        buttonMap.put(military, menuMap.get(4));
        Circle good = new Circle(SCREEN_HEIGHT * 0.95 - 120, SCREEN_HEIGHT * 0.95 - 90, 15);
        good.setFill(Icons.PLUS.getImagePattern());
        buttonMap.put(good, menuMap.get(5));
        Circle bad = new Circle(SCREEN_HEIGHT * 0.95 - 150, SCREEN_HEIGHT * 0.95 - 90, 15);
        bad.setFill(Icons.MINUS.getImagePattern());
        buttonMap.put(bad, menuMap.get(6));
        Circle towers = new Circle(SCREEN_HEIGHT * 0.95 - 180, SCREEN_HEIGHT * 0.95 - 90, 15);
        towers.setFill(Icons.TOWER.getImagePattern());
        buttonMap.put(towers, menuMap.get(7));
        Circle industry = new Circle(SCREEN_HEIGHT * 0.95 - 210, SCREEN_HEIGHT * 0.95 - 90, 15);
        industry.setFill(Icons.INDUSTRY.getImagePattern());
        buttonMap.put(industry, menuMap.get(3));
        Collections.addAll(buttons,house,food,armoury,military,good,bad,towers,industry);
        for (Circle circle : buttons) {
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    removeFromPane(buffer);
                    buffer.clear();
                    buffer.addAll(buttonMap.get(circle));
                    buffer.addAll(buttons);
                    addToPane(buffer);
                }
            });
        }
        setActionButtons();
        buffer.addAll(buttons);
        addToPane(buffer);
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

    public static <T, K> void alterMenu(ArrayList<T> first, ArrayList<K> second, Group pane) {
        for (T t : first) pane.getChildren().remove((Node) t);
        for (K k : second) pane.getChildren().add((Node) k);
    }

    private static void addToPane(ArrayList<Node> buffer) {
        pane.getChildren().addAll(buffer);
    }

    private static void removeFromPane(ArrayList<Node> buffer) {
        pane.getChildren().removeAll(buffer);
    }

    private static Polygon mouse = GameGraphicalController.mouse;

    public static void initArray(ArrayList<Node> array, ArrayList<Image> images, int height, int width, ArrayList<String> buildingNames) {
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
                    mouse.setVisible(true);
                }
            });
            array.add(rectangle);
        }
        menuMap.add(array);
        reservedShapes.addAll(array);
    }

    //Pause Menu

    public static ArrayList<Image> menuImages = new ArrayList<>();
    private static Rectangle pauseMenu = new Rectangle(SCREEN_WIDTH * 0.2, SCREEN_HEIGHT * 0.05, SCREEN_WIDTH * 0.6, SCREEN_HEIGHT * 0.7);
    private static Rectangle resume = new Rectangle(SCREEN_WIDTH * 0.5 - 50, SCREEN_HEIGHT * 0.3 - 50, 100, 100);
    private static Circle sound = new Circle(SCREEN_WIDTH * 0.5, SCREEN_HEIGHT * 0.7 - 70, 50);
    private static Rectangle exit = new Rectangle(SCREEN_WIDTH * 0.7, SCREEN_HEIGHT * 7, 30, 30);
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
    public static Circle info = new Circle(SCREEN_WIDTH * 0.65 + 20, SCREEN_HEIGHT * 0.9 + 50, 15);
    private static Rectangle buy = new Rectangle(SCREEN_WIDTH * 0.4, SCREEN_HEIGHT * 0.85 - 15, 100, 50);
    private static Rectangle sell = new Rectangle(SCREEN_WIDTH * 0.4, SCREEN_HEIGHT * 0.85 + 50, 100, 50);
    private static int currentCommodity;
    public static Circle back = new Circle(SCREEN_WIDTH * 0.65 + 20, SCREEN_HEIGHT * 0.9 - 50, 15);
    public static void deSelect()
    {
        removePastThings();
        buffer.addAll(houseAndStorages);
        buffer.addAll(buttons);
        addToPane(buffer);
    }

    static {
        back.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/back.png").toExternalForm())));
        shopKeys.add(back);

        buy.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/buy.png").toString())));

        sell.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/sell.png").toString())));

        shopKeys.add(buy);
        shopKeys.add(sell);

        info.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/info.png").toExternalForm())));

        shopKeys.add(info);

        Text text = new Text("Stone : To Build Buildings\n" +
                "Wood : To Build Buildings And Bows\n" +
                "Pitch : To Build Fire Traps\n" +
                "Iron : To Build Armor\n" +
                "Foods : To Make People Happy\n" +
                "Bow And CrossBow : Suitable For Distant Attack\n" +
                "Spear And Pike : Suitable For Attacking\n" +
                "Sword : Suitable For Strong Swordsmen");
        text.setX(SCREEN_WIDTH * 0.3);
        text.setY(SCREEN_HEIGHT * 0.3);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 12px");
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
                        removeFromPane(buffer);
                        buffer.clear();
                        buffer.addAll(houseAndStorages);
                        buffer.addAll(buttons);
                        currentBuilding = "graphicalMenu";
                        addToPane(buffer);
                    }
                    case 1 -> {
                        removeFromPane(buffer);
                        buffer.clear();
                        Collections.addAll(buffer,back,info);
                        buffer.addAll(ShopImages.shopMap.get(0));
                        addToPane(buffer);
                        currentDepth = 0;
                    }
                    case 2 -> {
                        removeFromPane(buffer);
                        buffer.clear();
                        Collections.addAll(buffer,back,info);
                        buffer.addAll(ShopImages.shopMap.get(currentShopMode));
                        addToPane(buffer);
                        currentDepth = 1;
                    }
                }
            }
        });
        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                System.out.println(currentCommodity);
                if (player.gold > 10 * Player.commodityPrice.get(currentCommodity))
                {
                    player.gold -= 10*Player.commodityPrice.get(currentCommodity);
                    player.commodityByIndex[currentCommodity] +=10;
                    alert.setTitle("Bought Successfully");
                    alert.setContentText("The Commodity Bought With Success");
                }
                else {
                    alert.setTitle("Bought Failed");
                    alert.setContentText("Not Enough Gold");
                }
                alert.showAndWait();
            }
        });
        sell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (player.commodityByIndex[currentCommodity] > 10 )
                {
                    player.gold += 10*Player.commodityPrice.get(currentCommodity);
                    player.commodityByIndex[currentCommodity] -=10;
                    alert.setTitle("Sell Successfully");
                    alert.setContentText("The Commodity Sold With Success");
                }
                else {
                    alert.setTitle("Sell Failed");
                    alert.setContentText("Not Enough Commodity");
                }
                alert.showAndWait();
            }
        });
    }
    private static String currentBuilding = "" ;
    public static void preInitShop() {
//        buffer.clear();
//        if (currentBuilding.equals("graphicalMenu"))
//            buffer.addAll(buttons);
        removePastThings();
        currentBuilding = "market";
        initShopMenu(null, 0, 0);
    }

    public static void initShopMenu(Rectangle rectangle, int mode, int identity) {
        removeFromPane(buffer);
        buffer.clear();
        Collections.addAll(buffer,back,info);
        if (rectangle == null) {
            HashMap<Integer, ArrayList<Rectangle>> map = ShopImages.shopMap;
            buffer.addAll(map.get(mode));
            if (mode > 0) {
                pane.getChildren().removeAll(map.get(0));
                for (int i = 0; i < map.get(mode).size(); ++i)
                    buffer.add(new Text(map.get(mode).get(i).getX() + map.get(mode).get(i).getHeight() / 2,
                            map.get(mode).get(i).getY() + 7 + map.get(mode).get(i).getHeight(),
                            Integer.toString(player.commodityByIndex[ShopImages.recToIdentity.get(map.get(mode).get(i))])));
            }
            addToPane(buffer);
            currentDepth = (mode == 0) ? 0 : 1;
            return;
        }
        currentCommodity = identity;

        Rectangle rectangle1 = new Rectangle(SCREEN_WIDTH*0.3, SCREEN_HEIGHT*0.85, 90, 90);
        rectangle1.setFill(rectangle.getFill());

        Text buyPrice = new Text(buy.getX()+buy.getWidth()+30,buy.getY()+buy.getHeight()/2,Double.toString(Player.commodityPrice.get(currentCommodity)));
        Text sellPrice = new Text(sell.getX()+sell.getWidth()+30,sell.getY()+sell.getHeight()/2,Double.toString(Player.commodityPrice.get(currentCommodity)/2));
        Collections.addAll(buffer,rectangle1,shopKeys.get(1),shopKeys.get(2),buyPrice,sellPrice);
        //addToBuffer(secondBuffer, rectangle1, shopKeys.get(1), shopKeys.get(2), buyPrice, sellPrice);
        addToPane(buffer);
        currentDepth = 2;
    }


    //Trade

    private static Rectangle tradeFirstMenu = new Rectangle(SCREEN_WIDTH * 0.05, SCREEN_HEIGHT * 0.05, SCREEN_WIDTH*0.9, SCREEN_HEIGHT * 0.9);
    private static Rectangle sendTrade = new Rectangle(tradeFirstMenu.getX()+0.1*tradeFirstMenu.getWidth(), tradeFirstMenu.getY() + 200, tradeFirstMenu.getWidth()*0.3, 150);
    private static Rectangle receiveTrade = new Rectangle(sendTrade.getX() + tradeFirstMenu.getWidth()*0.5, sendTrade.getY(), tradeFirstMenu.getWidth()*0.3, 150);
    private static Circle closeTrade = new Circle(tradeFirstMenu.getX()+tradeFirstMenu.getWidth(), tradeFirstMenu.getY(), 15);

    static {
        tradeFirstMenu.setOpacity(0.6);
        tradeFirstMenu.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/shop/tradeBackground.png").toExternalForm())));
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
                accountsTradeMenu();
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

    private static boolean inTradeMenu = false;

    private static void accountsTradeMenu()
    {
        //todo : complete this part;
        sendTrade(GameGraphicalController.getPlayer());
    }

    private static void tradeMenuHandler(boolean wtd) {
        if (!wtd) {
            pane.getChildren().remove(tradeFirstMenu);
            pane.getChildren().remove(sendTrade);
            pane.getChildren().remove(receiveTrade);
            pane.getChildren().remove(closeTrade);
            return;
        }
        pane.getChildren().add(tradeFirstMenu);
        pane.getChildren().add(sendTrade);
        pane.getChildren().add(receiveTrade);
        pane.getChildren().add(closeTrade);
    }

    private static ArrayList<Node> buffer = new ArrayList<>();
    private static ArrayList<Node> menuBuffer = new ArrayList<>();
    private static ArrayList<Node> menuCurrent = new ArrayList<>();

    public static void initTrade() {
        inTradeMenu = !inTradeMenu;
        tradeMenuHandler(inTradeMenu);
    }

    private static ImagePattern minus = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/minus.png").toExternalForm()));
    private static ImagePattern plus = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/plus.png").toString()));

    private static void sendTrade(Player destination) {
        HashMap<Integer, Integer> letter = new HashMap<>();
        for (int i = 1;i<=20;++i)
            letter.put(i,0);
        currentTradeLetter = letter;
        //  todo : handle all players , this is after that
        int hnt = 0;
        int vnt = 0;
        menuBuffer.clear();
        menuBuffer.add(closeTrade);
        closeTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeFromPane(menuBuffer);
            }
        });
        menuBuffer.add(tradeFirstMenu);
        ArrayList<Shape> items = new ArrayList<>();
        for (ShopImages item : ShopImages.values()) {
            if (item.getCategory() > 0) {
                Circle circle = new Circle(SCREEN_WIDTH * 0.1 + 225*hnt++ , SCREEN_HEIGHT * 0.1 + vnt * 130 , 50);
                if (hnt % 4 == 0){
                    hnt = 0;
                    vnt++;
                }
                circle.setFill(new ImagePattern(item.getImage()));
                items.add(circle);
                circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        regulateTrade(item,menuBuffer);
                    }
                });
            }
        }
        Circle send = new Circle(closeTrade.getCenterX() - 50, tradeFirstMenu.getY()+ tradeFirstMenu.getHeight()-20, 20);
        menuBuffer.add(send);
        menuBuffer.addAll(items);
        addToPane(menuBuffer);
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Trade tr;
                destination.receivedTrades.add((tr = new Trade(player,destination,"",letter)));
                player.sentTrades.add(tr);
                removeFromPane(menuBuffer);
                new Alert(Alert.AlertType.INFORMATION,"Sent Successfully").showAndWait();
            }
        });
    }

    private static Circle backTrade = new Circle(tradeFirstMenu.getX()+30,tradeFirstMenu.getY()+30,25);
    private static HashMap<Integer,Integer> currentTradeLetter ;
    private static Pair<Integer,Integer> tradePair ;
    static {
        backTrade.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/back.png").toExternalForm())));
        backTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeFromPane(menuCurrent);
                addToPane(menuBuffer);
                currentTradeLetter.replace(tradePair.getKey(),tradePair.getValue());
            }
        });
    }


    private static void regulateTrade(ShopImages item ,ArrayList<Node> tradeBuffer)
    {
        removeFromPane(tradeBuffer);
        menuCurrent.clear();
        menuCurrent.add(tradeFirstMenu);
        menuCurrent.add(closeTrade);
        menuCurrent.add(backTrade);
        handleTradeMenuClose(menuCurrent);
        Circle commodity = new Circle(tradeFirstMenu.getX()+tradeFirstMenu.getWidth()*0.3,tradeFirstMenu.getY()+tradeFirstMenu.getHeight()*0.3,100);
        commodity.setFill(new ImagePattern(item.getImage()));
        Circle lower = new Circle(commodity.getCenterX()+250,commodity.getCenterY(),70);
        Circle upper = new Circle(lower.getCenterX()+250,commodity.getCenterY(),70);
        lower.setFill(minus);
        upper.setFill(plus);
        Text value = new Text((lower.getCenterX()+upper.getCenterX())/2,commodity.getCenterY(),Integer.toString(currentTradeLetter.get(item.getIdentity())));
        value.setStyle("-fx-font-size: 20px");
        tradePair = new Pair<>(item.getIdentity(),0);
        lower.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int p;
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (( p = Integer.parseInt(value.getText())) > -226) {
                    value.setText(Integer.toString(p -= 25));
                    tradePair = new Pair<>(item.getIdentity(),p);
                }
            }
        });
        upper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int p;
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (( p = Integer.parseInt(value.getText())) < 226) {
                    value.setText(Integer.toString(p += 25));
                    tradePair = new Pair<>(item.getIdentity(),p);
                }
            }
        });
        menuCurrent.add(lower);
        menuCurrent.add(upper);
        menuCurrent.add(value);
        menuCurrent.add(commodity);
        addToPane(menuCurrent);
    }

    private static void receiveTrade() {
        handleTradeMenuClose(menuBuffer);
        menuBuffer.clear();
        menuBuffer.add(tradeFirstMenu);
        menuBuffer.add(closeTrade);
        Circle sent = new Circle(tradeFirstMenu.getX() + tradeFirstMenu.getWidth()/3, tradeFirstMenu.getY() + tradeFirstMenu.getHeight()/2, 100);
        sent.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/sent.png").toExternalForm())));
        Circle receive = new Circle(tradeFirstMenu.getX()+tradeFirstMenu.getWidth()*2/3, sent.getCenterY(), 100);
        receive.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/receive.png").toString())));
        menuBuffer.add(sent);
        menuBuffer.add(receive);
        sent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().remove(sent);
                pane.getChildren().remove(receive);
                ArrayList<Node> b = tradeHistory(false);
                pane.getChildren().addAll(b);
                menuBuffer.addAll(b);
            }
        });
        receive.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().remove(sent);
                pane.getChildren().remove(receive);
                ArrayList<Node> b = tradeHistory(true);
                pane.getChildren().addAll(b);
                menuBuffer.addAll(b);
            }
        });
        addToPane(menuBuffer);
    }

    private static void handleTradeMenuClose(ArrayList<Node> toBeRemoved)
    {
        closeTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeFromPane(toBeRemoved);
            }
        });
    }

    private static ImagePattern approve = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/approve.png").toExternalForm()));
    private static ImagePattern deny = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/deny.png").toExternalForm()));

    private static ArrayList<Node> tradeHistory(boolean amIReceiver) {
        ArrayList<Node> tradeHistory = new ArrayList<>();
        int cnt = 1;
        tradeHistory.add(new Text(SCREEN_WIDTH*0.1,SCREEN_HEIGHT*0.1,"Account"));
        for (ShopImages item : ShopImages.values()) {
            if (item.getCategory() > 0) {
                Circle circle = new Circle(SCREEN_WIDTH * 0.15 + (SCREEN_WIDTH*0.7/20)*cnt++ , SCREEN_HEIGHT * 0.1  , (SCREEN_WIDTH*0.7/40));
                circle.setFill(new ImagePattern(item.getImage()));
                tradeHistory.add(circle);
            }
        }
        tradeHistory.add(new Text(SCREEN_WIDTH * 0.15 + (SCREEN_WIDTH*0.7/20)*cnt , SCREEN_HEIGHT * 0.1  ,"STATUS"));
        cnt = 1;
        int hnt = 0;
        Text buffer;
        int max = amIReceiver ? player.receivedTrades.size() : player.sentTrades.size();
        for (int i =0;i<max;++i)
        {
            buffer = new Text(SCREEN_WIDTH*0.1,SCREEN_HEIGHT*0.1+cnt*50,amIReceiver? player.receivedTrades.get(i).getSender().getNickname():player.sentTrades.get(i).getReceiver().getNickname());
            buffer.setFill(Color.YELLOW);
            tradeHistory.add(buffer);
            hnt=1;
            for (int j = 1;j<=20;++j){
                buffer = new Text(SCREEN_WIDTH*0.15+(SCREEN_WIDTH*0.7/20)*hnt++,SCREEN_HEIGHT*0.1+cnt*50,amIReceiver ? player.receivedTrades.get(i).getLetter().get(j).toString():player.sentTrades.get(i).getLetter().get(j).toString());
                buffer.setFill(Color.BLACK);
                tradeHistory.add(buffer);
            }
            String bf = "WAIT";
            buffer = new Text(SCREEN_WIDTH * 0.15 + (SCREEN_WIDTH*0.7/20)*21,SCREEN_HEIGHT*0.1+cnt*50,bf);
            buffer.setFill(Color.YELLOW);
            if (amIReceiver?player.receivedTrades.get(i).isDenied() : player.sentTrades.get(i).isDenied()) {
                buffer.setText("DENIED");
                buffer.setFill(Color.RED);
            }
            else if (amIReceiver?player.receivedTrades.get(i).isApproved() : player.sentTrades.get(i).isApproved()) {
                buffer.setText("APPROVED");
                buffer.setFill(Color.GREEN);
            }
            if (amIReceiver && buffer.getText().equals("WAIT")){
                Circle app = new Circle(buffer.getX()-25,buffer.getY(),20);
                app.setFill(approve);
                Circle rej = new Circle(buffer.getX()+25,buffer.getY(),20);
                rej.setFill(deny);
                int k = i;
                Text kt = buffer;
                ArrayList<Node> th = tradeHistory;
                app.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(effectTrade(player.receivedTrades.get(k))) {
                            player.receivedTrades.get(k).setApproved(true);
                            new Alert(Alert.AlertType.INFORMATION,"Successfully Traded").showAndWait();
                            kt.setText("APPROVED");
                            kt.setFill(Color.GREEN);
                            pane.getChildren().removeAll(app,rej);
                            pane.getChildren().add(kt);
                            menuBuffer.remove(app);
                            menuBuffer.remove(rej);
                            menuBuffer.add(kt);
                        }
                        else
                            new Alert(Alert.AlertType.INFORMATION,"Not Enough Gold").showAndWait();
                    }
                });
                rej.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        player.receivedTrades.get(k).setDenied(true);
                        kt.setText("DENIED");
                        kt.setFill(Color.RED);
                        pane.getChildren().removeAll(app,rej);
                        pane.getChildren().add(kt);
                        menuBuffer.remove(app);
                        menuBuffer.remove(rej);
                        menuBuffer.add(kt);
                    }
                });
                tradeHistory.add(app);
                tradeHistory.add(rej);
            }
            else
                tradeHistory.add(buffer);
            ++cnt;
        }
        return tradeHistory;
    }

    private static boolean effectTrade(Trade trade)
    {
        return true;
    }

    //Other Buildings

    private static void removePastThings()
    {
        currentDepth = 0;
        removeFromPane(buffer);
        buffer.clear();
    }

    public static void initEngineerGuild() {
        removePastThings();
        Rectangle mangonel = new Rectangle(SCREEN_WIDTH*0.15,SCREEN_HEIGHT*0.85,90,90);
        mangonel.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/units/mangonel.png").toString())));
        mangonel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //todo : create a map of unit to price
                if (player.getGold() < 150)
                    new Alert(Alert.AlertType.INFORMATION,"Not Enough Gold").showAndWait();
                else {
                    player.gold -= 150;
                    GameGraphicalController.putMangonel() ;
                }
            }
        });
        Collections.addAll(buffer,back,mangonel);
        addToPane(buffer);
    }

    public static void initBarracks() {
        removePastThings();
        Rectangle swordsman = new Rectangle(SCREEN_WIDTH*0.15,SCREEN_HEIGHT*0.85,90,90);
        swordsman.setFill(new ImagePattern(new Image(MainMenu.class.getResource("/images/units/swordsman.png").toString())));
        swordsman.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //todo : create a map of unit to price or handle it with cost object
                if (player.getGold() < 150 )
                    new Alert(Alert.AlertType.INFORMATION,"Not Enough Gold").showAndWait();
                else {
                    player.gold -= 150;
                    //todo : hossein tabar , here you go
                }
            }
        });
        Collections.addAll(buffer,back,swordsman);
        addToPane(buffer);
    }


    public static void updateScribeNotes()
    {
        int pop = player.getPopularity();
        popularity.setText(Integer.toString(pop));
        if (pop <= 25)
            popularity.setFill(Color.RED);
        else if (pop <=50)
            popularity.setFill(Color.YELLOW);
        else if (pop <= 75)
            popularity.setFill(Color.LIGHTGREEN);
        else
            popularity.setFill(Color.GREEN);
        treasure.setText(Double.toString(player.getGold()));
        labourForce.setText(player.population+"/"+player.populationCapacity);
    }

    private static void initFaceAnimations()
    {
        menuBuffer.clear();
        Rectangle backGround = new Rectangle(SCREEN_WIDTH*0.1,SCREEN_HEIGHT*0.05,SCREEN_WIDTH*0.8,SCREEN_HEIGHT*0.9);
        backGround.setFill(Icons.FACE_BACKGROUND.getImagePattern());
        Text title = new Text("Popularity Effects");
        title.setX(backGround.getX()+50);title.setY(backGround.getY()+50);title.setStyle("-fx-font-size: 15px");title.setStyle("-fx-font-weight: bold");
        Circle close = new Circle(backGround.getX(),backGround.getY(),20);close.setFill(Icons.BACK.getImagePattern());
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeFromPane(menuBuffer);
            }
        });
        String style = "";
        style+="-fx-font-size: 15px" ;
        Text foodE = new Text("Food Rate : "+player.getFearRate());foodE.setStyle(style);
        foodE.setX(backGround.getX()+20);foodE.setY(backGround.getY()+100);
        Text fearRate = new Text("Fear Rate : "+player.getFearRate());fearRate.setStyle(style);
        fearRate.setX(backGround.getX()+20);fearRate.setY(backGround.getY()+200);
        Text treasureRate = new Text("Gold : "+player.getGold());treasureRate.setStyle(style);
        treasureRate.setX(backGround.getX()+20);treasureRate.setY(backGround.getY()+300);
        Text popularity = new Text("Popularity : "+player.getPopularity());popularity.setStyle(style);
        popularity.setX(backGround.getX()+20);popularity.setY(backGround.getY()+400);
        Circle foodFace = new Circle(foodE.getX()+backGround.getWidth()/2,foodE.getY(),40);
        Circle fearFace = new Circle(fearRate.getX()+backGround.getWidth()/2,fearRate.getY(),40);
        Circle treasureFace = new Circle(treasureRate.getX()+backGround.getWidth()/2,treasureRate.getY(),40);
        Circle popularityFace = new Circle(popularity.getX()+backGround.getWidth()/2,popularity.getY(),40);
        if (player.getFearRate() < 0 ){
            fearRate.setFill(Color.RED);
            fearFace.setFill(Icons.SAD.getImagePattern());
        }
        else if (player.getFearRate() < 1){
            fearRate.setFill(Color.YELLOW);
            fearFace.setFill(Icons.NEUTRAL.getImagePattern());
        }
        else {
            fearRate.setFill(Color.GREEN);
            fearFace.setFill(Icons.HAPPY.getImagePattern());
        }
        if (player.getGold() < 50 ){
            treasureRate.setFill(Color.RED);
            treasureFace.setFill(Icons.SAD.getImagePattern());
        }
        else if (player.getGold() <= 100){
            treasureRate.setFill(Color.YELLOW);
            treasureFace.setFill(Icons.NEUTRAL.getImagePattern());
        }
        else {
            treasureRate.setFill(Color.GREEN);
            treasureFace.setFill(Icons.HAPPY.getImagePattern());
        }
        if (player.getFoodRate() < 0 ){
            foodE.setFill(Color.RED);
            foodFace.setFill(Icons.SAD.getImagePattern());
        }
        else if (player.getFoodRate() < 0.5){
            foodE.setFill(Color.YELLOW);
            foodFace.setFill(Icons.NEUTRAL.getImagePattern());
        }
        else {
            foodE.setFill(Color.GREEN);
            foodFace.setFill(Icons.HAPPY.getImagePattern());
        }
        if (player.getPopularity() < 40 ){
            popularity.setFill(Color.RED);
            popularityFace.setFill(Icons.SAD.getImagePattern());
        }
        else if (player.getPopularity() < 60){
            popularity.setFill(Color.YELLOW);
            popularityFace.setFill(Icons.NEUTRAL.getImagePattern());
        }
        else {
            popularity.setFill(Color.GREEN);
            popularityFace.setFill(Icons.HAPPY.getImagePattern());
        }
        Collections.addAll(menuBuffer,backGround,title,close,foodE,foodFace,treasureFace,treasureRate,popularity,popularityFace,fearFace,fearRate);
        addToPane(menuBuffer);
    }

}



