package org.example.model.unit;

import org.example.model.Player;

public class LadderMan extends Unit {

    public LadderMan( Player owner , int hitPoint,int row , int column){
        super( "LadderMan" , owner , hitPoint,0 , 0 , row , column ) ;
    }

    public void putLadder(){
        // TODO : put ladder
    }

}
