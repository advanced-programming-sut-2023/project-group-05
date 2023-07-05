package org.example.controller;

import com.google.gson.Gson;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import org.example.view.GameRoomMenu;

import java.io.DataInput;
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

    public static boolean changeThing(String newThing,int mode)
    {   // todo : 1 = username change , 2 = nickname change , 3 = email change , 4 = slogan change , 5 : new password

        switch( mode ){
            case 1 -> GameController.currentUsername = newThing ;
            case 2 -> GameController.currentNickname = newThing ;
            case 3 -> GameController.currentEmail = newThing ;
            case 4 -> GameController.currentSlogan = newThing ;
            case 5 -> GameController.currentPassword= newThing ;
        }

        Socket socket ;
        DataOutputStream writer ;
        DataInputStream reader ;
        boolean ret ;
        try{
            socket = new Socket( "localhost" , 2020 ) ;
            writer = new DataOutputStream( socket.getOutputStream() ) ;
            reader = new DataInputStream( socket.getInputStream() ) ;
            String[] output ;
            output = new String[]{ "change" , GameController.currentUsername , GameController.currentPassword , GameController.currentEmail , GameController.currentSlogan , GameController.currentNickname } ;
            writer.writeUTF((new Gson()).toJson(output)) ;
            ret = reader.readBoolean() ;
            socket.close() ;
            return ret;
        } catch( Exception e ){
            e.printStackTrace();
        }
        return false ;
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

    public static boolean joinRoom( String roomName , String password , GameRoomMenu gameRoomMenu ){
        try{
            Socket socket = new Socket("localhost" , 2020) ;
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            String[] command = { "join" , GameController.currentUsername , roomName , password } ;
            writer.writeUTF( (new Gson()).toJson( command ) );
            String[] response = new Gson().fromJson( reader.readUTF() , String[].class ) ;
            if( response[0].equals("0") ) return false ;
            socket.close() ;
        } catch( Exception e ){
            e.printStackTrace();
            return false ;
        }
        return true ;
    }

    public static boolean userExists( String username ){
        boolean ret = false ;
        try {
            Socket socket = new Socket( "localhost", 2020 );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() );
            DataInputStream reader = new DataInputStream( socket.getInputStream() );
            String[] command = {"userexists", username};
            writer.writeUTF( new Gson().toJson( command ) );
            writer.flush();
            ret = reader.readBoolean();
            socket.close() ;
        } catch( Exception e ){
            e.printStackTrace();
        }
        return ret ;
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

    public static void addFriend( String friendName ){
        try {
            Socket socket = new Socket( "localhost", 2020 );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() );
            String[] command = {"addfriend", GameController.currentUsername, friendName};
            writer.writeUTF( new Gson().toJson( command ) );
            writer.flush();
            socket.close();
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void updateFriends( ArrayList<String> friends ){
        friends.clear() ;
        String[] friendsArr = null ;
        try {
            Socket socket = new Socket( "localhost", 2020 );
            DataInputStream reader = new DataInputStream( socket.getInputStream() );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() );
            String[] command = {"updatefriends", GameController.currentUsername};
            writer.writeUTF( ( new Gson() ).toJson( command ) );
            writer.flush() ;
            String input = reader.readUTF();
            socket.close();
            friendsArr = ( new Gson() ).fromJson( input, String[].class );
        } catch( Exception e ){
            e.printStackTrace();
        }
        for(int i = 0; i < friendsArr.length ; i++){
            friends.add( friendsArr[i] ) ;
        }
    }

    public static boolean changePassword( String username , String oldPassword , String newPassword ){
        // TODO : return true - > password change mishe
        // todo : return false - > old Password eshtebah bude
        // yes
        return false ;
    }

    public static void logout(Matcher matcher){
        try {
            Socket socket = new Socket( "localhost", 2020 );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() );
            String[] command = {"logout", GameController.currentUsername};
            writer.writeUTF((new Gson()).toJson(command));
            socket.close();
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void updateInvites( ArrayList<String> invites ){
        try{
            Socket socket = new Socket( "localhost", 2020 );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() );
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            String[] command = { "updateinvites" , GameController.currentUsername } ;
            writer.flush();
            socket.close();
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void updateFriends(String currentUsername, ArrayList<String> myFriends) {
        try{
            Socket socket = new Socket() ;
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            String[] command = { "updatefriends" , GameController.currentUsername } ;
            writer.writeUTF( new Gson().toJson( command ) ) ;
            writer.flush() ;
            String input = reader.readUTF() ;
            String[] friendsArr = new Gson().fromJson( input , String[].class ) ;
            myFriends.clear() ;
            for(String string : friendsArr)
                myFriends.add( string ) ;
            socket.close() ;
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void inviteFriend( String username ){
        try{
            Socket socket = new Socket( "localhost", 2020 );
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() );
            String[] command = { "invitefriend" , GameController.currentUsername  , username } ;
            writer.flush();
            socket.close();
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

}