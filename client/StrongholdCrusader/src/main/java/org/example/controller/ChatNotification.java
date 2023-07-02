package org.example.controller;

import com.google.gson.Gson;
import javafx.stage.Stage;
import org.example.model.Chat;
import org.example.model.ChatPacket;
import org.example.view.ChatMenu;
import org.example.view.ChatPage;
import org.example.view.Game;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.IOException;

public class ChatNotification extends Thread
{
    private final DataInputStream dataInputStream;
    public ChatNotification(DataInputStream inp)
    {
        this.dataInputStream = inp;
    }
    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                String data = dataInputStream.readUTF();
                System.out.println("Got Input: " + data);
                if(!data.startsWith("{"))
                {
                    System.out.println(data);
                }
                else
                {
                    JSONParser lastJsonParser = new JSONParser();
                    ChatPacket chatPacket = ChatPacket.fromJson((JSONObject) lastJsonParser.parse(data));
                    /// System.out.println(chatPacket.toJson());
                    if(chatPacket.command.equals("DATA") || chatPacket.command.equals("UPDATE"))
                    {
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(chatPacket.jsonInfo);
                        ChatPage.chat = Chat.fromJson(jsonObject);
                        System.out.println(ChatPage.chat.toJson().toJSONString());
                        ///ChatPage.updatePane();
                        //new ChatPage().start(ChatMenu.stage);
                    }
                    else if (chatPacket.command.equals("GetChatList"))
                    {
                        System.out.println("We Are Here!");
                        ChatMenu.privateChatNumber.clear();
                        ChatMenu.privateChatNames.clear();
                        ChatMenu.publicChatNames.clear();
                        ChatMenu.publicChatNumber.clear();
                        JSONParser jsonParser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) jsonParser.parse(chatPacket.jsonInfo);
                        System.out.println(jsonArray);
                        for(int i = 0; i < jsonArray.size(); i ++)
                        {
                            JSONObject now = (JSONObject) jsonArray.get(i);
                            String chatName = (String) now.get("name");
                            long num = (long) now.get("numberOfMessages");
                            if(chatName.startsWith("group"))
                            {
                                ChatMenu.publicChatNumber.add(num);
                                ChatMenu.publicChatNames.add(chatName);
                            }
                            else
                            {
                                ChatMenu.privateChatNumber.add(num);
                                ChatMenu.privateChatNames.add(chatName);
                            }
                        }
                        ChatMenu.updatePublicChat();
                        ChatMenu.updatePrivateChat();
                    }
                    else
                    {
                        System.out.println(chatPacket.command);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
