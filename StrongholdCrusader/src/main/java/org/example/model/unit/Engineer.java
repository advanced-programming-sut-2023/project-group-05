package org.example.model.unit;

import org.example.model.Player;

public class Engineer extends Unit {

    public Engineer(Player owner,int hitPoint){
        super( "Engineer" , owner , hitPoint,0 , 0 ) ;
    }

    public void pourOil(){
        // TODO : pour oil
    }

    public void refillOil(){
        // TODO : refill oil
    }

}
