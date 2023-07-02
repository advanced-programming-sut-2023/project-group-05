package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PortDistributor extends Thread {

    private int freePort = 4000 ;
    @Override
    public void run(){
        ServerSocket serverSocket ;
        try{
            serverSocket = new ServerSocket(2019) ;
            DataOutputStream writer ;
            System.out.println( "### listening on port 2019 for port distribution" ) ;
            while( true ){
                Socket client = serverSocket.accept() ;
                writer = new DataOutputStream(client.getOutputStream()) ;
                while( true ){
                    try{
                        (new ServerSocket( freePort )).close() ; // checking if freePort is really free
                        new DataOutputStream(client.getOutputStream()).writeInt(freePort);
                        writer.writeInt( freePort ) ;
                        String clientType = new DataInputStream( client.getInputStream() ).readUTF() ;
                        System.out.println( freePort + " is going to be given to a client's " + clientType ) ;
                        freePort++ ;
                        break ;
                    } catch(Exception e){
                        System.out.println( "port " + freePort + " is currently unavailable. trying next one." ) ;
                        freePort++ ;
                    }
                }

            }
        } catch( Exception e ){
            e.printStackTrace();
        }

    }
}
