package org.example.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.example.Main;
import org.example.controller.GameGraphicalController;
import org.example.controller.bottomMenu;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    APPLE_ORCHARD("foods/appleorchard.png",2),
    DIARY_FARMER("foods/dairyfarmer.png",2),
    HOPS_FARMER("foods/hopsfarmer.png",2),
    HUNTER_POST("foods/hunterpost.png",2),
    WHEAT_FARM("foods/wheatfarmer.png",2),
    BAKERY("foods/bakery.png",2),
    BREWER("foods/brewer.png",2),
    GRANARY("houseAndStorage/granary.png",3),
    INN("foods/inn.png",2),
    MILL("foods/windmill.png",2),
    IRON_MINE("industries/ironmine.png",8),
    MARKET("houseAndStorage/market.png",3),
    OX_TETHER("industries/oxtether.png",8),
    PITCH_RIG("industries/pitchrig.png",8),
    QUARRY("industries/quarry.png",8),
    STOCKPILE("houseAndStorage/stockpile.png",3),
    WOODCUTTER("industries/woodcutter.png",8),
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
    BLACKSMITH("armouries/blacksmith.png",1),
    FLETCHER("armouries/fletcher.png",1),
    POLE_TURNER("armouries/poleturner.png",1),
    TANNER("armouries/tanner.png",1),
    SIGNPOST("houseAndStorage/signPost.png",3),
    MENU("menu.png",0);
//    TUNNEL_ENTRANCE(),
//    STAIR();

    private Image image ;
    private ImagePattern imagePattern ;

    private int category;
    private String name ;
    public static ArrayList<BuildingImages> allBuildingImages ;

    BuildingImages(String buildingImageName, int category ){
        Matcher matcher = Pattern.compile("/(?<name>\\S+)\\.((png)|(gif))$").matcher(buildingImageName) ;
        add(this) ;
        if( matcher.find() ){
            this.name = matcher.group("name") ;
        } else {
            System.out.println( "##############fuck-this-image##############" ) ;
            System.out.println( buildingImageName ) ;
        }
        if( null == MainMenu.class.getResource( "/images/buildings/buildingMenu/"+buildingImageName) )
            System.out.println( "YOUUUUUUUUUUU" + buildingImageName );
        this.image = new Image(MainMenu.class.getResource( "/images/buildings/buildingMenu/"+buildingImageName).toExternalForm() ) ;
        switch (category) {
            case 1 -> bottomMenu.armouries.add(image);
            case 2 -> bottomMenu.foods.add(image);
            case 3 -> bottomMenu.houseAndStorage.add(image);
            case 4 -> bottomMenu.militaryBuildings.add(image);
            case 5 -> bottomMenu.goodThings.add(image);
            case 6 -> bottomMenu.badThings.add(image);
            case 7 -> bottomMenu.towerAndWalls.add(image);
            case 8 -> bottomMenu.industries.add(image);
            default -> {
            }
        }
        this.imagePattern = new ImagePattern( image ) ;
    }

    private void add(BuildingImages o){
        if( allBuildingImages == null ) allBuildingImages = new ArrayList<>() ;
        allBuildingImages.add( o ) ;
    }

    public Image getImage(){
        return this.image;
    }

    private String getName(){
        return this.name ;
    }

    private ImagePattern getImagePattern(){
        return this.imagePattern ;
    }

    public static ImagePattern getImagePattern(String name){
        for( BuildingImages x : allBuildingImages ){
            if(x.getName().equals(name)){
                return x.getImagePattern() ;
            }
        }
        System.out.println( "IMAGE NOT FOUND" ) ;
        return null ;
    }

}
