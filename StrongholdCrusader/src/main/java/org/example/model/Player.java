package org.example.model;

import org.example.model.building.Building;
import org.example.model.building.StorageBuilding;
import org.example.model.unit.Jobless;
import org.example.model.unit.Unit;

import java.sql.Array;
import java.util.ArrayList;

public class Player {
    private Account account ;
    private ArrayList <Building> buildings ;
    private ArrayList<Unit> units ;
    //TODO : Handle Castle
    private Building castle;
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
    public boolean isStockPileCreated;
    public boolean isGranaryCreated;
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
        this.selectedBuilding = null ;
        this.buildings = new ArrayList<>();
        this.units = new ArrayList<>();
        this.selectedUnits = new ArrayList<>() ;
    }

    public Unit getUnitByType(String type)
    {
        for(Unit now : this.units)
        {
            if(now.getName().equals(type))
            {
                return now;
            }
        }
        return null;
    }

    public void removeUnit(Unit inp)
    {
        if(this.units.contains(inp))
        {
            this.units.remove(inp);
        }
        if(this.selectedUnits.contains(inp))
        {
            this.selectedUnits.remove(inp);
        }
        //TODO remove this unit from the cell and the map, elsewhere it's removed already

    }

    public Building getCastle(){
        return this.castle;
    }

    public ArrayList<Building> getBuildings(){
        return this.buildings ;
    }

    public ArrayList<Unit> getUnits(){
        return this.units ;
    }

<<<<<<< HEAD
    public static int getPopularityEffectOfTaxRate(int taxRate){
        if (taxRate == -3)
            return 7;
        if (taxRate == -2)
            return 5;
        if (taxRate == -1)
            return 3;
        if (taxRate == 0)
            return 1;
        if (taxRate == 1)
            return -2;
        if (taxRate == 2)
            return -4;
        if (taxRate == 3)
            return -6;
        if (taxRate == 4)
            return -8;
        if (taxRate == 5)
            return -12;
        if (taxRate == 6)
            return -16;
        if (taxRate == 7)
            return -20;
        if (taxRate == 8)
            return -24;
        return 0;
    }

    public static double getMoneyOfTaxRate(int taxRate){
        if (taxRate == -3)
            return -1;
        if (taxRate == -2)
            return -0.8;
        if (taxRate == -1)
            return -0.6;
        if (taxRate == 0)
            return 0;
        if (taxRate == 1)
            return 0.6;
        if (taxRate == 2)
            return 0.8;
        if (taxRate == 3)
            return 1;
        if (taxRate == 4)
            return 1.2;
        if (taxRate == 5)
            return 1.4;
        if (taxRate == 6)
            return 1.6;
        if (taxRate == 7)
            return 1.8;
        if (taxRate == 8)
            return 2;
        return 0;
    }

    public void setGold(double gold){
        this.gold = gold;
    }

    public String decreaseCost( Cost cost ){
=======

    public String decreaseCost( Cost cost )
    {

>>>>>>> origin/main

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

    public void changePopulation(int number){
        this.population +=number;
    }

    public void setCastle(Building castle){
        this.castle = castle;
    }

    public void decreaseGold(int gold){
        this.gold -= gold ;
    }

    public void increaseGold(int gold){
        this.gold += gold ;
    }

    public void changeGold(double gold){
        this.gold +=gold;
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

    public double getGold()
    {
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
    public void setFoodCapacity(int number)
    {
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

    public void changePopularity( int popularity ){
        this.popularity = Math.max(this.popularity+popularity,0);
    }

    public void setPopularity(int popularity){
        this.popularity = popularity;
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

    public void setSelectedUnits(Cell cell)
    {
        for (Unit unit : cell.units)
        {
            if (unit.getOwner().equals(this))
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

        if (gameMap.getCell(row-1,column).getBuilding().getName().equals(name))
            return true;

        if (gameMap.getCell(row+1,column).getBuilding().getName().equals(name))
            return true;

        if (gameMap.getCell(row,column+1).getBuilding().getName().equals(name))
            return true;

        if (gameMap.getCell(row,column-1).getBuilding().getName().equals(name))
            return true;

        return false;
    }


    public void handleBuildingEffectsOnPlayer(String name){
        //TODO : COMPLETE THIS PART
        if (name.equals("granary"))
            this.setFoodCapacity(1);
        else if (name.equals("hovel"))
            this.populationCapacity+=8;
    }

    public int getFoodDiversity () {
        int diversity = 0;
        if (this.apple>0)
            diversity++;
        if (this.cheese>0)
            diversity++;
        if (this.meat > 0)
            diversity++;
        if (this.bread > 0)
            diversity++;
        return diversity;
    }

}
