package controller;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ChatConnection extends Thread
{
    public PushNotification notifications;
    public static ArrayList < ChatConnection > connections = new ArrayList<>();
    public String userName;
    Socket socket;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;

    public ChatConnection(Socket _socket) throws IOException
    {
        System.out.println("ANOTHER CONNECTION HERE!! at port " + _socket.getPort());
        socket = _socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        connections.add(this);
        notifications = new PushNotification(socket);
        notifications.start();
        userName = dataInputStream.readUTF();

    }

    @Override
    public synchronized void run()
    {
        while (true)
        {
            if(!socket.isConnected())
            {
                System.out.println("Socket " + userName + " is Disconnected");
                /// connections.remove(this);
                /*try {
                    socket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }*/
                break;
            }
            try {
                if(dataInputStream.available() != 0)
                {
                    String dataIn = dataInputStream.readUTF();
                    System.out.println(" DataIn from client: " + dataIn);
                    try
                    {
                        JSONParser anotherParser = new JSONParser();
                        ChatPacket inp = ChatPacket.fromJson((JSONObject) anotherParser.parse(dataIn));
                        String _to = inp.to;
                        String command = inp.command;
                        String name = inp.name;
                        String json = inp.jsonInfo;


                        if(command.equals("GetSingleChat"))
                        {;
                            ChatPacket out = null;
                            for(int i = 0; i < ChatLog.chatLog.size(); i ++)
                            {
                                Chat chat = ChatLog.chatLog.get(i);
                                if(chat.getFirst().equals(name) && chat.getSecond().equals(userName))
                                {
                                    out = new ChatPacket(this.userName, "DATA", "", chat.toJson().toJSONString());
                                    break;
                                }
                                else if(chat.getSecond().equals(name))
                                {
                                    out = new ChatPacket(this.userName, "DATA", "", chat.toJson().toJSONString());
                                    break;
                                }
                            }
                            if(out == null)
                            {
                                out = new ChatPacket(this.userName, "Invalid", "", "");
                            }
                            notifications.Push(out);
                        }
                        else if (command.equals("UpdateChat"))
                        {
                            ChatPacket f = null, s = null;
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
                            Chat chat = Chat.fromJson(jsonObject);
                            String firstName = chat.getFirst() ;
                            String secondName = chat.getSecond() ;
                            int groupIndex = 0 ;
                            if( firstName.startsWith( "group" ) )
                                groupIndex = Integer.parseInt( firstName.substring( 5 , 6 ) ) ;
                            else if ( secondName.startsWith("group") )
                                groupIndex = Integer.parseInt( secondName.substring( 5 , 6 ) ) ;
                            for(int i = 0; i < ChatLog.chatLog.size(); i ++)
                            {
                                Chat cur = ChatLog.chatLog.get(i);
                                boolean condition1 = cur.getFirst().equals(chat.getFirst());
                                boolean condition2 = cur.getSecond().equals(chat.getSecond());
                                boolean condition3 = groupIndex != 0 && (cur.getFirst().equals("group"+groupIndex) || cur.getSecond().equals( "group" + groupIndex)) ;
                                if((condition1 && condition2) || condition3 )
                                {
                                    ChatLog.RemoveChat(cur);
                                    break;
                                }
                            }
                            if( groupIndex == 0 ){
                                ChatLog.addChat(chat);
                                f = new ChatPacket(chat.getFirst(), "UPDATE", "", chat.toJson().toJSONString());
                                s = new ChatPacket(chat.getSecond(), "UPDATE", "", chat.toJson().toJSONString());
                                notifications.Push(f);
                                notifications.Push(s);
                            }
                            else {
                                for( Map.Entry acc : Account.getAccountsMap().entrySet() ){
                                    Chat cur = new Chat( (String)acc.getKey() , "group" + groupIndex ) ;
                                    cur.setMessages( chat.getMessages() ) ;
                                    ChatLog.addChat( cur ) ;
                                    ChatPacket chatPacket = new ChatPacket( cur.getFirst() , "UPDATE" , "" , cur.toJson().toJSONString() ) ;
                                    notifications.Push( chatPacket ) ;
                                }
                            }
                        }
                        else if(command.equals("GetChatList"))
                        {
                            JSONArray jsonArray = new JSONArray();
                            for(int i = 0; i < ChatLog.chatLog.size(); i ++)
                            {
                                Chat chat = ChatLog.chatLog.get(i);
                                if(chat.getFirst().equals(this.userName))
                                {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("name", chat.getSecond());
                                    jsonObject.put("numberOfMessages", chat.getMessages().size());
                                    jsonArray.add(jsonObject);
                                }
                                else if(chat.getSecond().equals(this.userName))
                                {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("name", chat.getFirst());
                                    jsonObject.put("numberOfMessages", chat.getMessages().size());
                                    jsonArray.add(jsonObject);
                                }
                            }
                            notifications.Push(new ChatPacket(this.userName, "GetChatList", "", jsonArray.toJSONString()));
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
