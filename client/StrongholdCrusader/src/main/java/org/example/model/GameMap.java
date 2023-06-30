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
                if( i < 12 || i > 390 || j < 12 || j > 390 ) map[i][j] = new Cell( CellType.SEA , i , j ) ;
                else map[i][j] = new Cell( CellType.GROUND , i , j );
                maskedMap[i][j] = 0 ;
                maskedMapUnderGround[i][j] = 0 ;
                maskedMapUpperGround[i][j] = 1 ;
            }
        // creating a grass circle with radius 10 and center ( 30 , 30 )
        for(int i = 20 ; i <= 40 ; i++)
            for(int j = 20 ; j <= 40 ; j++)
                if( Math.pow((i-30),2) + Math.pow(j-30,2) <= 100 )
                    map[i][j].setCellType(CellType.GRASS) ;

        map[13][30].setCellType( CellType.SEA ) ;
        map[30][40].setCellType( CellType.SEA ) ;
        map[13][31].setCellType( CellType.SEA ) ;
        map[13][32].setCellType( CellType.SEA ) ;
        map[13][33].setCellType( CellType.SEA ) ;

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