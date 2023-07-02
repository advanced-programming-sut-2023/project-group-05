package org.example.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameConnectionWriter{

    private int port = -1 ;
    private DataOutputStream writer ;
    private Socket socket = null ;

    public GameConnectionWriter(String username){

        while(port == -1){
            try {
                Thread.sleep(1000) ;
                Socket askForPortSocket = new Socket( "localhost", 2019 );
                port = new DataInputStream( askForPortSocket.getInputStream() ).readInt();
                DataOutputStream askForPortWriter = new DataOutputStream( askForPortSocket.getOutputStream() ) ;
                askForPortWriter.writeUTF( "writer" + username );
                askForPortWriter.flush() ;
                System.out.println( "port " + port + " taken by writer" );
            } catch(Exception e) {
                System.out.println( "GameConnectionReader has failed to access port distributor") ;
            }
        }

        while( socket == null ){
            try {
                Thread.sleep( 1000 ) ;
                socket = new Socket( "localhost", port );
                writer = new DataOutputStream( socket.getOutputStream() ) ;
                System.out.println( "GameConnectionWriter connected to port " + port ) ;
            } catch( Exception e ){
                System.out.println( "GameConnectionWriter can't connect to port " + port ) ;
            }
        }


    }

    public void write( String string){
        try{
            writer.writeUTF( string ) ;
            writer.flush() ;
        } catch( Exception e ){ e.printStackTrace(); }
    }


}
