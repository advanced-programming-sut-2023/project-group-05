package controller;

import controller.GameMaster.GameMasterReader;
import controller.GameMaster.GameMasterWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class PortDistributor extends Thread {

    private int freePort = 4000 ;
    private HashMap <String,GameMasterWriter> portToWriter = new HashMap<>() ;
    @Override
    public void run(){
        ServerSocket serverSocket ;
        try{
            serverSocket = new ServerSocket(2019) ;
            DataOutputStream writer ;
            System.out.println( "### listening on port 2019 for port distribution" ) ;
            while( true ){
                Socket client = serverSocket.accept() ;
                System.out.println( "new request for port distributor has received." ) ;
                writer = new DataOutputStream(client.getOutputStream()) ;
                while( freePort < 4050 ){
                    try{
                        (new ServerSocket( freePort )).close() ; // checking if freePort is really free
                        new DataOutputStream(client.getOutputStream()).writeInt(freePort);
                        writer.writeInt( freePort ) ;
                        writer.flush() ;
                        String clientType = new DataInputStream( client.getInputStream() ).readUTF() ;
                        client.close() ;
                        System.out.println( freePort + " is going to be given to a client's " + clientType ) ;
                        if( clientType.contains("reader") ){
                            String username = clientType.replaceAll("^reader","") ;
                            portToWriter.put( username , new GameMasterWriter(freePort) ) ;
                        } else if ( clientType.contains("writer") ){
                            String username = clientType.replaceAll("^writer" , "") ;
                            (new GameMasterReader(freePort,portToWriter.get(username),username)).start() ;
                        } else {
                            System.out.println( "Unexpected client type." ) ;
                        }
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
