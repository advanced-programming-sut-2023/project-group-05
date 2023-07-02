package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Trade {
    private static ArrayList<Trade> trades = new ArrayList <Trade>() ;
    private final Player sender ;
    private HashMap<Integer,Integer> letter ;

    private Player receiver ;
    private int price ;

    private boolean open;

    public Player getSender() {
        return sender;
    }

    private String resourceType ;

    public Player getReceiver() {
        return receiver;
    }

    public boolean isApproved() {
        return approved;
    }

    private int amount ;
    private boolean denied;

    public void setDenied(boolean denied) {
        this.denied = denied;
    }

    public boolean isDenied() {
        return denied;
    }

    public HashMap<Integer, Integer> getLetter() {
        return letter;
    }

    private String message1 ;
    private String message2 ;

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    private boolean approved = false;

    public Trade( Player sender ,Player receiver, String message1 , HashMap<Integer,Integer> letter  ){
        this.sender = sender ;
        this.receiver = receiver;
        this.message1 =message1 ;
        this.letter = letter;
        this.message2 = null ;
        this.amount = amount ;
        this.open = true ;
        trades.add(this) ;
        for (int a : letter.keySet()) {
            price += Player.commodityPrice.get(a) * letter.get(a);
        }
    }
    public boolean getOpen(){
        return this.open ;
    }
    public void setOpen( boolean open ){
        this.open = open;
    }
    public boolean canHappen(){
        return receiver.getGold() >= this.price ;
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

    public int getPrice(){
        return this.price ;
    }

    public String getResourceType(){
        return this.resourceType ;
    }

    public static ArrayList<Trade> getTrades(){
        return trades ;
    }

}
