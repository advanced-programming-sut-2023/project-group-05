package org.example.model.unit;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.example.controller.PathFinder;

import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.enums.UnitImagesEnum;
import org.example.view.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    protected PathFinder pathFinder = new PathFinder() ;
    protected boolean isOnHighGround ;
    private final String name ;
    private int hitPoint;
    private final int movingSpeed ;
    private final Player owner ;
    private final int range ;
    private Cost cost ;
    public static ArrayList <Unit> units = new ArrayList<Unit>() ;
    private UnitModeEnum unitMode ;
    // TODO : private Building building ;
    protected boolean isMoving ;

    protected int row ;
    protected int column ;

    private static HashMap<String , UnitTypeEnum> unitTypeEnumMap = new HashMap<>();
    private static HashMap<String , UnitModeEnum> unitModeEnumMap = new HashMap<>();

    private boolean selectable ;
    protected int targetRow = -1 ;
    protected int targetColumn = -1 ;
    private Rectangle shape ;
    public Unit( String name , Player owner , int hitPoint,int movingSpeed , int range , int row , int column , boolean selectable ){
        this.name = name ;
        this.hitPoint = hitPoint;
        this.owner = owner ;
        this.row = row ;
        this.column = column ;
        this.isOnHighGround = false ;
        this.selectable = selectable ;
        this.movingSpeed = movingSpeed ;
        this.range = range ;
        units.add(this) ;
        owner.setPopulation( owner.getPopulation() + 1 );
        owner.getUnits().add(this) ;
    }

    public Rectangle getShape(){
        return this.shape ;
    }

    public void setShape(){
        this.shape = new Rectangle( 0 , 0 , 50 , 50 ) ;
        Image image = null ;

        switch( this.name ){
            case "archer" :
                image = UnitImagesEnum.ARCHER.image ;
                System.out.println( "Unit.java 68 : " + image ) ;
                break ;
            case "crossbowman" :
                image = UnitImagesEnum.CROSSBOWMAN.image ;
                break ;
            case "spearman" :
                image = UnitImagesEnum.ARCHER.image ;
                break ;
            case "pikeman" :
                image = UnitImagesEnum.ARCHER.image ;
                break ;
                // TODO : complete
        }

        this.shape.setFill( new ImagePattern( image ) ) ;

    }

    public boolean getSelectable(){
        return this.selectable ;
    }

    public boolean getIsOnHighGround(){
        return this.isOnHighGround ;
    }

    public void setIsOnHighGround(boolean highGround){
        this.isOnHighGround = highGround ;
    }

    public void die(GameMap gameMap){
        this.owner.getSelectedUnits().remove(this) ;
        this.owner.getUnits().remove(this) ;
        this.owner.setPopulation( this.owner.getPopulation() - 1 );
        Unit.getUnits().remove(this) ;
        gameMap.getCell(this.getRow() , this.getColumn()).getUnits().remove(this) ;
    }

    public void getDamaged(int x , GameMap gameMap){
        this.hitPoint -= x ;
        if(this.hitPoint <= 0)
            this.die(gameMap) ;
    }

    public static ArrayList<Unit> getUnits(){
        return units ;
    }

    public void setTarget( int row , int column , GameMap gameMap ){
        if(this.row == row && this.column == column){
            this.isMoving = false ;
        }
        this.targetRow = row ;
        this.targetColumn = column ;
        pathFinder.setGameMap( gameMap.getMaskedMap() , gameMap.getMaskedMapUpperGround() , 400 ) ;
        pathFinder.Run( row , column ) ;
        this.isMoving = true;
    }

    public int getNextRow(){
        int nextMoveMask = pathFinder.goInDirectionFrom(this.row , this.column)  ;
        /// up, down, left, right (0, 1, 2, 3) and -1 if it is fucked!
        switch(nextMoveMask){
            case 0 :
                return this.row - 1 ;
            case 1 :
                return this.row + 1  ;
            case 2 :
            case 3 :
                return this.row ;
        }
        return this.row ;
    }

    public int getNextColumn(){
        int nextMoveMask = pathFinder.goInDirectionFrom(this.row , this.column)  ;
        /// up, down, left, right (0, 1, 2, 3) and -1 if it is fucked!
        switch(nextMoveMask){
            case 0 :
            case 1 :
                return this.column ;
            case 2 :
                return this.column -1 ;
            case 3 :
                return this.column + 1 ;
        }
        return this.column ;
    }


    public ArrayList<Building> getAdjacantBuildings(GameMap gameMap){
        ArrayList<Building> ret = new ArrayList<Building>() ;
        int[][] adj = { {0,1} , {0,-1} , {1,0} , {-1,0} } ;
        for(int i = 0 ; i < 4 ; i++){
            Building building = gameMap.getCell(this.row+adj[i][0] , this.column+adj[i][1]).getBuilding() ;
            if(null != building)
                ret.add(building) ;
        }
        return ret ;
    }

    public int getHitPoint(){
        return this.hitPoint ;
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
        if(this.isMoving==false){
            this.targetColumn = -1 ;
            this.targetRow = -1 ;
        }
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

    public int getMovingSpeed(){
        return this.movingSpeed ;
    }

    public String getName(){
        return this.name ;
    }


    public static HashMap<String,UnitModeEnum> getUnitModeEnumMap() {
        return unitModeEnumMap ;
    }

    public static Unit createUnitByName(String type,Player owner,int row , int column){
        UnitTypeEnum unitType = UnitTypeEnum.getUnitTypeByName(type);
        if (unitType == UnitTypeEnum.ARCHER)
            return new Warrior(type,owner,10,100,10,20,10,10,10,true,false,false,false,false,false,false,row , column);
        if (unitType == UnitTypeEnum.CROSSBOWMEN)
            return new Warrior(type,owner,10,150,7,7,15,15,7,false,false,false,false,false,false,false,row,column);
        if (unitType == UnitTypeEnum.SPEARMEN)
            return new Warrior(type,owner,10,50,15,10,0,3,10,false,true,false,true,false,false,true,row,column);
        if (unitType == UnitTypeEnum.PIKEMEN)
            return new Warrior(type,owner,10,50,7,10,15,20,10,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.MACEMEN)
            return new Warrior(type,owner,10,150,15,10,20,13,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.SWORDSMEN)
            return new Warrior(type,owner,10,250,7,10,25,10,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.KNIGHT)
            return new Warrior(type,owner,10,250,20,10,25,20,5,false,false,true,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.TUNNELER)
            return new Tunneler(owner,5,row,column);
        if (unitType == UnitTypeEnum.LADDERMEN)
            return new LadderMan(owner,5,row,column);
        if (unitType == UnitTypeEnum.ENGINEER)
            return new Engineer(owner,5,row,column);
        if (unitType == UnitTypeEnum.BLACKMONK)
            return new Warrior(type,owner,10,50,7,5,10,10,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.ARCHERBOW)
            return new Warrior(type,owner,10,100,15,15,10,10,10,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.SLAVES)
            return new Warrior(type,owner,10,50,15,10,5,5,5,true,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.SLINGERS)
            return new Warrior(type,owner,10,50,15,5,7,5,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.ASSASSINS)
            return new Warrior(type,owner,10,150,10,10,10,10,10,false,false,false,false,true,true,true,row,column);
        if (unitType == UnitTypeEnum.HORSE_ARCHERS)
            return new Warrior(type,owner,10,100,25,12,10,15,10,false,false,true,false,false, false,false,row,column);
        if (unitType == UnitTypeEnum.ARABIAN_SWORDSMEN)
            return new Warrior(type,owner,10,200,20,5,20,20,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.FIRE_THROWERS)
            return new Warrior(type,owner,10,50,20,5,15,7,5,true,false,false,false,false,false,true,row,column);
        if (type.equals("jobless"))
            return new Jobless(owner , 2,row , column);
        if (type.equals("trap"))
            return new Warrior(type,owner,1000,5,0,1,1000,0,0,false,false,false,false,false,false,false,row,column);
        if (type.equals("manjenigh"))
            return new Warrior(type,owner,20,50,2,40,20,0,0,false,false,false,false,false,false,true,row,column);
        if (type.equals("dejkub"))
            return new Warrior(type,owner,20,50,4,1,20,0,0,false,false,false,false,false,false,true,row,column);
        if (type.equals("sangandazatashin"))
            return new Warrior(type,owner,20,50,2,10,20,0,0,false,false,false,false,false,false,true,row,column);

        if(type.equals("ox"))
            return new Unit("ox",owner,1,1,0,row,column,false) ;

        return null;
    }

    public static Cost getCostByName(String name){

        if(name.equals("archer")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 1 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("crossbowman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 1 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("spearman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 1 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("pikeman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                1 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("maceman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 1 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("swordsmen")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 1 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("knight")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 1 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("tunneler")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("ladderman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("engineer")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("blackmonk")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("archerbow")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("slave")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("slinger")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("assassin")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("horsearcher")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("arabianswordsman")) return new Cost( 0 , 0 , 0 , 100 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("firethrower")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;

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
