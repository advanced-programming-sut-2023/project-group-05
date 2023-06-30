package org.example.model;

import org.example.model.unit.Unit;

public enum UnitModeEnum {
    // TODO : write details of each mode
    DEFENSIVE("defensive") , AGGRESSIVE("aggressive") , STANDING("standing") ;

    private final String unitModeName ;

    UnitModeEnum( String unitModeName ){
        this.unitModeName = unitModeName ;
        Unit.getUnitModeEnumMap().put(unitModeName,this);
    }

    public static String getUnitModeName(UnitModeEnum unitModeEnum){
        return unitModeEnum.unitModeName ;
    }
    public static UnitModeEnum getUnitModeEnumByName (String name){
        return Unit.getUnitModeEnumMap().get(name);
    }
}
