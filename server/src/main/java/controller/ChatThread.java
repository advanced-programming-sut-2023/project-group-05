package controller;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatThread extends Thread {

    @Override
    public void run()
    {
        System.out.println("Chat Thread Online on port 2021 currently listening");
        ChatConnection.notifications = new PushNotification();
        ChatConnection.notifications.start();

        ServerSocket serverSocket ;
        try{
            serverSocket = new ServerSocket( 2021 ) ;
            while(true)
            {
                Socket client = serverSocket.accept() ;
                ///TODO: Danial have to give me the username after logging in
                ChatConnection connection = new ChatConnection(client);
                connection.start();
            }
        } catch( Exception e ){
            e.printStackTrace();
        }
    }
}
