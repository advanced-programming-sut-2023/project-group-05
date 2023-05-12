package org.example.model.unit;

import org.example.model.GameMap;
import org.example.model.Player;
import org.example.model.building.Building;

public class Warrior extends Unit {
    private boolean isAttacking ;
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
    private Unit attackingUnit = null ;
    private Building attackingBuilding = null ;

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

    public void attackUnit( Unit unit , GameMap gameMap ){
        this.attackingUnit = unit ;
        this.attackingBuilding = null ;
        this.setTarget(unit.getRow() , unit.getColumn() , gameMap) ;
    }

    public void attackBuilding( Building building ){
        this.attackingBuilding = building ;
        this.attackingUnit = null ;
        // TODO : ATTACK BULIDING
    }

    public void startDiggingMoat( int row , int column , int rowChange , int columnChange ){
        if( !canDigMoat ) return ;
        // TODO : dig moat
    }

    public boolean getIsAttacking(){
        return this.isAttacking ;
    }

    public Building getAttackingBuilding(){
        return this.attackingBuilding ;
    }

    public Unit getAttackingUnit(){
        return this.attackingUnit ;
    }

}
