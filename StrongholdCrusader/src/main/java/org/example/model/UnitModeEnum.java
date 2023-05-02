package org.example.model;

import org.example.model.unit.Unit;

public enum UnitModeEnum {
    // TODO : write details of each mode
    MODE1("name1") , MODE2("name2") , MODE3("name3") ;

    private String unitModeName ;

    private UnitModeEnum( String unitModeName ){
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
