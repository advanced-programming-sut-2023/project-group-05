package org.example.model.unit;

import org.example.controller.PathFinder;

import org.example.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    private PathFinder pathFinder ;
    private final String name ;
    public int currentRow;
    public int currentColumn;
    private final int movingSpeed ;
    private final Player owner ;
    private final int range ;
    private Cost cost ;
    public static ArrayList <Unit> units = new ArrayList<Unit>() ;
    private UnitModeEnum unitMode ;
    // TODO : private Building building ;
    private boolean isMoving ;

    private int row ;
    private int column ;

    private static HashMap<String , UnitTypeEnum> unitTypeEnumMap = new HashMap<>();
    private static HashMap<String , UnitModeEnum> unitModeEnumMap = new HashMap<>();

    private int targetRow = -1 ;
    private int targetColumn = -1 ;
    Unit( String name , Player owner , int movingSpeed , int range , int row , int column ){
        this.name = name ;
        this.owner = owner ;
        this.row = row ;
        this.column = column ;
        this.movingSpeed = movingSpeed ;
        this.range = range ;
        units.add(this) ;
    }


    public static ArrayList<Unit> getUnits(){
        return units ;
    }

    public void setTarget( int row , int column , GameMap gameMap ){
        this.targetRow = row ;
        this.targetColumn = column ;
        this.pathFinder = new PathFinder() ;
        pathFinder.setGameMap( gameMap.getMaskedMap() , 400 ) ;
        pathFinder.Run( row , column ) ;
    }

    public void getNextMove(Integer row , Integer column){
        int[] rc = { 0 , 0 } ;
        int nextMoveMask = pathFinder.goInDirectionFrom(this.row , this.column)  ;
        /// up, down, left, right (0, 1, 2, 3) and -1 if it is fucked!
        switch(nextMoveMask){
            case 0 :
                rc[0] = -1 ;
                break ;
            case 1 :
                rc[0] = 1  ;
                break ;
            case 2 :
                rc[1] = -1 ;
                break ;
            case 3 :
                rc[1] = 1 ;
                break ;
        }
    }

    public int getTargetColumn(){
        return this.targetColumn ;
    }

    public int getTargetRow(){
        return this.targetRow ;
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

    public static HashMap<String,UnitModeEnum> getUnitModeEnumMap() {
        return unitModeEnumMap ;
    }

    public static Unit createUnitByName(String type,Player owner,int row , int column){
        if (type.equals("archer"))
            return new Warrior(type,owner,10,20,10,10,10,true,false,false,false,false,false,false,row , column);
        if (type.equals("crossbowman"))
            return new Warrior(type,owner,7,7,15,15,7,false,false,false,false,false,false,false,row,column);
        if (type.equals("spearman"))
            return new Warrior(type,owner,15,10,0,3,10,false,true,false,true,false,false,true,row,column);
        if (type.equals("pikeman"))
            return new Warrior(type,owner,7,10,15,20,10,false,false,false,false,false,false,true,row,column);
        if (type.equals("maceman"))
            return new Warrior(type,owner,15,10,20,13,5,false,false,false,false,false,false,true,row,column);
        if (type.equals("swordsman"))
            return new Warrior(type,owner,7,10,25,10,5,false,false,false,false,false,false,true,row,column);
        if (type.equals("knight"))
            return new Warrior(type,owner,20,10,25,20,5,false,false,true,false,false,false,true,row,column);
        if (type.equals("tunneler"))
            return new Tunneler(owner,row,column);
        if (type.equals("ladderman"))
            return new LadderMan(owner,row,column);
        if (type.equals("engineer"))
            return new Engineer(owner,row,column);
        if (type.equals("blackmonk"))
            return new Warrior(type,owner,7,5,10,10,5,false,false,false,false,false,false,true,row,column);
        if (type.equals("archerbow"))
            return new Warrior(type,owner,15,15,10,10,10,false,false,false,false,false,false,true,row,column);
        if (type.equals("slave"))
            return new Warrior(type,owner,15,10,5,5,5,true,false,false,false,false,false,true,row,column);
        if (type.equals("slinger"))
            return new Warrior(type,owner,15,5,7,5,5,false,false,false,false,false,false,true,row,column);
        if (type.equals("assasin"))
            return new Warrior(type,owner,10,10,10,10,10,false,false,false,false,true,true,true,row,column);
        if (type.equals("horsearcher"))
            return new Warrior(type,owner,25,12,10,15,10,false,false,true,false,false, false,false,row,column);
        if (type.equals("arabianswordsman"))
            return new Warrior(type,owner,20,5,20,20,5,false,false,false,false,false,false,true,row,column);
        if (type.equals("firethrower"))
            return new Warrior(type,owner,20,5,15,7,5,true,false,false,false,false,false,true,row,column);
        if (type.equals("jobless"))
            return new Jobless(owner , row , column);
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

    public int getRow(){
        return this.row ;
    }

    public int getColumn(){
        return this.column ;
    }

    public void setColumn(int column){
        this.column = column ;
    }

    public void setRow(int row){
        this.row = row ;
    }

}
