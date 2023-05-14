package org.example.model.building;

import org.example.model.BuildingEnum;
import org.example.model.Cost;
import org.example.model.Player;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.Arrays;

import static org.example.model.Cost.negative;

public class TradeBuilding extends Building {

    private final int requiredNumberOfOperators ;
    private boolean functional ;
    private int tradeType ;
    private int rate ;
    private int turnBuilt ;
    private int capacity ;
    private int objectsCount ;
    private int numberOfTurnsToGenerateProduct;

    private int numberOfTurnsToGenerateProduct;

    public TradeBuilding( String name , String category , int width , int height , boolean passable ,
                          int popularityRate , boolean holdsAnimal , Player owner , int row , int column , int hitPoint , Cost cost ,
                          int requiredNumberOfOperators ,int rate, BuildingEnum buildingEnum )
    {
            super( name , height , width , passable , category , owner , row , column , cost , hitPoint ,
                    popularityRate , holdsAnimal , buildingEnum , 0 ) ;
        this.requiredNumberOfOperators = requiredNumberOfOperators ;
<<<<<<< HEAD
        this.numberOfTurnsToGenerateProduct = 1;
        this.rate = 1;
=======
        tradeType = 0;
        this.rate = rate;
        this.numberOfTurnsToGenerateProduct = rate;

>>>>>>> origin/main
    }

    public boolean trade()
    {
        if(!this.functional)
        {
            return false;
        }
        Cost input = new Cost(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        Cost output = new Cost(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        ArrayList < String > objectsInput = new ArrayList< String >();
        ArrayList < String > objectsOutput = new ArrayList< String >();
        ArrayList < String > Sarr;
        ArrayList < Integer > Iarr;
        if(super.getName().equals("barracks"))
        {
            //TODO in here you have to specificy change the
            objectsInput.add("jobless");
            Sarr = new ArrayList< String >(Arrays.asList("archer", "crossbowman", "spearman", "pikeman",
                    "maceman", "swordsman", "knight"));
            if(this.tradeType == 0) /// jobless to archer?
            {
                input.setBow(1);
            }
            if(this.tradeType == 1)
            {
                input.setCrossbow(1);
                input.setLeatherArmor(1);
            }
            if(this.tradeType == 2)
            {
                input.setSpear(1);
            }
            if(this.tradeType == 3)
            {
                input.setPike(1);
                input.setMetalArmor(1);
            }
            if(this.tradeType == 4)
            {
                input.setMace(1);
                input.setMetalArmor(1);
            }
            if(this.tradeType == 5)
            {
                input.setSword(1);
                input.setMetalArmor(1);
            }
            if(this.tradeType == 6)
            {
                input.setSword(1);
                input.setMetalArmor(1);
                input.setHorse(1);
            }
            objectsOutput.add(Sarr.get(tradeType));
        }
        if(super.getName().equals("mercenarypost"))
        {
            objectsInput.add("jobless");
            Iarr = new ArrayList< Integer >(Arrays.asList(75, 5, 12, 60, 80, 80, 100));
            Sarr = new ArrayList< String >(Arrays.asList("archerbow", "slave", "slinger",
                    "assasin", "horsearcher", "arabianswordsman", "firethrower"));
            input.setGold(Iarr.get(tradeType));
            objectsOutput.add(Sarr.get(tradeType));
        }
        if(super.getName().equals("engineerguild"))
        {
            objectsInput.add("jobless");
            Iarr = new ArrayList< Integer >(Arrays.asList(30, 4));
            Sarr = new ArrayList< String >(Arrays.asList("engineer", "ladderman"));
            input.setGold(Iarr.get(tradeType));
            objectsOutput.add(Sarr.get(tradeType));
        }
        /*if(super.getName().equals("killingpit"))
        {

        }
        if(super.getName().equals("pitchditch"))
        {

        }
        if(super.getName().equals("cagedwardog"))
        {

        }
        if(super.getName().equals("siegetent"))
        {

        }*/
        if(super.getName().equals("stable"))
        {
            output.setHorse(1);
        }
        if(super.getName().equals("tunnelerguild"))
        {
            objectsInput.add("jobless");
            input.setGold(30);
            objectsOutput.add("tunneler");
        }
        if(super.getName().equals("appleorchard"))
        {
            output.setApple(1);
        }
        if(super.getName().equals("diaryfarmer"))
        {
            output.setCheese(1);
        }
        if(super.getName().equals("hopsfarmer"))
        {
            output.setHop(1);
        }
        /*if(super.getName().equals("hunterpost"))
        {

        }*/
        if(super.getName().equals("wheatfarmer"))
        {
            output.setWheat(3);
        }
        if(super.getName().equals("bakery"))
        {
            input.setFlour(1);
            output.setBread(1);
        }
        if(super.getName().equals("brewer"))
        {
            input.setHop(1);
            output.setAle(1);
        }
        /*if(super.getName().equals("granary"))
        {

        }*/
        if(super.getName().equals("inn"))
        {
            input.setHop(1);
            output.setAle(1);
        }
        if(super.getName().equals("mill"))
        {
            input.setWheat(1);
            output.setFlour(1);
        }
        if(super.getName().equals("ironmine"))
        {
            output.setIron(1);
        }
        /*if(super.getName().equals("market"))
        {

        }*/
        if(super.getName().equals("pitchrig"))
        {
            output.setPitch(1);
        }
        if(super.getName().equals("quarry"))
        {
            output.setStone(1);
        }
        /*if(super.getName().equals("stockpile"))
        {

        }*/
        if(super.getName().equals("woodcutter"))
        {
            output.setWood(3);
        }
        /*if(super.getName().equals("apothecary"))
        {

        }
        if(super.getName().equals("hovel"))
        {

        }
        if(super.getName().equals("church"))
        {

        }*/
        if(super.getName().equals("cathedral"))
        {
            objectsInput.add("jobless");
            objectsOutput.add("blackmonk");
        }
        /*if(super.getName().equals("well"))
        {

        }
        if(super.getName().equals("waterpot"))
        {

        }
        if(super.getName().equals("goodthings"))
        {

        }
        if(super.getName().equals("badthings"))
        {

        }*/
        if(super.getName().equals("armourer"))
        {
            input.setIron(3);
            output.setMetalArmor(1);
        }
        if(super.getName().equals("blacksmith"))
        {
            input.setIron(2);
            if(tradeType == 0)
            {
                output.setMace(1);
            }
            if(tradeType == 1)
            {
                output.setSword(1);
            }
        }
        if(super.getName().equals("fletcher"))
        {
            input.setWood(2);
            if(tradeType == 0)
            {
                output.setCrossbow(1);
            }
            if(tradeType == 1)
            {
                output.setBow(1);
            }
        }
        if(super.getName().equals("poleturner"))
        {
            input.setWood(2);
            if(tradeType == 0)
            {
                output.setSpear(1);
            }
            if(tradeType == 1)
            {
                output.setPike(1);
            }
        }
        /*if(super.getName().equals("signpost"))
        {

        }
        if(super.getName().equals("tunnelentrance"))
        {

        }*/
        Player owner = super.getOwner();
        boolean validTrade = true;
        for(String cur : objectsInput)
        {
            if(owner.getUnitByType(cur) == null)
            {
                validTrade = false;
            }
        }
        if(!validTrade)
        {
            return false;
        }
        String ret = owner.decreaseCost(input);
        if(ret != null)  { return false; }
        for(String cur : objectsInput)
        {
            Unit current = owner.getUnitByType(cur);
            owner.removeUnit(current);
        }
        String assertString = owner.decreaseCost(negative(output));
        if(assertString != null)
        {
            System.out.println("Something is fuckedUp HEAVY!!");
            System.out.println(assertString);
            return false;
        }
        for(String cur : objectsOutput)
        {
            Unit.createUnitByName(cur, owner, super.getRow(), super.getColumn());
        }
        return true;
    }

    public void changeTradeType(int x)
    {
        this.tradeType = x;
    }

    public int getTradeType()
    {
        return this.tradeType;
    }

    public void updateFunctionality()
    {
        this.functional = ( requiredNumberOfOperators <= objectsCount ) ;
    }

    public boolean getFunctional()
    {
        return this.functional ;
    }

    public int getRate()
    {
        return this.rate ;
    }

    public int getTurnBuilt()
    {
        return this.turnBuilt ;
    }

    public int getCapacity()
    {
        return this.capacity ;
    }

    public int getObjectsCount()
    {
        return this.objectsCount ;
    }

    public int getRequiredNumberOfOperators()
    {
        return this.requiredNumberOfOperators ;
    }
<<<<<<< HEAD
    public int getNumberOfTurnsToGenerateProduct(){
        return this.numberOfTurnsToGenerateProduct;
    }
    public void setNumberOfTurnsToGenerateProduct(){
        this.numberOfTurnsToGenerateProduct = this.rate;
    }
=======

    public int getNumberOfTurnsToGenerateProduct()
    {
        return this.numberOfTurnsToGenerateProduct;
    }

>>>>>>> origin/main
}
