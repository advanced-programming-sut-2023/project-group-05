package org.example.model;

import org.example.model.building.Building;

import java.util.ArrayList;
import java.util.Arrays;

public enum BuildingEnum {
    //TODO : PUT BUILDING SIZE IN THE ARRAYLIST
    SMALL_STONE_WALL("smallstonewall",new ArrayList<Integer>(Arrays.asList(1,1))),
    BIG_STONE_WALL("bigstonewall",new ArrayList<Integer>(Arrays.asList(2,2))),
    SMALL_STONE_GATEHOUSE("smallstonegatehouse",new ArrayList<Integer>(Arrays.asList())),
    BIG_STONE_GATEHOUSE("bigstonegatehouse",new ArrayList<Integer>(Arrays.asList())),
    DRAW_BRIDGE("drawbridge",new ArrayList<Integer>(Arrays.asList())),
    LOOKOUT_TOWER("lookouttower",new ArrayList<Integer>(Arrays.asList())),
    PERIMETER_TOWER("perimetertower",new ArrayList<Integer>(Arrays.asList())),
    DEFENCE_TURRET("defencetower",new ArrayList<Integer>(Arrays.asList())),
    SQUARE_TOWER("squaretower",new ArrayList<Integer>(Arrays.asList())),
    ROUND_TOWER("roundtower",new ArrayList<Integer>(Arrays.asList())),
    ARMOURY("armoury",new ArrayList<Integer>(Arrays.asList())),
    BARRACKS("barracks",new ArrayList<Integer>(Arrays.asList())),
    MERCENARY_POST("mercenarypost",new ArrayList<Integer>(Arrays.asList())),
    ENGINEER_GUILD("engineerguild",new ArrayList<Integer>(Arrays.asList())),
    KILLING_PIT("killingpit",new ArrayList<Integer>(Arrays.asList())),
    OIL_SMELTER("oilsmelter",new ArrayList<Integer>(Arrays.asList())),
    PITCH_DITCH("pitchditch",new ArrayList<Integer>(Arrays.asList())),
    CAGED_WAR_DOGS("cagedwardogs",new ArrayList<Integer>(Arrays.asList())),
    SIEGE_TENT("siegetent",new ArrayList<Integer>(Arrays.asList())),
    STABLE("stable",new ArrayList<Integer>(Arrays.asList())),
    TUNNELER_GUILD("tunnelerguild",new ArrayList<Integer>(Arrays.asList())),
    APPLE_ORCHARD("appleorchard",new ArrayList<Integer>(Arrays.asList())),
    DIARY_FARMER("diaryfarmer",new ArrayList<Integer>(Arrays.asList())),
    HOPS_FARMER("hopsfarmer",new ArrayList<Integer>(Arrays.asList())),
    HUNTER_POST("hunterpost",new ArrayList<Integer>(Arrays.asList())),
    WHEAT_FARM("wheatfarm",new ArrayList<Integer>(Arrays.asList())),
    BAKERY("bakery",new ArrayList<Integer>(Arrays.asList())),
    BREWER("brewer",new ArrayList<Integer>(Arrays.asList())),
    GRANARY("granary",new ArrayList<Integer>(Arrays.asList())),
    INN("inn",new ArrayList<Integer>(Arrays.asList())),
    MILL("mill",new ArrayList<Integer>(Arrays.asList())),
    IRON_MINE("ironmine",new ArrayList<Integer>(Arrays.asList())),
    MARKET("market",new ArrayList<Integer>(Arrays.asList())),
    OX_TETHER("oxtether",new ArrayList<Integer>(Arrays.asList())),
    PITCH_RIG("pitchrig",new ArrayList<Integer>(Arrays.asList())),
    QUARRY("quarry",new ArrayList<Integer>(Arrays.asList())),
    STOCKPILE("stockpile",new ArrayList<Integer>(Arrays.asList())),
    WOODCUTTER("woodcutter",new ArrayList<Integer>(Arrays.asList())),
    APOTHECARY("apothecary",new ArrayList<Integer>(Arrays.asList())),
    HOVEL("hovel",new ArrayList<Integer>(Arrays.asList())),
    CHAPEL("chapel",new ArrayList<Integer>(Arrays.asList())),
    CHURCH("church",new ArrayList<Integer>(Arrays.asList())),
    CATHEDRAL("catherdal",new ArrayList<Integer>(Arrays.asList())),
    WELL("well",new ArrayList<Integer>(Arrays.asList())),
    WATER_POT("waterpot",new ArrayList<Integer>(Arrays.asList())),
    GOOD_THINGS("goodthings",new ArrayList<Integer>(Arrays.asList())),
    BAD_THINGS("badthings",new ArrayList<Integer>(Arrays.asList())),
    ARMOURER("armourer",new ArrayList<Integer>(Arrays.asList())),
    BLACKSMITH("blacksmith",new ArrayList<Integer>(Arrays.asList())),
    FLETCHER("fletcher",new ArrayList<Integer>(Arrays.asList())),
    POLE_TURNER("poleturner",new ArrayList<Integer>(Arrays.asList())),
    SIGNPOST("signpost",new ArrayList<Integer>(Arrays.asList())),
    TUNNEL_ENTRANCE("tunnerlentrance",new ArrayList<Integer>(Arrays.asList())),


    // TREES


    DESERT_TREE(null,new ArrayList<Integer>(Arrays.asList())),
    OLIVE_TREE(null,new ArrayList<Integer>(Arrays.asList())),
    DATE_TREE(null,new ArrayList<Integer>(Arrays.asList())),
    CHERRY_TREE(null,new ArrayList<Integer>(Arrays.asList())),
    COCONUT_TREE(null,new ArrayList<Integer>(Arrays.asList())),


    // ROCKS


    ROCK_NORTH(null,new ArrayList<Integer>(Arrays.asList())),
    ROCK_SOUTH(null,new ArrayList<Integer>(Arrays.asList())),
    ROCK_EAST(null,new ArrayList<Integer>(Arrays.asList())),
    ROCK_WEST(null,new ArrayList<Integer>(Arrays.asList())),

    //Castle
    CASTLE("castle",new ArrayList<Integer>(Arrays.asList()));
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
