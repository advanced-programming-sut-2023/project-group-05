package org.example;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.*;
import org.example.model.Account;
import org.example.model.Cost;
import org.example.view.Menu;
import org.example.view.StartMenu;
import org.json.simple.JSONObject;

import java.util.*;
public class Main
{
    public static void main( String[] args ) throws Exception {
        DataBase.wakeUp();
        SecurityQuestions.initialize();
        //SignupLoginMenu.run(Menu.getScanner()) ;
        StartMenu.main( args ) ;
    }
}