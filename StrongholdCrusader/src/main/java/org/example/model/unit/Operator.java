package org.example.model.unit;

import org.example.model.Player;
import org.example.model.building.Building;

public class Operator extends Unit {

    private Building building ;

    public Operator( String name , Player owner , int row , int column ){
        super( name ,owner , 0 , 0 , row , column ) ;
    }

}
