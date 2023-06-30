package controller;

import com.google.gson.Gson;
import model.DataBase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import model.Hash;
import model.Account ;

public class LoginRegisterController {

    private static boolean register( String username , String password , String slogan , String nickname , String email , String question , String answer ){
        if( Account.getAccountsMap().get(username) != null ) return false ;
        Account newAccount = null ;
        try {
            newAccount = new Account( username , nickname , email , Hash.encode(password) , 0 , slogan , Integer.parseInt(question) , Integer.parseInt(answer)) ;
            DataBase.addNewAccount( newAccount ) ;
        } catch( Exception e ) {
            e.printStackTrace() ;
        }
        return true ;
    }

    private static boolean login( String username , String password ){
        Account account = Account.getAccountsMap().get(username) ;
        return account != null && ( account.getPasswordHash() == Hash.encode( password ) );
    }

    public static void handle( Socket socket ){
        try{
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            String input = reader.readUTF() ;
            Gson gson = new Gson() ;
            String[] command = gson.fromJson( input , String[].class ) ;
            if( command[0].equals("register") ){
                System.out.println( "Register command :"
                        + " user:" + command[1]
                        + " pass:" + command[2]
                        + " slogan:" + command[3]
                        + " nickname:" + command[4]
                        + " email:" + command[5]
                        + " question:" + command[6]
                        + " answer:" + command[7]
                ) ;
                boolean ok ;
                writer.writeBoolean( ok = register(command[1],command[2],command[3],command[4],command[5],command[6],command[7]) );
                System.out.println( "register status : " + ok ) ;
                writer.flush() ;
            } else if ( command[0].equals( "login" ) ){
                System.out.println( "Login command : " + "user:" + command[1] + " pass:" + command[2] ) ;
                boolean ok ;
                writer.writeBoolean( ok = login( command[1] , command[2] ) );
                System.out.println( "login status : " + ok ) ;
            } else {
                System.out.println( "Unknown command in LoginRegister." ) ;
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
