package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataBase
{
    public static String location = "data.json";

    public static JSONObject transformAccountToJSONObject(Account account)
    {
        JSONObject newObject = new JSONObject();
        newObject.put("userName", account.getUserName());
        newObject.put("nickName", account.getNickName());
        newObject.put("email", account.getEmail());
        newObject.put("password", account.accountHash.getHsh());
        newObject.put("highScore", account.getHighScore());
        newObject.put("slogan", account.getSlogan());
        newObject.put("question", account.getQuestion());
        newObject.put("answer", account.getAnswer());
        return newObject;
    }
    public static boolean isAccountInData(Account account)
    {
        JSONObject current = transformAccountToJSONObject(account);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            if(data.contains(current))
            {
                return true;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static JSONObject getFromDataBase(String type, String userName)
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            for (Object datum : data)
            {
                JSONObject currentObject = (JSONObject) datum;
                String thisObjectUserName = (String) currentObject.get(type);
                if (thisObjectUserName.equals(userName))
                {
                    return currentObject;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static void addNewAccount(Account account)
    {
        if(isAccountInData(account))
        {
            return;
        }
        JSONObject current = transformAccountToJSONObject(account);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            data.add(current);
            try (FileWriter file = new FileWriter(location))
            {
                file.write(data.toJSONString());
                file.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
