package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginRegisterThread extends Thread {

    public void run(){
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket( 2020 ) ;
            while(true){
                Socket client = serverSocket.accept() ;
                System.out.println( "new Register/Login request received from : " + client.getInetAddress() ) ;
                LoginRegisterController.handle( client ) ;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}