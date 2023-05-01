package org.example.model;

import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;

public class Player {
    // TODO : some functions and constructor
    private Account account ;
    private ArrayList <Building> buildings ;
    private ArrayList<Unit> units ;
    private int fearRate ;
    private int score ;
    private int taxRate ;
    private int foodRate ;
    private int religionRate ;
    // TODO : private Account account ;
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

    public Player( Account account ){
        this.account = account ;
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
        this.meat = 0 ;
        this.pitch = 0 ;
        this.apple = 0 ;
        this.wineUsage = 0 ;
        this.gold = 0 ;
        this.stone = 0 ;
        this.wood = 0 ;
        this.iron = 0 ;
        this.population = 0 ;
        this.populationCapacity = 0 ;
        this.popularity = 0 ;
        this.score = 0 ;
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
}
