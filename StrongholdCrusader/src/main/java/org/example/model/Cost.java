package org.example.model;

public class Cost {

    private final double gold ;
    private final int apple ;
    private final int cheese ;
    private final int bread ;
    private final int meat ;
    private final int crossbow ;
    private final int wood ;
    private final int stone ;
    private final int bow ;
    private final int sword ;
    private final int mace ;
    private final int pitch ;
    private final int pike ;
    private final int spear ;
    private final int armor ;
    private final int hop ;
    private int leatherArmor ;
    private int wheat ;
    private int flour ;
    private int iron ;

    public int getLeatherArmor(){
        return leatherArmor;
    }

    public int getWheat(){
        return wheat;
    }

    public int getFlour(){
        return flour;
    }

    public int getIron(){
        return iron;
    }

    private int ale ;

    private int metalArmor ;

    public Cost( int apple , int cheese , int bread , int meat , double gold , int wood , int stone , int pike , int spear ,
          int bow , int sword , int mace , int crossbow , int pitch , int armor , int hop , int leatherArmor ,
          int metalArmor , int ale , int flour , int wheat , int iron ){
        this.apple = apple ;
        this.cheese = cheese ;
        this.meat = meat ;
        this.bread = bread ;
        this.gold = gold ;
        this.wood = wood ;
        this.bow = bow ;
        this.sword = sword ;
        this.mace = mace ;
        this.crossbow = crossbow ;
        this.spear = spear ;
        this.pike = pike ;
        this.leatherArmor = leatherArmor ;
        this.ale = ale ;
        this.flour = flour ;
        this.wheat = wheat ;
        this.stone = stone ;
        this.iron = iron ;
        this.metalArmor = metalArmor ;
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

    public int getMeat(){
        return this.meat ;
    }

    public int getPike(){
        return this.pike ;
    }

    public int getSpear(){
        return this.spear ;
    }

    public int getBread(){
        return this.bread ;
    }

    public int getApple(){
        return this.apple ;
    }

    public int getCheese(){
        return this.cheese ;
    }

    public int getAle(){
        return this.ale ;
    }

    public int getMetalArmor(){
        return this.metalArmor ;
    }

}
