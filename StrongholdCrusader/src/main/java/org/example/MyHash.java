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
    static public long getInt(char c)
    {
        return c;
    }
    static public long Encode(String S)
    {
        long nowHsh = 0;
        int n = S.length();
        for(int i = 0; i < n; i ++)
        {
            nowHsh = (nowHsh * getBASE() + getInt(S.charAt(i))) % getMOD();
        }
        return nowHsh;
    }
    private final long Hsh;
    public long getHsh()
    {
        return this.Hsh;
    }
    public boolean isEqual(String inp)
    {
        long now = Encode(inp);
        return now == this.Hsh;
    }
    public MyHash(String S)
    {
        Hsh = Encode(S);
    }

}
