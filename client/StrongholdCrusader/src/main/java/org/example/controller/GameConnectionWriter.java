package org.example.controller;

import javafx.scene.control.Alert;

import java.io.DataOutputStream;
import java.net.Socket;

public class GameConnectionWriter extends Thread {

    private int port ;

    public GameConnectionWriter(){
        // ask server for empty port
    }


    @Override
    public void run(){
        Socket socket = null ;
        DataOutputStream writer = null ;
        while( socket == null ){
            try {
                socket = new Socket( "localhost", port );
                writer = new DataOutputStream( socket.getOutputStream() ) ;
            } catch( Exception e ){
                Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                alert.setTitle( "could not initialize connection" ) ;
                alert.setContentText( "server might be down, press ok to try again." );
                alert.showAndWait() ;
            }
        }


    }
}
