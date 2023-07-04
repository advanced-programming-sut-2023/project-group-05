package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatThread extends Thread
{
    int port;
    ChatThread(int _port)
    {
        port = _port;
    }

    @Override
    public void run()
    {
        System.out.println("Chat Threads are Online on port " + port + " currently listening");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true)
            {
                Socket client = serverSocket.accept();
                (new ChatConnection(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
