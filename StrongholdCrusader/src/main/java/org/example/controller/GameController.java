package org.example.controller;

import org.example.model.Account;
import org.example.model.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {

    ArrayList<Player> players ;
    ArrayList <Account> accounts ;
    private int turn ;
    private Player winner ;

    public GameController( ArrayList<Account> accounts ){
        this.accounts = accounts ;
        for( Account acc : accounts ){
            this.players.add( new Player( acc ) ) ;
        }
        this.turn = 0 ;
        this.winner = null ;
    }

    public void nextTurn(){
        this.turn++ ;
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

    public String showMap(Matcher matcher){
        return null;
    }

    public String mapNavigation(Matcher matcher){
        return null;
    }

    public String showDetails(Matcher matcher){
        return null;
    }

    public String showPopularityFactors(Matcher matcher){
        return null;
    }

    public String showPopularity(Matcher matcher){
        return null;
    }

    public String showFoodList(Matcher matcher){
        return null;
    }

    public String setFoodRate(Matcher matcher){
        return null;
    }

    public String showFoodRate(Matcher matcher){
        return null;
    }

    public String setTaxRate(Matcher matcher){
        return null;
    }

    public String showTaxRate (Matcher matcher){
        return null;
    }

    public String setFearRate (Matcher matcher){
        return null;
    }

    public String dropBuilding(Matcher matcher){
        return null;
    }

    public String selectBuilding(Matcher matcher){
        return null;
    }

    public String createUnit(Matcher matcher){
        return null;
    }

    public String repair(Matcher matcher){
        return null;
    }

    public String selectUnit(Matcher matcher){
        return null;
    }

    public String moveUnit(Matcher matcher){
        return null;
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
