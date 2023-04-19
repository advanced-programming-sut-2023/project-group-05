package org.example.model;

public enum UnitModeEnum {
    // TODO : write details of each mode
    MODE1("name1") , MODE2("name2") , MODE3("name3") ;

    private final String unitModeName ;

    UnitModeEnum( String unitModeName ){
        this.unitModeName = unitModeName ;
    }

    String getUnitModeName(){
        return this.unitModeName ;
    }


}
