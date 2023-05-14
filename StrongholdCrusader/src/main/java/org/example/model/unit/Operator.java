package org.example.model.unit;

import org.example.model.Player;
import org.example.model.building.Building;

public class Operator extends Unit {

    private Building building ;
    private int turn ;

    public Operator( String name , Player owner ,int hitPoint, int row , int column , Building building ){
        super( name ,owner ,hitPoint, 1 , 0 , row , column , false ) ;
        this.building = building ;
        this.turn = 0 ;
    }

    public void updateTurn(){
        this.turn++ ;
    }

    public Building getBuilding(){
        return this.building ;
    }

    public int getTurn(){
        return this.turn;
    }

}
