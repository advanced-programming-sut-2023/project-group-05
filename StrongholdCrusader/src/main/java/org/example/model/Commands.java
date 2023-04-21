package org.example.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public enum Commands {
    FIND_USER("find user (\"(?<nickname>.*)\")|(?<nickname>\\S+)"),
    //handle options
    CREATE_USER("user create(?<options>( -u \\S+)?( -p \\S+ \\S+)?( -email \\S+)?( -n \\S+)?( -s slogan)?)"),
    //handle options
    ANSWER_QUESTION("question pick(?<options>( -q \\d+)?( -a \\S+)?( -c \\S+)?)"),
    IS_EMAIL_TRUE ("[\\w\\.]*@[\\w]*[\\.+][\\w\\.]*"),
    //handle options
    USER_LOGIN("user login(?<options>( -u \\S+)?( -p \\S+)?( --stay-logged-in)?)"),
    FORGET_PASSWORD("forgot my password -u (?<username>\\S+)"),
    LOGOUT ("user logout"),
    CHANGE_USERNAME ("profile change -u (?<username>)"),
    CHANGE_NICKNAME ("profile change -n (?<nickname>)"),
    CHANGE_PASSWORD("profile change password( -o (?<oldPassword>\\S+) -n (?<newPassowrd>\\S+))|(-n (?<newPassowrd>\\S+) -o (?<oldPassword>\\S+)))"),
    CHANGE_EMAIL ("profile change -e (?<email>\\S+)"),
    CHANGE_SLOGAN("profile change slogan -s (?<slogan>\\S+)"),
    REMOVE_SLOGAN("[Pp]rofile remove solgan"),
    DISPLAY_HIGHSCORE("profile display highscore"),
    DISPLAY_RANK("profile display rank"),
    DISPLAY_SLOGAN ("profile display slogan"),
    DISPLAY_WHOLE_PROFILE("profile display"),
    SHOW_MAP("show map( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+))"),
    SHOW_DETAILS("show details( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+))"),
    //handle options
    NAVIGATE_MAP("map(?<options>( up \\d+)?( down \\d+)?( right \\d+)?( left \\d+)?)"),
    SHOW_POPULARITY_FACTORS("show popularity factors"),
    SHOW_POPULARITY("show popularity"),
    SHOW_FOOD_TYPES("show food list"),
    SET_FOOD_RATE("food rate -r (?<rate>(-)?\\d)"),
    SHOW_FOOD_RATE("food rate show"),
    SET_TAX_RATE("tax rate -r (?<rate>(-)?\\d)"),
    SHOW_TAX_RATE("tax rate show"),
    SET_FEAR_RATE("fear rate -r (?<rate>(-)?\\d)"),
    //handle options
    DROP_BUILDING("dropbuilding(?<options>( -x \\d+)?( -y \\d+)?( -type \\S+)?)"),
    SELECT_BUILDING("select building( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+))"),
    CREATE_UNIT("createunit ( -t(?<type>\\S+) -c(?<count>\\d+))|( -c(?<count>\\d+) -t(?<type>\\S+))"),
    REPAIR("repair"),
    SELECT_UNIT("select unit( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+))"),
    MOVE_UNIT("move unit to( -x(?<row>\\\\d+) -y(?<column>\\\\d+))|( -y(?<column>\\\\d+) -x(?<row>\\\\d+))\")"),
    //handle options
    PATROL("patrol unit(?<options>( -x1 \\d+)?( -y1 \\d+)?( -x2 \\d+)?( -y2 \\d+)?)"),
    //handle options
    SET_UNIT_STATE("set(?<options>( -x \\d+)?( -y \\d+)?( -s [(standing)(defensive)(offensive)])?)"),
    ATTACK ("attack -e (?<row>\\d+) (?<column>\\d+)"),
    AIR_ATTACK ("attack( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+)"),
    POUR_OIL("pour oil -d (?<direction>(up)|(left)|(right)|(down))"),
    DIG_TUNNEL("dig tunnel( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+)"),
    BUILD_EQUIPMENT("build -q (?<equipment>\\S+)"),
    DISBAND("disband unit"),
    //handle options
    SET_TEXTURE_OF_CELL("settexture(?<options>( -x \\d+)?( -y \\d+)?( -t \\S+)?)"),
    //handle options
    SET_TEXTURE_OF_BLOCK("settexture(?<options>( -x1 \\d+)?( -y1 \\d+)?( -x2 \\d+)?( -y2 \\d+)?( -t \\S+)?)"),
    CLEAR_CELL ("clear( -x(?<row>\\d+) -y(?<column>\\d+))|( -y(?<column>\\d+) -x(?<row>\\d+)"),
    //handle options
    DROP_ROCK("droprock(?<options>( -x \\d+)?( -y \\d+)?( -d (n|e|w|s|r))?)"),
    //handle options
    DROPTREE("droptree(?<options>( -x \\d+)?( -y \\d+)?( -t \\S+)?)");
    private String regex;

    private Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands command) {
        return Pattern.compile(command.regex).matcher(input);
    }
    }
