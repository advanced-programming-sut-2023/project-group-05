package org.example.model.unit;

import org.example.model.Player;

public class Warrior extends Unit {

    private final int attackPower ;
    private final int defendPower ;
    private final int reloadSpeed ;
    private final boolean hasFire ;
    private final boolean canPushLadder ;
    private final boolean hasHorse ;
    private final boolean canDigMoat ;
    private final boolean isHidden ;
    private final boolean canClimb ;
    private final boolean canDamageCastle ;

    public Warrior( String name , Player owner , int movingSpeed , int range , int attackPower , int defendPower ,
                    int reloadSpeed , boolean hasFire , boolean canPushLadder , boolean hasHorse , boolean canDigMoat ,
                    boolean isHidden , boolean canClimb , boolean canDamageCastle , int row , int column ){
        super( name , owner , movingSpeed , range , row , column ) ;
        this.attackPower = attackPower ;
        this.defendPower = defendPower ;
        this.reloadSpeed = reloadSpeed ;
        this.hasFire = hasFire ;
        this.canPushLadder = canPushLadder ;
        this.hasHorse = hasHorse ;
        this.canDigMoat = canDigMoat ;
        this.isHidden = isHidden ;
        this.canClimb = canClimb ;
        this.canDamageCastle = canDamageCastle ;
    }

    public void attack( int row , int column , int rowChange , int columnChange  ){
        // TODO : attack
    }

    public void startDiggingMoat( int row , int column , int rowChange , int columnChange ){
        if( !canDigMoat ) return ;
        // TODO : dig moat
    }



}
