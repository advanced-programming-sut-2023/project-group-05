package controller.GameMaster;

import model.Account;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameMaster {
    ArrayList <GameMasterReader> gmr_arr ;
    ArrayList <GameMasterWriter> gmw_arr ;
    ArrayList<String> usernames ;
    public GameMaster( ArrayList<String> usernames , ArrayList<GameMasterReader> gmrArrayList , ArrayList<GameMasterWriter> gmwArrayList ){
        this.gmw_arr = gmwArrayList ;
        this.gmr_arr = gmrArrayList ;
        this.usernames = usernames ;
        for(GameMasterReader gmr : gmr_arr)
            gmr.setGameMaster( this ) ;
        for(GameMasterWriter gmw : gmw_arr)
            gmw.setGameMaster( this );
    }
}
