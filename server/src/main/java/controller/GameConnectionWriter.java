package controller;

import java.net.ServerSocket;

public class GameConnectionWriter extends Thread {

    private int port ;

    @Override
    public void run(){
        ServerSocket serverSocket = null ;
        try{
            serverSocket = new ServerSocket(4001) ;
        } catch( Exception e ){
            System.out.println( "Could not start server on port 4000." );
            return ;
        }
    }

}
