package org.example.view;

import org.example.controller.GameController;
import org.example.model.Account;
import org.example.model.Commands;
import org.example.model.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {

    public static void run ( Matcher playersMatcher ){
        System.out.println("WELCOME TO YOUR KINGDOM MY LORD.") ;
        ArrayList<Account> accounts = new ArrayList<Account>() ;
        Pattern patternUsername = Pattern.compile("(?<username>\\S+)") ;
        Matcher matcherUsername = patternUsername.matcher(playersMatcher.group("usernames"));
        while( matcherUsername.find() ){
            accounts.add( Account.getAccountsMap().get(matcherUsername.group("username")) ) ;
        }
        GameController gameController = new GameController(accounts);
        String input;
        while (true){
            input = Menu.getScanner().nextLine();
            if (Commands.getMatchingMatcher(input,Commands.SHOW_MAP)!=null){
                MapMenu.run(Commands.getMatchingMatcher(input,Commands.SHOW_MAP),gameController.getGameMap()) ;
                System.out.println( "YOU CAME BACK FROM MAP MENU TO GAME MENU" ) ;
            }
            else if( input.equals("next turn") )
                gameController.nextTurn() ;
            else if (Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY_FACTORS)!=null)
                System.out.println( gameController.showPopularityFactors(Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY_FACTORS)));
            else if (Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY)!=null)
                System.out.println( gameController.showPopularity(Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY)));

            else if (Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_TYPES)!=null)
                System.out.println( gameController.showFoodList(Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_TYPES)));

            else if (Commands.getMatchingMatcher(input,Commands.SET_FOOD_RATE)!=null)
                System.out.println( gameController.setFoodRate(Commands.getMatchingMatcher(input,Commands.SET_FOOD_RATE)));

            else if (Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_RATE)!=null)
                System.out.println( gameController.showFoodRate(Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_RATE)));

            else if (Commands.getMatchingMatcher(input,Commands.SET_TAX_RATE)!=null)
                System.out.println( gameController.setTaxRate(Commands.getMatchingMatcher(input,Commands.SET_TAX_RATE)));

            else if (Commands.getMatchingMatcher(input,Commands.SHOW_TAX_RATE)!=null)
                System.out.println( gameController.showTaxRate(Commands.getMatchingMatcher(input,Commands.SHOW_TAX_RATE)));

            else if (Commands.getMatchingMatcher(input,Commands.SET_FEAR_RATE)!=null)
                System.out.println( gameController.setFearRate(Commands.getMatchingMatcher(input,Commands.SET_FEAR_RATE)));

            else if (Commands.getMatchingMatcher(input,Commands.DROP_BUILDING)!=null)
                System.out.println( gameController.dropBuilding(Commands.getMatchingMatcher(input,Commands.DROP_BUILDING)));

            else if (Commands.getMatchingMatcher(input,Commands.SELECT_BUILDING)!=null)
                System.out.println( gameController.selectBuilding(Commands.getMatchingMatcher(input,Commands.SELECT_BUILDING)));

            else if (Commands.getMatchingMatcher(input,Commands.CREATE_UNIT)!=null)
                System.out.println( gameController.createUnit(Commands.getMatchingMatcher(input,Commands.CREATE_UNIT)));

            else if (Commands.getMatchingMatcher(input,Commands.REPAIR)!=null)
                System.out.println( gameController.repair(Commands.getMatchingMatcher(input,Commands.REPAIR)));

            else if (Commands.getMatchingMatcher(input,Commands.SELECT_UNIT)!=null)
                System.out.println(gameController.selectUnit(Commands.getMatchingMatcher(input,Commands.SELECT_UNIT)));

            else if (Commands.getMatchingMatcher(input,Commands.MOVE_UNIT)!=null)
                gameController.moveUnit(Commands.getMatchingMatcher(input,Commands.MOVE_UNIT));

            else if (Commands.getMatchingMatcher(input,Commands.PATROL)!=null)
                System.out.println( gameController.patrolUnit(Commands.getMatchingMatcher(input,Commands.PATROL)));

            else if (Commands.getMatchingMatcher(input,Commands.SET_UNIT_STATE)!=null)
                System.out.println( gameController.setState(Commands.getMatchingMatcher(input,Commands.SET_UNIT_STATE)));

            else if (Commands.getMatchingMatcher(input,Commands.ATTACK)!=null)
                System.out.println( gameController.attack(Commands.getMatchingMatcher(input,Commands.ATTACK)));

            else if (Commands.getMatchingMatcher(input,Commands.AIR_ATTACK)!=null)
                System.out.println( gameController.airAttack(Commands.getMatchingMatcher(input,Commands.AIR_ATTACK)));

            else if (Commands.getMatchingMatcher(input,Commands.POUR_OIL)!=null)
                System.out.println( gameController.pourOil(Commands.getMatchingMatcher(input,Commands.POUR_OIL)));

            else if (Commands.getMatchingMatcher(input,Commands.DIG_TUNNEL)!=null)
                System.out.println( gameController.digTunnel(Commands.getMatchingMatcher(input,Commands.DIG_TUNNEL)));

            else if (Commands.getMatchingMatcher(input,Commands.BUILD_EQUIPMENT)!=null)
                System.out.println( gameController.buildEquipment(Commands.getMatchingMatcher(input,Commands.BUILD_EQUIPMENT)));

            else if (Commands.getMatchingMatcher(input,Commands.DISBAND)!=null)
                System.out.println( gameController.disbandUnit(Commands.getMatchingMatcher(input,Commands.DISBAND)));

            else if (Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_CELL)!=null)
                System.out.println( gameController.setTextureCell(Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_CELL)));

            else if (Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_BLOCK)!=null)
                System.out.println( gameController.setTextureBlock(Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_BLOCK)));

            else if (Commands.getMatchingMatcher(input,Commands.CLEAR_CELL)!=null)
                System.out.println( gameController.clear(Commands.getMatchingMatcher(input,Commands.CLEAR_CELL)));

            else if (Commands.getMatchingMatcher(input,Commands.DROP_ROCK)!=null)
                System.out.println( gameController.dropRock(Commands.getMatchingMatcher(input,Commands.DROP_ROCK)));

            else if (Commands.getMatchingMatcher(input,Commands.DROPTREE)!=null)
                System.out.println( gameController.dropTree(Commands.getMatchingMatcher(input,Commands.DROPTREE)));

            else if (Commands.getMatchingMatcher(input,Commands.CREATE_BUILDING)!=null)
                System.out.println( gameController.createBuilding(Commands.getMatchingMatcher(input,Commands.CREATE_BUILDING)));

            else if (Commands.getMatchingMatcher(input,Commands.DROP_UNIT)!=null)
                System.out.println( gameController.dropUnit(Commands.getMatchingMatcher(input,Commands.DROP_UNIT)));

            else if (Commands.getMatchingMatcher(input,Commands.TRADE_REQUEST)!=null)
                System.out.println( gameController.tradeRequest(Commands.getMatchingMatcher(input,Commands.TRADE_REQUEST)));

            else if (Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_LIST)!=null)
                System.out.println( gameController.tradeList(Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_LIST)));

            else if (Commands.getMatchingMatcher(input,Commands.TRADE_ACCEPT)!=null)
                System.out.println( gameController.tradeAccept(Commands.getMatchingMatcher(input,Commands.TRADE_ACCEPT)));

            else if (Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_HISTORY)!=null)
                System.out.println( gameController.tradeHistory(Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_HISTORY)));

            else if (Commands.getMatchingMatcher(input,Commands.SHOW_PRICE_LIST)!=null)
                System.out.println( gameController.showPriceList(Commands.getMatchingMatcher(input,Commands.SHOW_PRICE_LIST)));

            else if (Commands.getMatchingMatcher(input,Commands.BUY)!=null)
                System.out.println( gameController.buy(Commands.getMatchingMatcher(input,Commands.BUY)));

            else if (Commands.getMatchingMatcher(input,Commands.SELL)!=null)
                System.out.println( gameController.sell(Commands.getMatchingMatcher(input,Commands.SELL)));

            else
                System.out.println("Invalid Command");

            // checking if game is finished
            if( gameController.getWinner() != null )
                return ;
        }
    }
}
