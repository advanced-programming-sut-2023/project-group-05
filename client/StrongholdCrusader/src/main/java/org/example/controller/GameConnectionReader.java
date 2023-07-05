package org.example.controller;

import javafx.animation.Transition;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameConnectionReader extends Transition {

    private int port = -1 ;
    private Socket socket = null ;
    private DataInputStream reader ;
    private Stage stage ;

    public GameConnectionReader(String username, Stage stage){

        this.setCycleCount( -1 );
        this.setCycleDuration( Duration.millis( 1000 ) );
        this.stage = stage ;

        while( port == -1 ){
            try{
                Thread.sleep(1000) ;
                Socket askForPortSocket = new Socket( "localhost" , 2019 ) ;
                port = new DataInputStream( askForPortSocket.getInputStream() ).readInt() ;
                DataOutputStream askForPortWriter = new DataOutputStream( askForPortSocket.getOutputStream() ) ;
                askForPortWriter.writeUTF( "reader" + username ) ;
                askForPortWriter.flush() ;
                System.out.println( "port " + port + " taken by reader." ) ;
            } catch( Exception e ){
                System.out.println( "GameConnectionReader has failed to access port distributor") ;
            }
        }

        while( socket == null ){
            try{
                Thread.sleep(1000) ;
                socket = new Socket( "localhost", port );
                System.out.println( "GameConnectionReader connected to port " + port ) ;
            } catch( Exception e ){
                System.out.println( "GameConnectionReader can't connect to port " + port ) ;
            }
        }


        try {
            reader = new DataInputStream( socket.getInputStream() ) ;
        } catch( Exception e ){
            e.printStackTrace();
        }


    }

    @Override
    protected void interpolate( double v ){
        String input ;
        try{while( true ){


            input = reader.readUTF() ;
            /*handle(){

            }*/

        }} catch( Exception e ){ e.printStackTrace(); }
        System.out.println( "you shouldn't see this" );
    }

}
