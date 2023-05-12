package org.example.model;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class Cost {

    private static ArrayList<String> itemNames = new ArrayList<String>( Arrays.asList(
            "Apple" , "Cheese" , "Bread" , "Meat", "Ale" , "Wheat" , "flour" ,
            "Stone" , "Wood" , "Iron" , "Pitch" ,
            "Pike" , "Spear" , "Bow" , "Sword" , "Crossbow" , "Metal Armour" , "Leather Armour"
    ) );
    private static ArrayList<Integer> itemPrices = new ArrayList<Integer>( Arrays.asList(
            50 , 40 , 60 , 30 , 30 , 40 , 50 ,
            70 , 80 , 90 , 100 ,
            20 , 25 , 30 , 20 , 30 , 40 , 20
    ) ) ;

    public static ArrayList < Integer > addingTwoCost(Cost A, Cost B)
    {
        ArrayList < Integer > ret = new ArrayList< Integer >();
        for(int i = 0; i < A.resource.size(); i ++)
        {
            ret.add(A.resource.get(i) + B.resource.get(i));
        }
        return ret;
    }

    public static ArrayList < Integer > minusTwoCost(Cost A, Cost B)
    {
        ArrayList < Integer > ret = new ArrayList< Integer >();
        for(int i = 0; i < A.resource.size(); i ++)
        {
            ret.add(A.resource.get(i) - B.resource.get(i));
        }
        return ret;
    }

    public static boolean isEnough(Cost A, Cost B) /// A hamechi az B ro dare? 1, 0
    {
        ArrayList < Integer > cur = minusTwoCost(A, B);
        for (Integer integer : cur)
        {
            if (integer < 0) return false;
        }
        return true;
    }

    private final int gold ;
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
    public ArrayList < Integer > resource;
    public int getLeatherArmor()
    {
        return leatherArmor;
    }

    public int getWheat()
    {
        return wheat;
    }

    public int getFlour()
    {
        return flour;
    }

    public int getIron()
    {
        return iron;
    }

    private int ale ;

    private int metalArmor ;

    public Cost( int apple , int cheese , int bread , int meat , int gold , int wood , int stone , int pike , int spear ,
          int bow , int sword , int mace , int crossbow , int pitch , int armor , int hop , int leatherArmor ,
          int metalArmor , int ale , int flour , int wheat , int iron )
    {
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
        this.resource = new ArrayList<>(Arrays.asList(apple, cheese, meat, bread, gold, wood, bow, sword, mace, crossbow, spear, pike, leatherArmor,
                ale, flour, wheat, stone, iron, metalArmor, pitch, armor, hop));
    }

    public static ArrayList<Integer> getItemPrices()
    {
        return itemPrices ;
    }

    public static ArrayList<String> getItemNames()
    {
        return itemNames ;
    }

    public double getGold()
    {
        return this.gold ;
    }

    public int getWood()
    {
        return this.wood ;
    }

    public int getStone()
    {
        return this.stone ;
    }

    public int getBow(){
        return this.bow ;
    }

    public int getSword()
    {
        return this.sword ;
    }

    public int getMace()
    {
        return this.mace ;
    }

    public int getCrossbow()
    {
        return this.crossbow ;
    }

    public int getPitch(){
        return this.pitch ;
    }

    public int getArmor()
    {
        return this.armor ;
    }

    public int getHop()
    {
        return this.hop ;
    }

    public int getMeat()
    {
        return this.meat ;
    }

    public int getPike(){
        return this.pike ;
    }

    public int getSpear()
    {
        return this.spear ;
    }

    public int getBread()
    {
        return this.bread ;
    }

    public int getApple()
    {
        return this.apple ;
    }

    public int getCheese()
    {
        return this.cheese ;
    }

    public int getAle()
    {
        return this.ale ;
    }

    public int getMetalArmor()
    {
        return this.metalArmor ;
    }

    public static Cost ArrayListToCost(ArrayList<Integer> arr){
        return new Cost(arr.get(0),arr.get(1),arr.get(2),arr.get(3),arr.get(4),arr.get(5),arr.get(6),arr.get(7),
                arr.get(8),arr.get(9),arr.get(10),arr.get(11),arr.get(12),arr.get(13),arr.get(14),arr.get(15),
                arr.get(16),arr.get(17),arr.get(18),arr.get(19),arr.get(20),arr.get(21)) ;
    }

}
