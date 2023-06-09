package org.example.controller;

import javafx.scene.image.Image;
import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.building.TradeBuilding;
import org.example.model.unit.*;
import org.example.view.Menu ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

public class GameController {

    public static String currentUsername ;
    public static String currentNickname ;
    public static String currentSlogan ;
    public static String currentEmail ;
    public static String currentPassword ;
    public static GameConnectionWriter gcw ;
    public static GameConnectionReader gcr ;
    public static Image currentAvatar = null;
    ArrayList<Player> players ;
    private int turn ;
    private Player winner ;
    Player player ;
    private GameMap gameMap;
    public static int maxRow = 400;
    public static int maxColumn = 400;
    public static HashMap <Operator, AtomicInteger> oxTurn = new HashMap <>() ;
    public static HashMap < Operator, Building> oxQuarry = new HashMap<>() ;
    public static HashMap< Operator , AtomicBoolean > oxHasStone = new HashMap<>() ;


    public GameController( ArrayList<Player> players ) throws Exception{
        this.players = players ;
        this.player = this.players.get(0) ;
        this.turn = 0 ;
        this.winner = null ;
        gameMap = new GameMap(400,400);
        Unit.setGameMap( gameMap ) ;
        // putYourCastle( this.player );
        // putYourStockPile( this.player );
        System.out.println( "TODO : we assume castle is put" ) ;
    }

    public void putCastles(){
        for(int i = 0 ; i < players.size() ; i++){
            int castleRow = (i % 2 == 0 ? 15 : 25) ;
            int castleColumn = ((i/2) % 2 == 0 ? 15 : 25) ;
            System.out.println( "castle built on " + castleRow + " " + castleColumn ) ;
            putBuildingInPlace ( Building.createBuildingByName( "castle" , players.get(i) , castleRow , castleColumn ) ) ;
            GameGraphicalController.addBuildingImage( castleRow , castleColumn , "castle" ) ;
        }
    }

    public ArrayList<Player> getPlayers(){
        return players ;
    }

    public void nextTurn() {
        this.turn++ ;
        this.player = this.players.get( this.turn % this.players.size() ) ;

        /*if(this.turn == this.turn % this.players.size()){
            putYourCastle( this.player );
            putYourStockPile( this.player );
        }*/

        // MOVING UNITS HAPPENS IN THIS PART

        moveAllUnits() ;

        // EACH WARRIOR IS SOME STATE : standing , defensive , aggressive based on that
        // the warrior will decide to attack someone or not.

        setWarriorsEnemies() ;

        // ALL WARRIORS WHICH ARE ATTACKING WILL DEAL DAMAGE IF THE ENEMY IS IN THEIR RANGE.
        // if x is damaged by y and y is warrior then whatever state y is in , it will start attacking x

        dealAllDamages() ;

        // OPERATORS : units working in some buildings ( workers )

        for( Unit unit : Unit.getUnits() ){
            if(!( unit instanceof Operator)) continue ;
            if(!unit.getName().equals("ox")) continue ;
            if( getAdjacentBuildings(unit.getRow(),unit.getColumn()).contains("stockpile") && oxHasStone.get(unit).getAcquire() ){
                player.stone+=1 ;
                oxHasStone.get(unit).set( false ) ;
            }
            if(unit.getIsMoving()) continue ;
            if( oxQuarry.get(unit) == null ){
                for( Building building : Building.getBuildings() ){
                    if( building.getName().equals("quarry") && building.getOwner() == unit.getOwner() ){
                        oxQuarry.put( (Operator)unit , building ) ;
                        break ;
                    }
                }
                if( oxQuarry.get(unit) == null ) continue ;
            }
            if( oxTurn.get(unit).getAcquire() == 0 ){
                // must go to a quarry if the quarry is ready
                TradeBuilding quarry = (TradeBuilding)oxQuarry.get(unit) ;
                if( ( this.turn - quarry.getTurnBuilt() ) % quarry.getRate() == 0  ){
                    for(Cell cell : getAdjacentCells( quarry )){
                        unit.setTarget(cell.getRow() , cell.getColumn(),gameMap) ;
                        if(unit.getNextColumn()!=unit.getColumn()&&unit.getNextRow()!=unit.getRow())
                            break ;
                    }
                    oxTurn.get(unit).set(1) ;
                }
            } else {
                // must go to the stockpile
                Building stockpile = null ;
                for(Building building : Building.getBuildings())
                    if(building.getName().equals("stockpile") && building.getOwner() == unit.getOwner()){
                        stockpile = building ;
                        break ;
                    }

                for( Cell cell : getAdjacentCells( stockpile ) ){
                    unit.setTarget( cell.getRow() , cell.getColumn() , gameMap );
                    if(unit.getNextColumn()!=unit.getColumn()&&unit.getNextRow()!=unit.getRow())
                        break ;
                }
                oxTurn.get(unit).set(0) ;
            }
        }

        // TRADE BUILDINGS will go brrrrrr
        tradeBuildingsWork() ;

        // ACTIONS IN THE END OF EACH N TURNS ( N = players.size() )
        // INCLUDING : TAX , POPULATION CHANGE , POPULARITY CHANGE , FOOD GETTING EATE

        // LIKE : TAX
        taxEffects(player);
        buildingEffectsOnPopularity(player);
        foodEffects(player);
        fearEffect(player);
        populationGrowth(player);

        // END GAME ?!
        int numberOfKings = 0 ;
        for(Unit unit : Unit.getUnits())
            if(unit.getName().equals("king")){
                numberOfKings++ ;
                winner = unit.getOwner() ;
            }
        if(numberOfKings == 1)
            endGame();
        else winner = null ;
    }

    public ArrayList<String> getAdjacentBuildings( int row , int column ){
        ArrayList<String> returnValue = new ArrayList<>() ;
        if( row + 1 < 400 && column + 1 < 400 && gameMap.getCell( row + 1 , column + 1 ).getBuilding() != null )
            returnValue.add( gameMap.getCell( row + 1 , column + 1 ).getBuilding().getName() ) ;
        if( row + 1 < 400 && column - 1 >=  0 && gameMap.getCell( row + 1 , column - 1 ).getBuilding() != null )
            returnValue.add( gameMap.getCell( row + 1 , column - 1 ).getBuilding().getName() ) ;
        if( row - 1 >= 0  && column + 1 < 400 && gameMap.getCell( row - 1 , column + 1 ).getBuilding()!= null )
            returnValue.add( gameMap.getCell( row - 1 , column + 1 ).getBuilding().getName() ) ;
        if( row - 1 >= 0  && column - 1 >=  0 && gameMap.getCell( row - 1 , column - 1 ).getBuilding() != null )
            returnValue.add( gameMap.getCell( row - 1 , column - 1 ).getBuilding().getName() ) ;
        return returnValue ;
    }

    public ArrayList<Cell> getAdjacentCells(Building building){
        ArrayList<Cell> adjacentCells = new ArrayList<Cell>() ;
        int column = building.getColumn() ;
        int row = building.getRow() ;
        int height = building.getHeight() ;
        int width = building.getWidth() ;

        for(int i = 0 ; i < building.getWidth() ; i++){
            if(row-1>=0  && column + i < 400) adjacentCells.add( gameMap.getCell(row-1 , column+i) ) ;
            if(row+height<400 && column + i < 400) adjacentCells.add( gameMap.getCell(row+height , column+i) ) ;
        }

        for(int j = 0 ; j < height ; j++){
            if(row + j < 400 && column -1 >= 0) adjacentCells.add( gameMap.getCell( row + j, column - 1 ) ) ;
            if(row + j < 400 && column + width < 400) adjacentCells.add( gameMap.getCell( row + j , column + width ) ) ;
        }

        if( row - 1 >= 0 && column - 1 >= 0 ) adjacentCells.add( gameMap.getCell(row-1,column-1) ) ;
        if( row + height < 400 && column + width < 400 ) adjacentCells.add( gameMap.getCell(row+height,column+width) ) ;
        if(column - 1 >= 0 && row + height < 400) adjacentCells.add( gameMap.getCell(row+height,column-1) ) ;
        if(row - 1 >= 0 && column + width < 400) adjacentCells.add( gameMap.getCell(row-1,column+width) ) ;

        return adjacentCells ;
    }

    public void populationGrowth(Player player){
        /*int increaseRate = player.getPopularity()/5;
        if (player.getPopularity() < 0)
            increaseRate = 0;
        int increased = 0;
        int castleRow = player.getCastle().getRow();
        int castleColumn = player.getCastle().getColumn();
        int freeCapacity = player.getPopulationCapacity() - player.getPopulation();
        while ( increased < freeCapacity && increased < increaseRate) {
            ++increased;
            Jobless jobless =(Jobless)Unit.createUnitByName("jobless",player,castleRow,castleColumn);
            player.addUnit(jobless);
            gameMap.getCell(castleRow,castleColumn).units.add(jobless);
        }
        player.changePopulation(increased);*/ // TODO : EVERY THING
    }

    public void dealAllDamages(){
        for(Unit attackerUnit : Unit.getUnits()){
            if(!(attackerUnit instanceof Warrior)) continue ;
            Warrior warrior = (Warrior)attackerUnit ;
            if(!warrior.getIsAttacking()) continue ;
            Building attackedBuilding = warrior.getAttackingBuilding();
            Unit attackedUnit = warrior.getAttackingUnit() ;
            if( attackedUnit != null ){
                int distance2 = (attackedUnit.getRow() - warrior.getRow())*(attackedUnit.getRow() - warrior.getRow()) +
                        (attackedUnit.getColumn() - warrior.getColumn()) * (attackedUnit.getColumn() - warrior.getColumn());
                if( Math.abs(Math.sqrt(distance2)) <= warrior.getRange() ){
                    attackedUnit.getDamaged(warrior.getDamage(),gameMap) ;
                    if(attackedUnit instanceof Warrior){
                        ((Warrior)attackedUnit).attackUnit(attackerUnit,gameMap) ;
                    }
                }
            }
            else if( attackedBuilding != null ){
                int distance2 = (attackedBuilding.getRow() - warrior.getRow())*(attackedBuilding.getRow() - warrior.getRow()) +
                        (attackedBuilding.getColumn() - warrior.getColumn()) * (attackedBuilding.getColumn() - warrior.getColumn());
                if( distance2 <= warrior.getRange()){
                    attackedBuilding.getDamaged(warrior.getDamage() , gameMap) ;
                }
            }
        }
    }

    public void tradeBuildingsWork(){
        for(Building anyBuilding : Building.getBuildings()){
            if(!(anyBuilding instanceof TradeBuilding)) continue ;
            TradeBuilding building = (TradeBuilding) anyBuilding ;
            building.updateFunctionality();
            building.functional = true ;
            if( building.getRate() != 0 && this.turn % building.getRate() == 0){
                System.out.println(building.trade(gameMap));
            }
        }
    }

    public void setWarriorsEnemies(){
        for(Unit unit : Unit.getUnits()){
            if( !(unit instanceof Warrior) ) continue ;
            Warrior warrior = (Warrior) unit ;

            // AGGRESSIVE GUYS

            if(warrior.getUnitMode()==UnitModeEnum.AGGRESSIVE){

                for(Unit nearbyUnit : Unit.getUnits()){
                    if(nearbyUnit.getOwner()==this.player) continue ;
                    int dr = nearbyUnit.getRow() - warrior.getRow() ;
                    int dc = nearbyUnit.getColumn() - warrior.getColumn() ;
                    if( dr * dr + dc * dc <= warrior.getRange() ){
                        warrior.attackUnit( nearbyUnit , gameMap ) ;
                        break ;
                    }
                }
            }

            // DEFENSIVE GUYS

            if(warrior.getUnitMode()==UnitModeEnum.DEFENSIVE){
                int num = 0 ;
                Unit attackedUnit = null ;
                for(Unit nearbyUnit : Unit.getUnits()){
                    if(nearbyUnit.getOwner()==this.player) continue ;
                    int dr = nearbyUnit.getRow() - warrior.getRow() ;
                    int dc = nearbyUnit.getColumn() - warrior.getColumn() ;
                    if( dr * dr + dc * dc <= warrior.getRange() ){
                        attackedUnit = nearbyUnit ;
                        num++ ;
                    }
                }
                if(num>=3)
                    warrior.attackUnit( attackedUnit , gameMap ) ;

            }

        }
    }

    public void taxEffects (Player player){
        if (player.gold == 0)
            player.setTaxRate(0);
        double totalGoldCollected = player.getPopulation()*Player.getMoneyOfTaxRate(player.getTaxRate());
        if (totalGoldCollected > -0.1) {
            player.changeGold(totalGoldCollected);
            player.changePopularity(Player.getPopularityEffectOfTaxRate(player.getTaxRate()));
            return;
        }
        double totalGoldToPay = -totalGoldCollected;
        double unSupplied = 0;
        if (player.gold < totalGoldToPay)
            unSupplied = totalGoldToPay - player.gold;
        player.setGold(Math.max(player.getGold()-totalGoldToPay,0));
        int popularityChange = (int)Math.floor((unSupplied/totalGoldToPay)*1
                +((totalGoldToPay-unSupplied)/(totalGoldToPay))*Player.getPopularityEffectOfTaxRate(player.getTaxRate()));


        // CHECK FOR WINNER AND IF THERE IS ONE , END THE GAME , GIVE POINTS , DELETE ALL INSTANCES FROM WHERE EVER THEY ARE NEEDED TO BE DELETED.

    }

    private void moveAllUnits(){
        int nextColumn = -1 ;
        int nextRow = -1 ;
        for(Unit unit : Unit.getUnits()){
            for( int i = 0; i < unit.getMovingSpeed() ; i++) {
                if ( !unit.getIsMoving() ){
                    System.out.println("1 - " +  unit.getName());
                    continue;
                }
                unit.setTarget( unit.getTargetRow(), unit.getTargetColumn(), gameMap ); // for always choosing minimum path
                nextRow = unit.getNextRow();
                nextColumn = unit.getNextColumn();
                if ( nextRow == unit.getRow() && nextColumn == unit.getColumn() && ! ( unit instanceof Warrior ) ) {
                    System.out.println("next row is row" + unit.getName()) ;
                    unit.setIsMoving( false );
                    continue;
                }
                if( unit instanceof Warrior && ((Warrior)unit).getIsAttacking() ){
                    Warrior warrior = (Warrior) unit ;
                    int dr = warrior.getRow() - warrior.getEnemyRow() ;
                    int dc = warrior.getColumn() - warrior.getEnemyColumn() ;
                    //System.out.println( "" + warrior + (dr * dr + dc * dc <= warrior.getRange() * warrior.getRange()) ) ;
                    if( dr * dr + dc * dc <= warrior.getRange() * warrior.getRange() ){
                        warrior.setIsMoving( false );
                        continue ;
                    }
                }
                if ( nextRow == unit.getRow() && nextColumn == unit.getColumn() && unit instanceof Warrior && ! ( (Warrior) unit ).getIsPatrolling() ) {
                    unit.setIsMoving( false );
                    continue;
                }
                if ( nextRow == unit.getRow() && nextColumn == unit.getColumn() && unit instanceof Warrior ) {
                    Warrior warrior = (Warrior) unit;
                    warrior.setTarget( warrior.getRow(), warrior.getColumn(), gameMap );
                    nextRow = warrior.getNextRow();
                    nextColumn = warrior.getNextColumn();
                }
                System.out.println("moved " + unit.getName() + " from " + unit.getRow() + " , " + unit.getColumn() ) ;
                Cell cell = gameMap.getCell( unit.getRow(), unit.getColumn() );
                Cell nextCell = gameMap.getCell( nextRow, nextColumn );
                cell.getUnits().remove( unit );
                nextCell.getUnits().add( unit );
                unit.setRow( nextRow );
                unit.setColumn( nextColumn );
                System.out.println("to " + unit.getRow() + " , " + unit.getColumn() ) ;
            }
        }
    }

    public void buildingEffectsOnPopularity(Player player){
        for (Building building : player.getBuildings()){
            if (building.getBuildingEnum() == BuildingEnum.INN)
                player.changePopularity(+2);
            else if (building.getBuildingEnum() == BuildingEnum.GOOD_THINGS)
                player.changePopularity(+1);
            else if (building.getBuildingEnum() == BuildingEnum.BAD_THINGS)
                player.changePopularity(-1);
            else if (building.getBuildingEnum() == BuildingEnum.CHURCH)
                player.changePopularity(+2);
            else if (building.getBuildingEnum() == BuildingEnum.CATHEDRAL)
                player.changePopularity(+2);
        }
    }

    public void fearEffect(Player player){
        player.changePopularity(player.getFearRate());
    }

    public void foodEffects (Player player){
        if(player.getFoodCount() == 0)
            player.setFoodRate(-2);
        int diversity = player.getFoodDiversity();
        player.changePopularity(diversity-1);
        double foodSupply = player.getFoodRate()*0.5+1;
        int totalFoodSupply=(int)Math.floor(player.getPopulation()*foodSupply);
        int unSuppliedFood = changePlayerFoodTypesAmount(player,totalFoodSupply);
        int popularityChange = (int)Math.floor(((double)unSuppliedFood/((double)totalFoodSupply))*-8
                                +((double)(totalFoodSupply-unSuppliedFood)/(double) totalFoodSupply)*player.getFoodRate()*4);
        player.changePopularity(popularityChange);
    }

    public int changePlayerFoodTypesAmount (Player player,int totalFoodSupply) {
        int suppliedFood = 0;
        suppliedFood+=Math.min(player.apple,totalFoodSupply);
        player.apple = Math.max(player.apple - totalFoodSupply,0);
        totalFoodSupply -= suppliedFood;
        suppliedFood+=Math.min(player.cheese,totalFoodSupply);
        player.cheese = Math.max(player.apple-totalFoodSupply,0);
        totalFoodSupply -= suppliedFood;
        suppliedFood+=Math.min(player.meat,totalFoodSupply);
        player.meat = Math.max(player.meat -totalFoodSupply,0);
        totalFoodSupply -= suppliedFood;
        suppliedFood+=Math.min(player.bread,totalFoodSupply);
        player.bread = Math.max(player.bread-totalFoodSupply,0);
        totalFoodSupply -= suppliedFood;
        return totalFoodSupply;
    }

    public void putYourCastle(Player owner) throws Exception {
        System.out.println("----WELCOME TO THE GAME OF KINGS MY LORD----\n" +
                ">>> Take A Location And Put Your Castle <<<\n" +
                "-> YOUR CASTLE IS THE HEART OF YOUR KINGDOM\n" +
                "-> CHOOSE A LOCATION FOR IT.");
        Integer row =0;Integer column = 0;

        ArrayList<Integer> castleCoordinates = getCoordinates();

        row = castleCoordinates.get(0) ;
        column = castleCoordinates.get(1) ;
        String name = "castle";
        Building castle = new Building (name,1,1,true,"",owner,row,column,Building.getBuildingCost(name),
                10000,0,false,BuildingEnum.CASTLE , 0);
        name = "king";
        Warrior king = new Warrior(name,owner, 50 , 500,1,1,30,30,0,
                false,false,false,false,false,false,true,row.intValue(),column.intValue());
        putBuildingInPlace(castle);
        gameMap.getCell(row,column).units.add(king);
        player.addUnit(king);
        player.setCastle(castle);
        player.setPopulationCapacity(8);
        for (int i =0;i<8;++i){
            Jobless jobless = new Jobless(player,2,row,column);
            gameMap.getCell(row,column).addUnit(jobless);
        }
    }

    public void putYourStockPile(Player owner) throws Exception {
        System.out.println("Please Choose A Location And Put Your Stockpile There");
        int row , column;
        ArrayList<Integer> buffer = getCoordinates();
        row = buffer.get(0);
        column = buffer.get(1);
        String name = "stockpile";
        Building stockpile = Building.createBuildingByName(name,owner,row,column);
        putBuildingInPlace(stockpile);
        player.isStockPileCreated = true;
    }

    public ArrayList<Integer> getCoordinates () throws Exception {

        String buffer;
        int row , column ;
        while (true) {
            System.out.print("Please Enter Row Number : ");
            try {
                buffer = Menu.getScanner().nextLine();
                Integer.parseInt(buffer);
                row = Integer.parseInt(buffer);
                if (row >0 && row <400)
                    break;
                System.out.print("Row Number Is Outside The Map,Re-enter it : ");
            } catch (Exception e) {
                throw new Exception(e) ;
            }
        }
        while (true) {
            System.out.print("Please Enter Column Number : ");
            try {
                buffer = Menu.getScanner().nextLine();
                Integer.parseInt(buffer);
                column = Integer.parseInt(buffer);
                if (column >0 && column <400)
                    break;
                System.out.print("Column Number Is Outside The Map,Re-enter it : ");
            } catch (Exception e) {
                throw new Exception(e) ;
            }
        }
        Cell cell = gameMap.getCell(row , column);
        if (cell.cellType!=CellType.GROUND || !cell.units.isEmpty() || cell.getBuilding() != null ) {
            System.out.println("Sorry , Can't Place The Castle Here, Please Re-Enter The Location");
            return getCoordinates();

        }
        ArrayList<Integer> ret = new ArrayList<Integer>( Arrays.asList(row , column) ) ;
        return ret ;
    }

    public void updateBuildingFunctionalities(Player player){
        int castleRow = player.getCastle().getRow();
        int castleColumn = player.getCastle().getColumn();
        for (Building building : player.getBuildings()){
            if (building instanceof TradeBuilding && !((TradeBuilding) building).getFunctional()
                && player.getNumberOfJobless()>=((TradeBuilding) building).getRequiredNumberOfOperators()){
                    for (Unit unit : gameMap.getCell(castleRow,castleRow).units){
                        if (unit instanceof Jobless) {
                            Operator operator = new Operator(building.getName(),player,5,castleRow,castleColumn,building);
                            gameMap.getCell(castleRow,castleColumn).units.remove(unit);
                            gameMap.getCell(castleRow,castleColumn).units.add(operator);
                            player.getUnits().remove(unit);
                            player.getUnits().add(operator);
                            operator.setTarget(building.getRow(), building.getColumn(), gameMap);
                        }
                    }
                    ((TradeBuilding) building).setObjectsCount(((TradeBuilding) building).getRequiredNumberOfOperators());
                    ((TradeBuilding) building).updateFunctionality();
            }
        }
    }

    private void endGame(){
        // TODO : give credit to winner, send to server
        Building.getBuildings().clear() ;
        Unit.getUnits().clear() ;
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
        return "FEAR RATE SET TO " + rate + ( rate > 0 ? " , PEOPLE ARE FEARING YOU MY LOooOrddd... sorry." :
                " , THANKS FOR BEING NICE MY LORD." ) ;
    }


    public String selectBuilding(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error = handleSelectBuildingError(row,column))!=null)
            return error;
        Building building = gameMap.getCell(row,column).getBuilding() ;
        player.setSelectedBuilding(building);
        return "SELECTED THE BUILDING MY KING.";
    }

    public String changeGateState(Matcher matcher){
        String newState = "CLOSED" ;
        int row = Integer.parseInt(matcher.group("row")) ;
        int column = Integer.parseInt(matcher.group("column")) ;
        Building building = gameMap.getCell(row,column).getBuilding() ;
        if(building == null || !building.getName().contains( "gate" ))
            return "THERE NO GATE HERE SIR" ;
        if(!building.getPassable())
            newState = "OPENED" ;
        building.setPassable(!building.getPassable(),gameMap) ;
        return "THE GATE YOU SELECTED IS NOW " + newState ;
    }

    public String showGateState(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row")) ;
        int column = Integer.parseInt(matcher.group("column")) ;
        Building building = gameMap.getCell(row,column).getBuilding() ;
        if(building == null || !building.getName().contains( "gate" ))
            return "THERE NO GATE HERE SIR" ;
        String state = "CLOSED" ;
        if(building.getPassable()) state = "OPENED" ;
        return "THIS GATE IS " + state ;
    }

    public String handleSelectBuildingError (int row , int column){
        if (row > 400 || row < 0 || column > 400 || column <0 )
            return "SelectBuilding Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        if (cell.getBuilding() == null)
            return  "SelectBuilding Failed : Cell Does Not Contain Any Building";
        return null;
    }

    public String selectUnit(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error= handleSelectUnitError(row,column))!= null)
            return error;
        Cell cell = gameMap.getCell(row,column);
        player.setSelectedUnits(cell);
        return "UNIT SELECTED SUCCESSFULLY SIR.";
    }

    public String handleSelectUnitError (int row , int column){
        if (row > 400 || column > 400 || row < 0 || column < 0)
            return "SelectUnit Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
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
        if (gameMap.getMaskedMap()[row][column] == 1 && gameMap.getMaskedMapUpperGround()[row][column] == 1 )
            return "Move Unit Failed : Destination Is Not Permeable";

        for (Unit unit : player.getSelectedUnits()){
            unit.setTarget(row,column,gameMap);
            if(unit instanceof Warrior && ( (Warrior) unit ).getIsPatrolling())
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
                    ((Warrior)unit).attackBuilding(enemyBuilding,gameMap) ;
                }
            }
        }

        return "ATTACKING THE ENEMY" ;
    }

    public String airAttack(Matcher matcher){
        return null;
    }

    public String pourOil(Matcher matcher){
        String directionString = matcher.group("direction") ;
        int directionNum = 0 ;
        switch(directionString){
            case "right" :
                directionNum = 1 ;
                break ;
            case "down" :
                directionNum = 2 ;
                break ;
            case "left" :
                directionNum = 3 ;
                break ;
        }
        boolean selectedEngineer = false ;
        for(Unit unit : player.getSelectedUnits()){
            if(unit instanceof Engineer )
                ((Engineer)unit).pourOil(directionNum,gameMap) ;
        }
        return "AS YOU WISH MY LORD." ;
    }

    public String digTunnel(Matcher matcher){
        boolean hasTunneler = false ;
        for(Unit unit : player.getSelectedUnits()){
            if(unit instanceof Tunneler){
                hasTunneler = true ;
                ((Tunneler)unit).dig(gameMap);
            }
        }
        if(!hasTunneler) return "YOU HAVE NO TUNNELERS SIRE." ;

        return "TUNNELERS GONE UNDER GROUND MY LORD.";
    }

    public String buildEquipment(Matcher matcher){
        String equipment = matcher.group("name");
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column")) ;
        int engineerCount = 0 ;

        for( Unit unit : Unit.getUnits() ){
            if( unit.getName().equals("engineer") && unit.getOwner() == player )
                engineerCount++ ;
        }

        if( equipment.equals("catapolt") ){
            if( engineerCount < 4 ) return "YOU NEED 4 engineers" ;
            gameMap.getCell( row , column ).addUnit(
                    new Warrior( "catapolt" , player , 100 , 200 , 0 , 40 , 1 , 1 , 1 , false , false , false , false , false , false , false , row , column )
            ) ;
            return "catapolt created" ;
        } else if ( equipment.equals("firecatapolt") ){
            gameMap.getCell( row , column ).addUnit(
                    new Warrior( "catapolt" , player , 100 , 200 , 0 , 40 , 1 , 1 , 1 , true , false , false , false , false , false , false , row , column )
            ) ;
            return "firecatapolt created" ;
        } else if ( equipment.equals("dezhkoob") ){
            gameMap.getCell( row , column ).addUnit(
                    new Warrior( "dezhkoob" , player , 100 , 200 , 0 , 40 , 1 , 1 , 1 , false , false , false , false , false , false , false , row , column )
            ) ;
            return "CREATED DEZHKOOB SUCCESSFULLY" ;
        }



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
                cell.cellType == CellType.SEA){
            gameMap.getMaskedMap()[row][column] = 1 ;
            gameMap.getMaskedMapUnderGround()[row][column] = 1 ;
        }
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

        gameMap.getCell( row , column ).setBuilding( Building.createBuildingByName( treeType , player , row , column ) ) ;


        return "TREE DROPPED SUCCESSFULLY." ;
    }

    public String createBuilding (Matcher matcher) {
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String type = matcher.group("type");
        String error = dropCreateBuildingErrorHandler(row,column,type,true);
        if (error != null)
            return error;
        if(type.equals("oxtether")){
            Building oxtether = Building.createBuildingByName("oxtether",player,row,column) ;
            Operator ox = new Operator("ox" , player , 5 , row , column , oxtether ) ;
            putBuildingInPlace(oxtether);
            oxTurn.put( ox , new AtomicInteger(0) ) ;
            oxHasStone.put( ox , new AtomicBoolean(false) ) ;
            gameMap.getCell( row , column ).addUnit( ox );
            return "Ox tether dropped successfully" ;
        }
        Building building = Building.createBuildingByName(type,player,row,column);
        putBuildingInPlace(building);
        player.handleBuildingEffectsOnPlayer(type);
        return "Create Building Successful!";
    }

    public String repair(Matcher matcher){
        if (player.getSelectedBuilding() == null )
            return "Repair Failed : No Building Selected";
        String name = player.getSelectedBuilding().getName();
        Building building = player.getSelectedBuilding();
        int currentHitPoint = building.getHitPoint();
        int initialHitPoint = BuildingEnum.getHitpointByName(name);
        if (currentHitPoint == initialHitPoint || initialHitPoint == 0)
            return "Repair Failed : Building Hit Point Is Max";
        int row = building.getRow();
        int column = building.getColumn();
        for (int i =row-3;i<row+3;++i){
            for (int j = column -3;j<column +3 ;j++){
                if (isOpponentInTheCell(gameMap.getCell(row,column),player))
                    return "Repair Failed : Opponent Is Near The Building";
            }
        }
        Cost cost = Building.getBuildingCost(name);
        double proportion = currentHitPoint/initialHitPoint;
        cost.alterCost(proportion);
        if (player.decreaseCost(cost) != null)
            return "Repair Failed : " + player.decreaseCost(cost);
        building.setHitPoint(initialHitPoint);
        return "Repaired Successful!";
    }

    public boolean isOpponentInTheCell(Cell cell , Player owner){
        for (Unit unit : cell.units){
            if (unit.getOwner().equals(owner))
                return true;
        }
        return false;
    }

    public String dropBuilding(Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String type = matcher.group("type");
        String error = dropCreateBuildingErrorHandler(row,column,type,false);
        if (error != null)
            return error;
        if(type.equals("oxtether")){
            Building oxtether = Building.createBuildingByName("oxtether",player,row,column) ;
            Operator ox = new Operator("ox" , player , 5 , row , column , oxtether ) ;
            putBuildingInPlace(oxtether);
            gameMap.getCell( row , column ).addUnit( ox );
            oxTurn.put( ox , new AtomicInteger(0) ) ;
            oxHasStone.put( ox , new AtomicBoolean(false) ) ;
            return "Ox tether dropped successfully" ;
        }
        Building building = Building.createBuildingByName(type,player,row,column);
        putBuildingInPlace(building);
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
        // If This Is Stockpile Or Granary : Is There Another stockpile/granary near it
        String canBePlacedHere = player.canBuildingPlacedHere(type,row,column,gameMap);
        if (canBePlacedHere != null)
            return canBePlacedHere;
        // is there enough space here to place this building
        String isHereEmpty = isThereBuildingConflict(type,row,column);
        if (isHereEmpty!= null)
            return isHereEmpty;
        //Is Cell Texture Appropriate
        if (createBuilding) {
            String result = isPlaceAppropriateForBuilding(row, column, type);
            if (result != null)
                return result;
        }
        return null;
    }

    private boolean validPos(int x , int y){
        return validRowColumn( x ) & validRowColumn( y ) ;
    }

    public String isThereBuildingConflict (String buildingName,int row , int column){
        int height = BuildingEnum.getBuildingHeightByName(buildingName);
        int width = BuildingEnum.getBuildingWidthByName(buildingName);
        if (!( validPos( row + height , column + width ) && validPos( row , column ) ))
            return "Building Can't Be Placed Here : It Exceeds The Map";
        for (int i = row ; i<row+height;++i){
            for (int j = column ; j < column + width;++j){
                if (gameMap.getCell(i,j).getBuilding() != null)
                    return "Building Can't Be Placed Here : Another Building Is Here";
                if (!gameMap.getCell(i,j).units.isEmpty())
                    return "Building Can't Be Placed Here : Some Units Are Here";
            }
        }
        return null;
    }

    public String isPlaceAppropriateForBuilding(int row , int column , String type){
        BuildingEnum buildingEnum = BuildingEnum.getBuildingEnumByName(type);
        ArrayList<CellType> array = getAppropriateCellTypeForABuilding(buildingEnum);
        for (int i = row ; i <row + BuildingEnum.getBuildingHeightByName(type);++i){
            for (int j = column; j < column+BuildingEnum.getBuildingWidthByName(type);++j){
                if (!array.contains(gameMap.getCell(i,j).getCellType()))
                    return "This Cell Texture Isn't Appropriate For Placing Building";
            }
        }
        return null;
    }

    public ArrayList<CellType> getAppropriateCellTypeForABuilding(BuildingEnum buildingEnum){
        ArrayList<CellType> array = new ArrayList<>();
        if (buildingEnum == BuildingEnum.APPLE_ORCHARD
            ||buildingEnum == BuildingEnum.WHEAT_FARM
            || buildingEnum == BuildingEnum.HOPS_FARMER
            || buildingEnum == BuildingEnum.DIARY_FARMER
            || buildingEnum == BuildingEnum.HUNTER_POST)
        {
            array.add(CellType.GRASS);
            array.add(CellType.GRASSLAND);
            array.add(CellType.MEADOW);
            return array;
        }
        if (buildingEnum == BuildingEnum.QUARRY){
            array.add(CellType.ROCK_MINE);
            return array;
        }
        if (buildingEnum == BuildingEnum.IRON_MINE){
            array.add(CellType.IRON_MINE);
            return array;
        }
        if (buildingEnum == BuildingEnum.PITCH_RIG){
            array.add(CellType.OIL_WELL);
            return array;
        }
        array.add(CellType.GROUND);
        array.add(CellType.GRASS);
        array.add(CellType.GRASSLAND);
        array.add(CellType.MEADOW);
        return array;
    }


    public void putBuildingInPlace( Building building ){
        int row = building.getRow();
        int column = building.getColumn();
        building.getOwner().addBuilding(building);
        for (int i = row ; i < row + building.getHeight(); i ++)
            for (int j = column ; j < column + building.getWidth(); j ++)
            {
                gameMap.getCell(i,j).setBuilding(building);
                if(!building.getPassable()) gameMap.getMaskedMap()[i][j] = 1 ;
                if(building.getName().contains( "gate" ) || building.getName().contains( "wall" ) || building.getName().contains( "tower" ) )
                    gameMap.getMaskedMapUpperGround()[i][j] = 0 ;
            }

        if(building.getName().contains( "stair" ))
            gameMap.getMaskedMapUpperGround()[row][column] = gameMap.getMaskedMap()[row][column] = 2 ;
    }

    public String dropUnit( String type , int count , int row , int column ){
        String error;
        if ((error = dropCreateUnitErrorChecker(type,count,row,column,false))!=null)
            return error;
        Unit unit = Unit.createUnitByName(type,player,row,column);
        unit.setShape() ;
        unit.setWalkingAnimations() ;
        unit.setPathFinder( gameMap ) ;
        gameMap.getCell(row,column).addUnit(unit);
        player.addUnit(unit);
        if (type.equals("trap"))
            unit.setUnitMode(UnitModeEnum.AGGRESSIVE);
        return null ;
    }

    public String createUnit(Matcher matcher){
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        Building building = player.getSelectedBuilding() ;
        if(building == null || !building.getName().equals("barracks") || !building.getName().equals("mercenarypost"))
            return "YOU SHOULD SELECT BARRACKS OR MERCENARY OR TUNNELER GUILD OR ENGINEER GUILD POST , MY DEAR LOVABLE POWERFUL LORD." ;
        String error;
        String[] barracksWarriors = { "archer" , "crossbowman" , "spearman" , "pikeman" , "maceman" , "swordsman" ,
        "knight" } ;
        String[] mercenaryWarriors = { "archerbow" , "slave" , "slinger" , "assassin" , "horseswordsman" , "firethrower" } ;
        String[] tunnelerGuildUnits = { "tunneler" } ;
        String[] engineerGuildUnits = { "engineer" , "ladderman" } ;
        boolean correctName = false ;
        if( building.getName().equals("barracks") ){
            for(String name : barracksWarriors) if(name.equals( type )) correctName = true ;
        } else if( building.getName().equals("mercenarypost") ){
            for(String name : mercenaryWarriors) if(name.equals( type )) correctName = true ;
        } else if( building.getName().equals("tunnelerguild") ){
            for(String name : tunnelerGuildUnits) if(name.equals( type )) correctName = true ;
        } else if( building.getName().equals("engineerguild") ) {
            for(String name : engineerGuildUnits) if(name.equals( type )) correctName = true ;
        } else {
            return "INVALID NAME FOR BUILDING" ;
        }
        if ((error = dropCreateUnitErrorChecker(type,count,row,column,true))!=null)
            return error;
        Cost cost = Unit.getCostByName(type);
        String enoughCost = player.decreaseCost(cost);
        if (enoughCost!=null)
            return enoughCost;
        Unit unit = Unit.createUnitByName(type,player,row,column);
        gameMap.getCell(row,column).addUnit(unit);
        player.addUnit(unit);
        unit.setShape() ;
        unit.setWalkingAnimations() ;
        if (type.equals("trap"))
            unit.setUnitMode(UnitModeEnum.AGGRESSIVE);
        return "Unit Created Successfully!";
    }

    public String dropCreateUnitErrorChecker(String type , int count , int row , int column , boolean create){
        if (row > 400 || row <0 || column >400 || column < 0)
            return "Drop/Create Unit Failed : x or y exceeded map!";
        if (count <=0)
            return "Drop/Create Failed : count is smaller than 1!";
        Cell cell = gameMap.getCell(row,column);
        UnitTypeEnum unitTypeEnum = UnitTypeEnum.getUnitTypeByName(type);
        if( create ){
            String error = player.decreaseCost( Unit.getCostByName( type ) ) ;
            if(error!=null)
                return error ;
        }
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
                    ,0, 0) ;
        else if( resourceType.equals("cheese") )
            cost = new Cost(0,amount,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("bread") )
            cost = new Cost(0,0,amount,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("meat") )
            cost = new Cost(0,0,0,amount,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("bow") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    amount,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("crossbow") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,amount,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("spear") )
            cost = new Cost(0,0,0,0,0,0,0,0,amount,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("pike") )
            cost = new Cost(0,0,0,0,0,0,0,amount,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("mace") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,amount,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("sword") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,amount,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("leatherarmor") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,amount,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("metalarmor") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,amount,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("wheat") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,amount
                    ,0, 0) ;
        else if( resourceType.equals("flour") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,amount,0
                    ,0, 0) ;
        else if( resourceType.equals("hop") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,amount,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("ale") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,amount,0,0
                    ,0, 0) ;
        else if( resourceType.equals("stone") )
            cost = new Cost(0,0,0,0,0,0,amount,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("iron") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,amount, 0) ;
        else if( resourceType.equals("wood") )
            cost = new Cost(0,0,0,0,0,amount,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0
                    ,0, 0) ;
        else if( resourceType.equals("pitch") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,
                    0,0,0,0,amount,0,0,0,0,0,0,0
                    ,0, 0) ;
        else
            return "RESOURCE TYPE INVALID." ;

        if( price < 0 )
            return "PRICE CAN'T BE NEGATIVE MY LORD." ;
        //new Trade( player , message , cost , price , amount , resourceType ) ;
        return "TRADE SUCCESSFULLY HAS BEEN PUBLISHED" ;
    }

    public String tradeList(Matcher matcher){
//        String ret = "TRADE LIST :" ;
//        int index = 1 ;
//        ret += "----------------------------------------" ;
//        for( Trade trade : Trade.getTrades() ){
//            ret += "\nINDEX : " + index + " < " ;
//            ret += "\nPlayer : " + trade.getPlayer1().getNickname() ;
//            ret += "\n  -> Price : " + trade.getPrice() ;
//            ret += "\n  -> Amount : " + trade.getAmount() ;
//            ret += "\n  -> Resource : " + trade.getResourceType() ;
//            ret += "\n  -> Message : " + trade.getMessage1() ;
//            if( trade.getOpen() ) ret += "\n>>>>TRADE IS OPEN<<<<" ;
//            else ret += "\n>>>>TRADE IS CLOSED<<<<\n  -> Cosing Message : " + trade.getMessage2() + "\n>>>>TRADE IS CLOSED<<<<" ;
//            ret += "\n----------------------------------------" ;
//            index++ ;
//        }
        return "";
    }

    public String tradeAccept(Matcher matcher){
//        int id = Integer.parseInt(matcher.group("id")) - 1 ;
//        String message = matcher.group("message") ;
//        if( id < 0 || id >= Trade.getTrades().size() )
//            return "INVALID ID." ;
//        Trade trade = Trade.getTrades().get(id) ;
//        Cost cost = trade.getCost() ;
//        if( player.equals(trade.getPlayer1()) )
//            return "YOU CAN NOT TRADE WITH YOURSELF MY LORD" ;
//        if( !trade.getOpen() ) return "TRADE NOT AVAILABLE" ;
//        String outputOfDecreaseCost = player.decreaseCost( cost ) ;
//        player.increaseGold( trade.getPrice() );
//        trade.getPlayer1().decreaseCost( Cost.negative( cost ) ) ;
//        if( outputOfDecreaseCost != null ) return outputOfDecreaseCost ;
//        trade.setMessage2( message ) ;
//        trade.setPlayer2( player ) ;
//        trade.setOpen(false) ;
        return "YOU HAVE TRADED WITH OTHER KINGDOMS SUCCESSFULLY , MY LORD." ;
    }

    public String tradeHistory(Matcher matcher){
//        String ret = "" ;
//        ret += "TRADES YOU STARTED : " ;
//        Trade trade ;
//        for(int i = 0 ; i < Trade.getTrades().size() ; i++){
//            trade = Trade.getTrades().get(i) ;
//            if( trade.getPlayer1() == player ){
//                ret += "\n----------------------------------------" ;
//                ret += "INDEX " + (i + 1) + "\n";
//                ret += "\nPrice : " + trade.getPrice() ;
//                ret += "\nAmount : " + trade.getAmount() ;
//                ret += "\nResource : " + trade.getResourceType() ;
//                ret += "\nMessage : " + trade.getMessage1() ;
//                if( trade.getMessage2() == null ) ret += "\n>>>>TRADE IS OPEN<<<<" ;
//                else ret += "\n  -> Cosing Message : " + trade.getMessage2() + "\n>>>>TRADE IS CLOSED<<<<" ;
//                ret += "\n----------------------------------------" ;
//            }
//        }
//        ret += "\nTRADES YOU ACCEPTED : " ;
//        for(int i = 0 ; i < Trade.getTrades().size() ; i++){
//            trade = Trade.getTrades().get(i) ;
//            if( trade.getPlayer2() == player ){
//                ret += "\n----------------------------------------" ;
//                ret += "INDEX " + (i + 1) + "\n";
//                ret += "\nPrice : " + trade.getPrice() ;
//                ret += "\nAmount : " + trade.getAmount() ;
//                ret += "\nResource : " + trade.getResourceType() ;
//                ret += "\nMessage : " + trade.getMessage1() ;
//                if( trade.getMessage2() == null ) ret += "\n>>>>TRADE IS OPEN<<<<" ;
//                else ret += "\n  -> Cosing Message : " + trade.getMessage2() + "\n>>>>TRADE IS CLOSED<<<<" ;
//                ret += "\n----------------------------------------" ;
//            }
//        }
        return "" ;
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
        if ( player.getSelectedBuilding() != null && player.getSelectedBuilding().getBuildingEnum()!=BuildingEnum.MARKET)
            return "You Should Select Market First";
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
        if (player.getSelectedBuilding().getBuildingEnum()!=BuildingEnum.MARKET)
            return "You Should Select Market First";
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
