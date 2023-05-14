package org.example;
import org.example.controller.*;
import org.junit.Assert;
import org.example.model.Account;
import org.example.model.Cost;
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
        ArrayList < String > cap0 = new ArrayList< String >();
        DataBase.wakeUp();
        SignupLoginMenu.run(Menu.getScanner()) ;
    }
}