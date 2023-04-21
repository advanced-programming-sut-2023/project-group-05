package org.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public enum Commands {
    FIND_USER("find user (\"(?<nickname>.*)\")|(?<nickname>\\S+)",null),
    CREATE_USER("user create",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"," -p ((?<password>\\S+) (?<passwordConfirmation>\\S+))|random","( -email (?<email>\\S+))?"," -n (?<nickname>\\S+)","( -s (?<slogan>\\S+)|random)?"))),
    ANSWER_QUESTION("question pick", new ArrayList<String>(Arrays.asList(" -q (?<questionNumber>\\d+)"," -a (?<answer>\\S+)"," -c (?<answerConfirm>\\S+)"))),
    USER_LOGIN("user login",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"," -p (?<password>\\S+)","( --stay-logged-in)?"))),
    FORGET_PASSWORD("forgot my password",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"))),
    LOGOUT ("user logout",null),
    CHANGE_USERNAME ("profile change",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"))),
    CHANGE_NICKNAME ("profile change",new ArrayList<String>(Arrays.asList(" -n (?<nickname>\\S+)"))),
    CHANGE_PASSWORD("profile change password",new ArrayList<String>(Arrays.asList(" -o (?<oldPassword>\\S+)"," -n (?<newPassword>\\S+)"))),
    CHANGE_EMAIL ("profile change",new ArrayList<String>(Arrays.asList("-e (?<email>\\S+)"))),
    CHANGE_SLOGAN("profile change slogan",new ArrayList<String>(Arrays.asList(" -s (?<slogan>\\S+)"))),
    REMOVE_SLOGAN("[Pp]rofile remove solgan",null),
    DISPLAY_HIGHSCORE("profile display highscore",null),
    DISPLAY_RANK("profile display rank",null),
    DISPLAY_SLOGAN ("profile display slogan",null),
    DISPLAY_WHOLE_PROFILE("profile display",null),
    SHOW_MAP("show map",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"))),
    SHOW_DETAILS("show details",new ArrayList<String>(Arrays.asList(" -x(?<row>\\d+)"," -y(?<column>\\d+)"))),
    NAVIGATE_MAP("map",new ArrayList<String>(Arrays.asList("( up (?<upNavigation>\\d+))?","( down (?<downNavigation>\\d+))?","( right (?<rightNavigation>\\d+))?","( left (?<leftNavigation>\\d+))?"))),
    SHOW_POPULARITY_FACTORS("show popularity factors",null),
    SHOW_POPULARITY("show popularity",null),
    SHOW_FOOD_TYPES("show food list",null),
    SET_FOOD_RATE("food rate",new ArrayList<String>(Arrays.asList(" -r (?<rate>(-)?\\d+)"))),
    SHOW_FOOD_RATE("food rate show",null),
    SET_TAX_RATE("tax rate",new ArrayList<String>(Arrays.asList(" -r (?<rate>(-)?\\d+)"))),
    SHOW_TAX_RATE("tax rate show",null),
    SET_FEAR_RATE("fear rate",new ArrayList<String>(Arrays.asList(" -r (?<rate>(-)?\\d+)"))),
    DROP_BUILDING("dropbuilding",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"," -type (?<type>\\S+)"))),
    SELECT_BUILDING("select building",new ArrayList<String>(Arrays.asList(" -x(?<row>\\d+)"," -y(?<column>\\d+)"))),
    CREATE_UNIT("createunit",new ArrayList<String>(Arrays.asList(" -t(?<type>\\S+)"," -c(?<count>\\d+)"))),
    REPAIR("repair",null),
    SELECT_UNIT("select unit",new ArrayList<String>(Arrays.asList(" -x(?<row>\\d+)"," -y(?<column>\\d+)"))),
    MOVE_UNIT("move unit to",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y(?<column>\\d+)"))),
    PATROL("patrol unit",new ArrayList<String>(Arrays.asList(" -x1 (?<beginRow>\\d+)"," -y1 (?<beginColumn>\\d+)"," -x2 (?<endRow>\\d+)"," -y2 (?<endColumn>\\d+)"))),
    SET_UNIT_STATE("set",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"," -s (?<state>(standing)|(defensive)|(offensive))"))),
    ATTACK ("attack",new ArrayList<String>(Arrays.asList(" -e (?<row>\\d+) (?<column>\\d+)"))),
    AIR_ATTACK ("attack",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"))),
    POUR_OIL("pour oil",new ArrayList<String>(Arrays.asList(" -d (?<direction>(up)|(left)|(right)|(down))"))),
    DIG_TUNNEL("dig tunnel",new ArrayList<String>(Arrays.asList(" -x(?<row>\\d+)"," -y(?<column>\\d+)"))),
    BUILD_EQUIPMENT("build",new ArrayList<String>(Arrays.asList(" -q (?<equipment>\\S+)"))),
    DISBAND("disband unit",null),
    SET_TEXTURE_OF_CELL("settexture",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"," -t (?<type>\\S+)"))),
    SET_TEXTURE_OF_BLOCK("settexture",new ArrayList<String>(Arrays.asList(" -x1 (?<beginRow>\\d+)"," -y1 (?<beginColumn\\d+)"," -x2 (?<endRow>\\d+)"," -y2 (?<endColumn>\\d+)"," -t (?<type>\\S+)"))),
    CLEAR_CELL ("clear",new ArrayList<String>(Arrays.asList(" -x(?<row>\\d+)"," -y(?<column>\\d+)"))),
    DROP_ROCK("droprock",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"," -d (?<direction>(n|e|w|s|r))"))),
    DROPTREE("droptree",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"," -t (?<type>\\S+)"))),
    REPLACE_BUILDING("dropbuilding",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+) -y (?<column>\\d+) -t (?<type>\\S+)"))),
    DROP_UNIT("dropunit",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+) -y (?<column>\\d+) -t (?<type>\\S+) -c (?<count>\\d+)"))),
    SHOW_TRADE_LIST("trade list",null),
    SHOW_TRADE_HISTORY("trade history",null),
    TRADE_REQUEST("trade",new ArrayList<String>(Arrays.asList(" -t (?<resourceType>\\S+) -a (?<resourceAmount>\\d+) -p (?<price>\\d+) -m (?<message>\\S+)"))),
    TRADE_ACCEPT("trade accept",new ArrayList<String>(Arrays.asList(" -i (?<id>\\d+) -m (?<message>\\S+)"))),
    SHOW_PRICE_LIST("show price list",null),
    BUY("buy",new ArrayList<String>(Arrays.asList(" -i (?<itemName>\\S+) -a (?<amount>\\d+)"))),
    SELL("sell",new ArrayList<String>(Arrays.asList(" -i (?<itemName>\\S+) -a (?<amount>\\d+)")));
    private String commandName;
    private ArrayList <String> options;

    private Commands(String commandName,ArrayList<String>options) {
        this.commandName = commandName;
        this.options = options;
    }

    public static Matcher getMatchingMatcher (String input,Commands command,int i,int permutationSize){
        Matcher matcher;
        String regex = command.commandName;
        if (command.options.size()==0)
            return (matcher = Pattern.compile(regex).matcher(input)).matches() ? matcher : null;
        ArrayList<ArrayList<String>> totalSet = new ArrayList<>(permutationSize);
        ArrayList<String> buffer = new ArrayList<>(command.options.size());
        for (String string : command.options)
            buffer.add(string);
        permute(buffer,0,totalSet);
        for (ArrayList<String> array : totalSet){
            regex = command.commandName;
            for (String option : array)
                regex+=option;
            matcher = Pattern.compile(regex).matcher(input);
            if (matcher.matches())
                return matcher;
        }
        return null;
    }

    private static void permute(ArrayList<String> array, int k,ArrayList<ArrayList<String>> totalSet){
        for(int i = k; i < array.size(); i++){
            java.util.Collections.swap(array, i, k);
            permute(array, k+1,totalSet);
            java.util.Collections.swap(array, k, i);
        }
        if (k == array.size() -1){
            totalSet.add(array);
        }
    }
}

