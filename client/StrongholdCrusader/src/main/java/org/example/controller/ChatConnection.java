package org.example.controller;

import com.google.gson.Gson;
import javafx.stage.Stage;
import org.example.model.Chat;
import org.example.model.ChatPacket;
import org.example.view.ChatMenu;
import org.example.view.ChatPage;
import org.example.view.MainMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatConnection
{
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ChatNotification chatNotification;
    public static String userName;

    public static ChatPage chatPage;

    public static ChatMenu chatMenu;

    public static void Run(String _userName) throws Exception
    {
        userName = _userName;
        String host = "localhost";
        int port = -1;
        System.out.println("get the port from server");
        Socket getPortSocket = new Socket(host, 2018);
        DataInputStream getPortInput = new DataInputStream(getPortSocket.getInputStream());
        DataOutputStream getPortOutput = new DataOutputStream(getPortSocket.getOutputStream());

        getPortOutput.writeUTF(userName);
        getPortOutput.flush();

        port = Integer.parseInt(getPortInput.readUTF());
        getPortSocket.close();

        System.out.println("Starting Client service on port " + port);
        Socket socket = new Socket(host, port);
        System.out.println(socket.isConnected());
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        chatNotification = new ChatNotification(dataInputStream);
        chatNotification.start();
        dataOutputStream.writeUTF(userName);
        dataOutputStream.flush();
        ChatPacket chatPacket = new ChatPacket("", "GetChatList", userName, "");
        dataOutputStream.writeUTF(chatPacket.toJson().toJSONString());
        dataOutputStream.flush();


        ChatPage.userName = _userName ;
        chatMenu = new ChatMenu();
        chatMenu.start(MainMenu.stage);

    }
    public static void getChatWith(String otherPerson) throws IOException {
        System.out.println("getChat");
        dataOutputStream.writeUTF(new ChatPacket("", "GetSingleChat", otherPerson, "").toJson().toJSONString());
    }
    public static void updateChatWith(String otherPerson, Chat chat) throws IOException {
        System.out.println("updateChat");
        ChatPage.updatePane() ;
        dataOutputStream.writeUTF(new ChatPacket("", "UpdateChat", "", chat.toJson().toJSONString()).toJson().toJSONString());
    }
}
