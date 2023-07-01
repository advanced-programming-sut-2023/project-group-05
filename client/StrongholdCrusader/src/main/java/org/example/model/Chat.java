package org.example.model;

import org.example.view.ChatPage;

import java.util.ArrayList;

public class Chat
{
    private final String first;
    private final String second;
    private ArrayList < Message > Messages = new ArrayList<>();

    public Chat(String first, String second)
    {
        this.first = first;
        this.second = second;
    }

    public void AddMessage(Message message)
    {
        Messages.add(message);
        ChatPage.updatePane();
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public ArrayList<Message> getMessages() {
        return Messages;
    }

}
