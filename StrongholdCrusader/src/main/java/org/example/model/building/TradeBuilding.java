package org.example.model.building;

import org.example.model.BuildingEnum;
import org.example.model.Cost;
import org.example.model.Player;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Objects;

public class TradeBuilding extends Building {

    private final int requiredNumberOfOperators ;
    private boolean functional ;
    private int tradeType ;
    private int rate ;
    private int turnBuilt ;
    private int capacity ;
    private int objectsCount ;

    public TradeBuilding( String name , String category , int width , int height , boolean passable ,
                          int popularityRate , boolean holdsAnimal , Player owner , int row , int column , int hitPoint , Cost cost ,
                          int requiredNumberOfOperators , BuildingEnum buildingEnum ){
            super( name , height , width , passable , category , owner , row , column , cost , hitPoint ,
                    popularityRate , holdsAnimal , buildingEnum ) ;
        this.requiredNumberOfOperators = requiredNumberOfOperators ;
        tradeType = 0;

    }

    public boolean trade()
    {
        if(!this.functional)
        {
            return false;
        }
        Cost input = new new Cost(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ,0 0, 0, 0, 0, 0, 0, 0);

        Cost output = new Cost(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ,0 0, 0, 0, 0, 0, 0, 0);

        ArrayList < String > objectsInput;
        ArrayList < String > objectsOutput;
        if(super.getName().equals("barracks"))
        {
            //TODO in here you have to specificy change the
        }
        if(super.getName().equals("mercenarypost"))
        {

        }
        if(super.getName().equals("engineerguild"))
        {

        }
        if(super.getName().equals("killingpit"))
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

        }
        if(super.getName().equals("stable"))
        {

        }
        if(super.getName().equals("tunnelerguild"))
        {

        }
        if(super.getName().equals("appleorchard"))
        {

        }
        if(super.getName().equals("diaryfarmer"))
        {

        }
        if(super.getName().equals("hopsfarmer"))
        {

        }
        if(super.getName().equals("hunterpost"))
        {

        }
        if(super.getName().equals("wheatfarmer"))
        {

        }
        if(super.getName().equals("bakery"))
        {

        }
        if(super.getName().equals("brewer"))
        {

        }
        if(super.getName().equals("granary"))
        {

        }
        if(super.getName().equals("inn"))
        {

        }
        if(super.getName().equals("mill"))
        {

        }
        if(super.getName().equals("ironmine"))
        {

        }
        if(super.getName().equals("market"))
        {

        }
        if(super.getName().equals("oxtether"))
        {

        }
        if(super.getName().equals("pitchrig"))
        {

        }
        if(super.getName().equals("quarry"))
        {

        }
        if(super.getName().equals("stockpile"))
        {

        }
        if(super.getName().equals("woodcutter"))
        {

        }
        if(super.getName().equals("apothecary"))
        {

        }
        if(super.getName().equals("hovel"))
        {

        }
        if(super.getName().equals("church"))
        {

        }
        if(super.getName().equals("cathedral"))
        {

        }
        if(super.getName().equals("well"))
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

        }
        if(super.getName().equals("armourer"))
        {

        }
        if(super.getName().equals("blacksmith"))
        {

        }
        if(super.getName().equals("fletcher"))
        {

        }
        if(super.getName().equals("poleturner"))
        {

        }
        if(super.getName().equals("signpost"))
        {

        }
        if(super.getName().equals("tunnelentrance"))
        {

        }
        boolean is_OK = 1;

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
}
