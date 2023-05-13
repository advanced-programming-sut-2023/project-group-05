package org.example.model.unit;

import org.example.model.GameMap;
import org.example.model.Player;
import org.example.model.building.Building;

public class Engineer extends Unit {

    private Building building = null ;
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

    @Override
    public void setTarget( int row , int column , GameMap gameMap ){
        super.setTarget(row , column , gameMap) ;
        if(building.getRow() == this.getRow() && this.getColumn() == building.getColumn()){
            this.hasOil = true ;
        }
    }

    public boolean refillOil(GameMap gameMap){
        if(this.building == null && !this.findYourBuilding(gameMap)) return false ;
        setTarget(this.building.getRow() , this.building.getColumn() , gameMap) ;
        return true ;
    }

    public boolean findYourBuilding(GameMap gameMap){
        this.building = null ;
        outer :
        for(int i = 0 ; i < 400 ; i++)
            for(int j = 0 ; j < 400 ; j++){ // TODO  : what is the name of this place ?
                Building building1 = gameMap.getCell(i,j).getBuilding() ;
                if(building1.getName().equals("") && this.getOwner() == building1.getOwner()){
                    this.building = gameMap.getCell(i,j).getBuilding() ;
                    break outer ;
                }
            }
        return this.building != null ;
    }



}
