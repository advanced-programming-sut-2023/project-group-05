package org.example.controller;

import org.example.model.*;
import org.example.model.building.Building;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {

    ArrayList<Player> players ;
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
        gameMap = new GameMap(400,400);
    }

    public void nextTurn(){
        this.turn++ ;
        this.player = this.players.get( this.turn % this.players.size() ) ;
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

    public String dropBuilding(Matcher matcher){
        return null;
    }

    public String selectBuilding(Matcher matcher){
        int r = Integer.parseInt(matcher.group("row")) ;
        int c = Integer.parseInt(matcher.group("column")) ;
        Building building = this.gameMap.getCell( r , c ).getBuilding() ;
        if( building == null ){
            return "THERE IS NO BUILDING IN THIS PLACE" ;
        }
        player.selectBuilding( building );
        return "BUILDING HAS BEEN SELECTED." ;
    }

    public String createUnit(Matcher matcher){
        return null;
    }

    public String repair(Matcher matcher){
        return null;
    }

    public void selectUnit(Matcher matcher){

    }

    public void moveUnit(Matcher matcher){

    }

    public String patrolUnit(Matcher matcher){
        return null;
    }

    public String setState(Matcher matcher){
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
        return null;
    }

    public String setTextureBlock(Matcher matcher){
        return null;
    }

    public String clear (Matcher matcher){
        return null;
    }

    public String dropRock(Matcher matcher){
        return null;
    }

    public String dropTree(Matcher matcher){
        return null;
    }

    public String replaceBuilding (Matcher matcher){
        return null;
    }

    public String dropUnit(Matcher matcher){
        return null;
    }

    public String tradeRequest (Matcher matcher){
        String resourceType = matcher.group("resourceType") ;
        int amount = Integer.parseInt( matcher.group("amount") ) ;
        String message = matcher.group("message") ;
        int price = Integer.parseInt(matcher.group("price")) ;
        Cost cost ;

        if( resourceType.equals("apple") )
            cost = new Cost(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0) ;
        else if( resourceType.equals("cheese") )
            cost = new Cost() ;
        else if( resourceType.equals("bread") )
            cost = new Cost() ;
        else if( resourceType.equals("meat") )
            cost = new Cost() ;
        else if( resourceType.equals("bow") )
            cost = new Cost() ;
        else if( resourceType.equals("crossbow") )
            cost = new Cost() ;
        else if( resourceType.equals("spear") )
            cost = new Cost() ;
        else if( resourceType.equals("pike") )
            cost = new Cost() ;
        else if( resourceType.equals("mace") )
            cost = new Cost() ;
        else if( resourceType.equals("sword") )
            cost = new Cost() ;
        else if( resourceType.equals("leatherarmor") )
            cost = new Cost() ;
        else if( resourceType.equals("metalarmor") )
            cost = new Cost() ;
        else if( resourceType.equals("wheat") )
            cost = new Cost() ;
        else if( resourceType.equals("flour") )
            cost = new Cost() ;
        else if( resourceType.equals("hop") )
            cost = new Cost() ;
        else if( resourceType.equals("ale") )
            cost = new Cost() ;
        else if( resourceType.equals("stone") )
            cost = new Cost() ;
        else if( resourceType.equals("iron") )
            cost = new Cost() ;
        else if( resourceType.equals("wood") )
            cost = new Cost() ;
        else if( resourceType.equals("pitch") )
            cost = new Cost() ;
        else
            return "RESOURCE TYPE INVALID." ;
        return "TRADE SUCCESSFULLY HAS BEEN PUBLISHED" ;
    }

    public String tradeList(Matcher matcher){
        String ret = "TRADE LIST :" ;
        for( Trade trade : Trade.getTrades() ){
            ret += "\nPlayer : " + trade.getPlayer().getAccount().getNickName() ;
            ret += "\n -> Price : " + trade.getPrice() ;
            ret += "\n -> Amount : " + trade.getAmount() ;
            ret += "\n -> Resource : " + trade.getResourceType() ;
        }
        return ret ;
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
