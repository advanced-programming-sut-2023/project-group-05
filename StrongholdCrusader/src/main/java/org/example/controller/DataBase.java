package org.example.controller;

import org.example.model.Account;
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
    public static String location = "src/main/java/org/example/data.json";

    public static JSONObject transformAccountToJSONObject( Account account)
    {
        JSONObject newObject = new JSONObject();
        newObject.put("userName", account.getUserName());
        newObject.put("nickName", account.getNickName());
        newObject.put("email", account.getEmail());
        newObject.put("password", account.getHash());
        newObject.put("highScore", account.getHighScore());
        newObject.put("slogan", account.getSlogan());
        newObject.put("question", account.getQuestion());
        newObject.put("answer", account.getAnswer());
        return newObject;
    }
    public static boolean isAccountInData(Account account)
    {
        return getFromDataBase("userName", account.getUserName()) != null;
    }
    public static JSONObject getFromDataBase(String type, String S)
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            for (Object datum : data)
            {
                JSONObject currentObject = (JSONObject) datum;
                String thisObjectByType = (String) currentObject.get(type);
                if (thisObjectByType.equals(S))
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
            return ;
        JSONObject current = transformAccountToJSONObject(account);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            data.add(current);
            reader.close() ;
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

    public static void wakeUp()
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            for (Object datum : data)
            {
                JSONObject currentObject = (JSONObject) datum;
                String userName = (String) currentObject.get("userName");
                String nickName = (String) currentObject.get("nickName");
                String email = (String) currentObject.get("email");
                long password = (long) currentObject.get("password");
                long highScore = (long) currentObject.get("highScore");
                long question = (long) currentObject.get("question");
                long answer = (long) currentObject.get("answer");
                String slogan = (String) currentObject.get("slogan");
                Account account = new Account(userName, nickName, email, password, highScore, slogan, question, answer);
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