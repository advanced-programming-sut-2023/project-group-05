package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.Objects;

public class GameMap {
    private Cell[][] map ;
    private int[][] maskedMap ;

    public GameMap(int height , int width){
        //height = number of rows
        //width = number of columns
        this.map = new Cell[height][width] ;
        this.maskedMap = new int[height][width] ;
        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width ; j++) {
                map[i][j] = new Cell( CellType.GROUND );
                maskedMap[i][j] = 0 ;
            }
    }

    public int[][] getMaskedMap(){
        return this.maskedMap ;
    }

    public boolean canGo(int row , int column){
        return true ;
    }

    public void setMask(int row , int column , int mask){
        maskedMap[row][column] = mask ;
    }

    /*public void addBuilding(int row , int column , Building building){
        this.map.get(row).get(column).buildings.add(building);
        //add to objects?
    }*/

    /*public void addUnit(int row , int column , Unit unit){
        this.map.get(row).get(column).units.add(unit);
        //add to objects ?
    }*/

    public Cell getCell (int row , int column){
        return this.map[row][column];
    }
}
