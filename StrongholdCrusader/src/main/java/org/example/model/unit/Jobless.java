package org.example.model.unit;

import org.example.model.Player;

public class Jobless extends Unit {

    public Jobless( Player owner , int row , int column ){
        super( "Jobless" , owner , 0 , 0 , row , column ) ;
    }

    public void goToJoblessArea(){
        // TODO : go to jobless area
    }

}
