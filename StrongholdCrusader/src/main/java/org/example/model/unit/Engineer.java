package org.example.model.unit;

import org.example.model.GameMap;
import org.example.model.Player;

public class Engineer extends Unit {

    private boolean hasOil ;
    private static int[][] dir = { { -1 , 0 } , { 0 , 1 } , { 1 , 0 } , { 0 , -1 } } ;

    public Engineer( Player owner ,int hitPoint, int row, int column ){
        super( "Engineer" , owner , hitPoint,0 , 0 , row , column ) ;
        this.hasOil = false ;
    }

    public boolean pourOil(int direction , GameMap gameMap){
        if(!hasOil) return false ;
        int row = this.getRow() ;
        int column = this.getColumn() ;
        for(int i = 0 ; i < 5 ; i++){
            row += dir[direction][0] ;
            column += dir[direction][1] ;
            if(!(row < 400 && column < 400 && column >= 0 && row >= 0)) break ;
            for(Unit unit : gameMap.getCell(row,column).getUnits() ){
                if(unit.getOwner()!=this.getOwner())
                    unit.getDamaged(50) ;
            }

        }
        return true ;
    }

    public void refillOil(){
        return ;
    }

}
