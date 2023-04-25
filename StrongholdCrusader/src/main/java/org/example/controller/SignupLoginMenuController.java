package org.example.controller;

import org.example.MyHash;
import org.example.SignUpLoginMenu;
import org.example.model.Account;
import org.example.model.Commands;
import org.example.view.MainMenu;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;
import org.json.simple.JSONObject;

import java.util.Random;
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
            return ("You left a filed empty!");

        if(!validUserName(userName))
            return ("Invalid Username!");

        if(!validPassword(email))
            return ("Invalid Email address!!");

        if(DataBase.getFromDataBase("userName", userName) != null)
        {
            String ret = "This username already exists :/" ;
            String randomUsername = userName ;
            Random random = new Random() ;
            while( DataBase.getFromDataBase("userName", randomUsername) != null )
                randomUsername += Math.abs(random.nextInt())  % 100 ;

            System.out.println("This username already exists :/\nYou can pick " + randomUsername + "if you like\n" +
                    "Enter ( y / n ) for yes or no : "  ) ;
            String inputYN = scanner.nextLine() ;
            if( !inputYN.equals( "y" ) ) return "create user failed" ;
            userName = randomUsername ;
        }
        if(slogan == null)
        {
            slogan = SecurityQuestions.askRandom();
            return ("Your slogan is: " + slogan);
        }
        if(password.equals("random"))
        {
            String[] character = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"} ;
            password = "" ;
            Random random = new Random() ;
            password += "#$%" ;
            for(int i = 0 ; i < 4 ; i++)
                password += character[ Math.abs(random.nextInt()) % 26 ] ;
            password += "$#5489" ;
            for(int i = 0 ; i < 4 ; i++)
                password += character[ Math.abs(random.nextInt()) % 26 ].toUpperCase() ;
            password += "@!@!" ;
            for(int i = 0 ; i < 4 ; i++)
                password += Math.abs(random.nextInt()) % 26 ;
            password += "#$@" ;
            passwordConfirm = password ;
            System.out.println("Your random password is here! " + password + "Please re-enter your password ;)");
            String inputReEnter = scanner.nextLine() ;
            if( !inputReEnter.equals(password) )
                return "create user failed : wrong re-entered password" ;

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

        System.out.println("Pick your security question:");
        for(int i = 1; i <= SecurityQuestions.questions.size(); i ++)
        {
            System.out.print(i);
            System.out.println( " - " + SecurityQuestions.questions.get(i - 1));
        }
        long questionNum = scanner.nextInt() ;
        System.out.print( "Insert your answer : " ) ;
        long answer = scanner.nextInt() ;
        System.out.println("\n") ;
        Account account = new Account( userName , nickName , email , (new MyHash(password)).getHsh() , 0 ,
                slogan , questionNum - 1 , answer ) ;
        return null;
    }

    public static void loginUser( Scanner scanner , Matcher matcher ){
        String password = matcher.group("password") ;
        String userName = matcher.group( "username" ) ;

        if(! SignUpLoginMenu.validUserName(userName) || ! SignUpLoginMenu.validPassword(password)){
            System.out.println("login failed : Invalid username / password") ;
            return ;
        }

        if(!SecurityQuestions.runCaptcha(scanner)){
            System.out.println("you didn't pass captcha test") ;
            return ;
        }

        if( DataBase.getFromDataBase("userName", userName) == null){
            System.out.println("This username does not exist") ;
            return ;
        }

        JSONObject cur = DataBase.getFromDataBase("userName", userName);
        long pass = (long) cur.get("password");
        if(pass != MyHash.Encode(password)){
            System.out.println("Wrong Password!") ;
            return ;
        }

        System.out.println("User Logged In! hooray !!") ;
        Account account = Account.getAccountsMap().get(userName);

        MainMenu.run( scanner , account ) ;
    }

    public static String questionPick(Matcher matcher){
        return null;
    }

    public static String forgetPassword (Matcher matcher){
        return null;
    }

    public static String logout(Matcher matcher){
        return "logged out" ;
    }

}
