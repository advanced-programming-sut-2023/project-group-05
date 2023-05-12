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
        putYourCastle( this.player );
    }

    public void debug(){
        for(Unit unit : Unit.getUnits()){
            System.out.println("UNIT : " + unit.getName() + " hitpoint : " + unit.getHitPoint() + " x = " + unit.getColumn() + " y = " + unit.getRow() ) ;
        }
        for(Building building : Building.getBuildings()){
            System.out.println("UNIT : " + building.getName() + " hitpoint : " + building.getHitPoint() + " x = " + building.getColumn() + " y = " + building.getRow() ) ;
        }
    }

    public void nextTurn(){
        this.turn++ ;
        this.player = this.players.get( this.turn % this.players.size() ) ;

        if(this.turn == this.turn % this.players.size())
            putYourCastle( this.player );

        // MOVING UNITS HAPPENS IN THIS PART

        Integer nextColumn = -1 ;
        Integer nextRow = -1 ;
        for(Unit unit : Unit.getUnits()){
            if(unit.getTargetRow() == -1) continue ;
            nextRow = unit.getNextMoveRow() ;
            nextColumn = unit.getNextMoveColumn() ;
            if(nextRow == unit.getRow() && nextColumn == unit.getColumn()&& !(unit instanceof Warrior)){
                unit.setIsMoving(false) ;
                continue ;
            }
            if(nextRow == unit.getRow() && nextColumn == unit.getColumn()&& unit instanceof Warrior && !((Warrior)unit).getIsPatrolling() ){
                unit.setIsMoving(false) ;
                continue ;
            }
            if(nextRow == unit.getRow() && nextColumn == unit.getColumn()){
                Warrior warrior = (Warrior)unit ;
                warrior.setTarget(warrior.getRow(),warrior.getColumn(),gameMap) ;
                nextRow = warrior.getNextMoveRow() ;
                nextColumn = warrior.getNextMoveColumn() ;
            }

            Cell cell = gameMap.getCell(unit.getRow() , unit.getColumn()) ;
            Cell nextCell = gameMap.getCell(nextRow , nextColumn) ;
            cell.getUnits().remove(unit) ;
            nextCell.getUnits().add(unit) ;
            unit.setRow( nextRow );
            unit.setColumn( nextColumn ) ;

        }

        // ATTACKING UNITS

        for(Unit unit : Unit.getUnits()){
            if(!(unit instanceof Warrior)) continue ;
            Warrior warrior = (Warrior)unit ;
            if(!warrior.getIsAttacking()) continue ;
            Building attackingBuilding = warrior.getAttackingBuilding();
            Unit attackingUnit = warrior.getAttackingUnit() ;
            if( attackingUnit != null ){
                int distance2 = (attackingUnit.getRow() - warrior.getRow())*(attackingUnit.getRow() - warrior.getRow()) +
                        (attackingUnit.getColumn() - warrior.getColumn()) * (attackingUnit.getColumn() - warrior.getColumn());
                if( Math.abs(Math.sqrt(distance2)) <= warrior.getRange() ){
                    attackingUnit.getDamaged(warrior.getDamage()) ;
                }
            }
            else if( attackingBuilding != null ){
                int distance2 = (attackingBuilding.getRow() - warrior.getRow())*(attackingBuilding.getRow() - warrior.getRow()) +
                        (attackingBuilding.getColumn() - warrior.getColumn()) * (attackingBuilding.getColumn() - warrior.getColumn());
                if( distance2 <= warrior.getRange()){
                    // TODO : ACTUAL ATTACK TO BUILDING
                }
            }
        }
        // ACTIONS IN THE END OF EACH N TURNS ( N = players.size() )
        // LIKE : TAX


    }

    public void putYourCastle(Player owner){
        System.out.println("----WELCOME TO THE GAME OF KINGS MY LORD----\n" +
                ">>> Take A Location And Put Your Castle <<<\n" +
                "-> YOUR CASTLE IS THE HEART OF YOUR KINGDOM\n" +
                "-> CHOOSE A LOCATION FOR IT.");
        Integer row =0;Integer column = 0;
        getCastleCoordinates(row,column);
        String name = "castle";
        Building castle = new Building (name,1,1,true,"",owner,row,column,Building.getBuildingCost(name),
                -1,0,false,BuildingEnum.CASTLE);
        gameMap.getCell(row,column).setBuilding(castle) ;
        name = "king";
        Warrior king = new Warrior(name,owner, 50 , 500,5,1,30,30,0,
                false,false,false,false,false,false,true,row.intValue(),column.intValue());
        gameMap.getCell(row,column).units.add(king);
        player.addBuilding(castle);
        player.addUnit(king);
    }

    public void getCastleCoordinates (Integer row , Integer column) {
        String buffer;
        while (true) {
            System.out.print("Please Enter Row Number : ");
            try {
                buffer = Menu.getScanner().nextLine();
                Integer.parseInt(buffer);
                row = Integer.parseInt(buffer);
                if (row >0 && row <400)
                    break;
                System.out.println("Row Number Is Outside The Map,Re-enter it");
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
                System.out.println("Column Number Is Outside The Map,Re-enter it");
            } catch (Exception e) {
                continue;
            }
        }
        Cell cell = gameMap.getCell(row.intValue(),column.intValue());
        if (cell.cellType!=CellType.GROUND || !cell.units.isEmpty() || cell.getBuilding() != null ) {
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
        Building building = gameMap.getCell(row,column).getBuilding() ;
        player.setSelectedBuilding(building);
        return "SelectBuilding Successful!";
    }

    public String handleSelectBuildingError (int row , int column){
        if (row > 400 || row < 0 || column > 400 || column <0 )
            return "SelectBuilding Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        if (cell.getBuilding() == null)
            return  "SelectBuilding Failed : Cell Does Not Contain Any Building";
        return null;
    }

    public String repair(Matcher matcher){
        return null;
    }

    public String selectUnit(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error= handleSelectUnitError(row,column))!= null)
            return error;
        Cell cell = gameMap.getCell(row,column);
        // player.getSelectedUnits().clear();
        // TODO : selected units get cleared ?
        player.setSelectedUnits(cell);
        return "SelectUnit Successful!";
    }

    public String handleSelectUnitError (int row , int column){
        if (row > 400 || column > 400 || row < 0 || column < 0)
            return "SelectUnit Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        //TODO : HANDLE THAT ONLY NON JOBLESS AND NON OPERATOR UNITS CAN BE SELECTED
        boolean ok = false ;
        for(Unit unit : cell.getUnits()){
            if(unit.getOwner()==player)
                ok = true ;
        }
        if(!ok)
            return "YOU HAVE NO UNITS HERE MY LORD." ;
        return null;
    }

    public String disbandUnit(Matcher matcher){
        if (player.getSelectedUnits().isEmpty())
            return "No Unit Selected";
        PathFinder pathFinder = new PathFinder();
        pathFinder.Run(player.getCastle().getRow(),player.getCastle().getColumn());
        int currentUnitRow = player.getSelectedUnits().get(0).getRow();
        int currentUnitColumn = player.getSelectedUnits().get(0).getColumn();
        if (pathFinder.goInDirectionFrom(currentUnitRow,currentUnitColumn)==-1)
            return "Disband Unit Failed : No Path To Castle Or Unit Is Already In Castle";
        for (Unit unit : player.getSelectedUnits())
            unit.setTarget(player.getCastle().getRow(),player.getCastle().getColumn(),gameMap);
        return "Unit Disbanded!";
    }

    public String moveUnit(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        if (player.getSelectedUnits().isEmpty())
            return "No Unit Selected";
        if (row > 400 || row <0 || column >400 || column < 0)
            return "Move Unit Failed : Row Or Column Exceeded Map";
        if (gameMap.getMaskedMap()[row][column] == 1)
            return "Move Unit Failed : Destination Is Not Permeable";
        // TODO : EXPLANATION --> IT IS ASSUMED THAT ALL SELECTED UNITS ARE FROM A SINGLE CELL AND THEY ARE GOING TO A SINGLE DESTINATION
        for (Unit unit : player.getSelectedUnits()){
            unit.setTarget(row,column,gameMap);
            if(unit instanceof Warrior)
                ((Warrior)unit).stopPatrol() ;
        }
        return "Unit Set For Move!";
    }

    private boolean validRowColumn(int x){
        return ( x < 400 && x >= 0 ) ;
    }

    public String patrolUnit(Matcher matcher){

        int beginRow = Integer.parseInt( matcher.group("beginRow") ) ;
        int beginColumn = Integer.parseInt( matcher.group("beginColumn") ) ;
        int endRow = Integer.parseInt( matcher.group("endRow") ) ;
        int endColumn = Integer.parseInt( matcher.group("endColumn") ) ;

        if(!(validRowColumn( beginRow ) && validRowColumn( beginColumn ) && validRowColumn( endRow ) && validRowColumn( endColumn )))
            return "INVALID COORDINATES" ;

        if(player.getSelectedUnits().size()==0)
            return "YOU HAVE NOT CHOSEN ANY SOLDIERS MY LORD." ;

        for(Unit unit : player.getSelectedUnits()){
            if(!(unit instanceof Warrior)) continue ;
            Warrior warrior = (Warrior)unit ;
            warrior.startPatrol( beginRow , beginColumn , endRow , endColumn , gameMap ) ;
        }

        return "SOLDIERS WHO COULD STARTED PATROLLING AROUND MY LOoOo0oRD" ;

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
        int row = Integer.parseInt(matcher.group("row")) ;
        int column = Integer.parseInt(matcher.group("column")) ;
        Unit enemyUnit = null ;
        Building enemyBuilding = null ;
        boolean isEnemyUnit = false ;
        for(Unit unit : gameMap.getCell( row,column ).getUnits()){
            if(unit.getOwner() != player){
                isEnemyUnit = true ;
                enemyUnit = unit ;
                break ;
            }
        }
        boolean isEnemyBuilding = false ;
        Building building = gameMap.getCell(row , column).getBuilding();
        if(building != null && building.getOwner() != player ){
            isEnemyBuilding = true ;
            enemyBuilding = building ;
        }
        if(!isEnemyUnit && !isEnemyBuilding)
            return "THERE IS NO ENEMY UNITS OR BUILDINGS THERE , MY LORD" ;

        if(enemyUnit != null){
            for(Unit unit : player.getSelectedUnits()){
                if(unit instanceof Warrior ){
                    ((Warrior)unit).attackUnit(enemyUnit,gameMap) ;
                }
            }
        }
        else{
            for(Unit unit : player.getSelectedUnits()){
                if(unit instanceof Warrior ){
                    ((Warrior)unit).attackBuilding(enemyBuilding) ;
                }
            }
        }

        return "ATTACKING THE ENEMY" ;
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
        if (cell.getBuilding() != null)
            return "SetTexture Failed : Cell Contains Building";
        cell.cellType = CellType.getCellTypeEnumByName(matcher.group("type"));
        if (cell.cellType == CellType.BOULDER || cell.cellType == CellType.RIVER ||
                cell.cellType == CellType.SMALL_POND || cell.cellType == CellType.BIG_POND ||
                cell.cellType == CellType.SEA)
            gameMap.getMaskedMap()[row][column] = 1 ;
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
                if (gameMap.getCell(i,j).getBuilding() == null || gameMap.getCell(i,j).units.size()>0)
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

        if( cell.getBuilding() != null )
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

        if( gameMap.getCell( row , column ).getBuilding() != null )
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
        if(gameMap.getCell(row , column).getBuilding() != null){
            return "A BUILDING IS ALREADY HERE MY LORD." ;
        }
        if (error != null)
            return error;
        Building building = Building.createBuildingByName(type,player,row,column);
        for (int i = row ; i<row+building.getHeight();++i){
            for (int j = column ; j < column + building.getWidth();++j)
                gameMap.getCell(i,j).setBuilding(building);
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
                gameMap.getCell(i,j).setBuilding(building) ;
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
                if (gameMap.getCell(row,column).getBuilding() != null)
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
        Unit unit = Unit.createUnitByName(type,player,row,column);
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
        Unit unit = Unit.createUnitByName(type,player,row,column);
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
        String ret = "------END_OF_PRICE_LIST------" ;
        for(int i = 0 ; i < Cost.getItemNames().size() ; i++){
            System.out.print(Cost.getItemNames().get(i)) ;
            System.out.println(" : ");
            System.out.print(Cost.getItemPrices().get(i)) ;
            System.out.println() ;
        }
        return ret ;
    }

    public String buy(Matcher matcher){
        String itemName = matcher.group("itemName") ;
        int amount = Integer.parseInt(matcher.group("amount")) ;
        int index = -1 ;
        for(int i = 0 ; i < Cost.getItemNames().size() ; i++){
            if( itemName.equals(Cost.getItemNames().get(i)) ){
                index = i;
                break ;
            }
        }

        if( index == -1 )
            return "INVALID NAME" ;


        if( Cost.getItemPrices().get(index) * amount > player.getGold() )
            return "YOU DON'T HAVE ENOUGH GOLD" ;

        player.decreaseGold( Cost.getItemPrices().get(index) * amount ) ;

        switch(index){
            case 0 :
                player.apple += amount ;
                break ;
            case 1 :
                player.cheese += amount ;
                break ;
            case 2 :
                player.bread += amount ;
                break ;
            case 3 :
                player.meat += amount ;
                break ;
            case 4 :
                player.ale += amount ;
                break ;
            case 5 :
                player.wheat += amount ;
                break ;
            case 6 :
                player.flour += amount ;
                break ;
            case 7 :
                player.stone += amount ;
                break ;
            case 8 :
                player.wood += amount ;
                break ;
            case 9 :
                player.iron += amount ;
                break ;
            case 10 :
                player.pitch += amount ;
                break ;
            case 11 :
                player.pike += amount ;
                break ;
            case 12 :
                player.spear += amount ;
                break ;
            case 13 :
                player.bow += amount ;
                break ;
            case 14 :
                player.sword += amount ;
                break ;
            case 15 :
                player.crossbow += amount ;
                break ;
            case 16 :
                player.metalArmour += amount ;
                break ;
            case 17 :
                player.leatherArmour += amount ;
                break ;
        }

        return "SUCCESSFULLY BOUGHT." ;
    }

    public String sell(Matcher matcher){
        int amount = Integer.parseInt(matcher.group("amount")) ;
        String itemName = matcher.group("itemName") ;

        int index = -1 ;

        for(int i = 0 ; i < Cost.getItemNames().size() ; i++){
            if( itemName.equals(Cost.getItemNames().get(i)) ){
                index = i;
                break ;
            }
        }

        if( index == -1 )
            return "INVALID NAME" ;

        player.increaseGold(Cost.getItemPrices().get(index)*amount) ;

        switch(index){
            case 0 :
                player.apple -= amount ;
                break ;
            case 1 :
                player.cheese -= amount ;
                break ;
            case 2 :
                player.bread -= amount ;
                break ;
            case 3 :
                player.meat -= amount ;
                break ;
            case 4 :
                player.ale -= amount ;
                break ;
            case 5 :
                player.wheat -= amount ;
                break ;
            case 6 :
                player.flour -= amount ;
                break ;
            case 7 :
                player.stone -= amount ;
                break ;
            case 8 :
                player.wood -= amount ;
                break ;
            case 9 :
                player.iron -= amount ;
                break ;
            case 10 :
                player.pitch -= amount ;
                break ;
            case 11 :
                player.pike -= amount ;
                break ;
            case 12 :
                player.spear -= amount ;
                break ;
            case 13 :
                player.bow -= amount ;
                break ;
            case 14 :
                player.sword -= amount ;
                break ;
            case 15 :
                player.crossbow -= amount ;
                break ;
            case 16 :
                player.metalArmour -= amount ;
                break ;
            case 17 :
                player.leatherArmour -= amount ;
                break ;
        }

        return "SUCCESSFULLY SOLD." ;
    }

}
