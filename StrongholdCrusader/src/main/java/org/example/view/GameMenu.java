package org.example.view;

import org.example.controller.GameController;
import org.example.model.Account;
import org.example.model.Commands;

import java.util.regex.Matcher;

public class GameMenu {
    public GameController gameControllerController;
    public GameMenu ( GameController gameControllerController ){
        this.gameControllerController = gameControllerController;
    }

    public void run ( Account host , Account guest ){
        GameController gameController = new GameController( host , guest ) ;
        String input;
        Matcher matcher;
        while (true){
            input = Menu.getScanner().nextLine();

            if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_MAP))!=null)
                System.out.println( gameControllerController.showMap(matcher));

            else if( input.equals("next turn") )
                gameController.nextTurn() ;

            else if ((matcher =Commands.getMatchingMatcher(input,Commands.SHOW_DETAILS))!=null)
                System.out.println( gameControllerController.showDetails(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.NAVIGATE_MAP))!=null)
                System.out.println( gameControllerController.mapNavigation(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY_FACTORS))!=null)
                System.out.println( gameControllerController.showPopularityFactors(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY))!=null)
                System.out.println( gameControllerController.showPopularity(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_TYPES))!=null)
                System.out.println( gameControllerController.showFoodList(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_FOOD_RATE))!=null)
                System.out.println( gameControllerController.setFoodRate(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_RATE))!=null)
                System.out.println( gameControllerController.showFoodRate(matcher));

            else if ((matcher =Commands.getMatchingMatcher(input,Commands.SET_TAX_RATE))!=null)
                System.out.println( gameControllerController.setTaxRate(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_TAX_RATE))!=null)
                System.out.println( gameControllerController.showTaxRate(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_FEAR_RATE))!=null)
                System.out.println( gameControllerController.setFearRate(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROP_BUILDING))!=null)
                System.out.println( gameControllerController.dropBuilding(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SELECT_BUILDING))!=null)
                System.out.println( gameControllerController.selectBuilding(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CREATE_UNIT))!=null)
                System.out.println( gameControllerController.createUnit(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.REPAIR))!=null)
                System.out.println( gameControllerController.repair(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SELECT_UNIT))!=null)
                System.out.println( gameControllerController.selectUnit(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.MOVE_UNIT))!=null)
                System.out.println( gameControllerController.moveUnit(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.PATROL))!=null)
                System.out.println( gameControllerController.patrolUnit(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_UNIT_STATE))!=null)
                System.out.println( gameControllerController.setState(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.ATTACK))!=null)
                System.out.println( gameControllerController.attack(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.AIR_ATTACK))!=null)
                System.out.println( gameControllerController.airAttack(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.POUR_OIL))!=null)
                System.out.println( gameControllerController.pourOil(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DIG_TUNNEL))!=null)
                System.out.println( gameControllerController.digTunnel(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.BUILD_EQUIPMENT))!=null)
                System.out.println( gameControllerController.buildEquipment(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISBAND))!=null)
                System.out.println( gameControllerController.disbandUnit(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_CELL))!=null)
                System.out.println( gameControllerController.setTextureCell(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_BLOCK))!=null)
                System.out.println( gameControllerController.setTextureBlock(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CLEAR_CELL))!=null)
                System.out.println( gameControllerController.clear(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROP_ROCK))!=null)
                System.out.println( gameControllerController.dropRock(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROPTREE))!=null)
                System.out.println( gameControllerController.dropTree(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.REPLACE_BUILDING))!=null)
                System.out.println( gameControllerController.replaceBuilding(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROP_UNIT))!=null)
                System.out.println( gameControllerController.dropUnit(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.TRADE_REQUEST))!=null)
                System.out.println( gameControllerController.tradeRequest(matcher));

            else if ((matcher =Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_LIST))!=null)
                System.out.println( gameControllerController.tradeList(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.TRADE_ACCEPT))!=null)
                System.out.println( gameControllerController.tradeAccept(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_HISTORY))!=null)
                System.out.println( gameControllerController.tradeHistory(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_PRICE_LIST))!=null)
                System.out.println( gameControllerController.showPriceList(matcher));


            else if ((matcher = Commands.getMatchingMatcher(input,Commands.BUY))!=null)
                System.out.println( gameControllerController.buy(matcher));

            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SELL))!=null)
                System.out.println( gameControllerController.sell(matcher));

            else
                System.out.println("Invalid Command");

            // checking if game is finished
            if( gameController.getWinner() != null )
                return ;
        }
    }
}
