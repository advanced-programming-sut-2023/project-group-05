package controller;

import com.google.gson.Gson;
import model.Chat;
import model.ChatPacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PushNotification extends Thread
{
    public LinkedBlockingQueue< ChatPacket > note = new LinkedBlockingQueue<>();

    public Socket socket;

    public DataOutputStream dataOutputStream;

    public PushNotification(Socket _socket) throws IOException
    {
        socket = _socket;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                handle();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void Push(ChatPacket chatPacket)
    {
        this.note.add(chatPacket);
    }

    public synchronized void handle() throws InterruptedException, IOException {
        if(note.isEmpty()) return;
        ChatPacket chatPacket = note.take();
        System.out.println("Sending this Packet: ");
        System.out.println(chatPacket.toJson().toJSONString());
        for(int i = ChatConnection.connections.size() - 1; i >= 0; i --)
        {
            ChatConnection connection = ChatConnection.connections.get(i);
            if(!connection.socket.isConnected())
            {
                continue;
            }
            if(connection.userName.equals(chatPacket.to) && connection.socket.getLocalPort() == ChatPortDistributor.ports.get(chatPacket.to))
            {
                System.out.println("here Send to " + chatPacket.to);
                System.out.println(connection.socket.isConnected());
                System.out.println(connection.socket.getLocalPort());
                connection.dataOutputStream.writeUTF(chatPacket.toJson().toJSONString());
            }
        }
    }
}
