package org.example.model.unit;

import org.example.model.Player;

public class Tunneler extends Unit {

    private boolean underGround ;

    public Tunneler( Player owner ){
        super( "Tunneler" , owner , 0 , 0 ) ;
        this.underGround = false ;
    }

    public void dig(){
        // TODO : dig
    }

}
