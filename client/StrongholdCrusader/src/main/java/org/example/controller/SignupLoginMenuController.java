package org.example.controller;

import com.google.gson.Gson;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupLoginMenuController {

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

    public static String createUser( String username , String password , String nickname , String passwordConfirm , String email , String slogan ){

        if(!validUserName(username))
            return "Username form is not valid";

        if(!validPassword(password))
            return "weak password";

        if(!validEmail(email))
            return "Invalid Email address!";

        if(slogan == null || slogan.equals(""))
            return "empty slogan" ;

        if(!password.equals(passwordConfirm))
            return "password confirmation does not match.";

        int answer = 0 ;
        int questionNum = 1 ;

        // TODO : answer and question number
        try {
            Socket socket = new Socket( "localhost", 2020 );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            String[] createUserJson = { "register" , username , password , slogan , nickname , email , "" + (questionNum - 1) , "" + answer } ;
            writer.writeUTF( (new Gson()).toJson( createUserJson ) );
            writer.flush() ;
            boolean ok = reader.readBoolean() ;
            socket.close() ;
            if( !ok ) return "register failed : username already exists" ;
        } catch( Exception e ){
            e.printStackTrace();
        }
        return null ;
    }

    public static String[] getScoreboard(){
        String[] returnValue = null ;
        try{
            Socket socket = new Socket( "localhost" , 2020 ) ;
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            String[] arr = { "scoreboard" } ;
            writer.writeUTF( (new Gson()).toJson( arr ) ) ;
            writer.flush() ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            returnValue = (new Gson()).fromJson( reader.readUTF() , String[].class ) ;
            socket.close() ;
        } catch( Exception e ){
            e.printStackTrace();
        }
        return returnValue ;
    }

    public static void forgetPassword(){
        // TODO
    }

    public static String[] getInfo( String username ){
        while( true ){
            try{
                Socket socket = new Socket( "localhost" , 2020 ) ;
                DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
                DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
                String[] arr = { "getinfo" , username } ;
                writer.writeUTF( (new Gson()).toJson( arr ) ) ;
                writer.flush() ;
                String[] ret = (new Gson()).fromJson( reader.readUTF() , String[].class );
                socket.close() ;
                return ret;
            } catch( Exception e ){
                e.printStackTrace() ;
            }
        }
    }

    public static String loginUser( String username , String password ) {
        if(!validUserName(username) || ! validPassword(password))
            return "login failed : Invalid username / password";
        Socket socket = null ;
        while( socket == null ){
            try {
                socket = new Socket( "localhost", 2020 );
            } catch( Exception e ){
                Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                alert.setTitle("Connection error") ;
                alert.setContentText("The server might be down temporarily.\nPress ok to try again.") ;
                alert.showAndWait() ;
            }
        }
        try {
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            String[] output = { "login" , username , password } ;
            Gson gson = new Gson() ;
            writer.writeUTF( gson.toJson( output ) ) ;
            writer.flush() ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            boolean ok = reader.readBoolean() ;
            socket.close() ;
            if( !ok ) return "your username / password does not exist in database." ;
        } catch( Exception e ){
            e.printStackTrace() ;
        }

        return null ;
    }


    /*public static String forgetPassword (Matcher matcher){
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
    }*/

    public static String logout(Matcher matcher){
        return "logged out" ;
    }
}