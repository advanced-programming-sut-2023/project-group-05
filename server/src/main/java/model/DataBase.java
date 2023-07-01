package model ;

import model.Account ;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase
{
    ///public static String location = ( Main.class.getResource("/data.json").toString()).replaceAll("file:/","") ;

    public static String location = "/home/aria/tamrin/AP/project-group-05/server/src/main/resources/data.json";
    public static void deleteAccount( String type, String S )
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            JSONArray newData = new JSONArray();
            int yetDeleted = 0;
            for (Object datum : data)
            {
                JSONObject currentObject = (JSONObject) datum;
                String thisObjectUserName = (String) currentObject.get(type);
                if(yetDeleted > 0 || !thisObjectUserName.equals(S))
                {
                    newData.add(currentObject);
                }
                else
                {
                    yetDeleted = 1;
                }
            }
            reader.close() ;
            try (FileWriter file = new FileWriter(location))
            {
                file.write(newData.toJSONString());
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

    public static void setStayLoggedIn( Account account )
    {
        deleteAccount("userName", account.getUserName());
        account.setPassword(0);
        addNewAccount(account);
    }

    public static ArrayList< Account > getStayLoggedInAccount()
    {
        ArrayList < Account > ret = new ArrayList< Account >();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(location))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            for (Object datum : data)
            {
                JSONObject currentObject = (JSONObject) datum;
                long thisObjectPassword = (long) currentObject.get("password");
                String thisObjectString = (String) currentObject.get("userName");
                if(thisObjectPassword == 0)
                {
                    ret.add(Account.getAccountsMap().get(thisObjectString));
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
        return ret;
    }


    public static JSONObject transformAccountToJSONObject( Account account)
    {
        JSONObject newObject = new JSONObject();
        newObject.put("userName", account.getUserName());
        newObject.put("nickName", account.getNickName());
        newObject.put("email", account.getEmail());
        newObject.put("password", account.getPasswordHash());
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
        try{
            FileReader reader = new FileReader(location) ;
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            for(Object datum : data)
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