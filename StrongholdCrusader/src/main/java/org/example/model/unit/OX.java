package org.example.model.unit;

import org.example.model.Player;

public class OX extends Unit {
    private boolean isReserved ;
    private int stoneAmount ;
    public OX(String name , Player owner , int hitPoint, int movingSpeed , int range , int row , int column ){
        super(name,owner,hitPoint,movingSpeed,range,row,column);
        this.isReserved = false;
        this.stoneAmount = 0 ;
    }

    public boolean getIsReserved(){
        return this.isReserved;
    }

    public void setIsReserved (boolean reservation){
        this.isReserved = reservation;
    }

    public void setStoneAmount(int amount){
        this.stoneAmount = amount;
    }

}
