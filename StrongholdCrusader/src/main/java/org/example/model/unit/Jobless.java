package org.example.model.unit;

import org.example.model.Player;

public class Jobless extends Unit {

    public Jobless( Player owner ,int hitPoint, int row , int column ){
        super( "Jobless" , owner ,hitPoint, 0 , 0 , row , column ,false ) ;
    }

    public void goToJoblessArea(){
        // TODO : go to jobless area
    }

}
