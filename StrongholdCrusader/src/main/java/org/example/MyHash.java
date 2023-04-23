package org.example;

public class MyHash
{
    static private long MOD;
    static private long BASE;
    static public void modSetter(long inp)
    {
        MOD = inp;
    }
    static public long modGetter()
    {
        return MOD;
    }
    static public void baseSetter(long inp)
    {
        BASE = inp;
    }
    static public long baseGetter()
    {
        return BASE;
    }
    public long getInt(char c)
    {
        return (long)c;
    }
    public long Hsh;
    public long Encode(String S)
    {
        long nowHsh = 0;
        int n = S.length();
        for(int i = 0; i < n; i ++)
        {
            nowHsh = (nowHsh * baseGetter() + getInt(S.charAt(i))) % modGetter();
        }
        return nowHsh;
    }
    public boolean isEqual(String inp)
    {
        long now = Encode(inp);
        return now == Hsh;
    }
    MyHash(String S)
    {
        Hsh = Encode(S);
    }

}
