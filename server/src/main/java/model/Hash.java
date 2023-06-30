package model ;

public class Hash
{
    static private long MOD = 1000000007 ;
    static private long BASE = 5021 ;
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

    static public long encode( String S)
    {
        long nowHsh = 0;
        int n = S.length();
        for(int i = 0; i < n; i ++)
        {
            nowHsh = (nowHsh * Hash.getBASE() + getInt(S.charAt(i))) % Hash.getMOD();
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
        long now = encode(inp);
        return now == this.Hsh;
    }
    public Hash( String S)
    {
        Hsh = encode(S);
    }

}
