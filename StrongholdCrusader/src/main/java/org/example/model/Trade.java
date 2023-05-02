package org.example.model;

import java.util.ArrayList;

public class Trade {
    private static ArrayList<Trade> trades = new ArrayList <Trade>() ;
    private final Player player ;
    private final Cost cost ;
    private double price ;

    private String resourceType ;
    private int amount ;
    private String message ;

    public Trade( Player player , String message , Cost cost , double price , int amount ,String resourceType ){
        this.player = player ;
        this.message =message ;
        this.cost = cost ;
        this.resourceType = resourceType ;
        this.amount = amount ;
        trades.add(this) ;
        this.price = price ;
    }

    public boolean canHappen(){
        return player.getGold() >= this.price ;
    }

    public String getMessage(){
        return this.message ;
    }

    public int getAmount(){
        return this.amount ;
    }

    public Player getPlayer(){
        return this.player ;
    }

    public Cost getCost(){
        return this.cost ;
    }

    public double getPrice(){
        return this.price ;
    }

    public String getResourceType(){
        return this.resourceType ;
    }

    public static ArrayList<Trade> getTrades(){
        return trades ;
    }

}
