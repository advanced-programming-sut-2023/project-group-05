package org.example.model;

import javafx.scene.image.Image;
import org.example.Main;
import org.example.controller.GameGraphicalController;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.Arrays;

public enum BuildingImages {
//    SMALL_STONE_WALL(),
//    BIG_STONE_WALL(),
    SMALL_STONE_GATEHOUSE("towersAndWalls/smallGateHouse.png",7),
    BIG_STONE_GATEHOUSE("towersAndWalls/bigGateHouse.png",7),
    DRAW_BRIDGE("towersAndWalls/drawBridge.png",7),
    LOOKOUT_TOWER("towersAndWalls/lookoutTower.png",7),
    PERIMETER_TOWER("towersAndWalls/perimeterTower.png",7),
    //DEFENCE_TURRET(),
    SQUARE_TOWER("towersAndWalls/squareTower.png",7),
    ROUND_TOWER("towersAndWalls/roundTower.png",7),
    ARMOURY("militaryBuildings/armoury.png",4),
    BARRACKS("militaryBuildings/barracks.png",4),
    MERCENARY_POST("militaryBuildings/mercenaryPost.png",4),
    ENGINEER_GUILD("militaryBuildings/engineerGuild.png",4),
//    KILLING_PIT(),
    OIL_SMELTER("towersAndWalls/traps/oilSmelter.png",7),
//    PITCH_DITCH(),
    CAGED_WAR_DOGS("towersAndWalls/traps/dogTrap.png",7),
    SIEGE_TENT("militaryBuildings/siegeTent.png",4),
    STABLE("militaryBuildings/stable.png",4),
    TUNNELER_GUILD("militaryBuildings/tunnelerGuild.png",4),
    APPLE_ORCHARD("foods/appleFarm.png",2),
    DIARY_FARMER("foods/cheeseFarm.png",2),
    HOPS_FARMER("foods/hopsFarm.png",2),
    HUNTER_POST("foods/hunters.png",2),
    WHEAT_FARM("foods/wheatFarm.png",2),
    BAKERY("foods/bakery.png",2),
    BREWER("foods/brewer.png",2),
    GRANARY("houseAndStorage/granary.png",3),
    INN("foods/inn.png",2),
    MILL("foods/windMill.png",2),
    IRON_MINE("industries/ironMine.png",8),
    MARKET("houseAndStorage/market.png",3),
    OX_TETHER("industries/oxTether.png",8),
    PITCH_RIG("industries/pitch.png",8),
    QUARRY("industries/quarry.png",8),
    STOCKPILE("houseAndStorage/stockpile.png",3),
    WOODCUTTER("industries/woodCutter.png",8),
    APOTHECARY("popularityBuildings/goodThings/Apothecary.png",5),
    HOVEL("houseAndStorage/house.png",3),
    CHAPEL("popularityBuildings/goodThings/chapel.png",5),
    CHURCH("popularityBuildings/goodThings/church.png",5),
    CATHEDRAL("popularityBuildings/goodThings/cathedral.png",5),
    WELL("houseAndStorage/waterWell.png",3),
    WATER_POT("houseAndStorage/waterPot.png",3),
    GOOD_THINGS_1("popularityBuildings/goodThings/shrine.png",5),
    GOOD_THINGS_2("popularityBuildings/goodThings/status.png",5),
    GOOD_THINGS_3("popularityBuildings/goodThings/maypole.png",5),
    GOOD_THINGS_4("popularityBuildings/goodThings/garden.png",5),
    BAD_THINGS_1("popularityBuildings/badThings/bad1.png",6),
    BAD_THINGS_2("popularityBuildings/badThings/bad2.gif",6),
    BAD_THINGS_3("popularityBuildings/badThings/bad3.gif",6),
    BAD_THINGS_4("popularityBuildings/badThings/bad4.gif",6),
    BAD_THINGS_5("popularityBuildings/badThings/bad5.gif",6),
    BAD_THINGS_6("popularityBuildings/badThings/bad6.gif",6),
    BAD_THINGS_7("popularityBuildings/badThings/bad7.gif",6),
    BAD_THINGS_8("popularityBuildings/badThings/bad8.gif",6),
    ARMOURER("armouries/armourer.png",1),
    BLACKSMITH("armouries/blackSmith.png",1),
    FLETCHER("armouries/fletcher.png",1),
    POLE_TURNER("armouries/poleTurner.png",1),
    TANNER("armouries/tanner.png",1),
    SIGNPOST("houseAndStorage/signPost.png",3),
    MENU("menu.png",0);
//    TUNNEL_ENTRANCE(),
//    STAIR();

    private Image image ;

    private int category;
    BuildingImages( String buildingImageName,int category ){
        this.image = new Image(MainMenu.class.getResource( "/images/buildings/buildingMenu/"+buildingImageName).toExternalForm() ) ;
        switch (category) {
            case 1 -> GameGraphicalController.armouries.add(image);
            case 2 -> GameGraphicalController.foods.add(image);
            case 3 -> GameGraphicalController.houseAndStorage.add(image);
            case 4 -> GameGraphicalController.militaryBuildings.add(image);
            case 5 -> GameGraphicalController.goodThings.add(image);
            case 6 -> GameGraphicalController.badThings.add(image);
            case 7 -> GameGraphicalController.towerAndWalls.add(image);
            case 8 -> GameGraphicalController.industries.add(image);
            default -> {
            }
        }
    }

    public Image getImage(){
        return this.image;
    }

}