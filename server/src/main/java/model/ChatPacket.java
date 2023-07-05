package model;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Random;

public class ChatPacket implements Serializable {
    public String to;
    public String command;
    public String name;
    public String jsonInfo;

    public Token token;

    public ChatPacket(String to, String command, String name, String jsonInfo)
    {
        this.to = to;
        this.command = command;
        this.name = name;
        this.jsonInfo = jsonInfo;
        Random rand = new Random();
        token = new Token(rand.nextLong(), rand.nextLong());
    }
    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to", to);
        jsonObject.put("command", command);
        jsonObject.put("name", name);
        jsonObject.put("jsonInfo", jsonInfo);
        jsonObject.put("token", token.toJson().toJSONString());
        return jsonObject;
    }
    public static ChatPacket fromJson(JSONObject jsonObject)
    {
        return new ChatPacket((String) jsonObject.get("to"), (String) jsonObject.get("command"), (String) jsonObject.get("name"), (String) jsonObject.get("jsonInfo"));
    }
}