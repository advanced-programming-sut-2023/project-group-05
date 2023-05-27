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
    public static void main( String[] args ) throws Exception {
        DataBase.wakeUp();
        Hash.setMOD(1000000007);
        Hash.setBASE(5021);
        SecurityQuestions.initialize();
        SignupLoginMenu.run(Menu.getScanner()) ;
    }
}