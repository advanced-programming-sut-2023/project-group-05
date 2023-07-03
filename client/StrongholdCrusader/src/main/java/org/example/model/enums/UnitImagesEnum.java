package org.example.model.enums;

import javafx.css.CssParser;
import javafx.scene.image.Image;
import org.example.model.BuildingEnum;
import org.example.view.MainMenu;

public enum UnitImagesEnum {

    ARCHER("archer.png", BuildingEnum.BARRACKS),
    //CROSSBOWMAN("archer.png"),
    OPERATOR("operator.png" ,BuildingEnum.ENGINEER_GUILD),
//    SPEARMAN("spearman.png"),
//    PIKEMAN("pikeman.png"),
//    MACEMAN("maceman.png"),
    SWORDSMAN("swordsman.png",BuildingEnum.BARRACKS);
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
    private BuildingEnum buildingEnum;

    UnitImagesEnum( String fileName ,BuildingEnum buildingEnum ){
        this.buildingEnum =buildingEnum;
        this.image = new Image( MainMenu.class.getResource( "/images/units/" + fileName ).toExternalForm() ) ;
    }
}
