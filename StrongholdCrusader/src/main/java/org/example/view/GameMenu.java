package org.example.view;

import com.sun.org.apache.xpath.internal.operations.Neg;
import org.example.controller.GameMenuController;
import org.example.model.Commands;

import java.util.TreeMap;
import java.util.regex.Matcher;

public class GameMenu {
    public GameMenuController gameMenuController;
    public GameMenu (GameMenuController gameMenuController){
        this.gameMenuController = gameMenuController;
    }

    public void run (){
        String input;
        Matcher matcher;
        while (true){
            input = Menu.getScanner().nextLine();
            if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_MAP))!=null)
                System.out.println(gameMenuController.showMap(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.NAVIGATE_MAP))!=null)
                System.out.println(gameMenuController.mapNavigation(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY_FACTORS))!=null)
                System.out.println(gameMenuController.showPopularityFactors(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_POPULARITY))!=null)
                System.out.println(gameMenuController.showPopularity(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_TYPES))!=null)
                System.out.println(gameMenuController.showFoodList(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_FOOD_RATE))!=null)
                System.out.println(gameMenuController.setFoodRate(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_FOOD_RATE))!=null)
                System.out.println(gameMenuController.showFoodRate(matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.SET_TAX_RATE))!=null)
                System.out.println(gameMenuController.setTaxRate(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_TAX_RATE))!=null)
                System.out.println(gameMenuController.showTaxRate(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_FEAR_RATE))!=null)
                System.out.println(gameMenuController.setFearRate(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROP_BUILDING))!=null)
                System.out.println(gameMenuController.dropBuilding(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SELECT_BUILDING))!=null)
                System.out.println(gameMenuController.selectBuilding(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CREATE_UNIT))!=null)
                System.out.println(gameMenuController.createUnit(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.REPAIR))!=null)
                System.out.println(gameMenuController.repair(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SELECT_UNIT))!=null)
                System.out.println(gameMenuController.selectUnit(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.MOVE_UNIT))!=null)
                System.out.println(gameMenuController.moveUnit(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.PATROL))!=null)
                System.out.println(gameMenuController.patrolUnit(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_UNIT_STATE))!=null)
                System.out.println(gameMenuController.setState(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.ATTACK))!=null)
                System.out.println(gameMenuController.attack(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.AIR_ATTACK))!=null)
                System.out.println(gameMenuController.airAttack(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.POUR_OIL))!=null)
                System.out.println(gameMenuController.pourOil(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DIG_TUNNEL))!=null)
                System.out.println(gameMenuController.digTunnel(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.BUILD_EQUIPMENT))!=null)
                System.out.println(gameMenuController.buildEquipment(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISBAND))!=null)
                System.out.println(gameMenuController.disbandUnit(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_CELL))!=null)
                System.out.println(gameMenuController.setTextureCell(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SET_TEXTURE_OF_BLOCK))!=null)
                System.out.println(gameMenuController.setTextureBlock(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CLEAR_CELL))!=null)
                System.out.println(gameMenuController.clear(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROP_ROCK))!=null)
                System.out.println(gameMenuController.dropRock(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROPTREE))!=null)
                System.out.println(gameMenuController.dropTree(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.REPLACE_BUILDING))!=null)
                System.out.println(gameMenuController.replaceBuilding(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DROP_UNIT))!=null)
                System.out.println(gameMenuController.dropUnit(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.TRADE_REQUEST))!=null)
                System.out.println(gameMenuController.tradeRequest(matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_LIST))!=null)
                System.out.println(gameMenuController.tradeList(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.TRADE_ACCEPT))!=null)
                System.out.println(gameMenuController.tradeAccept(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_TRADE_HISTORY))!=null)
                System.out.println(gameMenuController.tradeHistory(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SHOW_PRICE_LIST))!=null)
                System.out.println(gameMenuController.showPriceList(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.BUY))!=null)
                System.out.println(gameMenuController.buy(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.SELL))!=null)
                System.out.println(gameMenuController.sell(matcher));
            else
                System.out.println("Invalid Command");
        }
    }
}
