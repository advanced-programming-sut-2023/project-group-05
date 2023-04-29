package org.example.model;

import org.example.controller.DataBase;

import java.util.HashMap;

public class Account
{
    private long passwordHash;
    private String userName;
    private String nickName;
    private String email;
    private long highScore;
    private String slogan;
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
    public long getPasswordHash()
    {
        return this.passwordHash;
    }
    public void setSlogan( String slogan ){
        this.slogan = slogan ;
    }
    public void setEmail( String email ){
        this.email = email ;
    }
    public void setUserName( String username ){
        this.userName = username ;
    }
    public void setPassword( long password ){
        this.passwordHash = password ;
    }
    public void setNickname( String nickName ){
        this.nickName = nickName ;
    }
    public void setHighScore( int newHighScore ){
        this.highScore = newHighScore ;
    }
    public Account(String _userName, String _nickName, String _email, long _password, long _highScore, String _slogan, long _question, long _answer)
    {
        this.userName = _userName;
        this.nickName = _nickName;
        this.email = _email;
        this.passwordHash = _password;
        this.highScore = _highScore;
        this.slogan = _slogan;
        this.question = _question;
        this.answer = _answer;
        accountsMap.put(this.userName, this);
        if(! DataBase.isAccountInData(this)) DataBase.addNewAccount(this);
    }

}