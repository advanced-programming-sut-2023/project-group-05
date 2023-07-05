package controller.GameMaster;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameMasterWriter {

    private int port ;
    private GameMaster gm ;
    private DataOutputStream writer ;
    private ServerSocket serverSocket ;
    private String username ;

    public GameMasterWriter(int port){
        this.port = port ;
        try{
            serverSocket = new ServerSocket(port) ;
            Socket client = serverSocket.accept() ;
            writer = new DataOutputStream( client.getOutputStream() ) ;
        } catch( Exception e ){
            System.out.println( "GameMasterWriter failed to connect on port " + port );
        }

    }

    public void write( String string ){
        try{
            writer.writeUTF( string ) ;
            writer.flush() ;
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    public void setGameMaster( GameMaster gm ){
        this.gm = gm ;
    }

}
