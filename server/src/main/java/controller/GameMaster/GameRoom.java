package controller.GameMaster;

import model.Account;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRoom {

    private ArrayList <String> usernames ;
    public static HashMap <String,GameRoom> gameRoomHashMap = new HashMap<>() ;
    private final String password ;
    private final int capacity ;

    public GameRoom(String name, String password, int capacity ){
        this.capacity = capacity ;
        usernames = new ArrayList <>() ;
        this.password = password ;
        gameRoomHashMap.put( name , this ) ;
    }

    public boolean addUser(String username, String password){
        if( usernames.contains(username) ) return false ;
        if( Account.getAccountsMap().get(username) == null ) return false ;
        if( !this.password.equals(password) ) return false ;
        usernames.add( username ) ;
        return true ;
    }

    public int getCapacity(){
        return this.capacity ;
    }

    public ArrayList<String> getUsernames(){
        return this.usernames ;
    }

    public void startGame(){
        // send message to each client and put their castles in some place
    }

}
