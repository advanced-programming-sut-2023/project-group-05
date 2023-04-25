package org.example.model;

import javax.swing.*;

public enum CellType {
    GROUND("ground"),
    IRON_MINE("ironMine"),
    ROCK_MINE("rockMine"),
    BOULDER("boulder"),
    GRASS("grass"),
    MEADOW("meadow"),
    GRASSLAND("grassland"),
    OIL_WELL("oilWell"),
    DELTA("delta"),
    RIVER("river"),
    SHOAL("shoal"),
    SMALL_POND("smallPond"),
    BIG_POND("bigPond"),
    COAST("coast"),
    SEA("sea");
    private String name;
    private CellType(String name){
        this.name = name;
    }
    public static String getCellTypeName(CellType cellType){
        return cellType.name;
    }

}
