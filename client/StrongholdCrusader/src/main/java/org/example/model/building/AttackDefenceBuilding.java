package org.example.model.building;

import org.example.model.BuildingEnum;
import org.example.model.Cost;
import org.example.model.Player;

public class AttackDefenceBuilding extends Building {

    private int defendRange , damage ;
    private boolean functional ;
    private int attackDamage ;

    public AttackDefenceBuilding( String name , int width , int height , boolean passable , String category ,
                                  Player owner , int row , int column , Cost cost , int hitPoint ,
                                  int popularityRate , boolean holdsAnimal , int defendRange , int damage ,
                                  boolean functional , int attackDamage , BuildingEnum buildingEnum ){
        super( name , width , height , passable , category , owner , row , column , cost , hitPoint, popularityRate
                , holdsAnimal , buildingEnum , 0 ) ;
        this.defendRange = defendRange ;
        this.damage = damage ;
        this.functional = functional ;
        this.attackDamage = attackDamage ;
    }

    public void attack(){
        // TODO : attack
    }

    public void getDamaged( int damage ){
        this.hitPoint -= damage ;
        // TODO : get damaged
    }

    public void canBeAttackedBy( Object object ){
        // TODO : can be attacked by object
    }



}
