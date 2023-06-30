package org.example.model.building;

import org.example.model.BuildingEnum;
import org.example.model.Cost;
import org.example.model.Player;

import java.util.ArrayList;

public class StorageBuilding extends Building {

    public static int maxGranaryCapacityForEachFood = 200;
    public static int maxStockpileCapacityForEachResource = 200;
    private final int upperBound ;
    private ArrayList<Object> objects ;

    public StorageBuilding( String name , int width , int height , boolean passable , String category , Player owner ,
                            int row , int column , Cost cost , int hitPoint , int popularityRate , boolean holdsAnimal ,
                            int upperBound, BuildingEnum buildingEnum){

        super( name , width , height , passable , category , owner, row , column , cost , hitPoint ,
               popularityRate , holdsAnimal , buildingEnum , 0 ) ;

        this.upperBound = upperBound;

    }

    public ArrayList<Object> getObjects(){
        return this.objects ;
    }

    public void addObject( Object object ){
        this.objects.add(object) ;
    }

    public int getUpperBound(){
        return this.upperBound ;
    }

}
