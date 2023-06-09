package org.example.model.enums;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.example.controller.BottomMenu;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingImages {
//    SMALL_STONE_WALL(),
//    BIG_STONE_WALL(),
ARMOURER("armouries/armourer.png",1),
    BLACKSMITH("armouries/blacksmith.png",1),
    FLETCHER("armouries/fletcher.png",1),
    POLE_TURNER("armouries/poleturner.png",1),
    TANNER("armouries/tanner.png",1),
    APPLE_ORCHARD("foods/appleorchard.png",2),
    BAKERY("foods/bakery.png",2),
    BREWER("foods/brewer.png",2),
    DIARY_FARMER("foods/dairyfarmer.png",2),
    HOPS_FARMER("foods/hopsfarmer.png",2),
    HUNTER_POST("foods/hunterpost.png",2),
    INN("foods/inn.png",2),
    WHEAT_FARM("foods/wheatfarmer.png",2),
    MILL("foods/food9.png",2),
    GRANARY("houseAndStorage/granary.png",3),
    MARKET("houseAndStorage/market.png",3),
    STOCKPILE("houseAndStorage/stockpile.png",3),
    HOVEL("houseAndStorage/hovel.png",3),
    WELL("houseAndStorage/has5.png",3),
    WATER_POT("houseAndStorage/has6.png",3),
    SIGNPOST("houseAndStorage/has7.png",3),
    ARMOURY("militaryBuildings/armoury.png",4),
    BARRACKS("militaryBuildings/barracks.png",4),
    MERCENARY_POST("militaryBuildings/mercenarypost.png",4),
    ENGINEER_GUILD("militaryBuildings/engineerguild.png",4),
    SIEGE_TENT("militaryBuildings/siegetent.png",4),
    STABLE("militaryBuildings/stable.png",4),
    TUNNELER_GUILD("militaryBuildings/tunnelerGuild.png",4),
    GOOD_THINGS_1("popularityBuildings/goodThings/gt1.png",5),
    GOOD_THINGS_2("popularityBuildings/goodThings/gt2.png",5),
    GOOD_THINGS_3("popularityBuildings/goodThings/gt3.png",5),
    GOOD_THINGS_4("popularityBuildings/goodThings/gt4.png",5),
    APOTHECARY("popularityBuildings/goodThings/gt5.png",5),
    CHAPEL("popularityBuildings/goodThings/gt6.png",5),
    CHURCH("popularityBuildings/goodThings/gt7.png",5),
    CATHEDRAL("popularityBuildings/goodThings/gt8.png",5),
    BAD_THINGS_1("popularityBuildings/badThings/bt1.png",6),
    BAD_THINGS_2("popularityBuildings/badThings/bt2.gif",6),
    BAD_THINGS_3("popularityBuildings/badThings/bt3.gif",6),
    BAD_THINGS_4("popularityBuildings/badThings/bt4.gif",6),
    BAD_THINGS_5("popularityBuildings/badThings/bt5.gif",6),
    BAD_THINGS_6("popularityBuildings/badThings/bt6.gif",6),
    BAD_THINGS_7("popularityBuildings/badThings/bt7.gif",6),
    BAD_THINGS_8("popularityBuildings/badThings/bt8.gif",6),
    SMALL_STONE_GATEHOUSE("towersAndWalls/smallstonegatehouse.png",7),
    BIG_STONE_GATEHOUSE("towersAndWalls/bigstonegatehouse.png",7),
    DRAW_BRIDGE("towersAndWalls/taw3.png",7),
    LOOKOUT_TOWER("towersAndWalls/lookouttower.png",7),
    PERIMETER_TOWER("towersAndWalls/perimetertower.png",7),
    //DEFENCE_TURRET(),
    SQUARE_TOWER("towersAndWalls/squaretower.png",7),
    ROUND_TOWER("towersAndWalls/roundtower.png",7),
    //    KILLING_PIT(),
    OIL_SMELTER("towersAndWalls/traps/taw8.png",7),
    //    PITCH_DITCH(),
    CAGED_WAR_DOGS("towersAndWalls/traps/taw9.png",7),

    MANGONEL("mangonel/mangonel.png",-1),
    CASTLE("mangonel/castle.png",-1),



    IRON_MINE("industries/ironmine.png",8),
    OX_TETHER("industries/oxtether.png",8),
    PITCH_RIG("industries/pitchrig.png",8),
    QUARRY("industries/quarry.png",8),

    WOODCUTTER("industries/woodcutter.png",8),

    MENU("menu/menu.png",0);
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
        this.image = new Image(MainMenu.class.getResource( "/images/buildings/buildingMenu/"+buildingImageName).toExternalForm()) ;
        switch (category) {
            case 1 -> BottomMenu.armouries.add(image);
            case 2 -> BottomMenu.foods.add(image);
            case 3 -> BottomMenu.houseAndStorage.add(image);
            case 4 -> BottomMenu.militaryBuildings.add(image);
            case 5 -> BottomMenu.goodThings.add(image);
            case 6 -> BottomMenu.badThings.add(image);
            case 7 -> BottomMenu.towerAndWalls.add(image);
            case 8 -> BottomMenu.industries.add(image);
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
