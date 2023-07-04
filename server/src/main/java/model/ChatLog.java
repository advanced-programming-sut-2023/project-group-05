package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ChatLog
{
    public static String location = "/home/aria/tamrin/AP/project-group-05/server/src/main/resources/ChatLog.json";
    /// public static String location = "C:\\Users\\TUF\\Desktop\\university\\AdvancedProgramming\\project-group-05\\server\\src\\main\\resources\\ChatLog.json" ;
    public static ArrayList < Chat > chatLog = new ArrayList<>();
    public static void WAKEUP() throws FileNotFoundException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for (Object datum : data) {
                Chat chat = Chat.fromJson((JSONObject) datum);
                chatLog.add(chat);
            }
            fileReader.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean AddChat(Chat chat)
    {
        for(int i = 0; i < chatLog.size(); i ++)
        {
            Chat now = chatLog.get(i);
            boolean c1 = now.getFirst().equals(chat.getFirst()) && now.getSecond().equals(chat.getSecond());
            boolean c2 = now.getFirst().equals(chat.getSecond()) && now.getSecond().equals(chat.getFirst());
            if(c1 || c2)
            {
                return false;
            }
        }
        chatLog.add(chat);
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            data.add(chat.toJson());
            fileReader.close();
            try (FileWriter file = new FileWriter(location))
            {
                file.write(data.toJSONString());
                file.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static JSONArray toJson()
    {
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < chatLog.size(); i ++)
        {
            jsonArray.add(chatLog.get(i).toJson());
        }
        return jsonArray;
    }

    public static void RemoveChat(Chat chat) throws IOException {
        chatLog.remove(chat);
        try (FileWriter fileWriter = new FileWriter(location))
        {
            fileWriter.write(toJson().toJSONString());
        }
    }
}
