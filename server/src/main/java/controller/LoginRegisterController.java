package controller;

import com.google.gson.Gson;
import controller.GameMaster.GameRoom;
import model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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
        for(int i = 1 ; i <=3 ; i++)
            ChatLog.addChat( new Chat( newAccount.getUserName(), "group" + i) ) ;
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

    private static boolean changeAccount( String[] command ){
        // input = { "change" , "username" , "password" , "email" , "slogan" , "nickname" }
        long answer = Account.getAccountsMap().get( command[1] ).getAnswer() ;
        long question = Account.getAccountsMap().get( command[1] ).getQuestion() ;
        long highscore = Account.getAccountsMap().get( command[1] ).getHighScore() ;
        Account newAccount = new Account( command[1] , command[5] , command[3] , Hash.encode(command[2]) , highscore ,command[4] ,question, answer ) ;
        DataBase.deleteAccount( "userName" , newAccount.getUserName() );
        DataBase.addNewAccount( newAccount );
        Account.getAccountsMap().remove(Account.getAccountsMap().get(command[1])) ;
        for(String string : command){
            System.out.print( string ) ;
        }
        System.out.println("") ;
        return true ;
    }

    private static void joinRoom( String[] command , DataOutputStream writer ){
        String username = command[1] ;
        String gameRoomName = command[2] ;
        String password = command[3] ;
        boolean ok = true ;
        GameRoom gameRoom = GameRoom.gameRoomHashMap.get(gameRoomName) ;
        if( gameRoom == null ){
            String[] output = { "0" } ;
            try {
                writer.writeUTF( (new Gson()).toJson(output) ) ;
            } catch( Exception e ){
                e.printStackTrace() ;
            }
        }
        ok = gameRoom.addUser( username, password ) ;
        if( !ok ){
            String[] output = { "0" } ;
            try {
                writer.writeUTF( (new Gson()).toJson(output) ) ;
            } catch( Exception e ){
                e.printStackTrace() ;
            }
        }
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
        }
    }

    public static void updateFriends( String[] command , DataOutputStream writer ){
        String username = command[1] ;
        System.out.println( "update friend request from " + username ) ;
        // todo : get friends from database
        ArrayList <String> friends = new ArrayList <>( Arrays.asList( "Arya" , "Amin" , "Danial" ) ) ;
        String[] friendsArr = new String[ friends.size() ] ;
        for(int i = 0 ; i < friends.size() ; i++)
            friendsArr[i] = friends.get( i ) ;
        try {
            writer.writeUTF( ( new Gson() ).toJson( friends ) );
            writer.flush();
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void addFriend( String[] command ){
        String username = command[1] ;
        String newFriend = command[2] ;
        System.out.println( "add friend request from " + username + " to add " + newFriend ) ;
        // todo : add to friends
    }

    public static void logout( String[] command ){
        String username = command[1] ;
        // todo : logout
    }

    public static void userExists( String[] command , DataOutputStream writer ){
        try {
            writer.writeBoolean( Account.getAccountsMap().containsKey( command[1] ) );
            writer.flush();
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public static void inviteFriend( String[] command ){
        String username = command[1] ;
        String newFriend = command[2] ;
        // todo : username invites newFriends to be their friend
    }

    public static void updateInvites( String[] command , DataOutputStream writer ){
        String username = command[1] ;
        // todo : get invites
        //String[] invitesArr ;
        //writer.writeUTF( new Gson().toJson( invitesArr ) ) ;
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
            } else if ( command[0].equals( "updateinvites" ) ){
                updateInvites( command , writer );
            } else if ( command[0].equals( "invitefriend" ) ){
                inviteFriend( command ) ;
            } else if ( command[0].equals( "userexists" ) ){
                userExists( command , writer ) ;
            } else if ( command[0].equals("change") ){
                System.out.print( "New Request to change account info : " ) ;
                writer.writeBoolean( changeAccount( command ) ) ;
            } else if ( command[0].equals("getinfo") ){
                sendInfo( command[1], writer ) ;
            } else if ( command[0].equals("join") ){
                joinRoom( command , writer ) ;
            } else if ( command[0].equals("updatefriends") ){
                updateFriends( command , writer ) ;
            } else if ( command[0].equals("logout") ){
                logout( command ) ;
            } else if ( command[0].equals("addfriend") ){
                addFriend( command ) ;
            }
            else {
                System.out.println( "Unknown command in LoginRegister." ) ;
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
