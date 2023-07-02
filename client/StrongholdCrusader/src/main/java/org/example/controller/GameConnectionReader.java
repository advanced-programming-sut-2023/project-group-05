package org.example.controller;

import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.net.Socket;

public class GameConnectionReader extends Thread {

    private int port ;

    public GameConnectionReader(){
        // ask server the port for reader
    }

    @Override
    public void run(){
        Socket socket = null ;
        DataInputStream reader = null ;
        while( socket == null ){
            try {
                socket = new Socket( "localhost", port );
                reader = new DataInputStream( socket.getInputStream() ) ;
            } catch( Exception e ){
                Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                alert.setTitle( "could not initialize connection" ) ;
                alert.setContentText( "server might be down, press ok to try again." );
                alert.showAndWait() ;
            }
        }
    }
}
