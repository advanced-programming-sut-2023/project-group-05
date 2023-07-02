package org.example.controller;

import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameConnectionReader extends Thread {

    private int port ;

    public GameConnectionReader(){
        try{
            Socket socket = new Socket( "localhost" , 2019 ) ;
            port = new DataInputStream( socket.getInputStream() ).readInt() ;
            new DataOutputStream( socket.getOutputStream() ).writeUTF( "reader" ) ;
            System.out.println( "port " + port + " taken by reader." ) ;
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        Socket socket = null ;
        DataInputStream reader = null ;
        while( socket == null ){
            try {
                socket = new Socket( "localhost", port );
                reader = new DataInputStream( socket.getInputStream() ) ;
                System.out.println( "GameConnectionReader connected to port " + port ) ;
            } catch( Exception e ){
                System.out.println( "GameConnectionReader can't connect to port " + port ) ;
                try { Thread.sleep( 3000 ) ; } catch( Exception ignored ) { }
            }
        }
    }
}
