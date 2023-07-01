package org.example.model;

import org.example.model.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Chat
{
    private final String first;
    private final String second;
    private ArrayList < Message > messages = new ArrayList<>();

    public Chat(String first, String second)
    {
        this.first = first;
        this.second = second;
    }


    public static Chat fromJson(JSONObject jsonObject)
    {
        String First = jsonObject.get("first").toString();
        String Second = jsonObject.get("second").toString();
        Chat chat = new Chat(First, Second);
        JSONArray jsonArray = (JSONArray) jsonObject.get("messages");
        for (Object o : jsonArray) {
            chat.getMessages().add(Message.fromJson((JSONObject) o));
        }
        return chat;
    }
    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("first", first);
        jsonObject.put("second", second);
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < messages.size(); i ++)
        {
            jsonArray.add(messages.get(i).toJson());
        }
        jsonObject.put("messages", jsonArray);
        return jsonObject;
    }

    public void AddMessage(Message message)
    {
        messages.add(message);
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

}
