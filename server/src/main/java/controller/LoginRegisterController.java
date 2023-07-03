package controller;

import com.google.gson.Gson;
import controller.GameMaster.GameRoom;
import model.DataBase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Map;

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

    private static void sendScoreBoard( DataOutputStream writer ){
        String[] scoreboard = new String[2*Account.getAccountsMap().size()] ;
        int index = 0 ;
        for(Map.Entry acc : Account.getAccountsMap().entrySet()){
            scoreboard[index++] = (String)acc.getKey() ;
            scoreboard[index++] = ""+((Account)acc.getValue()).getHighScore() ;
        }
        try{
            writer.writeUTF((new Gson()).toJson(scoreboard)) ;
            writer.flush() ;
        } catch(Exception e){
            e.printStackTrace() ;
        }
    }

    private static void sendInfo( String username , DataOutputStream writer ){
        Account acc = Account.getAccountsMap().get(username) ;
        String[] output = { acc.getUserName() , acc.getNickName() , acc.getSlogan() , acc.getEmail() } ;
        try{
            writer.writeUTF( (new Gson()).toJson(output) ) ;
            writer.flush() ;
        } catch( Exception ignored ){ }
    }

    private static void changeAccount( String[] command ){
        // command = { "getinfo" , username , password , nickname , slogan }
        // change in Account & Database
        // close socket in client-side
        // update in client side too
    }

    private static void joinRoom( String[] command , DataOutputStream writer ){
        String username = command[1] ;
        String gameRoomName = command[2] ;
        String password = command[3] ;
        boolean ok = true ;
        GameRoom gameRoom = GameRoom.gameRoomHashMap.get(gameRoomName) ;
        if( gameRoom == null ) ok = false ;
        ok = gameRoom.addUser( username, password ) ;
        if( ok ){
            String[] output = new String[ 1 + 2 * gameRoom.getUsernames().size() ] ;
            output[0] = "1" ;
            for(int i = 1 ; i < output.length ; i+=2 ){
                output[i] = gameRoom.getUsernames().get( i - 1 );
                output[i+1] = Account.getAccountsMap().get(output[i]).getNickName() ;
                try {
                    writer.writeUTF( ( new Gson() ).toJson( output ) );
                } catch( Exception e ) { e.printStackTrace(); }
            }
        } else {
            String[] output = { "0" } ;
            try {
                writer.writeUTF( (new Gson()).toJson(output) ) ;
            } catch( Exception e ){
                e.printStackTrace() ;
            }
        }
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
            } else if ( command[0].equals("scoreboard") ){
                sendScoreBoard( writer ) ;
            } else if ( command[0].equals("change") ){
                changeAccount( command ) ;
            } else if ( command[0].equals("getinfo") ){
                sendInfo( command[1], writer ) ;
            } else if ( command[0].equals("join") ){
                joinRoom( command , writer ) ;
            }
            else {
                System.out.println( "Unknown command in LoginRegister." ) ;
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
