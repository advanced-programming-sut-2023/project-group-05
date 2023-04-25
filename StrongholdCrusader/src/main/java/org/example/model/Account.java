package org.example.model;

import org.example.controller.DataBase;
import org.example.MyHash;

import java.util.ArrayList;
import java.util.HashMap;

public class Account
{
    private MyHash accountHash;
    private final String userName;
    private final String nickName;
    private final String email;
    private final long highScore;
    private final String slogan;
    private final int question;
    private final int answer;
    static private final ArrayList < Account > accounts = new ArrayList< Account >();
    static private final HashMap < String, Account > accountsMap = new HashMap < String, Account >();
    static public ArrayList < Account > getAccounts()
    {
        return accounts;
    }
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
    public int getAnswer()
    {
        return answer;
    }
    public String getSlogan()
    {
        return slogan;
    }
    public int getQuestion()
    {
        return question;
    }
    public Account(String _userName, String _nickName, String _email, String _password, long _highScore, String _slogan, int _question, int _answer)
    {
        this.userName = _userName;
        this.nickName = _nickName;
        this.email = _email;
        accountHash = new MyHash(_password);
        this.highScore = _highScore;
        this.slogan = _slogan;
        this.question = _question;
        this.answer = _answer;
        accounts.add(this);
        accountsMap.put(this.userName, this);
        if(! DataBase.isAccountInData(this)) DataBase.addNewAccount(this);
    }

    public MyHash getAccountHash(){
        return this.accountHash ;
    }

}
