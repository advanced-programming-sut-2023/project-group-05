package org.example.controller;

import org.example.model.Cell;
import org.example.model.CellType;
import org.example.model.ConsoleColors;
import org.example.model.GameMap;
import org.example.model.building.AttackDefenceBuilding;
import org.example.model.building.Building;
import org.example.model.unit.Unit;
import org.example.view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class MapMenuController {
    public static void showMap(int row , int column, GameMap gameMap) {
        // the whole map is 400 * 400
        // the displayed portion in this function is 41 * 41 , which row and column is in the center of this square
        //TODO : HANDLE ASSASSIN DISPLAY
        row = Math.max(21, row);row = Math.min(379,row);
        column = Math.max(21, column);column = Math.min(379,column);
        for (int i = row - 21; i < row + 21; ++i) {
            for (int j = column - 21; j < column + 21; ++j) {
                Cell cell = gameMap.getCell(i, j);
                String background = getCellTypeByColor(cell);
                String textColor = getTextColor(background);
                String content = getCellContentByCharacter(cell);
                if(content.equals(" B ")){
                    background = ConsoleColors.RED_BACKGROUND ;
                }
                System.out.print("|"+background+textColor+content+ConsoleColors.RESET);

            }
            System.out.println();
        }
    }

    public static String getCellTypeByColor(Cell cell) {
        CellType cellType = cell.cellType;
        if (cellType == CellType.GROUND ||
                cellType == CellType.COAST
        )
            return ConsoleColors.YELLOW_BACKGROUND_BRIGHT;
        if (cellType == CellType.GRASS ||
                cellType == CellType.GRASSLAND ||
                cellType == CellType.MEADOW ||
                cellType == CellType.DELTA
        )
            return ConsoleColors.GREEN_BACKGROUND;
        if (cellType == CellType.ROCK_MINE)
            return ConsoleColors.WHITE_BACKGROUND_BRIGHT;
        if (cellType == CellType.RIVER ||
                cellType == CellType.SMALL_POND ||
                cellType == CellType.BIG_POND ||
                cellType == CellType.SEA ||
                cellType == CellType.SHOAL
        )
            return ConsoleColors.BLUE_BACKGROUND_BRIGHT;
        if (cellType == CellType.IRON_MINE)
            return ConsoleColors.BLACK_BACKGROUND;
        if (cellType == CellType.BOULDER)
            return ConsoleColors.RED_BACKGROUND;
        if (cellType == CellType.OIL_WELL)
            return ConsoleColors.PURPLE_BACKGROUND_BRIGHT;
        return null;
    }

    public static String getTextColor(String background){
        if (background.equals(ConsoleColors.YELLOW_BACKGROUND_BRIGHT))
            return ConsoleColors.BLACK_BOLD;
        if (background.equals(ConsoleColors.GREEN_BACKGROUND))
            return ConsoleColors.CYAN_BOLD;
        if (background.equals(ConsoleColors.WHITE_BACKGROUND_BRIGHT))
            return ConsoleColors.BLACK_BOLD;
        if (background.equals(ConsoleColors.BLUE_BACKGROUND_BRIGHT))
            return ConsoleColors.WHITE_BOLD;
        if (background.equals(ConsoleColors.BLACK_BACKGROUND))
            return ConsoleColors.WHITE_BOLD;
        if (background.equals(ConsoleColors.RED_BACKGROUND))
            return ConsoleColors.WHITE_BOLD;
        if (background.equals(ConsoleColors.PURPLE_BACKGROUND_BRIGHT))
            return ConsoleColors.WHITE_BOLD;
        return ConsoleColors.BLACK_BOLD;
    }

    public static String getCellContentByCharacter(Cell cell) {
        if (cell.units.size() > 0)
            return " S ";
        Building building = cell.getBuilding() ;
        if (building != null) {
                if (building instanceof AttackDefenceBuilding)
                    return " W ";
                if (building.getName().equals("deserttree") || building.getName().equals("cherrytree") || building.getName().equals("datetree") || building.getName().equals("olivetree") || building.getName().equals("coconuttree"))
                    return " T ";
                if (building.getName().equals("rockeast") || building.getName().equals("rocknorth") || building.getName().equals("rocksouth") || building.getName().equals("rockwest"))
                    return " R ";
            return " B ";
        }
        return " # ";
    }

    public static void showDetails(Matcher matcher, GameMap gameMap) {
        int row = Integer.parseInt(matcher.group("row"));
        int column = Integer.parseInt(matcher.group("column"));
        if (row > 400 || row < 0 || column>400||column <0){
            System.out.println("Coordinates Are Outside The Map");
            return;
        }
        Cell cell = gameMap.getCell(row,column);
        System.out.println("Cell Type:\n"+CellType.getCellTypeNameByEnum(cell.cellType));

        //TODO : CELL RESOURCES AMOUNTS

        System.out.println("Units:");
        HashMap<Unit,Boolean> visited = new HashMap<>();
        for (Unit unit : cell.units){
            if (visited.containsKey(unit))
                continue;
            visited.put(unit,true);
            System.out.println(unit.getName()+":"+cell.getUnitNumbers(unit));
        }
        System.out.println("Buildings:");
        System.out.println(cell.getBuilding().getName());
    }

    public static void mapNavigation(Matcher matcher, GameMap gameMap,int row,int column) {
        int up =0;
        int down = 0;
        int right = 0;
        int left = 0;
        if (matcher.group("upNavigation")!=null)
            up += (matcher.group("upNumber")==null)? 1 : Integer.parseInt(matcher.group("upNumber"));
        if (matcher.group("downNavigation")!=null)
            down += (matcher.group("downNumber")==null)? 1 : Integer.parseInt(matcher.group("downNumber"));
        if (matcher.group("rightNavigation")!=null)
            right += (matcher.group("rightNumber")==null)? 1 : Integer.parseInt(matcher.group("rightNumber"));
        if (matcher.group("leftNavigation")!=null)
            left += (matcher.group("leftNumber")==null)? 1 : Integer.parseInt(matcher.group("leftNumber"));
        row -= (up - down);
        column +=(right - left);
        if (row > 400 || row < 0 || column > 400 || column <0){
            System.out.println("The Navigation Can't Be Applied : Numbers Exceed Map Size!");
            row +=(up-down);
            column +=(right - left);
            return;
        }
        showMap(row,column,gameMap);
    }
}
