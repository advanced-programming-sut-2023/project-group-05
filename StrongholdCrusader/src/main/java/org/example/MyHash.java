package org.example;

public class MyHash
{
    static private long MOD;
    static private long BASE;
    static public void setMOD(long inp)
    {
        MOD = inp;
    }
    static public long getMOD()
    {
        return MOD;
    }
    static public void setBASE(long inp)
    {
        BASE = inp;
    }
    static public long getBASE()
    {
        return BASE;
    }
    public long getInt(char c)
    {
        return (long)c;
    }
    private long Hsh;
    public long Encode(String S)
    {
        long nowHsh = 0;
        int n = S.length();
        for(int i = 0; i < n; i ++)
        {
            nowHsh = (nowHsh * getBASE() + getInt(S.charAt(i))) % getMOD();
        }
        return nowHsh;
    }
    public boolean isEqual(String inp)
    {
        long now = Encode(inp);
        return now == this.Hsh;
    }
    MyHash(String S)
    {
        Hsh = Encode(S);
    }

}
