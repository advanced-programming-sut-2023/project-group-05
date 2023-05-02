package org.example;

import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.controller.SecurityQuestions;
import org.example.model.Account;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;
import org.json.simple.JSONObject;

import java.util.*;

public class Main
{
    public static void main( String[] args )
    {
        SecurityQuestions.initialize();
        init();
        DataBase.wakeUp();
        SignupLoginMenu.run(Menu.getScanner()) ;
    }

    public static void uploadDataFromDataBase()
    {
        DataBase.wakeUp();
    }
    public static void init()
    {
        Hash.setMOD(1000000007);
        Hash.setBASE(5021);
        ArrayList < String > cap0 = new ArrayList< String >();
        ///DataBase.addNewAccount(test);
    }

}