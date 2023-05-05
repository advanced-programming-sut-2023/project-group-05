package org.example.model;

import org.example.model.building.Building;

public enum BuildingEnum {
    SMALL_STONE_WALL("smallstonewall"),
    BIG_STONE_WALL("bigstonewall"),
    SMALL_STONE_GATEHOUSE("smallstonegatehouse"),
    BIG_STONE_GATEHOUSE("bigstonegatehouse"),
    DRAW_BRIDGE("drawbridge"),
    LOOKOUT_TOWER("lookouttower"),
    PERIMETER_TOWER("perimetertower"),
    DEFENCE_TURRET("defencetower"),
    SQUARE_TOWER("squaretower"),
    ROUND_TOWER("roundtower"),
    ARMOURY("armoury"),
    BARRACKS("barracks"),
    MERCENARY_POST("mercenarypost"),
    ENGINEER_GUILD("engineerguild"),
    KILLING_PIT("killingpit"),
    OIL_SMELTER("oilsmelter"),
    PITCH_DITCH("pitchditch"),
    CAGED_WAR_DOGS("cagedwardogs"),
    SIEGE_TENT("siegetent"),
    STABLE("stable"),
    TUNNELER_GUILD("tunnelerguild"),
    APPLE_ORCHARD("appleorchard"),
    DIARY_FARMER("diaryfarmer"),
    HOPS_FARMER("hopsfarmer"),
    HUNTER_POST("hunterpost"),
    WHEAT_FARM("wheatfarm"),
    BAKERY("bakery"),
    BREWER("brewer"),
    GRANARY("granary"),
    INN("inn"),
    MILL("mill"),
    IRON_MINE("ironmine"),
    MARKET("market"),
    OX_TETHER("oxtether"),
    PITCH_RIG("pitchrig"),
    QUARRY("quarry"),
    STOCKPILE("stockpile"),
    WOODCUTTER("woodcutter"),
    APOTHECARY("apothecary"),
    HOVEL("hovel"),
    CHAPEL("chapel"),
    CHURCH("church"),
    CATHEDRAL("catherdal"),
    WELL("well"),
    WATER_POT("waterpot"),
    GOOD_THINGS("goodthings"),
    BAD_THINGS("badthings"),
    ARMOURER("armourer"),
    BLACKSMITH("blacksmith"),
    FLETCHER("fletcher"),
    POLE_TURNER("poleturner"),
    SIGNPOST("signpost"),
    TUNNEL_ENTRANCE("tunnerlentrance"),


    // TREES


    DESERT_TREE(null),
    OLIVE_TREE(null),
    DATE_TREE(null),
    CHERRY_TREE(null),
    COCONUT_TREE(null),


    // ROCKS


    ROCK_NORTH(null),
    ROCK_SOUTH(null),
    ROCK_EAST(null),
    ROCK_WEST(null);
    String name;
    BuildingEnum(String name){
        this.name = name;
        Building.getBuildingEnumByName().put(name,this);
    }
    public static BuildingEnum getBuildingEnumByName(String name){
        return Building.getBuildingEnumByName().get(name);
    }

}
