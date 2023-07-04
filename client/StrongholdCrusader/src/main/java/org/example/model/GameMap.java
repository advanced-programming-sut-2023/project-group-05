package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameMap {
    private int height ;
    private int width ;
    private Cell[][] map ;
    private int[][] maskedMap ; // zeros and ones showing whether if soldiers can go here
    private int[][] maskedMapUnderGround ; // same for underground
    private int[][] maskedMapUpperGround ; // same for above the buildings

    public GameMap(int height , int width){
        //height = number of rows
        //width = number of columns
        this.height = height ;
        this.width = width ;
        this.map = new Cell[height][width];
        this.maskedMap = new int[height][width] ;
        this.maskedMapUpperGround = new int[height][width] ;
        this.maskedMapUnderGround = new int[height][width] ;

        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width ; j++) {
                if( i < 10 || i > 389 || j < 10 || j > 389 ){
                    map[i][j] = new Cell( CellType.SEA , i , j ) ;
                    maskedMap[i][j] = 1 ;
                }
                else{
                    map[i][j] = new Cell( CellType.GROUND , i , j );
                    maskedMap[i][j] = 0 ;
                }
                maskedMapUnderGround[i][j] = 0 ;
                maskedMapUpperGround[i][j] = 1 ;
            }

        int grassWidth = 30 ;

        for(int i = 0 ; i < height ; i++){
            for(int j = height / 2 - grassWidth ; j < height / 2 + grassWidth ; j++){
                if( i < 10 || i > 389 || j < 10 || j > 389 ) continue ;
                map[i][j].setCellType( CellType.GRASS );
                map[j][i].setCellType( CellType.GRASS );
            }
        }


        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < height ; j++){
                if( (i - 200)*(i-200) + (j-200) * (j-200) <= 4900 ){
                    map[i][j].setCellType( CellType.SEA ) ;
                    maskedMap[i][j] = 1 ;
                }
            }
        }

    }

    public int getWidth(){
        return this.width ;
    }

    public int getHeight(){
        return this.height ;
    }

    private ArrayList<int[]> stairs ;
    private boolean[][] ok = new boolean[400][400] ;
    public ArrayList<int[]> getStairsFrom(int x , int y , boolean ground){
        for(int i = 0 ; i < 400 ; i++)
            for(int j = 0 ; j < 400 ; j++)
                ok[i][j] = false ;
        this.stairs = new ArrayList<int[]>() ;
        search(x,y,(ground ? maskedMap : maskedMapUpperGround)) ;
        return stairs ;
    }
    private int[][] adj = { {0,1} , {0,-1} , {1,0} , {-1,0} } ;
    private void search(int x , int y , int map[][]){
        if( x > 400 || x < 0 || y > 400 || y < 0 || ok[x][y] || map[x][y] == 1 ) return ;
        if(maskedMapUpperGround[x][y] == 0 && maskedMap[x][y] == 0) stairs.add( new int[]{ x , y } ) ;
        ok[x][y] = true ;
        for(int i = 0 ; i < 4 ; i++)
            search( x + adj[i][0] , y + adj[i][1] , map ) ;
    }

    public int[][] getMaskedMapUnderGround(){
        return this.maskedMapUnderGround ;
    }

    public int[][] getMaskedMap(){
        return this.maskedMap ;
    }

    public int[][] getMaskedMapUpperGround(){
        return this.maskedMapUpperGround ;
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
