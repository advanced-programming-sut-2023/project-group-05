package org.example.model.unit;

import org.example.controller.PathFinder;
import org.example.model.GameMap;
import org.example.model.Player;
import org.example.model.UnitModeEnum;
import org.example.model.building.Building;

import java.util.ArrayList;

public class Warrior extends Unit {
    private boolean isAttacking ;
    private final int attackPower ;
    private int patrolTurn ;
    private int stairRow ;
    private int stairColumn ;
    private int patrolEndRow ;
    private int patrolEndColumn ;
    private int patrolBeginColumn ;
    private int patrolBeginRow ;
    private boolean isPatrolling ;
    private final int defendPower ;
    private final int reloadSpeed ;

    private UnitModeEnum unitMode ;
    private int damage ;
    private final boolean hasFire ;
    private final boolean canPushLadder ;
    private final boolean hasHorse ;
    private final boolean canDigMoat ;
    private final boolean isHidden ;
    private final boolean canClimb ;
    private final boolean canDamageCastle ;
    private Unit attackingUnit = null ;
    private Building attackingBuilding = null ;

    public Warrior( String name , Player owner , int damage , int hitPoint ,int movingSpeed , int range , int attackPower , int defendPower ,
                    int reloadSpeed , boolean hasFire , boolean canPushLadder , boolean hasHorse , boolean canDigMoat ,
                    boolean isHidden , boolean canClimb , boolean canDamageCastle , int row , int column ){
        super( name , owner , hitPoint,movingSpeed , range , row , column , true ) ;
        this.attackPower = attackPower ;
        this.defendPower = defendPower ;
        this.reloadSpeed = reloadSpeed ;
        this.isPatrolling = false ;
        this.isMoving = false ;
        this.hasFire = hasFire ;
        this.canPushLadder = canPushLadder ;
        this.hasHorse = hasHorse ;
        this.canDigMoat = canDigMoat ;
        this.isHidden = isHidden ;
        this.canClimb = canClimb ;
        this.damage = damage ;
        this.unitMode = UnitModeEnum.STANDING ;
        this.canDamageCastle = canDamageCastle ;
        this.isAttacking = false ;
        this.isPatrolling = false ;
    }

    public void setUnitMode(UnitModeEnum unitMode){
        this.unitMode = unitMode ;
    }

    // THE STAIR YOU PASS THROUGH GOING TO SOMEWHERE
    private int middleStairRow ;
    private int middleStairColumn ;

    @Override
    public void setTarget( int row , int column , GameMap gameMap ){

        if(this.row == row && this.column == column && !this.isPatrolling){
            this.isMoving = false ;
            return ;
        }

        if( this.isPatrolling && (this.row == row) && (column == this.column) ){
            this.patrolTurn++ ;
            if(patrolTurn % 2 == 0){
                column = this.patrolEndColumn ;
                row = this.patrolEndRow ;
            }
            else{
                column = this.patrolBeginColumn ;
                row = this.patrolBeginRow ;
            }
        }
        ArrayList <int[]> stairs ;

        /*if(needStair){
            PathFinder pf = new PathFinder() ;
            stairs = gameMap.getStairsFrom( this.row , this.column , !this.isOnHighGround ) ;
            for( int[] stair : stairs ){
                pf.Run( stair[0] , stair[1] ) ;
                if(pf.goInDirectionFrom( this.row , this.column ) != -1){
                    row = stair[0] ;
                    column = stair[1] ;
                    break ;
                }
            }
        }*/

        super.targetRow = row ;
        super.targetColumn = column ;
        super.pathFinder.setGameMap( gameMap.getMaskedMap(), gameMap.getMaskedMapUpperGround(), 400 ) ;
        super.pathFinder.Run( row , column ) ;
        super.isMoving = true;

    }

    public void startPatrol(int beginRow , int beginColumn , int endRow , int endColumn , GameMap gameMap){
        // turn 0 : go to end
        // turn 1 : go to begin
        // turn 2 : go to end
        // ...
        this.patrolBeginColumn = beginColumn ;
        this.patrolBeginRow = beginRow ;
        this.patrolEndRow = endRow ;
        this.patrolEndColumn = endColumn ;
        this.patrolTurn = 0 ;
        setTarget( endRow , endColumn , gameMap ) ;
        this.setIsMoving(true) ;
        this.isPatrolling = true ;
    }

    public boolean getIsPatrolling(){
        return this.isPatrolling ;
    }

    public UnitModeEnum getUnitMode(){
        return this.unitMode ;
    }

    public void stopPatrol(){
        this.isPatrolling = false ;
        this.patrolTurn = 0 ;
        this.setIsMoving(false) ;
    }

    public int getDamage(){
        return this.damage ;
    }

    public int getEnemyRow(){
        if(attackingUnit != null){
            return attackingUnit.getRow() ;
        }
        if(attackingBuilding != null){
            return attackingBuilding.getRow() ;
        }
        return 0 ;
    }

    public int getEnemyColumn(){
        if(attackingUnit != null){
            return attackingUnit.getColumn() ;
        }
        if(attackingBuilding != null){
            return attackingBuilding.getColumn() ;
        }
        return 0 ;
    }

    public void attackUnit( Unit unit , GameMap gameMap ){
        this.attackingUnit = unit ;
        this.attackingBuilding = null ;
        this.isAttacking = true ;
        int dr = unit.getRow() - this.getRow() ;
        int dc = unit.getColumn() - this.getColumn() ;
        if( dr * dr + dc * dc > this.getRange() * this.getRange() )
            this.setTarget(unit.getRow() , unit.getColumn() , gameMap) ;
    }

    public void attackBuilding( Building building , GameMap gameMap ){
        this.attackingBuilding = building ;
        this.attackingUnit = null ;
        int dr = building.getRow() - this.getRow() ;
        int dc = building.getColumn() - this.getColumn() ;
        if( dr * dr + dc * dc > this.getRange() * this.getRange() )
            this.setTarget(building.getRow() , building.getColumn() , gameMap) ;
    }

    public void stopAttacking(){
        this.attackingUnit = null ;
        this.attackingBuilding = null ;
        this.isAttacking = false ;
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
