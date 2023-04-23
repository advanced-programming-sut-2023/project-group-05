package org.example.model.building;

import org.example.model.Cost;
import org.example.model.Player;

import java.util.ArrayList;

public class TradeBuilding extends Building {

    private final int requiredNumberOfOperators ;
    private boolean functional ;
    private int rate ;
    private int turnBuilt ;
    private int capacity ;
    private int objectsCount ;

    public TradeBuilding( String name , String category , int width , int height , boolean passable ,
                          int popularityRate , boolean holdsAnimal , Player owner , int row , int column , int hitPoint , Cost cost ,
                          int requiredNumberOfOperators ){
            super( name , height , width , passable , category , owner , row , column , cost , hitPoint ,
                    popularityRate , holdsAnimal ) ;
        this.requiredNumberOfOperators = requiredNumberOfOperators ;
    }

    public void trade( Cost cost , ArrayList <Object> objects , Object object ){
        // TODO : trade
    }

    public void updateFunctionality(){
        this.functional = ( capacity == objectsCount ) ;
    }

    public boolean getFunctional(){
        return this.functional ;
    }

    public int getRate(){
        return this.rate ;
    }

    public int getTurnBuilt(){
        return this.turnBuilt ;
    }

    public int getCapacity(){
        return this.capacity ;
    }

    public int getObjectsCount(){
        return this.objectsCount ;
    }

    public int getRequiredNumberOfOperators(){
        return this.requiredNumberOfOperators ;
    }
}
