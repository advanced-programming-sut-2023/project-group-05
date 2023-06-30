package Controller;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class LoginRegisterController {

    private static boolean register( String username , String password , String slogan , String nickname ){
        // ret true if ok, false if not, just check username exists or not !!
        // add in database if ok
        return false ;
    }

    private static boolean login( String username , String password ){
        // ret true if ok else false
        return false ;
    }

    public static void handle( Socket socket ){
        try{
            DataOutputStream writer = new DataOutputStream( socket.getOutputStream() ) ;
            DataInputStream reader = new DataInputStream( socket.getInputStream() ) ;
            String input = reader.readUTF() ;
            Gson gson = new Gson() ;
            String[] command = gson.fromJson( input , String[].class ) ;
            if( command[0].equals("register") ){
                System.out.println( "Register command : \n"
                        +"user:" + command[0]
                        + "\npass:" + command[1]
                        + "\nslogan:" + command[2]
                        + "\nnickname:" + command[2] ) ;
                writer.writeBoolean( register(command[0],command[1],command[2],command[3]) );
            } else if ( command[0].equals( "login" ) ){
                System.out.println( "Login command :\n" + "user:" + command[1] + "\npass:" + command[2] ) ;
                writer.writeBoolean( login( command[0] , command[1] ) );
            } else {
                System.out.println( "Unknown command in LoginRegister." ) ;
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
