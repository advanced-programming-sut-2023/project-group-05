package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.Objects;

public class GameMap {
    public ArrayList<ArrayList<Cell>> map;

    public GameMap(int height , int width){
        //height = number of rows
        //width = number of columns
        map = new ArrayList<ArrayList<Cell>>(height);
        for (int row = 0; row <height ;row++){
            ArrayList<Cell> buffer = new ArrayList<Cell>(width);
            map.add(buffer);
        }
    }

    public void addBuilding(int row , int column , Building building){
        map.get(row).get(column).buildings.add(building);
        //add to objects?
    }

    public void addUnit(int row , int column , Unit unit){
        map.get(row).get(column).units.add(unit);
        //add to objects ?
    }

    public Cell getCell (int row , int column){
        return map.get(row).get(column);
    }
}
