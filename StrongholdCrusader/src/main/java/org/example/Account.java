package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Account
{
    static private ArrayList < Account > accounts = new ArrayList< Account >();
    static private HashMap < String, Account > accountsMap = new HashMap < String, Account >();
    static public ArrayList < Account > getAccounts()
    {
        return accounts;
    }
    static public HashMap < String , Account > getAccountsMap()
    {
        return accountsMap;
    }
    private String userName, nickName, email;
    private long highScore;
    private int slogan, question, answer;
    public String getUserName()
    {
        return this.userName;
    }
    public String getNickName()
    {
        return this.nickName;
    }
    public String getEmail()
    {
        return this.email;
    }
    public long getHighScore()
    {
        return highScore;
    }
    public int getAnswer()
    {
        return answer;
    }
    public int getSlogan()
    {
        return slogan;
    }
    public int getQuestion()
    {
        return question;
    }
    public Account(String _userName, String _nickName, String _email, long _highScore, int _slogan, int _question, int _answer)
    {
        this.userName = _userName;
        this.nickName = _nickName;
        this.email = _email;
        this.highScore = _highScore;
        this.slogan = _slogan;
        this.question = _question;
        this.answer = _answer;
        accounts.add(this);
        accountsMap.put(this.userName, this);
    }
}
