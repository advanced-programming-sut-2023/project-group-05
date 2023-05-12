package org.example.model;

import org.example.model.building.Building;
import org.example.model.building.StorageBuilding;
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
    private boolean isStockPileCreated;
    private boolean isGranaryCreated;
    public int hop ;
    public int cheese ;
    public int bread ;
    public int meat ;
    public int pitch ;
    public int apple ;
    public int wineUsage ;
    public double gold ;
    public int stone ;
    public int wood ;
    public int iron ;
    public int population ;
    public int populationCapacity ;
    public int popularity ;
    public double taxForEachUnit ;
    public int sword ;
    public int spear ;
    public int wheat ;
    public int mace ;
    public int ale ;
    public int bow ;
    public int pike ;
    public int metalArmour ;
    public int flour ;
    public int crossbow ;
    public int leatherArmour ;

    public Player( Account account ){
        this.account = account ;
        this.isGranaryCreated = false;
        this.isStockPileCreated = false;
        this.mace = 0 ;
        this.spear = 0 ;
        this.pike = 0 ;
        this.bow = 0 ;
        this.metalArmour = 0 ;
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
        this.leatherArmour = 0 ;
        this.wood = 0 ;
        this.iron = 0 ;
        this.population = 0 ;
        this.populationCapacity = 0 ;
        this.popularity = 0 ;
        this.score = 0 ;

        //TODO : IS IT RIGHT ?
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
    }

    public String decreaseCost( Cost cost ){

        if( this.apple < cost.getApple() ) return "NOT ENOUGH APPLE." ;
        if( this.meat < cost.getMeat() ) return "NOT ENOUGH MEAT." ;
        if( this.cheese < cost.getCheese() ) return "NOT ENOUGH CHEESE." ;
        if( this.bread < cost.getBread() ) return "NOT ENOUGH BREAD." ;
        if( this.bow < cost.getBow() ) return "NOT ENOUGH BOW." ;
        if( this.crossbow < cost.getCrossbow() ) return "NOT ENOUGH CROSSBOW." ;
        if( this.spear < cost.getSpear() ) return "NOT ENOUGH SPEAR." ;
        if( this.pike < cost.getPike() ) return "NOT ENOUGH PIKE." ;
        if( this.mace < cost.getMace() ) return "NOT ENOUGH MACE." ;
        if( this.sword < cost.getSword() ) return "NOT ENOUGH SWORD." ;
        if( this.leatherArmour < cost.getLeatherArmor() ) return "NOT ENOUGH LEATHER ARMOR." ;
        if( this.hop < cost.getHop() ) return "NOT ENOUGH HOP" ;
        if( this.metalArmour < cost.getMetalArmor() ) return "NOT ENOUGH ARMOR." ;
        if( this.wheat < cost.getWheat() ) return "NOT ENOUGH WHEAT." ;
        if( this.flour < cost.getFlour() ) return "NOT ENOUGH FLOUR." ;
        if( this.ale < cost.getAle() ) return "NOT ENOUGH ALE." ;
        if( this.stone < cost.getStone() ) return "NOT ENOUGH STONE." ;
        if( this.iron < cost.getIron() ) return "NOT ENOUGH IRON." ;
        if( this.wood < cost.getWood() ) return "NOT ENOUGH WOOD." ;
        if( this.pitch < cost.getPitch() ) return "NOT ENOUGH PITCH." ;

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
        this.leatherArmour -= cost.getLeatherArmor() ;
        this.hop -= cost.getHop() ;
        this.ale -= cost.getAle() ;
        this.stone -= cost.getStone() ;
        this.iron -= cost.getIron() ;
        this.wood -= cost.getWood() ;
        this.pitch -= cost.getPitch() ;

        return null ;
    }

    public void decreaseGold(int gold){
        this.gold -= gold ;
    }

    public void increaseGold(int gold){
        this.gold += gold ;
    }

    public void selectUnit( Unit unit ){
        this.selectedUnits.add( unit ) ;
    }

    public void addBuilding(Building building){
        this.buildings.add(building);
    }
    public void addUnit(Unit unit){
        this.units.add(unit);
    }

    public void selectBuilding( Building building ){
        this.selectedBuilding = building ;
        // WHAT ?!
        /*this.selectedBuilding = null;
        this.selectedUnits = new ArrayList<>();*/
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
    public void setFoodCapacity(int number){
        this.breadCapacity +=number* StorageBuilding.maxGranaryCapacityForEachFood;
        this.cheeseCapacity += number * StorageBuilding.maxGranaryCapacityForEachFood;
        this.meatCapacity += number*StorageBuilding.maxGranaryCapacityForEachFood;
        this.breadCapacity += number*StorageBuilding.maxGranaryCapacityForEachFood;
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
    public String canBuildingPlacedHere (String name,int row,int column ,GameMap gameMap){
        boolean isNearSimilarBuilding = false;
        if (name.equals("granary") && !isGranaryCreated)
            isGranaryCreated = true;
        else if (name.equals("stockpile") && !isStockPileCreated)
                isStockPileCreated = true;
        //TODO : IT IS ASSUMED THAT EACH GRANARY OR STOCKPILE IS 1*1
        else if (name.equals("granary"))
            return isSimilarBuildingNearMe(name,row,column,gameMap) ? null : "Granary Should Be Placed Near Another Granary";
        else if (name.equals("stockpile"))
            return isSimilarBuildingNearMe(name,row,column,gameMap) ? null : "Stockpile Should Be Placed Near Another Stockpile";
        return null;
    }

    public boolean isSimilarBuildingNearMe(String name ,int row , int column,GameMap gameMap){
        for (Building building : gameMap.getCell(row-1,column).buildings){
            if (building.getName().equals(name))
                return true;
        }
        for (Building building : gameMap.getCell(row+1,column).buildings){
            if (building.getName().equals(name))
                return true;
        }
        for (Building building : gameMap.getCell(row,column-1).buildings){
            if (building.getName().equals(name))
                return true;
        }
        for (Building building : gameMap.getCell(row,column+1).buildings){
            if (building.getName().equals(name))
                return true;
        }
        return false;
    }

    public void handleBuildingEffectsOnPlayer(String name){
        //TODO : COMPLETE THIS PART
        if (name.equals("granary"))
            this.setFoodCapacity(1);
        else if (name.equals("goodthings"))
            this.popularity+=1;
        else if (name.equals("badthings"))
            this.popularity-=1;
        else if (name.equals("hovel"))
            this.setPopulationCapacity(8);
    }
}
