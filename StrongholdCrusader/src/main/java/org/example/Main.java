package org.example;
import org.junit.Assert;
import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.controller.PathFinder;
import org.example.controller.SecurityQuestions;
import org.example.model.Account;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.*;

public class Main
{
    public static void main( String[] args ){
        SecurityQuestions.initialize();
        Hash.setMOD(1000000007);
        Hash.setBASE(5021);
        /// Account account = new Account("Ariya", "AH", "prefix.suffix.aria@gmail.com", 100, 10, "not yet", 0, 0);
        ArrayList < String > cap0 = new ArrayList< String >();
        DataBase.wakeUp();
        /* example of working with PathFinder:
        PathFinder current = new PathFinder();
        current.gameMap[9][10] = 1;
        current.gameMap[10][9] = 1;
        current.Run(10, 10);
        System.out.println(current.goInDirectionFrom(8, 10));
        */
        SignupLoginMenu.run(Menu.getScanner()) ;
    }
}