package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginRegisterThread extends Thread {

    @Override
    public void run(){
        System.out.println("listening on port 2020 for Login/Register commands");
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket( 2020 ) ;
            while(true){
                Socket client = serverSocket.accept() ;
                LoginRegisterController.handle( client ) ;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}