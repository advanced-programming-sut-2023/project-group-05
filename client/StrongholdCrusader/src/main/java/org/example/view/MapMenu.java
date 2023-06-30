package org.example.view;

import org.example.controller.MapMenuController;
import org.example.model.Commands;
import org.example.model.GameMap;

import java.util.regex.Matcher;

public class MapMenu {
    public static void run(Matcher matcher , GameMap gameMap){
        System.out.println( "YOU HAVE ENTERED MAP MENU" ) ;
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        MapMenuController.showMap(row,column,gameMap);
        String input;
        Matcher commandMatcher;
        while (true){
            input = Menu.getScanner().nextLine();
            if ((commandMatcher = Commands.getMatchingMatcher(input,Commands.SHOW_MAP))!=null)
                MapMenuController.showMap(row,column,gameMap);
            else if ((commandMatcher = Commands.getMatchingMatcher(input,Commands.SHOW_DETAILS))!=null)
                MapMenuController.showDetails(commandMatcher,gameMap);
            else if ((commandMatcher = Commands.getMatchingMatcher(input,Commands.NAVIGATE_MAP))!=null)
                MapMenuController.mapNavigation(commandMatcher,gameMap,row,column);
            else if (input.matches("\\s*exit\\s*"))
                return;
            else
                System.out.println("Invalid Command!");
        }
    }
}
