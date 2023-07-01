package controller;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatThread extends Thread {

    @Override
    public void run(){
        ServerSocket serverSocket ;
        try{
            serverSocket = new ServerSocket( "localhost" , 2021 ) ;
            while(true){
                Socket client = serverSocket.accept() ;

            }
        } catch( Exception e ){
            e.printStackTrace();
        }
    }
}
