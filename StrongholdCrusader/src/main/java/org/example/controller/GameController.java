package org.example.controller;

import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.unit.Unit;
import org.example.model.unit.Warrior;
import org.example.view.Menu;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

public class GameController {
    //TODO : DANIAL & MOHAMMAD AMIN : ATTACK - AIR ATTACK - POUR OIL - DIG TUNNEL - BUILD EQUIPMENT - PATROL UNIT
    ArrayList<Player> players ;
    ArrayList <Account> accounts ;
    private int turn ;
    private Player winner ;
    Player player ;
    private GameMap gameMap;
    public static int maxRow = 400;
    public static int maxColumn = 400;


    public GameController( ArrayList<Account> accounts ){
        this.accounts = accounts ;
        this.players = new ArrayList<Player>() ;
        for( Account acc : accounts ){
            this.players.add( new Player( acc ) ) ;
        }
        this.player = this.players.get(0) ;
        this.turn = 0 ;
        this.winner = null ;
        gameMap = new GameMap(400,400);
    }

    public void nextTurn(){
        this.turn++ ;
        this.player = this.players.get( this.turn % this.players.size() ) ;
    }

    public void putYourCastle(Player owner){
        System.out.println("Take A Location And Put Your Castle");
        Integer row =0;Integer column = 0;
        getCastleCoordinates(row,column);
        String name = "castle";
        Building castle = new Building (name,1,1,true,"",owner,row,column,Building.getBuildingCost(name),-1,0,false,BuildingEnum.CASTLE);
        gameMap.getCell(row,column).buildings.add(castle);
        name = "king";
        Warrior king = new Warrior(name,owner,150,5,1,30,30,0,false,false,false,false,false,false,true);
        gameMap.getCell(row,column).units.add(king);
    }

    public void getCastleCoordinates (Integer row , Integer column) {
        String buffer;
        while (true) {
            System.out.println("Please Enter Row Number");
            try {
                buffer = Menu.getScanner().nextLine();
                Integer.parseInt(buffer);
                row = Integer.parseInt(buffer);
                if (row >0 && row <400)
                    break;
                System.out.println("Row Number Is Outside The Map,Re enter it");
            } catch (Exception e) {
                continue;
            }
        }
        while (true) {
            System.out.println("Please Enter Column Number");
            try {
                buffer = Menu.getScanner().nextLine();
                Integer.parseInt(buffer);
                column = Integer.parseInt(buffer);
                if (column >0 && column <400)
                    break;
                System.out.println("Column Number Is Outside The Map,Re enter it");
            } catch (Exception e) {
                continue;
            }
        }
        Cell cell = gameMap.getCell(row.intValue(),column.intValue());
        if (cell.cellType!=CellType.GROUND || !cell.units.isEmpty() || cell.buildings.isEmpty()) {
            System.out.println("Sorry , Can't Place The Castle Here, Please Re-Enter The Location");
            getCastleCoordinates(row, column);
        }
    }

    private void endGame(){
        System.out.println( "\nTHIS MATCH HAS ENDED AFTER " + this.turn / accounts.size() + " ROUNDS \n" +
                            "THE WINNER IS " + winner.getAccount().getNickName() + "\n" ) ;
        for(Player player : this.players)
            player.getAccount().setHighScore((int)Math.max(player.getAccount().getHighScore(),player.getScore())) ;
        System.out.println( "the winner is " + winner.getAccount().getNickName() ) ;
    }

    public Player getWinner(){
        return winner ;
    }

    public GameMap getGameMap(){
        return this.gameMap;
    }

    public String showPopularityFactors(Matcher matcher){
        String ret = "" ;
        ret += "Tax : " + this.player.getTaxRate() ;
        ret += "\nFear factor : " + this.player.getFearRate() ;
        ret += "\nReligion : " + this.player.getReligionRate() ;
        ret += "\nFood : " + this.player.getFoodCount() ;
        return ret ;
    }

    public String showPopularity(Matcher matcher){
        return "YOUR POPULARITY IS " + this.player.getPopularity() ;
    }

    public String showFoodList(Matcher matcher){
        String ret = "" ;
        ret += "APPLE : " + this.player.getApple() ;
        ret += "\nMEAT : " + this.player.getMeat() ;
        ret += "\nBREAD : " + this.player.getBread() ;
        ret += "\nCHEESE : " + this.player.getCheese() ;
        return ret ;
    }

    public String setFoodRate(Matcher matcher){
        int rate = Integer.parseInt(matcher.group("rate"));
        if( rate < -2 || rate > 2 ){
            return "RATE IS INVALID." ;
        }
        player.setFoodRate( rate ) ;
        player.setPopularity( player.getPopularity() + 4 * rate ) ;
        return "YOU HAVE SET FOOD RATE TO " + rate + "\nYOUR POPULARITY IS NOW " + player.getPopularity() ;
    }

    public String showFoodRate(Matcher matcher){
        return "YOUR FOOD RATE IS " + player.getFoodRate() ;
    }

    public String setTaxRate(Matcher matcher){
        int tax = Integer.parseInt( "rate" ) ;
        if( tax < -3 || tax > 8 )
            return "TAX RATE INVALID." ;
        int popularityChange = 0 ;
        switch( tax ){
            case -3 :
                popularityChange = 7 ;
                player.setTaxForEachUnit( -1 );
                break ;
            case -2 :
                popularityChange = 5 ;
                player.setTaxForEachUnit( -0.8 );
                break ;
            case -1 :
                popularityChange = 3 ;
                player.setTaxForEachUnit( -0.6 );
                break ;
            case 0 :
                popularityChange = 1 ;
                player.setTaxForEachUnit( 0 );
                break ;
            case 1 :
                popularityChange = -2 ;
                player.setTaxForEachUnit( 0.6 );
                break;
            case 2 :
                popularityChange = -4 ;
                player.setTaxForEachUnit( 0.8 );
                break ;
            case 3 :
                popularityChange = -6 ;
                player.setTaxForEachUnit( 1 );
                break ;
            case 4 :
                popularityChange = -8 ;
                player.setTaxForEachUnit( 1.2 );
                break ;
            case 5 :
                popularityChange = -12 ;
                player.setTaxForEachUnit( 1.4 );
                break ;
            case 6 :
                popularityChange = -16 ;
                player.setTaxForEachUnit( 1.6 );
                break ;
            case 7 :
                popularityChange = -20 ;
                player.setTaxForEachUnit( 1.8 );
                break ;
            case 8 :
                popularityChange = -24 ;
                player.setTaxForEachUnit( 2 );
                break ;
        }
        player.setPopularity( player.getPopularity() + popularityChange ) ;
        if( player.getGold() == 0 ){
            player.setTaxRate(0) ;
            player.setTaxForEachUnit( 0 );
            return "YOUR TAX RATE IS SET TO 0 DUE TO NOT HAVING ANY GOLD." ;
        }
        return "YOUR TAX RATE IS " + tax ;
    }

    public String showTaxRate (Matcher matcher){
        return "YOUR TAX RATE IS " + player.getTaxRate() ;
    }

    public String setFearRate (Matcher matcher){
        int rate = Integer.parseInt(matcher.group("rate")) ;
        if( rate < -5 || rate > 5 ){
            return "invalid rate" ;
        }
        player.setFearRate( rate ) ;
        return "FEAR RATE SET TO " + rate ;
    }


    public String selectBuilding(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error = handleSelectBuildingError(row,column))!=null)
            return error;
        Building building = gameMap.getCell(row,column).buildings.get(0);
        player.setSelectedBuilding(building);
        return "SelectBuilding Successful!";
    }

    public String handleSelectBuildingError (int row , int column){
        if (row > 400 || row < 0 || column > 400 || column <0 )
            return "SelectBuilding Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        if (cell.buildings.size()==0)
            return  "SelectBuilding Failed : Cell Does Not Contain Any Building";
        return null;
    }

    //TODO : MOHAMMAD AMIN
    public String repair(Matcher matcher){
        return null;
    }

    public String selectUnit(Matcher matcher){
        // we get select unit -x column -y row -t type?
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String type = matcher.group("type");
        String error;
        if ((error= handleSelectUnitError(row,column,type))!= null)
            return error;
        Cell cell = gameMap.getCell(row,column);
        //TODO : THIS PART CAN BE HANDLED MORE QUICKLY IF WE ADD UNITTYPE ENUM TO UNIT !
        player.getSelectedUnits().clear();
        player.setSelectedUnits(type,cell);
        return "SelectUnit Successful!";
    }

    public String handleSelectUnitError (int row , int column , String type){
        if (row > 400 || column > 400 || row < 0 || column < 0)
            return "SelectUnit Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        //TODO : HANDLE THAT ONLY NON JOBLESS AND NON OPERATOR UNITS CAN BE SELECTED
        UnitTypeEnum unitType = UnitTypeEnum.getUnitTypeByName(type);
        if (unitType == null)
            return "SelectUnit Failed : Unit Type Does Not Exists At All";
        Boolean contain = false;
        for (Unit unit : cell.units){
            if (unit.getName().equals(type)){
                contain = true;
                break;
            }
        }
        if (!contain)
            return "SelectUnit Failed : Unit You Mentioned Is Not In The Cell";
        return null;
    }

    public String disbandUnit(Matcher matcher){
        if (player.getSelectedUnits().isEmpty())
            return "No Unit Selected";
        PathFinder pathFinder = new PathFinder();
        pathFinder.Run(player.getCastle().getRow(),player.getCastle().getColumn());
        int currentUnitRow = player.getSelectedUnits().get(0).currentRow;
        int currentUnitColumn = player.getSelectedUnits().get(0).currentColumn;
        if (pathFinder.goInDirectionFrom(currentUnitRow,currentUnitColumn)==-1)
            return "Disband Unit Failed : No Path To Castle Or Unit Is Already In Castle";
        for (Unit unit : player.getSelectedUnits())
            unit.setTarget(player.getCastle().getRow(),player.getCastle().getColumn());
        return "Unit Disbanded!";
    }

    public String moveUnit(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        if (player.getSelectedUnits().isEmpty())
            return "No Unit Selected";
        if (row > 400 || row <0 || column >400 || column < 0)
            return "Move Unit Failed : Row Or Column Exceeded Map";
        if (!gameMap.getCell(row,column).permeable(null))
            return "Move Unit Failed : Destination Is Not Permeable";
        // TODO : EXPLANATION --> IT IS ASSUMED THAT ALL SELECTED UNITS ARE FROM A SINGLE CELL AND THEY ARE GOING TO A SINGLE DESTINATION
        PathFinder pathFinder = new PathFinder();
        pathFinder.Run(row,column);
        if (pathFinder.goInDirectionFrom(player.getSelectedUnits().get(0).currentRow,player.getSelectedUnits().get(0).currentColumn) == -1 )
            return "Move Unit Failed : No Path Found Or Unit Is Already There";
        for (Unit unit : player.getSelectedUnits())
            unit.setTarget(row,column);
        return "Unit Set For Move!";
    }

    public String patrolUnit(Matcher matcher){
        return null;
    }

    public String setState(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String state = matcher.group("state");
        String error;
        if ((error = handleSetStateError(row,column))!=null)
            return error;
        //TODO : EXPLANATION --> IN THE SELECTED UNIT , WE ONLY SELECT APPROPRIATE UNITS
        for (Unit unit : player.getSelectedUnits())
            unit.setUnitMode(UnitModeEnum.getUnitModeEnumByName(state));
        return "SetState Successful!";
    }

    public String handleSetStateError(int row,int column){
        if (row > 400 || row <0 || column > 400 || column <0)
            return "SetState Failed : Row Or Column Exceeded Map";
        if (player.getSelectedUnits().isEmpty())
            return "SetState Failed : No Unit Selected";
        return null;
    }


    public String attack(Matcher matcher){
        return null;
    }

    public String airAttack(Matcher matcher){
        return null;
    }

    public String pourOil(Matcher matcher){
        return null;
    }

    public String digTunnel(Matcher matcher){
        return null;
    }

    public String buildEquipment(Matcher matcher){
        return null;
    }


    public String setTextureCell (Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        if (row > 400 || row <0 || column >400 || column < 0)
            return "SetTexture Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        if (cell.units.size()>0)
            return "SetTexture Failed  : Cell Contains Units";
        if (cell.buildings.size() > 0)
            return "SetTexture Failed : Cell Contains Building";
        cell.cellType = CellType.getCellTypeEnumByName(matcher.group("type"));
        return "SetTexture Successful!";
    }

    public String setTextureBlock(Matcher matcher){
        int beginRow = Integer.parseInt(matcher.group("beginRow"));
        int endRow = Integer.parseInt(matcher.group("endRow"));
        int beginColumn = Integer.parseInt(matcher.group("beginColumn"));
        int endColumn = Integer.parseInt(matcher.group("endColumn"));
        if (beginRow > endRow || beginColumn > endColumn)
            return "SetTexture Failed : Invalid Row Or Column Order";
        if (endRow > 400 || endRow < 0 || endColumn >400 || endColumn <0)
            return "SetTexture Failed : Row Or Column Exceeded Map";
        for (int i =beginRow-1;i<endRow;++i){
            for (int j = beginColumn -1 ; j<endColumn;++j){
                if (gameMap.getCell(i,j).buildings.size() > 0 || gameMap.getCell(i,j).units.size()>0)
                    return "SetTexture Failed : Block Contains Unit Or Building";
            }
        }
        CellType cellType = CellType.getCellTypeEnumByName(matcher.group("type"));
        for (int i =beginRow-1;i<endRow;++i){
            for (int j = beginColumn -1 ; j<endColumn;++j)
                gameMap.getCell(i,j).cellType = cellType;
        }
        return "SetTexture Successful!";
    }

    public String clear (Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        if (row > 400 || row <0 || column > 400 || column < 0)
            return "Clear Failed : Row Or Column Exceeded Map!";
        gameMap.getCell(row,column).units.clear();
        gameMap.getCell(row,column).cellType = CellType.GROUND;
        return "Cell Cleared Successfully!";
    }

    public String dropRock(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String direction = matcher.group("direction") ;

        if( row < 0 || row > 400 || column < 0 || column > 400 )
            return "INVALID COORDINATES." ;

        String[] validDirections = { "e" , "w" , "n" , "s" } ;
        Random random = new Random() ;
        if(direction.equals("random"))
            direction = validDirections[ random.nextInt() % 4 ] ;

        boolean validDirection = false ;
        for(String dir : validDirections) if(direction.equals(dir)){
            validDirection = true ;
            break ;
        }

        if(!validDirection) return "DIRECTION IS NOT VALID." ;

        Cell cell = gameMap.getCell( row , column ) ;

        if( cell.units.size() != 0 )
            return "THERE IS UNIT IN THIS PLACE." ;

        if( cell.buildings.size() != 0 )
            return "THERE IS BUILDING IN THIS PLACE." ;

        if( cell.cellType == CellType.SEA || cell.cellType == CellType.RIVER || cell.cellType == CellType.BIG_POND
                || cell.cellType == CellType.SMALL_POND )
            return "CAN'T PLACE ROCK ON WATER!" ;

        String[] fullDirections = { "east" , "west" , "north" , "south" } ;
        String fullDirection = "" ;

        for(int i = 0 ; i < 4 ; i++){
            if(direction.equals(validDirections[i])) fullDirection = fullDirections[i] ;
        }

        Building.createBuildingByName( "rock" + fullDirection , player , row , column  ) ;

        return "ROCK DROPPED SUCCESSFULLY" ;
    }

    public String dropTree(Matcher matcher){
        String treeType = matcher.group("type") ;
        int row = Integer.parseInt(matcher.group("row")) ;
        int column = Integer.parseInt(matcher.group("column")) ;
        String direction = matcher.group("direction") ;
        if( !direction.equals("e") && !direction.equals("random") && !direction.equals("n") && !direction.equals("s")
                && !direction.equals("w") )
            return "INVALID DIRECTION." ;
        if( row > 400 || row < 0 || column < 0 || column > 400 )
            return "INVALID COORDINATES." ;
        String[] treeTypesNames = { "datetree" , "coconuttree" , "olivetree" , "cherrytree" , "deserttree" } ;
        boolean validType = false ;
        for( String type : treeTypesNames ) if(treeType.equals(type)){
            validType = true ;
            break ;
        }
        if(!validType) return "INVALID TREE TYPE." ;

        if( gameMap.getCell( row , column ).getBuildings().size() != 0 )
            return "THERE IS A BUILDING IN THIS PLACE MY LORD!" ;

        if( gameMap.getCell( row , column ).units.size() != 0 )
            return "THERE IS A UNIT IN THIS PLACE MY LORD!" ;

        if( gameMap.getCell( row , column ).cellType != CellType.GRASS && !treeType.equals("deserttree") )
            return "THIS TREE CAN ONLY BE PLACED ON GRASS." ;

        if( gameMap.getCell( row , column ).cellType != CellType.GROUND && treeType.equals("deserttree") )
            return "DESERT TREES CAN ONLY BE PLACED ON GROUND." ;

        Building.createBuildingByName( treeType , player , row , column ) ;

        return "TREE DROPPED SUCCESSFULLY." ;
    }

    public String createBuilding (Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String type = matcher.group("type");
        String error = dropCreateBuildingErrorHandler(row,column,type,true);
        if (error != null)
            return error;
        Building building = Building.createBuildingByName(type,player,row,column);
        for (int i = row ; i<row+building.getHeight();++i){
            for (int j = column ; j < column + building.getWidth();++j)
                gameMap.getCell(i,j).buildings.add(building);
        }
        player.addBuilding(building);
        player.handleBuildingEffectsOnPlayer(type);
        return "Create Building Successful!";
    }

    public String dropBuilding(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String type = matcher.group("type");
        String error = dropCreateBuildingErrorHandler(row,column,type,false);
        if (error != null)
            return error;
        Building building = Building.createBuildingByName(type,player,row,column);
        for (int i = row ; i<row+building.getHeight();++i){
            for (int j = column ; j < column + building.getWidth();++j)
                gameMap.getCell(i,j).buildings.add(building);
        }
        player.addBuilding(building);
        player.handleBuildingEffectsOnPlayer(type);
        return "Drop Building Successful!";
    }

    public String dropCreateBuildingErrorHandler (int row , int column , String type,boolean createBuilding){
        if (row > maxRow || row < 0 || column > maxColumn || column <0)
            return "Drop/Create Building Failed : Row Or Column Exceeded Map";
        if (BuildingEnum.getBuildingEnumByName(type)==null)
            return "Drop/Create Building Failed : No Such Building Name Exists";
        // Is There Enough Resources
        if (createBuilding) {
            Cost cost = Building.getBuildingCost(type);
            String enoughCost = player.decreaseCost(cost);
            if (enoughCost != null)
                return enoughCost;
        }
        // If This Is StocKpile Or Granary : Is There Another stockpile/granary near it
        String canBePlacedHere = player.canBuildingPlacedHere(type,row,column,gameMap);
        if (canBePlacedHere != null)
            return canBePlacedHere;
        // is there enough space here to place this building
        String isHereEmpty = isThereBuildingConflict(type,row,column);
        if (isHereEmpty!= null)
            return isHereEmpty;
        return null;
    }
    public String isThereBuildingConflict (String buildingName,int row , int column){
        int height = BuildingEnum.getBuildingHeightByName(buildingName);
        int width = BuildingEnum.getBuildingWidthByName(buildingName);
        for (int i = row ; i<row+height;++i){
            for (int j = column ; j < column + width;++j){
                if (!gameMap.getCell(row,column).buildings.isEmpty())
                    return "Building Can't Be Placed Here : Another Building Is Here";
                if (!gameMap.getCell(row,column).units.isEmpty())
                    return "Building Can't Be Placed Here : Some Units Are Here";
            }
        }
        return null;
    }

    public String dropUnit(Matcher matcher){
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error = dropCreateUnitErrorChecker(type,count,row,column))!=null)
            return error;
        Unit unit = Unit.createUnitByName(type,player);
        gameMap.getCell(row,column).addUnit(unit);
        player.addUnit(unit);
        return "Unit Dropped Successfully!";
    }

    public String createUnit(Matcher matcher){
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error = dropCreateUnitErrorChecker(type,count,row,column))!=null)
            return error;
        Cost cost = Unit.getCostByName(type);
        String enoughCost = player.decreaseCost(cost);
        if (enoughCost!=null)
            return enoughCost;
        Unit unit = Unit.createUnitByName(type,player);
        gameMap.getCell(row,column).addUnit(unit);
        player.addUnit(unit);
        return "Unit Created Successfully!";
    }

    public String dropCreateUnitErrorChecker(String type , int count , int row , int column){
        if (row > 400 || row <0 || column >400 || column < 0)
            return "Drop/Create Unit Failed : x or y exceeded map!";
        if (count <=0)
            return "Drop/Create Failed : count is smaller than 1!";
        Cell cell = gameMap.getCell(row,column);
        UnitTypeEnum unitTypeEnum = UnitTypeEnum.getUnitTypeByName(type);
        if (unitTypeEnum == null)
            return "Drop/Create Failed : Unit Type Does Not Exists!";
        if (!cell.permeable(unitTypeEnum))
            return "Drop/Create Failed : Cell Is Not Permeable!";
        return null;
    }

    public String tradeRequest (Matcher matcher){
        String resourceType = matcher.group("resourceType") ;
        int amount = Integer.parseInt( matcher.group("amount") ) ;
        String message = matcher.group("message") ;
        int price = Integer.parseInt(matcher.group("price")) ;
        Cost cost ;

        if( resourceType.equals("apple") )
            cost = new Cost(amount,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("cheese") )
            cost = new Cost(0,amount,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("bread") )
            cost = new Cost(0,0,amount,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("meat") )
            cost = new Cost(0,0,0,amount,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("bow") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    amount,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("crossbow") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,amount,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("spear") )
            cost = new Cost(0,0,0,0,0,0,0,0,amount,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("pike") )
            cost = new Cost(0,0,0,0,0,0,0,amount,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("mace") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,amount,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("sword") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,amount,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("leatherarmor") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,amount,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("metalarmor") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,amount,0,0,0
                    ,0) ;
        else if( resourceType.equals("wheat") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,amount
                    ,0) ;
        else if( resourceType.equals("flour") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,amount,0
                    ,0) ;
        else if( resourceType.equals("hop") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,amount,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("ale") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,amount,0,0
                    ,0) ;
        else if( resourceType.equals("stone") )
            cost = new Cost(0,0,0,0,0,0,amount,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("iron") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,amount) ;
        else if( resourceType.equals("wood") )
            cost = new Cost(0,0,0,0,0,amount,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0) ;
        else if( resourceType.equals("pitch") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,amount,0,0,0,0,0,0,0
                    ,0) ;
        else
            return "RESOURCE TYPE INVALID." ;

        if( price < 0 )
            return "PRICE CAN'T BE NEGATIVE MY LORD." ;
        new Trade( player , message , cost , price , amount , resourceType ) ;
        return "TRADE SUCCESSFULLY HAS BEEN PUBLISHED" ;
    }

    public String tradeList(Matcher matcher){
        String ret = "TRADE LIST :" ;
        int index = 1 ;
        ret += "----------------------------------------" ;
        for( Trade trade : Trade.getTrades() ){
            ret += "\nINDEX : " + index + " < " ;
            ret += "\nPlayer : " + trade.getPlayer1().getAccount().getNickName() ;
            ret += "\n  -> Price : " + trade.getPrice() ;
            ret += "\n  -> Amount : " + trade.getAmount() ;
            ret += "\n  -> Resource : " + trade.getResourceType() ;
            ret += "\n  -> Message : " + trade.getMessage1() ;
            if( trade.getMessage2() == null ) ret += "\n>>>>TRADE IS OPEN<<<<" ;
            else ret += "\n  -> Cosing Message : " + trade.getMessage2() + "\n>>>>TRADE IS CLOSED<<<<" ;
            ret += "\n----------------------------------------" ;
            index++ ;
        }
        return ret ;
    }

    public String tradeAccept(Matcher matcher){
        int id = Integer.parseInt(matcher.group("id")) - 1 ;
        String message = matcher.group("message") ;
        if( id < 1 || id > Trade.getTrades().size() )
            return "INVALID ID." ;
        Trade trade = Trade.getTrades().get(--id) ;
        Cost cost = trade.getCost() ;
        if( player.equals(trade.getPlayer1()) )
            return "YOU CAN NOT TRADE WITH YOURSELF MY LORD" ;
        String outputOfDecreaseCost = player.decreaseCost( cost ) ;
        if( outputOfDecreaseCost != null ) return outputOfDecreaseCost ;
        trade.setMessage2( message ) ;
        trade.setPlayer2( player ) ;
        return "YOU HAVE TRADED WITH OTHER KINGDOMS SUCCESSFULLY , MY LORD." ;
    }

    public String tradeHistory(Matcher matcher){
        String ret = "" ;
        ret += "TRADES YOU STARTED : " ;
        Trade trade ;
        for(int i = 0 ; i < Trade.getTrades().size() ; i++){
            trade = Trade.getTrades().get(i) ;
            if( trade.getPlayer1() == player ){
                ret += "\n----------------------------------------" ;
                ret += "INDEX " + (i + 1) + "\n";
                ret += "\nPrice : " + trade.getPrice() ;
                ret += "\nAmount : " + trade.getAmount() ;
                ret += "\nResource : " + trade.getResourceType() ;
                ret += "\nMessage : " + trade.getMessage1() ;
                if( trade.getMessage2() == null ) ret += "\n>>>>TRADE IS OPEN<<<<" ;
                else ret += "\n  -> Cosing Message : " + trade.getMessage2() + "\n>>>>TRADE IS CLOSED<<<<" ;
                ret += "\n----------------------------------------" ;
            }
        }
        ret += "\nTRADES YOU ACCEPTED : " ;
        for(int i = 0 ; i < Trade.getTrades().size() ; i++){
            trade = Trade.getTrades().get(i) ;
            if( trade.getPlayer2() == player ){
                ret += "\n----------------------------------------" ;
                ret += "INDEX " + (i + 1) + "\n";
                ret += "\nPrice : " + trade.getPrice() ;
                ret += "\nAmount : " + trade.getAmount() ;
                ret += "\nResource : " + trade.getResourceType() ;
                ret += "\nMessage : " + trade.getMessage1() ;
                if( trade.getMessage2() == null ) ret += "\n>>>>TRADE IS OPEN<<<<" ;
                else ret += "\n  -> Cosing Message : " + trade.getMessage2() + "\n>>>>TRADE IS CLOSED<<<<" ;
                ret += "\n----------------------------------------" ;
            }
        }
        return ret ;
    }

    public String showPriceList(Matcher matcher){
        return null;
    }

    public String buy(Matcher matcher){
        return null;
    }

    public String sell(Matcher matcher){
        return null;
    }

}
