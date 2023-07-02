package controller.GameMaster;

import javafx.scene.chart.PieChart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameMasterReader extends Thread {

    private int port ;
    private GameMasterWriter gmw ;
    private DataInputStream reader ;
    private ServerSocket serverSocket ;

    public GameMasterReader(int port, GameMasterWriter gmw){
        this.port = port ;
        this.gmw = gmw ;
        gmw.setGmr( this ) ;
        while(true){
            try{
                serverSocket = new ServerSocket(port) ;
                Socket client = serverSocket.accept() ;
                reader = new DataInputStream( client.getInputStream() ) ;
                break ;
            } catch( Exception e ){
                System.out.println( "GameMasterReader failed to connect to port " + port );
                try{Thread.sleep( 3000 ) ;} catch( Exception ignored ){ }
            }
        }
    }

    @Override
    public void run(){

        String input ;
        try{while(true){

            input = reader.readUTF() ;
            System.out.println( "gmr = " + input ) ;

        }} catch( Exception e ){ e.printStackTrace(); }


    }
}
