package org.example.controller;

import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {

    ArrayList<Player> players ;
    public static int maxRow = 400;
    public static int maxColumn = 400;
    ArrayList <Account> accounts ;
    private int turn ;
    private Player winner ;
    Player player ;
    private GameMap gameMap;

    public GameController( ArrayList<Account> accounts ){
        this.accounts = accounts ;
        this.players = new ArrayList<Player>() ;
        for( Account acc : accounts ){
            this.players.add( new Player( acc ) ) ;
        }
        this.player = this.players.get(0) ;
        this.turn = 0 ;
        this.winner = null ;
        gameMap = new GameMap(maxRow,maxColumn);
    }

    public void nextTurn(){
        this.turn++ ;
        this.player = this.players.get( this.turn % this.players.size() ) ;
    }

    private void endGame(){
        System.out.println( "\nTHIS MATCH HAS ENDED AFTER " + this.turn / accounts.size() + " ROUNDS \n" +
                "THE WINNER IS " + winner.getAccount().getNickName() ) ;
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
        return null;
    }


    public String dropBuilding(Matcher matcher){
        return null;
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
        if (row > maxRow || row < 0 || column > maxColumn || column <0 )
            return "SelectBuilding Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
        if (cell.buildings.size()==0)
            return  "SelectBuilding Failed : Cell Does Not Contain Any Building";
        return null;
    }

    public String createUnit(Matcher matcher){
        return null;
    }

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
        if (row > maxRow || column > maxColumn || row < 0 || column < 0)
            return "SelectUnit Failed : Row Or Column Exceeded Map";
        Cell cell = gameMap.getCell(row,column);
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

    public void moveUnit(Matcher matcher){

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
        Cell cell = gameMap.getCell(row,column);
        player.setState(state);
        return "SetState Successful!";
    }

    public String handleSetStateError(int row,int column){
        if (row > maxRow || row <0 || column > maxColumn || column <0)
            return "SetState Failed : Row Or Column Exceeded Map";
        if (player.getSelectedUnits().isEmpty())
            return "SetState Failed : No Selected Unit";
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

    public String disbandUnit(Matcher matcher){
        return null;
    }

    public String setTextureCell (Matcher matcher){
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        if (row > maxRow || row <0 || column >maxColumn || column < 0)
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
        if (endRow > maxRow || endRow < 0 || endColumn >maxColumn || endColumn <0)
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
        if (row > maxRow || row <0 || column > maxColumn || column < 0)
            return "Clear Failed : Row Or Column Exceeded Map!";
        gameMap.getCell(row,column).units.clear();
        gameMap.getCell(row,column).cellType = CellType.GROUND;
        return "Cell Cleared Successfully!";
    }

    public String dropRock(Matcher matcher){
        return null;
    }

    public String dropTree(Matcher matcher){
        return null;
    }

    public String replaceBuilding (Matcher matcher){
        //TODO : This Function Is Equal To DropBuilding In Beginning Of The Game
        return null;
    }

    public String dropUnit(Matcher matcher){
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        String error;
        if ((error = dropUnitErrorChecker(type,count,row,column))!=null)
            return error;
        Unit unit = Unit.createUnitByName(type,player);
        Cell cell = gameMap.getCell(row,column);
        while (--count>0)
            cell.addUnit(unit);
        return "Unit Dropped Successfully!";
    }

    public String dropUnitErrorChecker(String type , int count , int row , int column){
        if (row > maxRow || row <0 || column >maxColumn || column < 0)
            return "DropUnit Failed : x or y exceeded map!";
        if (count <=0)
            return "DropUnit Failed : count is smaller than 1!";
        Cell cell = gameMap.getCell(row,column);
        UnitTypeEnum unitTypeEnum = UnitTypeEnum.getUnitTypeByName(type);
        if (unitTypeEnum == null)
            return "DropUnit Failed : Unit Type Does Not Exists!";
        if (!cell.permeable(unitTypeEnum))
            return "DropUnit Failed : Cell Is Not Permeable!";
        return null;
    }

    public String tradeRequest (Matcher matcher){
        return null;
    }

    public String tradeList(Matcher matcher){
        return null;
    }

    public String tradeAccept(Matcher matcher){
        return null;
    }

    public String tradeHistory(Matcher matcher){
        return null;
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
