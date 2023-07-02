package controller;

import com.google.gson.Gson;
import model.ChatPacket;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PushNotification extends Thread
{
    public LinkedBlockingQueue< ChatPacket > note = new LinkedBlockingQueue<>();
    @Override
    public void run()
    {
        while (true)
        {
            try {
                handle();
            } catch (InterruptedException | IOException e) {
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
        for(int i = 0; i < ChatConnection.connections.size(); i ++)
        {
            ChatConnection cur = ChatConnection.connections.get(i);
            if(!cur.isAlive()) continue;
            if(cur.userName.equals(chatPacket.to))
            {
                System.out.println("Output Data " + chatPacket.toJson().toJSONString());
                cur.dataOutputStream.writeUTF(chatPacket.toJson().toJSONString());
            }
        }
    }
}
