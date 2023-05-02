package org.example.model.building;

import org.example.model.BuildingEnum;
import org.example.model.Cost;
import org.example.model.unit.Unit ;
import org.example.model.Player ;

import javax.print.DocFlavor;
import java.util.ArrayList;

public class Building {
    private final String name ;
    private final String category ;
    private ArrayList <Unit> units ;
    private final Player owner ;
    private final int height ;
    private final int width ;
    private final Cost cost ;
    private final int row ;
    private BuildingEnum buildingEnum ;
    private final int column ;
    private int popularityRate ;
    private final boolean holdsAnimal ;
    private boolean passable ;
    protected int hitPoint ;

    Building( String name , int width , int height , boolean passable , String category , Player owner
            , int row , int column , Cost cost , int hitPoint , int popularityRate , boolean holdsAnimal ,
              BuildingEnum buildingEnum ){

        this.name = name ;
        this.category = category ;
        this.owner = owner ;
        this.height = height ;
        this.width = width ;
        this.cost = cost ;
        this.row = row ;
        this.column = column ;
        this.buildingEnum = buildingEnum ;
        this.holdsAnimal = holdsAnimal ;
        this.passable = passable ;
        this.hitPoint = hitPoint ;
        this.popularityRate = popularityRate ;
        owner.setPopularity( owner.getPopularity() + popularityRate );

    }

    public void setPassable( boolean passable ){
        this.passable = passable ;
    }

    public void addUnit( Unit unit ){
        this.units.add( unit ) ;
    }

    public void getDamaged( int damage ){
        this.hitPoint -= damage ;
    }

    public int getHitPoint(){
        return this.hitPoint ;
    }

    public boolean getPassable(){
        return this.passable ;
    }

    public boolean getHoldsAnimal(){
        return this.holdsAnimal ;
    }

    public int getColumn(){
        return this.column ;
    }

    public int getRow(){
        return this.row ;
    }

    public Cost getCost(){
        return this.cost ;
    }

    public int getHeight(){
        return this.height ;
    }

    public int getWidth(){
        return this.width ;
    }

    public Player getOwner(){
        return this.owner ;
    }

    public ArrayList<Unit> getUnits(){
        return this.units ;
    }

    public String getCategory(){
        return this.category ;
    }

    public String getName(){
        return this.name ;
    }

    public static Building createBuildingByName (String type , Player owner,int row , int column){
        // TREES

        // TODO : ADD TREES

        // OTHER
        if (type.equals("smallstonewall"))
            return new Building(type,1,1,false,"",owner,row,column,getBuildingCost(type),500,0,false,BuildingEnum.SMALL_STONE_WALL);
        if (type.equals("bigstonewall"))
            return new Building(type,2,2,false,"",owner,row,column,getBuildingCost(type),2000,0,false,BuildingEnum.BIG_STONE_WALL);
        if (type.equals("drawbridge"))
            return new Building(type,1,3,true,"",owner,row,column,getBuildingCost(type),200,0,false,BuildingEnum.DRAW_BRIDGE);
        if (type.equals("lookouttower"))
            return new AttackDefenceBuilding(type,3,3,false,"",owner,row,column,getBuildingCost(type),5000,0,false,70,20,false,20,BuildingEnum.LOOKOUT_TOWER);
        if (type.equals("perimetertower"))
            return new AttackDefenceBuilding(type,4,4,false,"",owner,row,column,getBuildingCost(type),4000,0,false,35,20,false,20,BuildingEnum.PERIMETER_TOWER);
        if (type.equals("squaretower"))
            return  new AttackDefenceBuilding(type,3,3,false,"",owner,row,column,getBuildingCost(type),4000,0,false,50,20,false,20,BuildingEnum.SQUARE_TOWER);
        if (type.equals("roundtower"))
            return  new AttackDefenceBuilding(type,3,3,false,"",owner,row,column,getBuildingCost(type),4000,0,false,50,20,false,20,BuildingEnum.ROUND_TOWER);
        if (type.equals("defenceturret"))
            return new AttackDefenceBuilding(type,3,3,false,"",owner,row,column,getBuildingCost(type),2000,0,false,50,20,false,20,BuildingEnum.DEFENCE_TURRET);
        if (type.equals("smallstonegateway"))
            return new AttackDefenceBuilding(type,2,2,true,"",owner,row,column,getBuildingCost(type),500,0,false,30,20,false,20,BuildingEnum.SMALL_STONE_GATEHOUSE);
        if (type.equals("bigstonegateway"))
            return new AttackDefenceBuilding(type,4,4,true,"",owner,row,column,getBuildingCost(type),1000,0,false,40,20,false,20,BuildingEnum.BIG_STONE_WALL);
        if (type.equals("armoury"))
            return  new StorageBuilding(type,4,5,false,"",owner,row,column,getBuildingCost(type),2000,0,false,200,BuildingEnum.ARMOURY);
        if (type.equals("barracks"))
            return new TradeBuilding(type,"",4,4,false,0,false,owner,row,column,2000,getBuildingCost(type),0,BuildingEnum.BARRACKS);
        if (type.equals("barracks"))
            return new TradeBuilding(type,"",4,4,false,0,false,owner,row,column,2000,getBuildingCost(type),0,BuildingEnum.MERCENARY_POST);
        if (type.equals("engineerguild"))
            return new TradeBuilding(type,"",2,3,false,0,false,owner,row,column,400,getBuildingCost(type),0,BuildingEnum.ENGINEER_GUILD);
        if (type.equals("killingpit"))
            return new TradeBuilding(type,"",1,1,true,0,false,owner,row,column,0,getBuildingCost(type),0,BuildingEnum.KILLING_PIT);
        if (type.equals("oilsmelter"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,200,getBuildingCost(type),1,BuildingEnum.OIL_SMELTER);//TODO : HOW MANY OPERATORS?
        if (type.equals("pitchditch"))
            return new TradeBuilding(type,"",1,1,true,0,false,owner,row,column,0,getBuildingCost(type),0,BuildingEnum.PITCH_DITCH);
        if (type.equals("cagedwardog"))
            return new TradeBuilding(type,"",2,2,false,0,true,owner,row,column,100,getBuildingCost(type),3,BuildingEnum.CAGED_WAR_DOGS);//TODO : HOW MANY OPERATOR?
        if (type.equals("siegetent"))
            return new TradeBuilding(type,"",5,5,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.SIEGE_TENT);
        if (type.equals("stable"))
            return new TradeBuilding(type,"",3,5,false,0,false,owner,row,column,200,getBuildingCost(type),0,BuildingEnum.STABLE);//TODO : HOW MANY OPERATORS?
        if (type.equals("tunnelerguild"))
            return new TradeBuilding(type,"",2,3,false,0,false,owner,row,column,0,getBuildingCost(type),0,BuildingEnum.TUNNELER_GUILD);//TODO : HOW MANY OPERATOS?
        if (type.equals("appleorchard"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.APPLE_ORCHARD);
        if (type.equals("diaryfarmer"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.DIARY_FARMER);
        if (type.equals("hopsfarmer"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.HOPS_FARMER);
        if (type.equals("hunterpost"))
            return new TradeBuilding(type,"",3,5,true,0,true,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.HUNTER_POST);//TODO: HOLD ANIMALS?
        if (type.equals("wheatfarmer"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.WHEAT_FARM);
        if (type.equals("bakery"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.BAKERY);
        if (type.equals("brewer"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.BREWER);
        if (type.equals("granary"))
            return new StorageBuilding(type,2,2,false,"",owner,row,column,getBuildingCost(type),500,0,false,800,BuildingEnum.GRANARY);
        if (type.equals("inn"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.INN);
        if (type.equals("mill"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,400,getBuildingCost(type),3,BuildingEnum.MILL);
        if (type.equals("ironmine"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.IRON_MINE);
        if (type.equals("market"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,400,getBuildingCost(type),1,BuildingEnum.MARKET);//TODO : HOW MANY OPERAYORS?
        if (type.equals("oxtether"))
            return new TradeBuilding(type,"",1,2,false,0,false,owner,row,column,100,getBuildingCost(type),0,BuildingEnum.OX_TETHER);//TODO
        if (type.equals("pitchrig"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,0,getBuildingCost(type),1,BuildingEnum.PITCH_RIG);//TODO
        if (type.equals("quarry"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),3,BuildingEnum.QUARRY);//TODO
        if (type.equals("stockpile"))
            return new StorageBuilding(type,3,3,false,"",owner,row,column,getBuildingCost(type),-1,0,false,1000,BuildingEnum.STOCKPILE);//TODO
        if (type.equals("woodcutter"))
            return new TradeBuilding(type,"",2,3,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.WOODCUTTER);//TODO
        if (type.equals("apothecary"))
            return new TradeBuilding(type,"",2,3,false,+1,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.APOTHECARY);//TODO
        if (type.equals("hovel"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,200,getBuildingCost(type),0,BuildingEnum.HOVEL);//TODO
        if (type.equals("chapel"))
            return new TradeBuilding(type,"",2,3,false,+1,false,owner,row,column,350,getBuildingCost(type),0,BuildingEnum.CHAPEL);//TODO
        if (type.equals("church"))
            return new TradeBuilding(type,"",2,4,false,+1,false,owner,row,column,400,getBuildingCost(type),0,BuildingEnum.CHURCH);//TODO
        if (type.equals("cathedral"))
            return new TradeBuilding(type,"",3,5,false,+1,false,owner,row,column,400,getBuildingCost(type),0,BuildingEnum.CATHEDRAL);//TODO
        if (type.equals("well"))
            return new TradeBuilding(type,"",1,1,false,0,false,owner,row,column,50,getBuildingCost(type),1,BuildingEnum.WELL);//TODO
        if (type.equals("waterpot"))
            return new TradeBuilding(type,"",1,2,false,0,false,owner,row,column,150,getBuildingCost(type),3,BuildingEnum.WATER_POT);//TODO
        if (type.equals("goodthings"))
            return new TradeBuilding(type,"",1,1,false,+2,false,owner,row,column,50,getBuildingCost(type),0,BuildingEnum.GOOD_THINGS);//TODO
        if (type.equals("badthings"))
            return new TradeBuilding(type,"",1,1,false,-2,false,owner,row,column,50,getBuildingCost(type),0,BuildingEnum.BAD_THINGS);//TODO
        if (type.equals("armourer"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.ARMOURER);//TODO
        if (type.equals("blacksmith"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.BLACKSMITH);//TODO
        if (type.equals("fletcher"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.FLETCHER);//TODO
        if (type.equals("poleturner"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1,BuildingEnum.POLE_TURNER);//TODO
        if (type.equals("signpost"))
            return new TradeBuilding(type,"",1,1,false,-3,false,owner,row,column,150,getBuildingCost(type),0,BuildingEnum.SIGNPOST);//TODO
        if (type.equals("tunnelentrance"))
            return new TradeBuilding(type,"",1,1,false,0,false,owner,row,column,100,getBuildingCost(type),0,BuildingEnum.TUNNEL_ENTRANCE);//TODO
        return null;
    }

    public static Cost getBuildingCost(String type){
        return null;
    }

}
