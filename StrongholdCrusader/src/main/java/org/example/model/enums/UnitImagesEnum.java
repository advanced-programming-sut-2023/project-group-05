package org.example.model.enums;

import javafx.css.CssParser;
import javafx.scene.image.Image;
import org.example.view.MainMenu;

public enum UnitImagesEnum {

    ARCHER("archer.png"),
    CROSSBOWMAN("archer.png");
//    SPEARMAN("spearman.png"),
//    PIKEMAN("pikeman.png"),
//    MACEMAN("maceman.png"),
//    SWORDSMAN("swordsman.png"),
//    KNIGHT("knight.png"),
//    TUNNELER("tunneler.png"),
//    LADDERMAN("ladderman.png"),
//    ENGINEER("engineer.png"),
//    BLACK_MONK("blackmonk.png"),
//    SLAVE("slave.png"),
//    SLINGER("slinger.png"),
//    ASSASSIN("assassin.png"),
    //ARABIAN_SWORDSMAN("arabianswordsman.png"),
    //FIRE_THROWER("firethrower.png");

    public Image image = null ;

    UnitImagesEnum( String fileName ){
        this.image = new Image( MainMenu.class.getResource( "/images/units/" + fileName ).toExternalForm() ) ;
    }



}
