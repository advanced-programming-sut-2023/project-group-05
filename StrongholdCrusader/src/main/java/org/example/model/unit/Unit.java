package org.example.model.unit;

import org.example.controller.PathFinder;
import org.example.model.Cost;
import org.example.model.Player;
import org.example.model.UnitModeEnum;
import org.example.model.UnitTypeEnum;

import java.util.HashMap;

public class Unit {

    private final String name ;
    public int currentRow;
    public int currentColumn;
    private final int movingSpeed ;
    private final Player owner ;
    private final int range ;
    private Cost cost ;
    private int targetRow;
    private int targetColumn;
    private UnitModeEnum unitMode ;
    // TODO : private Building building ;
    private boolean isMoving ;

    private static HashMap<String , UnitTypeEnum> unitTypeEnumMap = new HashMap<>();
    private static HashMap<String , UnitModeEnum> unitModeEnumMap = new HashMap<>();
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
    public void setUnitMode (UnitModeEnum unitMode){
        this.unitMode = unitMode;
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

    public void move( int row , int column ){
        // TODO : move unit to ( row , column )

    }

    public void setTarget(int row , int column){
        this.targetRow = row;
        this.targetColumn = column;
        this.isMoving = true;
    }

    public static HashMap<String,UnitModeEnum> getUnitModeEnumMap() {
        return unitModeEnumMap ;
    }

    public static Unit createUnitByName(String type,Player owner){
        if (type.equals("archer"))
            return new Warrior(type,owner,10,20,10,10,10,true,false,false,false,false,false,false);
        if (type.equals("crossbowman"))
            return new Warrior(type,owner,7,7,15,15,7,false,false,false,false,false,false,false);
        if (type.equals("spearman"))
            return new Warrior(type,owner,15,10,0,3,10,false,true,false,true,false,false,true);
        if (type.equals("pikeman"))
            return new Warrior(type,owner,7,10,15,20,10,false,false,false,false,false,false,true);
        if (type.equals("maceman"))
            return new Warrior(type,owner,15,10,20,13,5,false,false,false,false,false,false,true);
        if (type.equals("swordsman"))
            return new Warrior(type,owner,7,10,25,10,5,false,false,false,false,false,false,true);
        if (type.equals("knight"))
            return new Warrior(type,owner,20,10,25,20,5,false,false,true,false,false,false,true);
        if (type.equals("tunneler"))
            return new Tunneler(owner);
        if (type.equals("ladderman"))
            return new LadderMan(owner);
        if (type.equals("engineer"))
            return new Engineer(owner);
        if (type.equals("blackmonk"))
            return new Warrior(type,owner,7,5,10,10,5,false,false,false,false,false,false,true);
        if (type.equals("archerbow"))
            return new Warrior(type,owner,15,15,10,10,10,false,false,false,false,false,false,true);
        if (type.equals("slave"))
            return new Warrior(type,owner,15,10,5,5,5,true,false,false,false,false,false,true);
        if (type.equals("slinger"))
            return new Warrior(type,owner,15,5,7,5,5,false,false,false,false,false,false,true);
        if (type.equals("assasin"))
            return new Warrior(type,owner,10,10,10,10,10,false,false,false,false,true,true,true);
        if (type.equals("horsearcher"))
            return new Warrior(type,owner,25,12,10,15,10,false,false,true,false,false, false,false);
        if (type.equals("arabianswordsman"))
            return new Warrior(type,owner,20,5,20,20,5,false,false,false,false,false,false,true);
        if (type.equals("firethrower"))
            return new Warrior(type,owner,20,5,15,7,5,true,false,false,false,false,false,true);
        if (type.equals("jobless"))
            return new Jobless(owner);
        //TODO : HANDLE OPERATOR IN-PLACE
        return null;
    }

    public static Cost getCostByName(String name){

        if(name.equals("archer")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 1 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("crossbowman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 1 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("spearman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 1 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("pikeman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                1 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("maceman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 1 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("swordsmen")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 1 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("knight")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 1 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("tunneler")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("ladderman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("engineer")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("blackmonk")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("archerbow")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("slave")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("slinger")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("assassin")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("horsearcher")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("arabianswordsman")) return new Cost( 0 , 0 , 0 , 100 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;
        if(name.equals("firethrower")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0) ;

        return null ;
    }

}
