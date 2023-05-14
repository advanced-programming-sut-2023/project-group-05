package org.example.model;

import org.example.model.building.Building;

import java.util.ArrayList;
import java.util.Arrays;

public enum BuildingEnum {
    //TODO : PUT BUILDING SIZE IN THE ARRAYLIST
    SMALL_STONE_WALL("smallstonewall",new ArrayList<Integer>(Arrays.asList(1,1))),
    BIG_STONE_WALL("bigstonewall",new ArrayList<Integer>(Arrays.asList(2,2))),
    SMALL_STONE_GATEHOUSE("smallstonegatehouse",new ArrayList<Integer>(Arrays.asList(1,1))),
    BIG_STONE_GATEHOUSE("bigstonegatehouse",new ArrayList<Integer>(Arrays.asList(2,2))),
    DRAW_BRIDGE("drawbridge",new ArrayList<Integer>(Arrays.asList(1,3))),
    LOOKOUT_TOWER("lookouttower",new ArrayList<Integer>(Arrays.asList(3,3))),
    PERIMETER_TOWER("perimetertower",new ArrayList<Integer>(Arrays.asList(4,4))),
    DEFENCE_TURRET("defencetower",new ArrayList<Integer>(Arrays.asList(3,3))),
    SQUARE_TOWER("squaretower",new ArrayList<Integer>(Arrays.asList(3,3))),
    ROUND_TOWER("roundtower",new ArrayList<Integer>(Arrays.asList(3,3))),
    ARMOURY("armoury",new ArrayList<Integer>(Arrays.asList(3,3))),
    BARRACKS("barracks",new ArrayList<Integer>(Arrays.asList(3,3))),
    MERCENARY_POST("mercenarypost",new ArrayList<Integer>(Arrays.asList(3,3))),
    ENGINEER_GUILD("engineerguild",new ArrayList<Integer>(Arrays.asList(2,3))),
    KILLING_PIT("killingpit",new ArrayList<Integer>(Arrays.asList(1,1))),
    OIL_SMELTER("oilsmelter",new ArrayList<Integer>(Arrays.asList(1,1))),
    PITCH_DITCH("pitchditch",new ArrayList<Integer>(Arrays.asList(1,1))),
    CAGED_WAR_DOGS("cagedwardogs",new ArrayList<Integer>(Arrays.asList(1,1))),
    SIEGE_TENT("siegetent",new ArrayList<Integer>(Arrays.asList(4,4))),
    STABLE("stable",new ArrayList<Integer>(Arrays.asList(3,5))),
    TUNNELER_GUILD("tunnelerguild",new ArrayList<Integer>(Arrays.asList(2,3))),
    APPLE_ORCHARD("appleorchard",new ArrayList<Integer>(Arrays.asList(3,5))),
    DIARY_FARMER("diaryfarmer",new ArrayList<Integer>(Arrays.asList(3,5))),
    HOPS_FARMER("hopsfarmer",new ArrayList<Integer>(Arrays.asList(3,5))),
    HUNTER_POST("hunterpost",new ArrayList<Integer>(Arrays.asList(3,5))),
    WHEAT_FARM("wheatfarm",new ArrayList<Integer>(Arrays.asList(3,5))),
    BAKERY("bakery",new ArrayList<Integer>(Arrays.asList(2,2))),
    BREWER("brewer",new ArrayList<Integer>(Arrays.asList(2,2))),
    GRANARY("granary",new ArrayList<Integer>(Arrays.asList(1,1))),
    INN("inn",new ArrayList<Integer>(Arrays.asList(3,3))),
    MILL("mill",new ArrayList<Integer>(Arrays.asList(3,3))),
    IRON_MINE("ironmine",new ArrayList<Integer>(Arrays.asList(3,3))),
    MARKET("market",new ArrayList<Integer>(Arrays.asList(3,3))),
    OX_TETHER("oxtether",new ArrayList<Integer>(Arrays.asList(1,2))),
    PITCH_RIG("pitchrig",new ArrayList<Integer>(Arrays.asList(2,2))),
    QUARRY("quarry",new ArrayList<Integer>(Arrays.asList(2,2))),
    STOCKPILE("stockpile",new ArrayList<Integer>(Arrays.asList(1,1))),
    WOODCUTTER("woodcutter",new ArrayList<Integer>(Arrays.asList(2,3))),
    APOTHECARY("apothecary",new ArrayList<Integer>(Arrays.asList(2,3))),
    HOVEL("hovel",new ArrayList<Integer>(Arrays.asList(2,2))),
    CHAPEL("chapel",new ArrayList<Integer>(Arrays.asList(1,1))),
    CHURCH("church",new ArrayList<Integer>(Arrays.asList(2,4))),
    CATHEDRAL("catherdal",new ArrayList<Integer>(Arrays.asList(3,5))),
    WELL("well",new ArrayList<Integer>(Arrays.asList(1,1))),
    WATER_POT("waterpot",new ArrayList<Integer>(Arrays.asList(1,1))),
    GOOD_THINGS("goodthings",new ArrayList<Integer>(Arrays.asList(1,1))),
    BAD_THINGS("badthings",new ArrayList<Integer>(Arrays.asList(1,1))),
    ARMOURER("armourer",new ArrayList<Integer>(Arrays.asList(2,2))),
    BLACKSMITH("blacksmith",new ArrayList<Integer>(Arrays.asList(2,2))),
    FLETCHER("fletcher",new ArrayList<Integer>(Arrays.asList(2,2))),
    POLE_TURNER("poleturner",new ArrayList<Integer>(Arrays.asList(2,2))),
    SIGNPOST("signpost",new ArrayList<Integer>(Arrays.asList(1,1))),
    TUNNEL_ENTRANCE("tunnerlentrance",new ArrayList<Integer>(Arrays.asList(1,1))),
    STAIR("staircase",new ArrayList<Integer>(Arrays.asList(1,1))),


    // TREES


    DESERT_TREE(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    OLIVE_TREE(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    DATE_TREE(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    CHERRY_TREE(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    COCONUT_TREE(null,new ArrayList<Integer>(Arrays.asList(1,1))),


    // ROCKS


    ROCK_NORTH(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    ROCK_SOUTH(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    ROCK_EAST(null,new ArrayList<Integer>(Arrays.asList(1,1))),
    ROCK_WEST(null,new ArrayList<Integer>(Arrays.asList(1,1))),

    //Castle
    CASTLE("castle",new ArrayList<Integer>(Arrays.asList(1,1)));
    String name;
    ArrayList<Integer> shape;
    BuildingEnum(String name,ArrayList<Integer> shape){
        this.name = name;
        this.shape = shape;
        Building.getBuildingEnumByName().put(name,this);
    }
    public static BuildingEnum getBuildingEnumByName(String name){
        return Building.getBuildingEnumByName().get(name);
    }

    public static int getBuildingWidthByName(String name){
        return Building.getBuildingEnumByName().get(name).shape.get(1);
    }

    public static int getBuildingHeightByName(String name){
        return Building.getBuildingEnumByName().get(name).shape.get(1);
    }

}
