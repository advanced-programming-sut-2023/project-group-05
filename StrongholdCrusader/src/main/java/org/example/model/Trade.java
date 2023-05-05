package org.example.model;

import java.util.ArrayList;

public class Trade {
    private static ArrayList<Trade> trades = new ArrayList <Trade>() ;
    private final Player player1 ;
    private Player player2 ;
    private final Cost cost ;
    private double price ;

    private String resourceType ;
    private int amount ;
    private String message1 ;
    private String message2 ;

    public Trade( Player player1 , String message1 , Cost cost , double price , int amount ,String resourceType ){
        this.player1 = player1 ;
        this.message1 =message1 ;
        this.cost = cost ;
        this.message2 = null ;
        this.resourceType = resourceType ;
        this.amount = amount ;
        trades.add(this) ;
        this.price = price ;
    }

    public boolean canHappen(){
        return player1.getGold() >= this.price ;
    }

    public String getMessage1(){
        return this.message1 ;
    }
    public String getMessage2(){
        return this.message2 ;
    }

    public String setMessage2(String message){
        return this.message2 ;
    }

    public int getAmount(){
        return this.amount ;
    }

    public Player getPlayer1(){
        return this.player1 ;
    }

    public void setPlayer2( Player player ){
        this.player2 = player2 ;
    }

    public Player getPlayer2(){
        return this.player2 ;
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
