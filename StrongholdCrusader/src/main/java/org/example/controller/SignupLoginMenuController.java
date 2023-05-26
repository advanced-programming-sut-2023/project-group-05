package org.example.controller;

import org.example.model.Account;
import org.example.model.Sleep;
import org.example.view.MainMenu;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;
import org.json.simple.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupLoginMenuController {
    SignupLoginMenu signupLoginMenu;
    SignupLoginMenuController(SignupLoginMenu signupMenu){
        this.signupLoginMenu = signupMenu;
    }

    public static boolean validNickname( String nickname )
    {
        Pattern pattern = Pattern.compile("^[ A-Za-z]+$") ;
        Matcher matcher = pattern.matcher( nickname ) ;
        return matcher.find() ;
    }

    public static boolean validUserName(String userName){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_\\.]+") ;
        if(pattern.matcher(userName).matches())
            return true ;
        return false ;
    }

    public static boolean validEmail(String email){
        Pattern pattern = Pattern.compile( "^[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\.]+\\.[a-zA-Z0-9\\.]+$" ) ;
        boolean valid = pattern.matcher( email ).find() ;
        return valid ;
    }
    public static boolean validPassword(String password){
        boolean validLength = password.length() >= 6 ;
        boolean hasNum = Pattern.compile("[0-9]").matcher(password).find() ;
        boolean hasCapital = Pattern.compile("[A-Z]").matcher(password).find() ;
        boolean hasSmall = Pattern.compile("[a-z]").matcher(password).find() ;
        boolean hasOther = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find() ;
        return validLength && hasNum && hasCapital && hasSmall && hasOther ;
    }

    public static String createUser(Scanner scanner , Matcher matcher,boolean mode){
        String userName = matcher.group("username") ;
        String nickName = matcher.group("nickname") ;
        String password = matcher.group("password") ;
        String passwordConfirm = matcher.group("passwordConfirmation") ;
        String email = matcher.group("email") ;
        String slogan = matcher.group("sloganchecker")!=null ? matcher.group("slogan") : "no slogan selected";
        if(userName == null || nickName == null || email == null)
            return ("You left a field empty!");
        if (matcher.group("sloganchecker")!=null && matcher.group("slogan")==null)
            return ("you left slogan field empty");
        if(!validUserName(userName))
            return ("Invalid Username!");

        if(!validPassword(password))
            return ("Invalid password");

        if(!validEmail(email))
            return ("Invalid Email address!!");

        boolean emailExists = false ;
        for( Account acc : Account.getAccountsMap().values() ){
            if( acc.getEmail().equalsIgnoreCase( email ) ){
                emailExists = true ;
                break ;
            }
        }
        if( emailExists )
            return "Email already exists" ;
        if(DataBase.getFromDataBase("userName", userName) != null)
        {
            String randomUsername = userName ;
            Random random = new Random() ;
            while( DataBase.getFromDataBase("userName", randomUsername) != null )
                randomUsername += Math.abs(random.nextInt())  % 100 ;
            if (!mode)
                return "create user failed : Username Already Exists";
            System.out.println("This username already exists :/\nYou can pick " + randomUsername + "if you like\n" +
                    "Enter ( y / n ) for yes or no : "  ) ;
            String inputYN = scanner.nextLine() ;
            if( !inputYN.equals( "y" ) )
                return "create user failed : Username Already Exists";
            userName = randomUsername ;
        }
        if( slogan.equals("random") ){
            slogan = SecurityQuestions.getRandomSlogan();
            System.out.println( "YOUR SLOGAN IS RANDOMLY CHOSEN : " + slogan ) ;
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
            return ("Invalid Password, good luck");
        if(!password.equals(passwordConfirm))
            return ("password confirmation does not match.");
        if(!SecurityQuestions.runCaptcha(scanner)) return "I knew it! You are a damn robot :(" ;
        System.out.println( "you passed" );
        System.out.println("Pick your security question:");
        for(int i = 1; i <= SecurityQuestions.questions.size(); i ++)
        {
            System.out.print(i);
            System.out.println( " - " + SecurityQuestions.questions.get(i - 1));
        }
        long questionNum ;
        try{questionNum = Integer.parseInt(scanner.nextLine()) ;}
        catch(Exception e){
            return "Create Account failed : You did not enter a number." ;
        }
        if(questionNum > SecurityQuestions.questions.size() || questionNum < 1 )
            return "INVALID NUMBER : out of range" ;
        System.out.print( "Insert your answer : " ) ;
        long answer ;
        try{answer = Integer.parseInt( scanner.nextLine() ) ;}
        catch(Exception e) {
            return "ERROR : enter a number as your answer!";
        }
        Account account = new Account( userName , nickName , email , (new Hash(password)).getHsh() , 0 ,
                slogan , questionNum - 1 , answer ) ;
        return "account created successfully" ;
    }

    public static String loginUserStayLoggedIn( Scanner scanner, Matcher matcher) throws Exception
    {
        ArrayList < Account > myList = DataBase.getStayLoggedInAccount();
        for(Account cur : myList)
        {
            System.out.println("User Logged In ;)");
            MainMenu.run(scanner, cur);
            return "success";
        }
        return "there are not any stayed logged in account :(";
    }

    public static String loginUser( Scanner scanner , Matcher matcher, boolean mode ) throws Exception
    {
        //mode true is for real game --- mode false is for unit test
        String password = matcher.group("password") ;
        String userName = matcher.group( "username" ) ;

        boolean stay_in = (matcher.group("stayloggedin") != null);
        if(!validUserName(userName) || ! validPassword(password))
            return "login failed : Invalid username / password\n";
        if(mode && !SecurityQuestions.runCaptcha(scanner))
            return "you didn't pass captcha test";
        if( DataBase.getFromDataBase("userName", userName) == null)
            return "This username does not exist\n";
        JSONObject cur = DataBase.getFromDataBase("userName", userName);
        long pass = (long) cur.get("password");
        if (pass != 0 && pass != Hash.encode(password)) {
            ++SignupLoginMenu.wrongPassCounter;
            if(!mode)
                return "Wrong Password!\n";
            System.out.println("Wrong Password");
            for(int j = 0 ; j < SignupLoginMenu.wrongPassCounter ; j++)
                Sleep.handleSleep(); ;
            return "You Can Try It Again Now\n";
        }
        if (!mode)
            return "User Logged In! hooray !";
        System.out.println("User Logged In! hooray !");
        Account account = Account.getAccountsMap().get(userName);
        if(stay_in) DataBase.setStayLoggedIn(account);
        MainMenu.run(scanner, account);
        return "";
    }


    public static String forgetPassword (Matcher matcher){
        String username = matcher.group("username") ;
        if( DataBase.getFromDataBase("userName", username) == null)
            return "This username does not exist\n";
        System.out.println("Oh thats really bad , unfortunately you can't recover it") ;
        System.out.println("... JK dude :) , yeah just answer this question and then bob's your uncle") ;
        Account account = Account.getAccountsMap().get(username);
        System.out.println( SecurityQuestions.questions.get((int)account.getQuestion()) );
        String answer = Menu.getScanner().nextLine() ;
        Integer ans = (int) account.getAnswer() ;
        if(!ans.toString().equals(answer))
            return "that was wrong , sorry , goodbye." ;
        System.out.print("OK , this time choose a simpler password that your brain is able to carry it around : ") ;
        String newPass = Menu.getScanner().nextLine() ;
        long newPasswordHash = (new Hash(newPass)).getHsh() ;
        Account.removeAccount(account) ;
        account.setPassword( newPasswordHash );
        Account.addAccount(account) ;
        return "password recovered successfully";
    }

    public static String logout(Matcher matcher){
        return "logged out" ;
    }
}