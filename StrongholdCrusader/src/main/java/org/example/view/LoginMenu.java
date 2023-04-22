package org.example.view;

import org.example.controller.LoginMenuController;

import java.util.regex.Matcher;

public class LoginMenu {
    LoginMenuController loginMenuController;
    LoginMenu (LoginMenuController loginMenuController){
        this.loginMenuController = loginMenuController;
    }

    public void run (){
        Matcher matcher;
        while (true){
            String input =Menu.getScanner().nextLine();
        }
    }
}
