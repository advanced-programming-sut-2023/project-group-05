package model;

public class Message
{

    private String sender;
    private String receiver; /// These 2 are UserNames :)
    private String text;
    private String date;
    private boolean Seen;

    public Message(String sender, String receiver, String text, String date)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
        Seen = false;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
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

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
