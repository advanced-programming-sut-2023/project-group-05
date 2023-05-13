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

    public static Cost manfi(Cost A)
    {
        Cost zero = new Cost(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        return ArrayListToCost(minusTwoCost(zero, A));
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
    public static ArrayList<Integer> getItemPrices()
    {
        return itemPrices ;
    }

    public static ArrayList<String> getItemNames()
    {
        return itemNames ;
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
    public int ale ;
    public int metalArmor ;
    public ArrayList < Integer > resource;


    public void setApple(int apple)
    {
        this.apple = apple;
        this.resource.set(0, apple);
    }

    public void setCheese(int cheese)
    {
        this.cheese = cheese;
        this.resource.set(1, cheese);
    }

    public void setBread(int bread)
    {
        this.bread = bread;
        this.resource.set(2, bread);
    }

    public void setMeat(int meat)
    {
        this.meat = meat;
        this.resource.set(3, meat);
    }

    public void setGold(int gold)
    {
        this.gold = gold;
        this.resource.set(4, gold);
    }

    public void setWood(int wood)
    {
        this.wood = wood;
        this.resource.set(5, wood);
    }

    public void setStone(int stone)
    {
        this.stone = stone;
        this.resource.set(6, stone);
    }

    public void setPike(int pike)
    {
        this.pike = pike;
        this.resource.set(7, pike);
    }

    public void setSpear(int spear)
    {
        this.spear = spear;
        this.resource.set(8, spear);
    }

    public void setBow(int bow)
    {
        this.bow = bow;
        this.resource.set(9, bow);
    }

    public void setSword(int sword)
    {
        this.sword = sword;
        this.resource.set(10, sword);
    }

    public void setMace(int mace)
    {
        this.mace = mace;
        this.resource.set(11, mace);
    }

    public void setCrossbow(int crossbow)
    {
        this.crossbow = crossbow;
        this.resource.set(12, crossbow);
    }

    public void setPitch(int pitch)
    {
        this.pitch = pitch;
        this.resource.set(13, pitch);
    }

    public void setArmor(int armor)
    {
        this.armor = armor;
        this.resource.set(14, armor);
    }

    public void setHop(int hop)
    {
        this.hop = hop;
        this.resource.set(15, hop);
    }

    public void setLeatherArmor(int leatherArmor)
    {
        this.leatherArmor = leatherArmor;
        this.resource.set(16, leatherArmor);
    }

    public void setMetalArmor(int metalArmor)
    {
        this.metalArmor = metalArmor;
        this.resource.set(17, metalArmor);
    }

    public void setAle(int ale)
    {
        this.ale = ale;
        this.resource.set(18, ale);
    }

    public void setFlour(int flour)
    {
        this.flour = flour;
        this.resource.set(19, flour);
    }

    public void setWheat(int wheat)
    {
        this.wheat = wheat;
        this.resource.set(20, wheat);
    }


    public void setIron(int iron)
    {
        this.iron = iron;
        this.resource.set(21, iron);
    }


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

    public int getGold()
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
    public Cost( int apple , int cheese , int bread , int meat , int gold , int wood , int stone , int pike , int spear ,
                 int bow , int sword , int mace , int crossbow , int pitch , int armor , int hop , int leatherArmor ,
                 int metalArmor , int ale , int flour , int wheat , int iron )
    {
        /// Ignore this fucking sort just focus on the input and the ArrayList
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
        this.resource = new ArrayList<>(Arrays.asList(apple, cheese, bread, meat, gold, wood, stone, pike, spear, bow, sword, mace, crossbow,
                pitch, armor, hop, leatherArmor, metalArmor, ale, flour, wheat, iron));
    }

    public static Cost ArrayListToCost(ArrayList<Integer> arr)
    {
        return new Cost(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), arr.get(5), arr.get(6), arr.get(7),
                arr.get(8), arr.get(9), arr.get(10), arr.get(11), arr.get(12), arr.get(13), arr.get(14), arr.get(15),
                arr.get(16), arr.get(17), arr.get(18), arr.get(19), arr.get(20), arr.get(21)) ;
    }

}
