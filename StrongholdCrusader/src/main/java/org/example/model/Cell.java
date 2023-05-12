package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Cell {
    public ArrayList<Objects> objects;
    public ArrayList<Unit> units;
    public ArrayList<Building> buildings;
    public CellType cellType;
    private static HashMap<String , CellType> cellTypeEnumByName = new HashMap<>();
    public Cell(CellType cellType){
        this.cellType = cellType;
        this.buildings = new ArrayList<>(0);
        this.units = new ArrayList<>(0);
        this.objects = new ArrayList<>(0);
    }

    public ArrayList<Building> getBuildings(){
        return this.buildings ;
    }

    public CellType getCellTypeName(){
        return this.cellType;
    }

    public void setCellType(CellType cellType){
        this.cellType = cellType;
    }

    public boolean permeable (UnitTypeEnum unitType){
        if (this.cellType == CellType.BOULDER ||
            this.cellType == CellType.RIVER ||
            this.cellType == CellType.SMALL_POND ||
            this.cellType == CellType.BIG_POND ||
            this.cellType == CellType.SEA)
            return false;
        //TODO : handle special units through special places
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

    public ArrayList<Unit> getUnits(){
        return this.units ;
    }

    public void addUnit(Unit unit){
        units.add(unit);
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    public static HashMap<String, CellType> getCellTypeEnumByName (){
        return cellTypeEnumByName;
    }


}
