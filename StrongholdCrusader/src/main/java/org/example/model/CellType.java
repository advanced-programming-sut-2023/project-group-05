package org.example.model;

import org.example.model.unit.Unit;

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
    CellType(String name){
        this.name = name;
        Cell.getCellTypeEnumByName().put(name,this);
    }
    public static String getCellTypeNameByEnum(CellType cellType){
        return cellType.name;
    }
    public static CellType getCellTypeEnumByName(String name){
        return Cell.getCellTypeEnumByName().get(name);
    }

}
