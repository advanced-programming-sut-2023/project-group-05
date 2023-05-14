package org.example.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public enum Commands {
    FIND_USER("find user (?<nickname>(\\S+)|(\".+\"))",null),
    CREATE_USER("user create",new ArrayList<String>(Arrays.asList(" -u( (?<username>\\S+))?"," -p( (((?<password>\\S+) (?<passwordConfirmation>\\S+))|random))?"," -email( (?<email>\\S+))?"," -n( (?<nickname>(\".+\")|(\\S+)))?","((?<sloganchecker> -s)( (?<slogan>(\\S+)|(\".+\")|random))?)?"))),
    ANSWER_QUESTION("question pick", new ArrayList<String>(Arrays.asList(" -q (?<questionNumber>\\d+)"," -a (?<answer>\\S+)"," -c (?<answerConfirm>\\S+)"))),
    USER_LOGIN("user login",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"," -p (?<password>\\S+)","(?<stayloggedin> --stay-logged-in)?"))),
    STAY_LOGGED_IN("LOGIN", null),
    FORGET_PASSWORD("forgot my password",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"))),
    START_GAME( "start game with" , new ArrayList<String>( Arrays.asList( " -u(?<usernames>( \\S+)+)" ) ) ) ,
    LOGOUT ("user logout",null),
    CHANGE_USERNAME ("profile change",new ArrayList<String>(Arrays.asList(" -u (?<username>\\S+)"))),
    CHANGE_NICKNAME ("profile change",new ArrayList<String>(Arrays.asList(" -n (?<nickname>(\\S+)|(\".+\"))"))),
    CHANGE_PASSWORD("profile change password",new ArrayList<String>(Arrays.asList(" -o (?<oldPassword>\\S+)"," -n (?<newPassword>\\S+)"))),
    CHANGE_EMAIL ("profile change",new ArrayList<String>(Arrays.asList("-e (?<email>\\S+)"))),
    CHANGE_SLOGAN("profile change slogan",new ArrayList<String>(Arrays.asList(" -s (?<slogan>(\\S+)|(\".+\"))"))),
    REMOVE_SLOGAN("profile remove solgan",null),
    DISPLAY_HIGHSCORE("profile display highscore",null),
    DISPLAY_RANK("profile display rank",null),
    DISPLAY_SLOGAN ("profile display slogan",null),
    DISPLAY_WHOLE_PROFILE("profile display",null),

    //MAP MENU :

    SHOW_MAP("show map",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"))),
    SHOW_DETAILS("show details",new ArrayList<String>(Arrays.asList(" -x(?<row>\\d+)"," -y(?<column>\\d+)"))),
    NAVIGATE_MAP("map",new ArrayList<String>(Arrays.asList("(?<upNavigation> up( (?<upNumber>\\d+))?)?","(?<downNavigation> down( (?<downNumber>\\d+))?)?","(?<rightNavigation> right( (?<rightNumber>\\d+))?)?","(?<leftNavigation> left( (?<leftNumber>\\d+))?)?"))),

    //GAME MENU

    SHOW_POPULARITY_FACTORS("show popularity factors",null),
    SHOW_POPULARITY("show popularity",null),
    SHOW_FOOD_TYPES("show food list",null),
    SET_FOOD_RATE("food rate",new ArrayList<String>(Arrays.asList(" -r (?<rate>(-)?\\d+)"))),
    SHOW_FOOD_RATE("food rate show",null),
    SET_TAX_RATE("tax rate",new ArrayList<String>(Arrays.asList(" -r (?<rate>(-)?\\d+)"))),
    SHOW_TAX_RATE("tax rate show",null),
    SET_FEAR_RATE("fear rate",new ArrayList<String>(Arrays.asList(" -r (?<rate>(-)?\\d+)"))),
    DROP_BUILDING("dropbuilding",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"," -t (?<type>\\S+)"))),
    SELECT_BUILDING("select building",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"))),
    CREATE_UNIT("createunit",new ArrayList<String>(Arrays.asList(" -t(?<type>\\S+)"," -c(?<count>\\d+)"))),
    REPAIR("repair",null),
    SELECT_UNIT("select unit",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"))),
    MOVE_UNIT("move unit to",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"))),
    PATROL("patrol unit",new ArrayList<String>(Arrays.asList(" -x1 (?<beginColumn>\\d+)"," -y1 (?<beginRow>\\d+)"," -x2 (?<endColumn>\\d+)"," -y2 (?<endRow>\\d+)"))),
    SET_UNIT_STATE("set",new ArrayList<String>(Arrays.asList(" -x (?<row>\\d+)"," -y (?<column>\\d+)"," -s (?<state>(standing)|(defensive)|(aggressive))"))),
    ATTACK ("attack",new ArrayList<String>(Arrays.asList(" -e (?<row>\\d+) (?<column>\\d+)"))),
    AIR_ATTACK ("attack",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"))),
    POUR_OIL("pour oil",new ArrayList<String>(Arrays.asList(" -d (?<direction>(up)|(left)|(right)|(down))"))),
    DIG_TUNNEL("dig tunnel",new ArrayList<String>(Arrays.asList(" -x(?<column>\\d+)"," -y(?<row>\\d+)"))),
    BUILD_EQUIPMENT("build",new ArrayList<String>(Arrays.asList(" -q (?<equipment>\\S+)"))),
    DISBAND("disband unit",null),
    SET_TEXTURE_OF_CELL("settexture",new ArrayList<String>(Arrays.asList(" -y (?<row>\\d+)"," -x (?<column>\\d+)"," -t (?<type>\\S+)"))),
    SET_TEXTURE_OF_BLOCK("settexture",new ArrayList<String>(Arrays.asList(" -y1 (?<beginRow>\\d+)"," -x1 (?<beginColumn>\\d+)"," -y2 (?<endRow>\\d+)"," -x2 (?<endColumn>\\d+)"," -t (?<type>\\S+)"))),
    CLEAR_CELL ("clear",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y(?<row>\\d+)"))),
    DROP_ROCK("droprock",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"," -d (?<direction>(n|e|w|s|r))"))),
    DROPTREE("droptree",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"," -t (?<type>\\S+)"))),
    CREATE_BUILDING("create building",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"," -t (?<type>\\S+)"))),
    DROP_UNIT("dropunit",new ArrayList<String>(Arrays.asList(" -x (?<column>\\d+)"," -y (?<row>\\d+)"," -t (?<type>\\S+)"," -c (?<count>\\d+)"))),
    SHOW_TRADE_LIST("trade list",null),
    SHOW_TRADE_HISTORY("trade history",null),
    TRADE_REQUEST("trade",new ArrayList<String>(Arrays.asList(" -t (?<resourceType>\\S+) -a (?<resourceAmount>\\d+) -p (?<price>\\d+) -m (?<message>\\S+)"))),
    TRADE_ACCEPT("trade accept",new ArrayList<String>(Arrays.asList(" -i (?<id>\\d+) -m (?<message>\\S+)"))),
    SHOW_PRICE_LIST("show price list",null),
    //TODO : fix these regexes
    BUY("buy",new ArrayList<String>(Arrays.asList(" -i (?<itemName>\\S+)" , " -a (?<amount>\\d+)"))),
    SHOW_GATE_STATE("show gate state" , new ArrayList <>(Arrays.asList(" -x (?<column>\\d+)" , " -y (?<row>\\d+)")) ) ,
    CHANGE_GATE_STATE("change gate state" , new ArrayList <>(Arrays.asList(" -x (?<column>\\d+)" , " -y (?<row>\\d+)")) ) ,
    SELL("sell",new ArrayList<String>(Arrays.asList(" -i (?<itemName>\\S+) -a (?<amount>\\d+)")));
    private String commandName;
    private ArrayList <String> options;

    private Commands(String commandName,ArrayList<String>options) {
        this.commandName = commandName;
        this.options = options;
    }

    private static ArrayList< String > allPermutations = new ArrayList <String>() ;
    private static ArrayList< Integer > used = new ArrayList <Integer>() ;
    private static String currentPermutation ;

    public static Matcher getMatchingMatcher (String input,Commands command){
        if( command.options == null ){
            Matcher matcher = Pattern.compile( "^(" + command.commandName + ")$" ).matcher(input) ;
            if( matcher.find() ){
                return matcher ;
            }
            else return null ;
        }
        allPermutations.clear() ;
        used.clear() ;
        for(int i = 0 ; i < command.options.size() ; i++) used.add(0) ;
        currentPermutation = "" ;
        generatePermutations( command , 0 ) ;
        String currectPerm ;
        for( String perm : allPermutations ){
            currectPerm = "^(" + perm + ")$" ;
            Matcher matcher = Pattern.compile(currectPerm).matcher(input) ;
            if( matcher.find() ){
                return matcher ;
            }
        }
        return null ;
    }

    private static void generatePermutations( Commands command , int k ){

        if( k == command.options.size() ){
            allPermutations.add( command.commandName + currentPermutation ) ;
            return ;
        }
        for( int i = 0 ; i < command.options.size() ; i++ ){
            if( used.get(i) == 1 ) continue ;
            used.set( i , 1 ) ;
            String currentPermutationTemp = currentPermutation ;
            currentPermutation = command.options.get( i ) + currentPermutation ;
            generatePermutations( command , k + 1 ) ;
            currentPermutation = currentPermutationTemp ;
            used.set( i , 0 ) ;
        }

    }

}
