package model;

import org.json.simple.JSONObject;

public class Token
{
    public static long LIMIT = 1000000000;
    long start_time;
    long shortToken;
    long refreshToken;

    public Token(long shortToken, long refreshToken)
    {
        this.start_time = System.currentTimeMillis();
        this.shortToken = shortToken;
        this.refreshToken = refreshToken;
    }

    public boolean isValid()
    {
        return (System.currentTimeMillis() - start_time <= LIMIT);
    }

    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shortToken", shortToken);
        jsonObject.put("refreshToken", refreshToken);
        jsonObject.put("startTime", start_time);
        return jsonObject;
    }

    public static Token fromJson(JSONObject jsonObject)
    {
        Token ret = new Token((long) jsonObject.get("shortToken"), (long) jsonObject.get("refreshToken"));
        ret.start_time = (long)jsonObject.get("startTime");
        return ret;
    }

}