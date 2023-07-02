package controller.GameMaster;

import java.util.ArrayList;

public class GameRoom {

    public String adminUsername ;
    public ArrayList <String> usernames = new ArrayList<>() ;

    public GameRoom(String adminUsername){
        this.adminUsername = adminUsername;
        usernames.add(adminUsername);
    }

    public void addUser(String username){

    }

}
