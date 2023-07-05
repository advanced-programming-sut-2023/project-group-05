package controller.GameMaster;

import model.Account;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRoom {

    private ArrayList <String> usernames ;
    public static HashMap <String,GameRoom> gameRoomHashMap = new HashMap<>() ;
    public static HashMap <String,GameRoom> usernameToGameRoom = new HashMap<>();
    public HashMap<String,GameMasterWriter> userToGMW = new HashMap<>() ;
    public HashMap<String,GameMasterReader> userToGMR = new HashMap<>() ;
    private final String password ;
    private final int capacity ;
    private boolean locked = false ;
    private String name ;

    public GameRoom(String name, String password, int capacity){
        System.out.println( "new GameRoom created" ) ;
        this.capacity = capacity ;
        this.name = name ;
        usernames = new ArrayList <>() ;
        this.password = password ;
        gameRoomHashMap.put( name , this ) ;
    }

    public boolean addUser(String username, String password){
        if( usernames.contains(username) ) return false ;
        if( Account.getAccountsMap().get(username) == null ) return false ;
        if( !this.password.equals(password) ) return false ;
        usernames.add( username ) ;
        usernameToGameRoom.put( username , this ) ;
        System.out.println( "new user added to " + "(" + name + ") game room" ) ;
        return true ;
    }

    public int getCapacity(){
        return this.capacity ;
    }

    public void start(){
        if( usernames.size() < 2 ){
            System.out.println( "can't start the game due to small number of players" );
            return ;
        }
        if( locked ) return ;
        locked = true;
        ArrayList<GameMasterReader> orderedGMRs = new ArrayList<>() ;
        ArrayList<GameMasterWriter> orderedGMWs = new ArrayList<>() ;
        // first we should give some order to the users and then start a game for them in GameMaster
        for( String username : usernames ){
            orderedGMWs.add( userToGMW.get( username ) ) ;
            orderedGMRs.add( userToGMR.get( username ) ) ;
        }
        System.out.println( "game started" ) ;
        new GameMaster( usernames , orderedGMRs , orderedGMWs ) ;
    }

    public ArrayList<String> getUsernames(){
        return this.usernames ;
    }

    public void startGame(){
        // send message to each client and put their castles in some place
    }

}
