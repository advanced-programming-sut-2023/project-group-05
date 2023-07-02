package org.example.controller;

import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameConnectionWriter{

    private int port ;

    public GameConnectionWriter(){
        try{
            Socket socket = new Socket( "localhost" , 2019 ) ;
            port = new DataInputStream( socket.getInputStream() ).readInt() ;
            new DataOutputStream( socket.getOutputStream() ).writeUTF( "writer" ) ;
            System.out.println( "port " + port + " taken by writer" ) ;
        } catch( Exception e ){
            e.printStackTrace();
        }
    }


    public void start(){

        Socket socket = null ;
        DataOutputStream writer = null ;
        while( socket == null ){
            try {
                socket = new Socket( "localhost", port );
                writer = new DataOutputStream( socket.getOutputStream() ) ;
                System.out.println( "GameConnectionWriter connected to port " + port ) ;
            } catch( Exception e ){
                System.out.println( "GameConnectionWriter can't connect to port " + port ) ;
                try { Thread.sleep( 3000 ) ; } catch( Exception ignored ) { }
            }
        }

    }



}
