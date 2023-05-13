package org.example.model.unit;

import org.example.controller.PathFinder;
import org.example.model.CellType;
import org.example.model.GameMap;
import org.example.model.Player;

public class Tunneler extends Unit {

    private boolean underGround ;

    public Tunneler( Player owner , int hitPoint,int row , int column ){
        super( "Tunneler" , owner , 0 ,0, 0 , row , column ) ;
        this.underGround = false ;
    }

    public void dig( GameMap gameMap){
        if(gameMap.getCell(this.row,this.column).getCellTypeName() == CellType.GROUND){
            this.underGround = true ;
        }
    }

    @Override
    public void setTarget(int row , int column , GameMap gameMap){
        if(this.row == row && this.column == column){
            this.isMoving = false ;
        }
        this.targetRow = row ;
        this.targetColumn = column ;
        this.pathFinder = new PathFinder() ;
        if(this.underGround) pathFinder.setGameMap( gameMap.getMaskedMapUnderGround() , 400 ) ;
        else pathFinder.setGameMap( gameMap.getMaskedMap() , 400 ) ;
        pathFinder.Run( row , column ) ;
        this.isMoving = true;
    }

}
