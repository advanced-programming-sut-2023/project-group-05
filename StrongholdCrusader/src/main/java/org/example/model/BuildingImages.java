package org.example.model;

import javafx.scene.image.Image;
import org.example.Main;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.Arrays;

public enum BuildingImages {
//    SMALL_STONE_WALL(),
//    BIG_STONE_WALL(),
    SMALL_STONE_GATEHOUSE("towersAndWalls/smallGateHouse.png"),
    BIG_STONE_GATEHOUSE("towersAndWalls/bigGateHouse.png"),
    DRAW_BRIDGE("towersAndWalls/drawBridge.png"),
    LOOKOUT_TOWER("towersAndWalls/lookoutTower.png"),
    PERIMETER_TOWER("towersAndWalls/perimeterTower.png"),
    //DEFENCE_TURRET(),
    SQUARE_TOWER("towersAndWalls/squareTower.png"),
    ROUND_TOWER("towersAndWalls/roundTower.png"),
    ARMOURY("militaryBuildings/armoury.png"),
    BARRACKS("militaryBuildings/barracks.png"),
    MERCENARY_POST("militaryBuildings/mercenaryPost.png"),
    ENGINEER_GUILD("militaryBuildings/engineerGuild.png"),
//    KILLING_PIT(),
    OIL_SMELTER("towersAndWalls/traps/oilSmelter.png"),
//    PITCH_DITCH(),
    CAGED_WAR_DOGS("towersAndWalls/traps/dogTrap.png"),
    SIEGE_TENT("militaryBuildings/siegeTent.png"),
    STABLE("militaryBuildings/stable.png"),
    TUNNELER_GUILD("militaryBuildings/tunnelerGuild.png"),
    APPLE_ORCHARD("foods/appleFarm.png"),
    DIARY_FARMER("foods/cheeseFarm.png"),
    HOPS_FARMER("foods/hopsFarm.png"),
    HUNTER_POST("foods/hunters.png"),
    WHEAT_FARM("foods/wheatFarm.png"),
    BAKERY("foods/bakery.png"),
    BREWER("foods/brewer.png"),
    GRANARY("houseAndStorage/granary.png"),
    INN("foods/inn.png"),
    MILL("foods/windMill.png"),
    IRON_MINE("industries/ironMine.png"),
    MARKET("houseAndStorage/market.png"),
    OX_TETHER("industries/oxTether.png"),
    PITCH_RIG("industries/pitch.png"),
    QUARRY("industries/quarry.png"),
    STOCKPILE("houseAndStorage/stockpile.png"),
    WOODCUTTER("industries/woodCutter.png"),
    APOTHECARY("popularityBuildings/goodThings/Apothecary.png"),
    HOVEL("houseAndStorage/house.png"),
    CHAPEL("popularityBuildings/goodThings/chapel.png"),
    CHURCH("popularityBuildings/goodThings/church.png"),
    CATHEDRAL("popularityBuildings/goodThings/cathedral.png"),
    WELL("houseAndStorage/waterWell.png"),
    WATER_POT("houseAndStorage/waterPot.png"),
    GOOD_THINGS_1("popularityBuildings/goodThings/shrine.png"),
    GOOD_THINGS_2("popularityBuildings/goodThings/status.png"),
    GOOD_THINGS_3("popularityBuildings/goodThings/maypole.png"),
    GOOD_THINGS_4("popularityBuildings/goodThings/garden.png"),
    BAD_THINGS_1("popularityBuildings/badThings/bad1.png"),
    BAD_THINGS_2("popularityBuildings/badThings/bad2.gif"),
    BAD_THINGS_3("popularityBuildings/badThings/bad3.gif"),
    BAD_THINGS_4("popularityBuildings/badThings/bad4.gif"),
    BAD_THINGS_5("popularityBuildings/badThings/bad5.gif"),
    BAD_THINGS_6("popularityBuildings/badThings/bad6.gif"),
    BAD_THINGS_7("popularityBuildings/badThings/bad7.gif"),
    BAD_THINGS_8("popularityBuildings/badThings/bad8.gif"),
    ARMOURER("armouries/armourer.png"),
    BLACKSMITH("armouries/blackSmith.png"),
    FLETCHER("armouries/fletcher.png"),
    POLE_TURNER("armouries/poleTurner.png"),
    TANNER("armouries/tanner.png"),
    SIGNPOST("houseAndStorage/signPost.png"),
    MENU("menu.png");
//    TUNNEL_ENTRANCE(),
//    STAIR();

    private Image image ;

    BuildingImages( String buildingImageName ){
        System.out.println(buildingImageName);
        this.image = new Image(MainMenu.class.getResource( "/images/buildings/buildingMenu/"+buildingImageName).toExternalForm() ) ;
    }

    public Image getImage(){
        return this.image;
    }

}
