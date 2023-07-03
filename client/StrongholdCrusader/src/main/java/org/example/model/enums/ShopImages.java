package org.example.model.enums;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.example.controller.GameGraphicalController;
import org.example.controller.BottomMenu;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;

public enum ShopImages {
    RICE("food/rice.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.15, 50, 1),
    MEAT("food/meat.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.22, 50, 2),
    CHEESE("food/cheese.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.29, 50, 3),
    BREAD("food/bread.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.36, 50, 4),
    APPLE("food/apple.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.43, 50, 5),
    ALE("food/ale.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.5, 50, 6),
    VEG("food/veg.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.57, 50, 7),
    WHEAT("food/wheat.png", 1, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.64, 50, 8),
    FOOD("food/food.png", -1, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.15, 90, 0),
    IRON("rawMaterial/iron.jpeg", 2, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.15, 75, 9),
    PITCH("rawMaterial/pitch.png", 2, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.3, 75, 10),
    STONE("rawMaterial/stone.jpeg", 2, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.45, 75, 11),
    WOOD("rawMaterial/wood.jpeg", 2, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.57, 75, 12),
    RAW_MATERIAL("rawMaterial/rawMaterial.png", -2, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.3, 90, 0),
    ARMOR("weapons/armor.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.15, 55, 13),
    BOW("weapons/bow.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.23, 55, 14),
    CROSS_BOW("weapons/crossBow.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.31, 55, 15),
    MACE("weapons/mace.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.39, 55, 16),
    METAL_ARMOR("weapons/metalArmor.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.47, 55, 17),
    PIKE("weapons/pike.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.55, 55, 18),
    SPEAR("weapons/spear.jpeg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.63, 55, 19),
    SWORD("weapons/sword.jpg", 3, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.7, 55, 20),
    WEAPON("weapons/weapon.jpeg", -3, GameGraphicalController.SCREEN_HEIGHT*0.85, GameGraphicalController.SCREEN_WIDTH*0.45, 90, 0),
    TRADE("trade.png", 0, GameGraphicalController.SCREEN_HEIGHT*0.87, GameGraphicalController.SCREEN_WIDTH*0.65, 35, -1);
    private Rectangle rectangle;
    private int category;
    private int identity;
    public static HashMap<Integer, ArrayList<Rectangle>> shopMap = new HashMap<>();

    static {
        for (int i = -3; i <= 3; ++i) {
            shopMap.put(i, new ArrayList<>());
        }
    }

    private Image image;

    ShopImages(String imageName, int category, double row, double column, double size, int identity) {
        this.identity = identity;
        this.image = new Image(MainMenu.class.getResource("/images/shop/" + imageName).toString());
        this.category = category;
        this.rectangle = new Rectangle(column, row, size, size);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (identity == -1) {
                    BottomMenu.initTrade();
                } else {
                    switch (category) {
                        case -1 -> BottomMenu.initShopMenu(null, 1, 0);
                        case -2 -> BottomMenu.initShopMenu(null, 2, 0);
                        case -3 -> BottomMenu.initShopMenu(null, 3, 0);
                        default -> BottomMenu.initShopMenu(rectangle, 0, identity);
                    }
                }
            }
        });

    }

    public static HashMap<Rectangle,Integer> recToIdentity = new HashMap<>();

    static {
        for (ShopImages shopImages : ShopImages.values()) {
            shopMap.get(shopImages.category).add(shopImages.rectangle);
            recToIdentity.put(shopImages.rectangle,shopImages.identity);
        }
        shopMap.get(0).add(RAW_MATERIAL.rectangle);
        shopMap.get(0).add(WEAPON.rectangle);
        shopMap.get(0).add(FOOD.rectangle);
    }

    public Image getImage() {
        return image;
    }

    public int getIdentity() {
        return identity;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }


    public int getCategory() {
        return category;
    }

    public static HashMap<Integer, ArrayList<Rectangle>> getShopMap() {
        return shopMap;
    }
}