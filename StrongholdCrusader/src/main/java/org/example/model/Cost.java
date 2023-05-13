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

    public int gold ;
    public int apple ;
    public int cheese ;
    public int bread ;
    public int meat ;
    public int crossbow ;
    public int wood ;
    public int stone ;
    public int bow ;
    public int sword ;
    public int mace ;
    public int pitch ;
    public int pike ;
    public int spear ;
    public int armor ;
    public int hop ;
    public int leatherArmor ;
    public int wheat ;
    public int flour ;
    public int iron ;
    public ArrayList < Integer > resource;
    public int getLeatherArmor()
    {
        return leatherArmor;
    }

    public void changeLeatherArmor( int x )
    {
        this.leatherArmor = x;
    }

    public int getWheat()
    {
        return wheat;
    }

    public void changeWheat( int x )
    {
        wheat = x;
    }
    public int getFlour()
    {
        return flour;
    }

    public void changeFlour( int x )
    {
        this.flour = x;
    }

    public int getIron()
    {
        return iron;
    }

    public void changeIron(int x)
    {
        this.iron = x;
        this.resource.set(17, x);
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

}
