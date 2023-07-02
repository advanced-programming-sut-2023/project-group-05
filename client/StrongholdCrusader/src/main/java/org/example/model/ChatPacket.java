package org.example.model;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

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
    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to", to);
        jsonObject.put("command", command);
        jsonObject.put("name", name);
        jsonObject.put("jsonInfo", jsonInfo);
        return jsonObject;
    }
    public static ChatPacket fromJson(JSONObject jsonObject)
    {
        return new ChatPacket((String) jsonObject.get("to"), (String) jsonObject.get("command"), (String) jsonObject.get("name"), (String) jsonObject.get("jsonInfo"));
    }
}
