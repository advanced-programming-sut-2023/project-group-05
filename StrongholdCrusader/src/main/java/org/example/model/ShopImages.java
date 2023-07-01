package org.example.model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.example.controller.bottomMenu;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;

public enum ShopImages {
    RICE("food/rice.jpeg", 1, 0, 0, 0, 1),
    MEAT("food/meat.png", 1, 0, 0, 0, 2),
    CHEESE("food/cheese.jpeg", 1, 0, 0, 0, 3),
    BREAD("food/bread.png", 1, 0, 0, 0, 4),
    APPLE("food/apple.png", 1, 0, 0, 0, 5),
    ALE("food/ale.jpeg", 1, 0, 0, 0, 6),
    VEG("food/veg.jpeg", 1, 0, 0, 0, 7),
    WHEAT("food/wheat.jpeg", 1, 0, 0, 0, 8),
    FOOD("food/food.jpeg", -1, 0, 0, 0, 0),
    IRON("rawMaterial/iron.jpeg", 2, 0, 0, 0, 9),
    PITCH("rawMaterial/pitch.png", 2, 0, 0, 0, 10),
    STONE("rawMaterial/stone.jpeg", 2, 0, 0, 0, 11),
    WOOD("rawMaterial/wood.jpeg", 2, 0, 0, 0, 12),
    RAW_MATERIAL("rawMaterial/rawMaterial.png", -2, 0, 0, 0, 0),
    ARMOR("weapons/armor.jpeg", 3, 0, 0, 0, 13),
    BOW("weapons/bow.jpeg", 3, 0, 0, 0, 14),
    CROSS_BOW("weapons/crossBow.jpeg", 3, 0, 0, 0, 15),
    MACE("weapons/mace.jpeg", 3, 0, 0, 0, 16),
    METAL_ARMOR("weapons/metalArmor.jpeg", 3, 0, 0, 0, 17),
    PIKE("weapons/pike.jpeg", 3, 0, 0, 0, 18),
    SPEAR("weapons/spear.jpeg", 3, 0, 0, 0, 19),
    SWORD("weapons/sword.jpg", 3, 0, 0, 0, 20),
    WEAPON("weapons/weapon.jpeg", -3, 0, 0, 0, 0),
    TRADE("trade.png", 0, 0, 0, 0, -1);
    private Rectangle rectangle;
    private int cost;
    private int category;
    public static HashMap<Integer, ArrayList<Rectangle>> shopMap = new HashMap<>();

    static {
        for (int i = -3; i <= 3; ++i) {
            shopMap.put(i, new ArrayList<>());
        }
    }

    private Image image;

    ShopImages(String imageName, int category, int row, int column, int size, int identity) {
        this.image = new Image(MainMenu.class.getResource("/images/shop/" + imageName).toString());
        this.category = category;
        this.rectangle = new Rectangle(row, column, size, size);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (identity == -1) {
                    bottomMenu.initTrade();
                } else {
                    switch (category) {
                        case -1 -> bottomMenu.initShopMenu(null, 1, 0);
                        case -2 -> bottomMenu.initShopMenu(null, 2, 0);
                        case -3 -> bottomMenu.initShopMenu(null, 3, 0);
                        default -> bottomMenu.initShopMenu(rectangle, 0, cost);
                    }
                }
            }
        });

    }

    static {
        for (ShopImages shopImages : ShopImages.values())
            shopMap.get(shopImages.category).add(shopImages.rectangle);
        shopMap.get(0).add(RAW_MATERIAL.rectangle);
        shopMap.get(0).add(WEAPON.rectangle);
        shopMap.get(0).add(FOOD.rectangle);
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getCost() {
        return cost;
    }

    public int getCategory() {
        return category;
    }

    public static HashMap<Integer, ArrayList<Rectangle>> getShopMap() {
        return shopMap;
    }
}
