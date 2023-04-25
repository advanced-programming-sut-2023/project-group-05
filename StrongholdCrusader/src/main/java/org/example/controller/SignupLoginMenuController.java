package org.example.controller;

import org.example.model.Account;
import org.example.model.Commands;
import org.example.view.MainMenu;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupLoginMenuController {
    SignupLoginMenu signupLoginMenu;
    SignupLoginMenuController(SignupLoginMenu signupMenu){
        this.signupLoginMenu = signupMenu;
    }

    public static String findUser(Matcher matcher){
        return null;
    }

    private static boolean validUserName(String userName){
        return true ;
    }

    private static boolean validEmail(String userName){
        return true ;
    }
    private static boolean validPassword(String userName){
        return true ;
    }

    public static String createUser(Scanner scanner , Matcher matcher){
        String userName = matcher.group("username") ;
        String nickName = matcher.group("nickname") ;
        String password = matcher.group("password") ;
        String passwordConfirm = matcher.group("passwordConfirmation") ;
        String email = matcher.group("email") ;
        String slogan = matcher.group("slogan") ;
        //SecurityQuestions securityQuestions =  ;

        if(userName.length() == 0 || nickName.length() == 0 || email.length() == 0)
        {
            return ("You left a filed empty!");

        }
        if(!validUserName(userName))
        {
            return ("Invalid Username!");

        }
        if(!validPassword(email))
        {
            return ("Invalid Email address!!");

        }
        if(DataBase.getFromDataBase("userName", userName) != null)
        {
            return ("This username already exists :/");
            /// do random suggestion afterwards;

        }
        if(slogan == null)
        {
            slogan = SecurityQuestions.askRandom();
            return ("Your slogan is: " + slogan);
        }
        if(password.equals("random")) /// here again you have to generate random strings
        {
            password = "abracadabra";
            System.out.println("Your random password is here! " + password + "Please re-enter your password ;)");
            /// input the re-entered password for confirmation
        }
        if(!validPassword(password))
        {
            return ("Invalid Password, good luck");
        }
        if(!password.equals(passwordConfirm))
        {
            return ("You entered your password wrong, It's not confirmed yet");
        }
        if(!SecurityQuestions.runCaptcha(scanner)) return "I knew it! You are a damn robot :(" ;
        System.out.println( "you passed" );
        Account account = Account.getAccountsMap().get(userName) ;
        MainMenu.run(scanner , account );
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
