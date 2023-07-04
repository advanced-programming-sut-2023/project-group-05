package org.example.model;

import org.json.simple.JSONObject;

import java.util.Date;

public class Message
{

    public String sender;
    public String text;
    public String date;
    public boolean Seen;

    public Message(String sender, String text)
    {
        this.sender = sender;
        this.text = text;
        this.date = (new Date()).toString();
        Seen = false;
    }

    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", sender);
        jsonObject.put("text", text);
        jsonObject.put("date", date);
        jsonObject.put("Seen", Seen);
        return jsonObject;
    }

    public static Message fromJson(JSONObject jsonObject)
    {
        Message message =  new Message(jsonObject.get("sender").toString(), jsonObject.get("text").toString());
        message.setDate(jsonObject.get("date").toString());
        message.setSeen((boolean) jsonObject.get("Seen"));
        return message;
    }

    public String getSender() {
        return sender;
    }


    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public boolean isSeen() {
        return Seen;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSeen(boolean seen) {
        Seen = seen;
    }

}