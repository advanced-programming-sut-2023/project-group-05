package org.example.model;

public class Cost {

    private final double gold ;
    private int apple ;
    private int cheese ;
    private int bread ;
    private int meat ;
    private final int wood ;
    private final int stone ;
    private final int bow ;
    private final int sword ;
    private final int mace ;
    private final int crossbow ;
    private final int pitch ;
    private final int armor ;
    private final int hop ;

    Cost( double gold , int wood , int stone , int bow , int sword , int mace , int crossbow , int pitch , int armor , int hop ){

        this.gold = gold ;
        this.wood = wood ;
        this.stone = stone ;
        this.bow = bow ;
        this.sword = sword ;
        this.mace = mace ;
        this.crossbow = crossbow ;
        this.pitch = pitch ;
        this.armor = armor ;
        this.hop = hop ;

    }

    public double getGold(){
        return this.gold ;
    }

    public int getWood(){
        return this.wood ;
    }

    public int getStone(){
        return this.stone ;
    }

    public int getBow(){
        return this.bow ;
    }

    public int getSword(){
        return this.sword ;
    }

    public int getMace(){
        return this.mace ;
    }

    public int getCrossbow(){
        return this.crossbow ;
    }

    public int getPitch(){
        return this.pitch ;
    }

    public int getArmor(){
        return this.armor ;
    }

    public int getHop(){
        return this.hop ;
    }

}
