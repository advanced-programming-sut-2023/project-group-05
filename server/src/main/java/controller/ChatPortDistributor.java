package controller;

import model.Hash;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ChatPortDistributor extends Thread
{

    private int freePort = 3051;
    public static HashMap < String, Integer > ports = new HashMap<>();

    public static HashMap < String , String > offlineUsers = new HashMap<>();
    public void OUTPUT(String userName, int port)
    {
        System.out.println("user: " + userName + " connected to port: "  + port);
    }
    @Override
    public void run()
    {
        ServerSocket serverSocket ;
        try
        {
            serverSocket = new ServerSocket(2018);
            DataInputStream reader;
            DataOutputStream writer;
            System.out.println("### listening on port 2018 for Chat's port distribution");
            while(true)
            {
                Socket client = serverSocket.accept();
                System.out.println(ChatPortDistributor.offlineUsers);
                System.out.println("new request for Chat's port distributor has received.");
                reader = new DataInputStream(client.getInputStream());
                writer = new DataOutputStream(client.getOutputStream());

                ///System.out.println(reader.readUTF());
                String userName = reader.readUTF();

                System.out.println("here with userName: " + userName);
                /// return;

                while(freePort < 3555)
                {
                    try
                    {
                        (new ServerSocket(freePort)).close();
                        (new ChatThread(freePort)).start();
                        writer.writeUTF(""+freePort);
                        writer.flush();
                        client.close();

                        ports.put(userName, freePort);
                        System.out.println("1");
                        OUTPUT(userName, freePort);

                        freePort ++;
                        break;
                    }
                    catch(Exception e)
                    {
                        System.out.println("port " + freePort + " is currently unavailable. trying next one.") ;
                        freePort++ ;
                    }
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}