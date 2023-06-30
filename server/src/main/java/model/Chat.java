package model;

import java.util.ArrayList;

public class Chat
{
    private String first;
    private String second;
    private ArrayList < Message > Messages = new ArrayList<>();

    public Chat(String first, String second)
    {
        this.first = first;
        this.second = second;
    }
    public void AddMessage(Message message)
    {
        Messages.add(message);
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
