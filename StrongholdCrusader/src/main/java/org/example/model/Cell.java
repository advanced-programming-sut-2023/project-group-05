package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.Objects;

public class Cell {
    public ArrayList<Objects> objects;
    public ArrayList<Unit> units;
    public ArrayList<Building>buildings;
    public CellType cellType;
    public Cell(CellType cellType){
        this.cellType = cellType;
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
        this.objects = new ArrayList<>();
    }

    public CellType getCellTypeName(){
        return this.cellType;
    }

    public void setCellType(CellType cellType){
        this.cellType = cellType;
    }

    public boolean permeable (Unit unit){
        if (this.cellType.equals("boulder")||
            this.cellType.equals("river")||
            this.cellType.equals("smallPond")||
            this.cellType.equals("bigPond")||
            this.cellType.equals("sea"))
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
