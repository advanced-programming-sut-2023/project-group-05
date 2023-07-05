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
import java.util.Dictionary;

public class FriendsDB
{
    public static String location = "/home/aria/tamrin/AP/project-group-05/server/src/main/resources/Friends.json";

    public static boolean AddNewUser(String name)
    {
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for(int i = 0; i < data.size(); i ++)
            {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String userName = (String) jsonObject.get("userName");
                if(userName.equals(name))
                {
                    return false;
                }
            }
            JSONObject newUser = new JSONObject();
            JSONArray newUserFriendList = new JSONArray();
            JSONArray newUserSentList = new JSONArray();
            JSONArray newUserReceivedList = new JSONArray();
            newUser.put("userName", name);
            newUser.put("friends", newUserFriendList);
            newUser.put("sent", newUserSentList);
            newUser.put("received", newUserReceivedList);
            data.add(newUser);
            fileReader.close();
            try (FileWriter fileWriter = new FileWriter(location))
            {
                fileWriter.write(data.toJSONString());
                fileWriter.flush();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean AddFriend(String fir, String sec) throws FileNotFoundException {
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for(int i = 0; i < data.size(); i ++)
            {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String userName = (String) jsonObject.get("userName");
                JSONArray friendList = (JSONArray) jsonObject.get("friends");
                JSONArray sent = (JSONArray) jsonObject.get("sent");
                JSONArray received = (JSONArray) jsonObject.get("received");
                if(userName.equals(fir))
                {
                    for(int j = 0; j < friendList.size(); j ++)
                    {
                        String other = (String)((JSONObject)friendList.get(j)).get("userName");
                        if(other.equals(sec))
                        {
                            return false;
                        }
                    }
                    JSONObject newFriend = new JSONObject();
                    newFriend.put("userName", sec);
                    friendList.add(newFriend);
                }
                else if(userName.equals(sec))
                {
                    for(int j = 0; j < friendList.size(); j ++)
                    {
                        String other = (String)((JSONObject)friendList.get(j)).get("userName");
                        if(other.equals(fir))
                        {
                            return false;
                        }
                    }
                    JSONObject newFriend = new JSONObject();
                    newFriend.put("userName", fir);
                    friendList.add(newFriend);
                }
            }
            fileReader.close();
            try (FileWriter fileWriter = new FileWriter(location))
            {
                fileWriter.write(data.toJSONString());
                fileWriter.flush();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean AddRequest(String from, String to)
    {
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for(int i = 0; i < data.size(); i ++)
            {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String userName = (String) jsonObject.get("userName");
                JSONArray sent = (JSONArray) jsonObject.get("sent");
                JSONArray received = (JSONArray) jsonObject.get("received");
                if(userName.equals(from))
                {
                    for(int j = 0; j < sent.size(); j ++)
                    {
                        String other = (String)((JSONObject)sent.get(j)).get("userName");
                        if(other.equals(to))
                        {
                            return false;
                        }
                    }
                    JSONObject newFriend = new JSONObject();
                    newFriend.put("userName", to);
                    sent.add(newFriend);
                }
                else if(userName.equals(to))
                {
                    for(int j = 0; j < received.size(); j ++)
                    {
                        String other = (String)((JSONObject)received.get(j)).get("userName");
                        if(other.equals(from))
                        {
                            return false;
                        }
                    }
                    JSONObject newFriend = new JSONObject();
                    newFriend.put("userName", from);
                    received.add(newFriend);
                }
            }
            fileReader.close();
            try (FileWriter fileWriter = new FileWriter(location))
            {
                fileWriter.write(data.toJSONString());
                fileWriter.flush();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static ArrayList < String > getFriendList(String name)
    {
        ArrayList < String > ret = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for(int i = 0; i < data.size(); i ++)
            {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String userName = (String) jsonObject.get("userName");
                JSONArray friendList = (JSONArray) jsonObject.get("friends");
                JSONArray sent = (JSONArray) jsonObject.get("sent");
                JSONArray received = (JSONArray) jsonObject.get("received");
                if(userName.equals(name))
                {
                    for(int j = 0; j < friendList.size(); j ++)
                    {
                        ret.add((String) ((JSONObject)friendList.get(j)).get("userName"));
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public static ArrayList < String > getSentList(String name)
    {
        ArrayList < String > ret = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(location))
        {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for(int i = 0; i < data.size(); i ++)
            {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String userName = (String) jsonObject.get("userName");
                JSONArray friendList = (JSONArray) jsonObject.get("friends");
                JSONArray sent = (JSONArray) jsonObject.get("sent");
                JSONArray received = (JSONArray) jsonObject.get("received");
                if(userName.equals(name))
                {
                    for(int j = 0; j < sent.size(); j ++)
                    {
                        ret.add((String) ((JSONObject)sent.get(j)).get("userName"));
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public static ArrayList < String > getReceivedList(String name)
    {
        ArrayList<String> ret = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(location)) {
            JSONArray data = (JSONArray) jsonParser.parse(fileReader);
            for (int i = 0; i < data.size(); i++) {
                JSONObject jsonObject = (JSONObject) data.get(i);
                String userName = (String) jsonObject.get("userName");
                JSONArray friendList = (JSONArray) jsonObject.get("friends");
                JSONArray sent = (JSONArray) jsonObject.get("sent");
                JSONArray received = (JSONArray) jsonObject.get("received");
                if (userName.equals(name)) {
                    for (int j = 0; j < received.size(); j++) {
                        ret.add((String) ((JSONObject) received.get(j)).get("userName"));
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
