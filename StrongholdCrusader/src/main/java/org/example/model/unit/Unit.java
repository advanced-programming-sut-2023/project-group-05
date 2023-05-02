package org.example.model.unit;

import org.example.model.Cost;
import org.example.model.Player;
import org.example.model.UnitModeEnum;
import org.example.model.UnitTypeEnum;

import java.util.HashMap;

public class Unit {

    private final String name ;
    private final int movingSpeed ;
    private final Player owner ;
    private final int range ;
    private Cost cost ;
    private UnitModeEnum unitMode ;
    // TODO : private Building building ;
    private boolean isMoving ;

    private static HashMap<String , UnitTypeEnum> unitTypeEnumMap = new HashMap<>();

    Unit( String name , Player owner , int movingSpeed , int range ){
        this.name = name ;
        this.owner = owner ;
        this.movingSpeed = movingSpeed ;
        this.range = range ;
    }

    /*
    TODO : public Building getBuilding(){
        return this.building ;
    }
    */

    /*
    TODO : public void goToJob( Building building ){
        // go to the job in that building
    }
    */

    public boolean getIsMoving(){
        return this.isMoving ;
    }

    public static HashMap<String,UnitTypeEnum> getUnitTypeEnumMap(){
        return unitTypeEnumMap ;
    }

    public void setIsMoving( boolean isMoving ){
        this.isMoving = isMoving ;
    }

    public UnitModeEnum getUnitMode(){
        return this.unitMode ;
    }

    public Cost getCost(){
        return this.cost ;
    }

    public int getRange(){
        return this.range ;
    }

    public Player getOwner(){
        return this.owner ;
    }

    public int getMovingSPeed(){
        return this.movingSpeed ;
    }

    public String getName(){
        return this.name ;
    }

    public void moveUnit( int row , int column ){
        // TODO : move unit to ( row , column )
    }

    public static Unit createUnitByName(String type,Player owner){
        Unit unit;
        Warrior warrior;
        Tunneler tunneler;
        Operator operator;
        LadderMan ladderMan;
        Jobless jobless;
        Engineer engineer;
        if (type.equals("archer"))
            return warrior = new Warrior(type,owner,10,20,10,10,10,true,false,false,false,false,false,false);
        if (type.equals("crossbowmen"))
            return warrior = new Warrior(type,owner,7,7,15,15,7,false,false,false,false,false,false,false);
        if (type.equals("spearmen"))
            return warrior = new Warrior(type,owner,15,10,0,3,10,false,true,false,true,false,false,true);
        if (type.equals("pikemen"))
            return warrior = new Warrior(type,owner,7,10,15,20,10,false,false,false,false,false,false,true);
        if (type.equals("macemen"))
            return warrior = new Warrior(type,owner,15,10,20,13,5,false,false,false,false,false,false,true);
        if (type.equals("swordsmen"))
            return warrior = new Warrior(type,owner,7,10,25,10,5,false,false,false,false,false,false,true);
        if (type.equals("knight"))
            return warrior = new Warrior(type,owner,20,10,25,20,5,false,false,true,false,false,false,true);
        if (type.equals("tunneler"))
            return tunneler = new Tunneler(owner);
        if (type.equals("laddermen"))
            return ladderMan = new LadderMan(owner);
        if (type.equals("engineer"))
            return engineer = new Engineer(owner);
        if (type.equals("blackmonk"))
            return warrior = new Warrior(type,owner,7,5,10,10,5,false,false,false,false,false,false,true);
        if (type.equals("archerbow"))
            return warrior = new Warrior(type,owner,15,15,10,10,10,false,false,false,false,false,false,true);
        if (type.equals("slaves"))
            return warrior = new Warrior(type,owner,15,10,5,5,5,true,false,false,false,false,false,true);
        if (type.equals("slingers"))
            return warrior = new Warrior(type,owner,15,5,7,5,5,false,false,false,false,false,false,true);
        if (type.equals("assasins"))
            return warrior = new Warrior(type,owner,10,10,10,10,10,false,false,false,false,true,true,true);
        if (type.equals("horsearchers"))
            return warrior = new Warrior(type,owner,25,12,10,15,10,false,false,true,false,false, false,false);
        if (type.equals("arabianswordsmen"))
            return warrior = new Warrior(type,owner,20,5,20,20,5,false,false,false,false,false,false,true);
        if (type.equals("firethrowers"))
            return warrior = new Warrior(type,owner,20,5,15,7,5,true,false,false,false,false,false,true);
        if (type.equals("jobless"))
            return jobless = new Jobless(owner);
        //TODO : HANDLE OPERATOR IN-PLACE
        return null;
    }

}
