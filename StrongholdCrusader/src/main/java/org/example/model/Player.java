package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;

public class Player {
    private Account account ;
    private ArrayList <Building> buildings ;
    private ArrayList<Unit> units ;
    private int fearRate ;
    private Building selectedBuilding ;
    private ArrayList<Unit> selectedUnits ;
    private int score ;
    private int taxRate ;
    private int foodRate ;
    private int religionRate ;
    private int pitchCapacity ;
    private int meatCapacity ;
    private int breadCapacity ;
    private int cheeseCapacity ;
    private int hop ;
    private int cheese ;
    private int bread ;
    private int meat ;
    private int pitch ;
    private int apple ;
    private int wineUsage ;
    private double gold ;
    private int stone ;
    private int wood ;
    private int iron ;
    private int population ;
    private int populationCapacity ;
    private int popularity ;
    private double taxForEachUnit ;
    private int sword ;
    private int spear ;
    private int wheat ;
    private int mace ;
    private int ale ;
    private int bow ;
    private int pike ;
    private int metalArmor ;
    private int flour ;
    private int crossbow ;
    private int leatherArmor ;

    public Player( Account account ){
        this.account = account ;
        this.mace = 0 ;
        this.spear = 0 ;
        this.pike = 0 ;
        this.bow = 0 ;
        this.metalArmor = 0 ;
        this.wheat = 0 ;
        this.ale = 0 ;
        this.flour = 0 ;
        this.crossbow = 0 ;
        this.fearRate = 0 ;
        this.taxRate = 0 ;
        this.foodRate = 0 ;
        this.religionRate = 0 ;
        this.pitchCapacity = 0 ;
        this.meatCapacity = 0 ;
        this.breadCapacity = 0 ;
        this.cheeseCapacity = 0 ;
        this.hop = 0 ;
        this.cheese = 0 ;
        this.bread = 0 ;
        this.sword = 0 ;
        this.meat = 0 ;
        this.pitch = 0 ;
        this.apple = 0 ;
        this.wineUsage = 0 ;
        this.gold = 0 ;
        this.stone = 0 ;
        this.leatherArmor = 0 ;
        this.wood = 0 ;
        this.iron = 0 ;
        this.population = 0 ;
        this.populationCapacity = 0 ;
        this.popularity = 0 ;
        this.score = 0 ;
    }

    public boolean decreaseCost( Cost cost ){
        boolean possible = ( this.apple >= cost.getApple() ) && ( this.meat >= cost.getMeat() ) && ( this.cheese >= cost.getCheese() )
                && ( this.bread >= cost.getBread() ) && ( this.bow >= cost.getBow() ) && ( this.crossbow >= cost.getCrossbow() )
                && ( this.spear >= cost.getSpear() ) && ( this.pike >= cost.getPike() ) && ( this.mace >= cost.getMace() )
                && ( this.sword >= cost.getSword() ) && ( this.leatherArmor >= cost.getLeatherArmor() )
                && ( this.metalArmor >= cost.getMetalArmor() ) && ( this.wheat >= cost.getWheat() ) && ( this.flour >= cost.getFlour() )
                && ( this.hop >= cost.getHop() ) && ( this.ale >= cost.getAle() ) && ( this.stone >= cost.getStone() )
                && ( this.iron >= cost.getIron() ) && ( this.wood >= cost.getWood() ) && ( this.pitch >= cost.getPitch() ) ;
        if(!possible) return false ;

        this.apple-=cost.getApple() ;
        this.meat-=cost.getMeat() ;
        this.cheese -= cost.getCheese() ;
        this.bread -= cost.getBread() ;
        this.bow -= cost.getBow() ;
        this.crossbow-= cost.getCrossbow() ;
        this.spear -= cost.getSpear() ;
        this.pike -= cost.getPike() ;
        this.mace -= cost.getMace() ;
        this.sword -= cost.getSword() ;
        this.leatherArmor -= cost.getLeatherArmor() ;
        this.hop -= cost.getHop() ;
        this.ale -= cost.getAle() ;
        this.stone -= cost.getStone() ;
        this.iron -= cost.getIron() ;
        this.wood -= cost.getWood() ;
        this.pitch -= cost.getPitch() ;

        return true ;
    }

    public void selectUnit( Unit unit ){
        this.selectedUnits.add( unit ) ;
    }

    public void selectBuilding( Building building ){
        this.selectedBuilding = building ;
        this.selectedBuilding = null;
        this.selectedUnits = new ArrayList<>();
    }

    public Account getAccount(){
        return this.account ;
    }

    public double getGold(){
        return this.gold ;
    }

    public double getTaxForEachUnit(){
        return this.taxForEachUnit ;
    }

    public void setTaxForEachUnit( double taxForEachOne ){
        this.taxForEachUnit = taxForEachOne ;
    }

    public int getMeat(){
        return this.meat ;
    }

    public int getCheese(){
        return this.cheese ;
    }

    public int getBread(){
        return this.bread ;
    }

    public int getApple(){
        return this.apple ;
    }

    public int getFearRate(){
        return this.fearRate ;
    }

    public void setFoodRate(int rate){
        this.foodRate = rate ;
    }

    public int getFoodRate(){
        return this.foodRate ;
    }
    public void setFearRate( int rate ){
        this.fearRate = rate ;
    }

    public int getFoodCount(){
        return this.apple + this.cheese + this.bread + this.meat ;
    }

    public void setPopularity( int popularity ){
        this.popularity = popularity ;
    }

    public int getPopularity(){
        return this.popularity ;
    }

    public void setTaxRate( int taxRate ){
        this.taxRate = taxRate ;
    }

    public int getTaxRate(){
        return this.taxRate ;
    }

    public int getReligionRate(){
        return this.religionRate ;
    }

    public void setPopulation(int population){
        this.population = population ;
    }

    public int getPopulation(){
        return this.population ;
    }

    public int getPopulationCapacity(){
        return this.populationCapacity ;
    }

    public void setPopulationCapacity(int populationCapacity){
        this.populationCapacity = populationCapacity ;
    }

    public long getScore(){
        return this.score;
    }

    public ArrayList<Unit> getSelectedUnits(){
        return this.selectedUnits;
    }

    public Building getSelectedBuilding(){
        return this.selectedBuilding;
    }

    public void setSelectedUnits(String unitType , Cell cell){
        for (Unit unit : cell.units){
            if (unit.getName().equals("type"))
                this.selectedUnits.add(unit);
        }
    }

    public void setSelectedBuilding (Building building){
        this.selectedBuilding = building;
    }

    public void setState (String state ){
        UnitModeEnum unitMode = UnitModeEnum.getUnitModeEnumByName(state);
        for (Unit unit : this.selectedUnits)
            unit.setUnitMode(unitMode);
    }
}
