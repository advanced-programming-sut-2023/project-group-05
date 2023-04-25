package org.example.model;

import org.example.controller.DataBase;

import java.util.HashMap;

public class Account
{
    private long Hash;
    private final String userName;
    private final String nickName;
    private final String email;
    private final long highScore;
    private final String slogan;
    private final long question;
    private final long answer;
    static private final HashMap < String, Account > accountsMap = new HashMap < String, Account >();
    static public HashMap < String , Account > getAccountsMap()
    {
        return accountsMap;
    }
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
    public long getAnswer()
    {
        return answer;
    }
    public String getSlogan()
    {
        return slogan;
    }
    public long getQuestion()
    {
        return question;
    }
    public long getHash()
    {
        return this.Hash;
    }
    public Account(String _userName, String _nickName, String _email, long _password, long _highScore, String _slogan, long _question, long _answer)
    {
        this.userName = _userName;
        this.nickName = _nickName;
        this.email = _email;
        this.Hash = _password;
        this.highScore = _highScore;
        this.slogan = _slogan;
        this.question = _question;
        this.answer = _answer;
        accountsMap.put(this.userName, this);
        if(! DataBase.isAccountInData(this)) DataBase.addNewAccount(this);
    }

}