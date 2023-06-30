package org.example.model;

import org.example.model.unit.Unit;

import java.util.HashMap;

public enum UnitTypeEnum {
    //TODO : Handle King
    ARCHER("archer"),
    CROSSBOWMEN("crowwbowmen"),
    SPEARMEN("spearmen"),
    PIKEMEN("pikemen"),
    MACEMEN("macemen"),
    SWORDSMEN("swordsman"),
    KNIGHT("knight"),
    TUNNELER("tunneler"),
    LADDERMEN("laddermen"),
    ENGINEER("enginner"),
    BLACKMONK("blackmonk"),
    ARCHERBOW("archerbow"),
    SLAVES("slaves"),
    SLINGERS("slingers"),
    ASSASSINS("assassins"),
    HORSE_ARCHERS("houseArchers"),
    ARABIAN_SWORDSMEN("arabianSwordsmen"),
    TRAP("trap"),
    FIRE_THROWERS("fireThrowers");

    private String type;

    UnitTypeEnum(String type){
        this.type = type;
        Unit.getUnitTypeEnumMap().put(type,this);
    }

    public static UnitTypeEnum getUnitTypeByName(String type){
        return Unit.getUnitTypeEnumMap().get(type);
    }
}
