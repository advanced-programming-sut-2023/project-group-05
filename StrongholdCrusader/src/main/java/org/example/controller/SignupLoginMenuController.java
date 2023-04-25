package org.example.controller;

import org.example.model.Commands;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupLoginMenuController {
    SignupLoginMenu signupLoginMenu;
    SignupLoginMenuController(SignupLoginMenu signupMenu){
        this.signupLoginMenu = signupMenu;
    }

    public String findUser(Matcher matcher){
        return null;
    }

    public static String createUser(Scanner scanner , Matcher matcher){

        return null;
    }

    public static String loginUser( Scanner scanner , Matcher matcher ){
        return null;
    }

    public static String questionPick(Matcher matcher){
        return null;
    }

    public static String forgetPassword (Matcher matcher){
        return null;
    }

    public static String logout(Matcher matcher){
        return null;
    }

}
