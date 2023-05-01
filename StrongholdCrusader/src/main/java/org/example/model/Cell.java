package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.Objects;

public class Cell {
    public ArrayList<Objects> objects;
    public ArrayList<Unit> units;
    public Building building;
    public CellType cellType;
    public Cell(CellType cellType){
        this.cellType = cellType;
        this.building = null ;
        this.units = new ArrayList<Unit>() ;
        this.objects = new ArrayList<>(0);
    }

    public Building getBuilding(){
        return this.building;
    }

    public CellType getCellTypeName(){
        return this.cellType;
    }

    public void setCellType(CellType cellType){
        this.cellType = cellType;
    }

    public boolean permeable (Unit unit){
        if (this.cellType == CellType.BOULDER ||
            this.cellType == CellType.RIVER ||
            this.cellType == CellType.SMALL_POND ||
            this.cellType == CellType.BIG_POND ||
            this.cellType == CellType.SEA)
            return false;
        //handle special units through special places
        return true;
    }
    public int getUnitNumbers(Unit unit){
        int count = 0;
        for (Unit unit1 : units){
            if (unit1.getName().equals(unit.getName()))
                ++count;
        }
        return count;
    }
}
