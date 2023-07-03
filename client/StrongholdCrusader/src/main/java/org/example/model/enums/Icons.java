package org.example.model.enums;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.example.view.MainMenu;

public enum Icons {
    DELETE("delete.png"),
    UNDO("undo.png"),
    BRIEF("brief.jpeg"),
    APPROVE("approve.png"),
    BACK("back.png"),
    BUY("buy.png"),
    DENY("deny.png"),
    INFO("info.png"),
    MINUS("minus.png"),
    PLUS("plus.png"),
    RECEIVE("receive.png"),
    SELL("sell.png"),
    SEND("send.jpeg"),
    SENT("sent.png"),
    SWORD("sword.png"),
    TOWER("tower.jpeg"),
    SWORDS("swordMenu.png"),
    RELIGION("religion.png"),
    INDUSTRY("industry.png"),
    FOOD("food.jpeg"),
    HOUSE("house.png"),
    MILITARY("military.png");
    private ImagePattern imagePattern;
    Icons(String name){
        this.imagePattern = new ImagePattern(new Image(MainMenu.class.getResource("/images/icons/"+name).toString()));
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}
