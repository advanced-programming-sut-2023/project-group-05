package org.example.model.building;

import org.example.model.Cost;
import org.example.model.unit.Unit ;
import org.example.model.Player ;

import java.util.ArrayList;

public class Building {

    private final String name ;
    private final String category ;
    private ArrayList <Unit> units ;
    private final Player owner ;
    private final int height ;
    private final int width ;
    private final Cost cost ;
    private final int row ;
    private final int column ;
    private final boolean holdsAnimal ;
    private boolean passable ;
    private int hitPoint ;

    Building( String name , int height , int width , boolean passable , String category , Player owner
            , int row , int column , Cost cost , int hitPoint , int popularityRate , boolean holdsAnimal ){

        this.name = name ;
        this.category = category ;
        this.owner = owner ;
        this.height = height ;
        this.width = width ;
        this.cost = cost ;
        this.row = row ;
        this.column = column ;
        this.holdsAnimal = holdsAnimal ;
        this.passable = passable ;
        this.hitPoint = hitPoint ;

    }

    public void setPassable( boolean passable ){
        this.passable = passable ;
    }

    public void addUnit( Unit unit ){
        this.units.add( unit ) ;
    }

    public void getDamaged( int damage ){
        this.hitPoint -= damage ;
    }

    public int getHitPoint(){
        return this.hitPoint ;
    }

    public boolean getPassable(){
        return this.passable ;
    }

    public boolean getHoldsAnimal(){
        return this.holdsAnimal ;
    }

    public int getColumn(){
        return this.column ;
    }

    public int getRow(){
        return this.row ;
    }

    public Cost getCost(){
        return this.cost ;
    }

    public int getHeight(){
        return this.height ;
    }

    public int getWidth(){
        return this.width ;
    }

    public Player getOwner(){
        return this.owner ;
    }

    public ArrayList<Unit> getUnits(){
        return this.units ;
    }

    public String getCategory(){
        return this.category ;
    }

    public String getName(){
        return this.name ;
    }

}
