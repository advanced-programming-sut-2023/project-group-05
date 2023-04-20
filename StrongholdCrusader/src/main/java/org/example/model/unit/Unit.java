package org.example.model.unit;

import org.example.model.Cost;
import org.example.model.Player;
import org.example.model.UnitModeEnum;

public class Unit {

    private final String name ;
    private final int movingSpeed ;
    private final Player owner ;
    private final int range ;
    private Cost cost ;
    private UnitModeEnum unitMode ;
    // TODO : private Building building ;
    private boolean isMoving ;

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

}
