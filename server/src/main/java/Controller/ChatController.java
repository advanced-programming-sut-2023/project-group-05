package Controller;

import Model.Chat;

import java.util.ArrayList;

public class ChatController
{
    public static ArrayList < Chat > Chats = new ArrayList<>();

    public static void AddChat(Chat chat)
    {
        Chats.add(chat);
    }

}
