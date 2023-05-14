package org.example.model;

import org.example.model.building.Building;

import java.util.ArrayList;
import java.util.Arrays;

public enum BuildingEnum {
    //TODO : PUT BUILDING SIZE IN THE ARRAYLIST
    // LET'S USE ROCK/TREE HITPOINTS FOR MINING AND EXTRACTION VALUES
    SMALL_STONE_WALL("smallstonewall",new ArrayList<Integer>(Arrays.asList(1,1)),500),
    BIG_STONE_WALL("bigstonewall",new ArrayList<Integer>(Arrays.asList(2,2)),2000),
    SMALL_STONE_GATEHOUSE("smallstonegatehouse",new ArrayList<Integer>(Arrays.asList()),500),
    BIG_STONE_GATEHOUSE("bigstonegatehouse",new ArrayList<Integer>(Arrays.asList()),1000),
    DRAW_BRIDGE("drawbridge",new ArrayList<Integer>(Arrays.asList()),200),
    LOOKOUT_TOWER("lookouttower",new ArrayList<Integer>(Arrays.asList()),5000),
    PERIMETER_TOWER("perimetertower",new ArrayList<Integer>(Arrays.asList()),4000),
    DEFENCE_TURRET("defencetower",new ArrayList<Integer>(Arrays.asList()),2000),
    SQUARE_TOWER("squaretower",new ArrayList<Integer>(Arrays.asList()),4000),
    ROUND_TOWER("roundtower",new ArrayList<Integer>(Arrays.asList()),4000),
    ARMOURY("armoury",new ArrayList<Integer>(Arrays.asList()),2000),
    BARRACKS("barracks",new ArrayList<Integer>(Arrays.asList()),2000),
    MERCENARY_POST("mercenarypost",new ArrayList<Integer>(Arrays.asList()),2000),
    ENGINEER_GUILD("engineerguild",new ArrayList<Integer>(Arrays.asList()),400),
    KILLING_PIT("killingpit",new ArrayList<Integer>(Arrays.asList()),0),
    OIL_SMELTER("oilsmelter",new ArrayList<Integer>(Arrays.asList()),100),
    PITCH_DITCH("pitchditch",new ArrayList<Integer>(Arrays.asList()),0),
    CAGED_WAR_DOGS("cagedwardogs",new ArrayList<Integer>(Arrays.asList()),100),
    SIEGE_TENT("siegetent",new ArrayList<Integer>(Arrays.asList()),300),
    STABLE("stable",new ArrayList<Integer>(Arrays.asList()),200),
    TUNNELER_GUILD("tunnelerguild",new ArrayList<Integer>(Arrays.asList()),0),
    APPLE_ORCHARD("appleorchard",new ArrayList<Integer>(Arrays.asList()),300),
    DIARY_FARMER("diaryfarmer",new ArrayList<Integer>(Arrays.asList()),300),
    HOPS_FARMER("hopsfarmer",new ArrayList<Integer>(Arrays.asList()),300),
    HUNTER_POST("hunterpost",new ArrayList<Integer>(Arrays.asList()),300),
    WHEAT_FARM("wheatfarm",new ArrayList<Integer>(Arrays.asList()),300),
    BAKERY("bakery",new ArrayList<Integer>(Arrays.asList()),300),
    BREWER("brewer",new ArrayList<Integer>(Arrays.asList()),300),
    GRANARY("granary",new ArrayList<Integer>(Arrays.asList()),500),
    INN("inn",new ArrayList<Integer>(Arrays.asList()),300),
    MILL("mill",new ArrayList<Integer>(Arrays.asList()),400),
    IRON_MINE("ironmine",new ArrayList<Integer>(Arrays.asList()),300),
    MARKET("market",new ArrayList<Integer>(Arrays.asList()),400),
    OX_TETHER("oxtether",new ArrayList<Integer>(Arrays.asList()),100),
    PITCH_RIG("pitchrig",new ArrayList<Integer>(Arrays.asList()),0),
    QUARRY("quarry",new ArrayList<Integer>(Arrays.asList()),300),
    STOCKPILE("stockpile",new ArrayList<Integer>(Arrays.asList()),100000),
    WOODCUTTER("woodcutter",new ArrayList<Integer>(Arrays.asList()),300),
    APOTHECARY("apothecary",new ArrayList<Integer>(Arrays.asList()),300),
    HOVEL("hovel",new ArrayList<Integer>(Arrays.asList()),200),
    CHAPEL("chapel",new ArrayList<Integer>(Arrays.asList()),0),
    CHURCH("church",new ArrayList<Integer>(Arrays.asList()),400),
    CATHEDRAL("catherdal",new ArrayList<Integer>(Arrays.asList()),400),
    WELL("well",new ArrayList<Integer>(Arrays.asList()),50),
    WATER_POT("waterpot",new ArrayList<Integer>(Arrays.asList()),150),
    GOOD_THINGS("goodthings",new ArrayList<Integer>(Arrays.asList()),50),
    BAD_THINGS("badthings",new ArrayList<Integer>(Arrays.asList()),50),
    ARMOURER("armourer",new ArrayList<Integer>(Arrays.asList()),300),
    BLACKSMITH("blacksmith",new ArrayList<Integer>(Arrays.asList()),300),
    FLETCHER("fletcher",new ArrayList<Integer>(Arrays.asList()),300),
    POLE_TURNER("poleturner",new ArrayList<Integer>(Arrays.asList()),300),
    SIGNPOST("signpost",new ArrayList<Integer>(Arrays.asList()),150),
    TUNNEL_ENTRANCE("tunnerlentrance",new ArrayList<Integer>(Arrays.asList()),100),


    // TREES


    DESERT_TREE(null,new ArrayList<Integer>(Arrays.asList()),100),
    OLIVE_TREE(null,new ArrayList<Integer>(Arrays.asList()),100),
    DATE_TREE(null,new ArrayList<Integer>(Arrays.asList()),100),
    CHERRY_TREE(null,new ArrayList<Integer>(Arrays.asList()),100),
    COCONUT_TREE(null,new ArrayList<Integer>(Arrays.asList()),100),


    // ROCKS


    ROCK_NORTH(null,new ArrayList<Integer>(Arrays.asList()),100),
    ROCK_SOUTH(null,new ArrayList<Integer>(Arrays.asList()),100),
    ROCK_EAST(null,new ArrayList<Integer>(Arrays.asList()),100),
    ROCK_WEST(null,new ArrayList<Integer>(Arrays.asList()),100),

    //Castle
    CASTLE("castle",new ArrayList<Integer>(Arrays.asList()),1000000);
    String name;
    int hitPoint;
    ArrayList<Integer> shape;
    BuildingEnum(String name,ArrayList<Integer> shape,int hitPoint){
        this.name = name;
        this.shape = shape;
        this.hitPoint = hitPoint;
        Building.getBuildingEnumByName().put(name,this);
        Building.getBuildingHitPointByName().put(name,this.hitPoint);
    }
    public static BuildingEnum getBuildingEnumByName(String name){
        return Building.getBuildingEnumByName().get(name);
    }

    public static int getHitpointByName(String name){
        return Building.getBuildingHitPointByName().get(name);
    }

    public static int getBuildingWidthByName(String name){
        return Building.getBuildingEnumByName().get(name).shape.get(1);
    }

    public static int getBuildingHeightByName(String name){
        return Building.getBuildingEnumByName().get(name).shape.get(1);
    }

}