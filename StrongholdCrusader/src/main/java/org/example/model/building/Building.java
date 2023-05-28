package org.example.model.building;

import org.example.model.BuildingEnum;
import org.example.model.Cost;
import org.example.model.GameMap;
import org.example.model.unit.Unit ;
import org.example.model.Player ;

import java.util.ArrayList;
import java.util.HashMap;

public class Building {
    private final String name ;
    private final String category ;
    private ArrayList <Unit> units ;
    private static ArrayList<Building> buildings = new ArrayList<Building>() ;
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
    private static HashMap<String , BuildingEnum> buildingEnumByName = new HashMap<>();
    private static HashMap<String , Integer> buildingHitPointByName = new HashMap<>();

    private int fearRate ;

    public Building( String name , int width , int height , boolean passable , String category , Player owner
            , int row , int column , Cost cost , int hitPoint , int popularityRate , boolean holdsAnimal ,
              BuildingEnum buildingEnum , int fearRate ){

        this.name = name ;
        this.category = category ;
        this.owner = owner ;
        this.height = height ;
        this.fearRate = fearRate ;
        this.width = width ;
        this.cost = cost ;
        this.row = row ;
        this.column = column ;
        this.buildingEnum = buildingEnum ;
        this.holdsAnimal = holdsAnimal ;
        this.passable = passable ;
        this.hitPoint = hitPoint ;
        this.popularityRate = popularityRate ;
        this.buildingEnum = buildingEnum;
        owner.setPopularity( owner.getPopularity() + popularityRate );
        buildings.add(this) ;
        owner.getBuildings().add(this) ;
    }

    public static ArrayList<Building> getBuildings(){
        return buildings ;
    }

    public static HashMap<String, BuildingEnum> getBuildingEnumByName(){
        return buildingEnumByName;
    }
    public void setPassable( boolean passable , GameMap gameMap ){
        this.passable = passable ;
        for(int i = 0 ; i < this.height ; i++) for(int j = 0 ; j < this.width ; j++)
            gameMap.getMaskedMap()[this.row+i][this.column+j] = ( passable ? 1 : 0 ) ;
    }

    public void addUnit( Unit unit ){
        this.units.add( unit ) ;
    }

    public void collapse( GameMap gameMap ){
        // removing this building from everywhere
        this.owner.getBuildings().remove(this) ;
        if( this == this.owner.getSelectedBuilding() ){
            this.owner.setSelectedBuilding(null) ;
        }
        for(int i = this.row ; i < this.row + this.height ; i++)
            for(int j = this.column ; j < this.column + this.width ; j++ )
                gameMap.getCell(i,j).setBuilding( null );
        // TODO : removing it's effects like : popularity , fearrate
        this.owner.setPopularity( this.owner.getPopularity() - this.popularityRate );
        this.owner.setFearRate( this.owner.getFearRate() ) ;
    }

    public void getDamaged( int damage , GameMap gameMap ){
        this.hitPoint -= damage ;
        if(this.hitPoint <= 0)
            this.collapse(gameMap) ;
    }

    public static HashMap<String , Integer> getBuildingHitPointByName (){
        return buildingHitPointByName;
    }

    public void setHitPoint(int initialHitPoint){
        this.hitPoint = initialHitPoint;
    }

    public int getHitPoint(){
        return this.hitPoint ;
    }
    public BuildingEnum getBuildingEnum(){
        return this.buildingEnum;
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

        if(type.equals("deserttree")) return new Building( "deserttree" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.DESERT_TREE , 0 ) ;
        if(type.equals("olivetree")) return new Building( "olivetree" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.OLIVE_TREE , 0 ) ;
        if(type.equals("coconuttree")) return new Building( "coconuttree" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.COCONUT_TREE , 0 ) ;
        if(type.equals("cherrytree")) return new Building( "cherrytree" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.CHERRY_TREE , 0 ) ;
        if(type.equals("datetree")) return new Building( "datetree" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.DATE_TREE , 0 ) ;

        // ROCKS

        if(type.equals("rockwest")) return new Building( "rockwest" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.ROCK_WEST ,  0 ) ;
        if(type.equals("rockeast")) return new Building( "rockeast" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.ROCK_EAST , 0 ) ;
        if(type.equals("rocknorth")) return new Building( "rocknorth" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.ROCK_NORTH , 0 ) ;
        if(type.equals("rocksouth")) return new Building( "rocksouth" , 1 , 1 , false , "" , owner , row , column , getBuildingCost(type) , 1 , 0 , false , BuildingEnum.ROCK_SOUTH , 0 ) ;


        // OTHER
        if (type.equals("smallstonewall"))
            return new Building(type,1,1,false,"",owner,row,column,getBuildingCost(type),500,0,false,BuildingEnum.SMALL_STONE_WALL,0);
        if (type.equals("bigstonewall"))
            return new Building(type,2,2,false,"",owner,row,column,getBuildingCost(type),2000,0,false,BuildingEnum.BIG_STONE_WALL,0);
        if (type.equals("drawbridge"))
            return new Building(type,1,3,true,"",owner,row,column,getBuildingCost(type),200,0,false,BuildingEnum.DRAW_BRIDGE,0);
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
        if (type.equals("smallstonegatehouse"))
            return new AttackDefenceBuilding(type,2,2,true,"",owner,row,column,getBuildingCost(type),500,0,false,30,20,false,20,BuildingEnum.SMALL_STONE_GATEHOUSE);
        if (type.equals("bigstonegatehouse"))
            return new AttackDefenceBuilding(type,4,4,true,"",owner,row,column,getBuildingCost(type),1000,0,false,40,20,false,20,BuildingEnum.BIG_STONE_GATEHOUSE);
        if (type.equals("armoury"))

            return  new StorageBuilding(type,3,3,false,"",owner,row,column,getBuildingCost(type),2000,0,false,200,BuildingEnum.ARMOURY);
        if (type.equals("barracks"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,2000,getBuildingCost(type),0, 200000 , BuildingEnum.BARRACKS);
        if (type.equals("mercenarypost"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,2000,getBuildingCost(type),0, 200000 , BuildingEnum.MERCENARY_POST);

        if (type.equals("engineerguild"))
            return new TradeBuilding(type,"",2,3,false,0,false,owner,row,column,400,getBuildingCost(type),0,0, BuildingEnum.ENGINEER_GUILD);
        if (type.equals("killingpit"))
            return new TradeBuilding(type,"",1,1,true,0,false,owner,row,column,0,getBuildingCost(type),0, 0, BuildingEnum.KILLING_PIT);
        if (type.equals("pitchditch"))
            return new TradeBuilding(type,"",1,1,true,0,false,owner,row,column,0,getBuildingCost(type),0, 200000 , BuildingEnum.PITCH_DITCH);
        if (type.equals("cagedwardog"))
            return new TradeBuilding(type,"",1,1,false,0,true,owner,row,column,100,getBuildingCost(type),3, 200000 , BuildingEnum.CAGED_WAR_DOGS);//TODO : HOW MANY OPERATOR?
        if (type.equals("siegetent"))
            return new TradeBuilding(type,"",4,4,true,0,false,owner,row,column,300,getBuildingCost(type),1,200000 , BuildingEnum.SIEGE_TENT);

        if (type.equals("stable"))
            return new TradeBuilding(type,"",3,5,false,0,false,owner,row,column,200,getBuildingCost(type),0, 1, BuildingEnum.STABLE);//TODO : HOW MANY OPERATORS?
        if (type.equals("tunnelerguild"))
            return new TradeBuilding(type,"",2,3,false,0,false,owner,row,column,0,getBuildingCost(type),0, 0, BuildingEnum.TUNNELER_GUILD);//TODO : HOW MANY OPERATOS?
        if (type.equals("appleorchard"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1, 3, BuildingEnum.APPLE_ORCHARD);
        if (type.equals("diaryfarmer"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1, 3, BuildingEnum.DIARY_FARMER);
        if (type.equals("hopsfarmer"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1, 3,BuildingEnum.HOPS_FARMER);
        if (type.equals("hunterpost"))
            return new TradeBuilding(type,"",3,5,true,0,true,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.HUNTER_POST);//TODO: HOLD ANIMALS?
        if (type.equals("wheatfarmer"))
            return new TradeBuilding(type,"",3,5,true,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.WHEAT_FARM);
        if (type.equals("bakery"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.BAKERY);
        if (type.equals("brewer"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.BREWER);
        if (type.equals("granary"))
            return new StorageBuilding(type,1,1,false,"",owner,row,column,getBuildingCost(type),500,0,false,800, BuildingEnum.GRANARY);
        if (type.equals("inn"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.INN);
        if (type.equals("mill"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,400,getBuildingCost(type),3, 0, BuildingEnum.MILL);
        if (type.equals("ironmine"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.IRON_MINE);
        if (type.equals("market"))
            return new TradeBuilding(type,"",3,3,false,0,false,owner,row,column,400,getBuildingCost(type),1, 0, BuildingEnum.MARKET);//TODO : HOW MANY OPERAYORS?
        if (type.equals("oxtether"))
            return new TradeBuilding(type,"",1,1,true,0,false,owner,row,column,100,getBuildingCost(type),0, 0, BuildingEnum.OX_TETHER);//TODO
        if (type.equals("pitchrig"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,0,getBuildingCost(type),1, 0, BuildingEnum.PITCH_RIG);//TODO
        if (type.equals("quarry"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),3, 2, BuildingEnum.QUARRY);//TODO
        if (type.equals("stockpile"))
            return new StorageBuilding(type,1,1,false,"",owner,row,column,getBuildingCost(type),10000,0,false,1000,BuildingEnum.STOCKPILE);//TODO
        if (type.equals("woodcutter"))
            return new TradeBuilding(type,"",2,3,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.WOODCUTTER);//TODO
        if (type.equals("apothecary"))
            return new TradeBuilding(type,"",2,3,false,+1,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.APOTHECARY);//TODO
        if (type.equals("hovel"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,200,getBuildingCost(type),0, 0, BuildingEnum.HOVEL);//TODO
        if (type.equals("church"))
            return new TradeBuilding(type,"",2,4,false,+1,false,owner,row,column,400,getBuildingCost(type),0, 0, BuildingEnum.CHURCH);//TODO
        if (type.equals("cathedral"))
            return new TradeBuilding(type,"",3,5,false,+1,false,owner,row,column,400,getBuildingCost(type),0, 0, BuildingEnum.CATHEDRAL);//TODO
        if (type.equals("well"))
            return new TradeBuilding(type,"",1,1,false,0,false,owner,row,column,50,getBuildingCost(type),1, 0, BuildingEnum.WELL);//TODO
        if (type.equals("waterpot"))
            return new Building(type , 1 , 1 , false , "" , owner , row , column , getBuildingCost( type ) , 50 , 200000 , false , BuildingEnum.WATER_POT , 0 );//TODO

        if (type.equals("goodthings"))
            return new TradeBuilding(type,"",1,1,false,+2,false,owner,row,column,50,getBuildingCost(type),0, 0, BuildingEnum.GOOD_THINGS);//TODO
        if (type.equals("badthings"))
            return new TradeBuilding(type,"",1,1,false,-2,false,owner,row,column,50,getBuildingCost(type),0, 0, BuildingEnum.BAD_THINGS);//TODO
        if (type.equals("armourer"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.ARMOURER);//TODO
        if (type.equals("blacksmith"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.BLACKSMITH);//TODO
        if (type.equals("fletcher"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.FLETCHER);//TODO
        if (type.equals("poleturner"))
            return new TradeBuilding(type,"",2,2,false,0,false,owner,row,column,300,getBuildingCost(type),1, 0, BuildingEnum.POLE_TURNER);//TODO
        if (type.equals("signpost"))
            return new TradeBuilding(type,"",1,1,false,-3,false,owner,row,column,150,getBuildingCost(type),0, 0, BuildingEnum.SIGNPOST);//TODO
        if (type.equals("tunnelentrance"))
            return new Building(type , 1 , 1 , true , "", owner , row , column , getBuildingCost( type ) , 100 , 0 , false , BuildingEnum.TUNNEL_ENTRANCE , 0 );//TODO
        if (type.equals("staircase"))
            return new Building(type,1,1,true,"",owner,row,column,getBuildingCost(type),20,0,false,BuildingEnum.STAIR,0);

        return null ;

    }

    public static Cost getBuildingCost(String type){

        // TREES : ZERO COST

        if(type.equals("deserttree")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("olivetree")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("coconuttree")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("cherrytree")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("datetree")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        // ROCKS : ZERO COST

        if(type.equals("rockwest")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("rockeast")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("rocknorth")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;
        if(type.equals("rocksouth")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;


        // WALLS :

        if (type.equals("smallstonewall")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                1 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("bigstonewall")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                2 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        // STAIRCASE :

        if(type.equals("staircase")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                5 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;


        // OTHER
        if (type.equals("drawbridge")) return new Cost( 0 , 0 , 0 , 0 , 0 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("lookouttower")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                10 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("perimetertower")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                10 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("squaretower")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                35 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("roundtower")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                40 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("defenceturret")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                15 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("smallstonegatehouse")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                10 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("bigstonegatehouse")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                20 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("armoury")) return new Cost( 0 , 0 , 0 , 0 , 0 , 5 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("barracks")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                15 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("mercenarypost")) return new Cost( 0 , 0 , 0 , 0 , 0 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0,0) ;

        if (type.equals("engineerguild")) return new Cost( 0 , 0 , 0 , 0 , 100 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("killingpit")) return new Cost( 0 , 0 , 0 , 0 , 0 , 6 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        // TODO : THIS ONE SHOULD BE HANDLED AT GAMEMENUCONTROLLER , NEEDS 2 PITCH IN EVERY 5 SQUARES
        if (type.equals("pitchditch")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("cagedwardog")) return new Cost( 0 , 0 , 0 , 0 , 100 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("siegetent")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("stable")) return new Cost( 0 , 0 , 0 , 0 , 400 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("tunnelerguild")) return new Cost( 0 , 0 , 0 , 0 , 100 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("appleorchard")) return new Cost( 0 , 0 , 0 , 0 , 0 , 5 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("diaryfarmer")) return new Cost( 0 , 0 , 0 , 0 , 0 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("hopsfarmer")) return new Cost( 0 , 0 , 0 , 0 , 0 , 15 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("hunterpost")) return new Cost( 0 , 0 , 0 , 0 , 0 , 5 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("wheatfarmer")) return new Cost( 0 , 0 , 0 , 0 , 0 , 15 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("bakery")) return new Cost( 0 , 0 , 0 , 0 , 0 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("brewer")) return new Cost( 0 , 0 , 0 , 0 , 0 , 10 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("granary")) return new Cost( 0 , 0 , 0 , 0 , 0 , 5 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("inn")) return new Cost( 0 , 0 , 0 , 0 , 100 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("mill")) return new Cost( 0 , 0 , 0 , 0 , 0 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("ironmine")) return new Cost( 0 , 0 , 0 , 0 , 0 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("market")) return new Cost( 0 , 0 , 0 , 0 , 0 , 5 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("oxtether")) return new Cost( 0 , 0 , 0 , 0 , 0 , 5 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("pitchrig")) return new Cost( 0 , 0 , 0 , 0 , 0 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("quarry")) return new Cost( 0 , 0 , 0 , 0 , 0 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("stockpile")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("woodcutter")) return new Cost( 0 , 0 , 0 , 0 , 0 , 3 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        // TODO : THIS IS REMOVED OR NOT ?
        if (type.equals("apothecary")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("hovel")) return new Cost( 0 , 0 , 0 , 0 , 0 , 6 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("church")) return new Cost( 0 , 0 , 0 , 0 , 250 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("cathedral")) return new Cost( 0 , 0 , 0 , 0 , 1000 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("well")) return new Cost( 0 , 0 , 0 , 0 , 30 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("goodthings")) return new Cost( 0 , 0 , 0 , 0 , 25 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("badthings")) return new Cost( 0 , 0 , 0 , 0 , 45 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("armourer")) return new Cost( 0 , 0 , 0 , 0 , 100 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("blacksmith")) return new Cost( 0 , 0 , 0 , 0 , 100 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("fletcher")) return new Cost( 0 , 0 , 0 , 0 , 100 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("poleturner")) return new Cost( 0 , 0 , 0 , 0 , 100 , 20 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        if (type.equals("tunnelentrance")) return new Cost( 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0, 0,0,0,0,0,
                0,0,0,0,0, 0) ;

        return null ;
    }

}
