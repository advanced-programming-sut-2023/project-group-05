package model;

import com.google.gson.Gson;

import java.io.Serializable;

public class ChatPacket implements Serializable {
    public String to;
    public String command;
    public String name;
    public String jsonInfo;

    public ChatPacket(String to, String command, String name, String jsonInfo)
    {
        this.to = to;
        this.command = command;
        this.name = name;
        this.jsonInfo = jsonInfo;
    }
    public String toJson()
    {
        return new Gson().toJson(this);
    }
}
