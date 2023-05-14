package org.example.controller;

import java.util.*;

public class SecurityQuestions
{
    public static ArrayList < String > questions = new ArrayList< String >( Arrays.asList(
            "How old are you?" ,
            "How tall are you in centimeters?( floor the answer )" ,
            "How many kids do you have ?" ,
            "How much do you weigh ? ( floor the answer )" ,
            "How many times you committed suicide ?" ) );
    public static ArrayList < String > slogans = new ArrayList< String >(Arrays.asList(
            "we will win" ,
            "we are great" ,
            "i am hungry" ,
            "i love you ( when your dead )"
    ));
    private static final HashMap < Integer, ArrayList < String > > captcha = new HashMap < Integer, ArrayList < String > >();
    public static void initialize(){
        ArrayList < ArrayList<String> > cap = new ArrayList<  >();

        for(int i = 0 ; i < 10 ; i++) cap.add( new ArrayList<String>() ) ;
        int u = 0 ;
        cap.get(u).add("               ") ;
        cap.get(u).add("    ,----..    ");
        cap.get(u).add("   /   /   \\  ");
        cap.get(u).add("  /   .     :  ");
        cap.get(u).add(" .   /   ;.  \\ ");
        cap.get(u).add(".   ;   /  ` ; ");
        cap.get(u).add(";   |  ; \\ ; | ");
        cap.get(u).add("|   :  | ; | ' ");
        cap.get(u).add(".   |  ' ' ' : ");
        cap.get(u).add("'   ;  \\; /  | ");
        cap.get(u).add(" \\   \\  ',  /  ");
        cap.get(u).add("  ;   :    /   ");
        cap.get(u).add("   \\   \\ .'    ");
        cap.get(u).add("    `---`      ");
        cap.get(u).add("               ") ;
        cap.get(u).add("               ") ;

        captcha.put( 0 , cap.get(u) ) ;

        u = 1 ;
        cap.get(u).add("           ") ;
        cap.get(u).add("           ") ;
        cap.get(u).add("     ,---, ");
        cap.get(u).add("  ,`--.' | ");
        cap.get(u).add(" /    /  : ");
        cap.get(u).add(":    |.' ' ");
        cap.get(u).add("`----':  | ");
        cap.get(u).add("   '   ' ; ");
        cap.get(u).add("   |   | | ");
        cap.get(u).add("   '   : ; ");
        cap.get(u).add("   |   | ' ");
        cap.get(u).add("   '   : | ");
        cap.get(u).add("   ;   |.' ");
        cap.get(u).add("   '---'   ");
        cap.get(u).add("           ");
        cap.get(u).add("           ");

        captcha.put( 1 , cap.get(1) ) ;

        u = 2 ;
        cap.get(u).add("             ") ;
        cap.get(u).add("             ") ;
        cap.get(u).add("      ,----,   ");
        cap.get(u).add("    .'   .' \\  ");
        cap.get(u).add("  ,----,'    | ");
        cap.get(u).add("  |    :  .  ; ");
        cap.get(u).add("  ;    |.'  /  ");
        cap.get(u).add("  `----'/  ;   ");
        cap.get(u).add("    /  ;  /    ");
        cap.get(u).add("   ;  /  /-,   ");
        cap.get(u).add("  /  /  /.`|   ");
        cap.get(u).add("./__;      :   ");
        cap.get(u).add("|   :    .'    ");
        cap.get(u).add(";   | .'       ");
        cap.get(u).add("`---'          ");
        cap.get(u).add("             ") ;

        captcha.put( 2 , cap.get(u) ) ;

        u = 3 ;
        cap.get(u).add("             ") ;
        cap.get(u).add("  .--,-``-.    ");
        cap.get(u).add(" /   /     '.  ");
        cap.get(u).add("/ ../        ; ");
        cap.get(u).add("\\ ``\\  .`-    '");
        cap.get(u).add(" \\___\\/   \\   :");
        cap.get(u).add("      \\   :   |");
        cap.get(u).add("      /  /   / ");
        cap.get(u).add("      \\  \\   \\ ");
        cap.get(u).add("  ___ /   :   |");
        cap.get(u).add(" /   /\\   /   :");
        cap.get(u).add("/ ,,/  ',-    .");
        cap.get(u).add("\\ ''\\        ; ");
        cap.get(u).add(" \\   \\     .'  ");
        cap.get(u).add("  `--`-,,-'    ");
        cap.get(u).add("             ") ;

        captcha.put( 3 , cap.get(u) ) ;
        u = 4 ;
        cap.get(u).add("            ") ;
        cap.get(u).add("        ,--,") ;
        cap.get(u).add("      ,--.'|") ;
        cap.get(u).add("   ,--,  | :") ;
        cap.get(u).add(",---.'|  : '") ;
        cap.get(u).add(";   : |  | ;") ;
        cap.get(u).add("|   | : _' |") ;
        cap.get(u).add(":   : |.'  |") ;
        cap.get(u).add("|   ' '  ; :") ;
        cap.get(u).add("\\   \\  .'. |") ;
        cap.get(u).add(" `---`:  | '") ;
        cap.get(u).add("      '  ; |") ;
        cap.get(u).add("      |  : ;") ;
        cap.get(u).add("      '  ,/ ") ;
        cap.get(u).add("      '--'  ") ;
        cap.get(u).add("            ") ;

        captcha.put( 4 , cap.get(u) ) ;

        u = 5 ;
        cap.get(u).add("       ,----,. ") ;
        cap.get(u).add("     ,'   ,' | ") ;
        cap.get(u).add("   ,'   .'   | ") ;
        cap.get(u).add(" ,----.'    .' ") ;
        cap.get(u).add(" |    |   .'   ") ;
        cap.get(u).add(" :    :  |--,  ") ;
        cap.get(u).add(" :    |  ;.' \\ ") ;
        cap.get(u).add(" |    |      | ") ;
        cap.get(u).add(" `----'.'\\   ; ") ;
        cap.get(u).add("   __  \\  .  | ") ;
        cap.get(u).add(" /   /\\/  /  : ") ;
        cap.get(u).add("/ ,,/  ',-   . ") ;
        cap.get(u).add("\\ ''\\       ;  ") ;
        cap.get(u).add(" \\   \\    .'   ") ;
        cap.get(u).add("  `--`-,-'     ") ;
        cap.get(u).add("               ") ;

        captcha.put( 5 , cap.get(u) ) ;
        u = 6 ;
        cap.get(u).add("             ") ;
        cap.get(u).add("             ") ;
        cap.get(u).add("    ,---.    ") ;
        cap.get(u).add("   /     \\   ") ;
        cap.get(u).add("  /    / '   ") ;
        cap.get(u).add(" .    ' /    ") ;
        cap.get(u).add("'    / ;     ") ;
        cap.get(u).add("|   :  \\     ") ;
        cap.get(u).add(";   |   ``.  ") ;
        cap.get(u).add("'   ;      \\ ") ;
        cap.get(u).add("'   |  .\\  | ") ;
        cap.get(u).add("|   :  ';  : ") ;
        cap.get(u).add(" \\   \\    /  ") ;
        cap.get(u).add("  `---`--`   ") ;
        cap.get(u).add("             ") ;
        cap.get(u).add("             ") ;

        captcha.put( 6 , cap.get(u) ) ;
        u = 7 ;
        cap.get(u).add("                ") ;
        cap.get(u).add("         ,----, ") ;
        cap.get(u).add("       .'   .`| ") ;
        cap.get(u).add("    .'   .'   ; ") ;
        cap.get(u).add("  ,---, '    .' ") ;
        cap.get(u).add("  |   :     ./  ") ;
        cap.get(u).add("  ;   | .'  /   ") ;
        cap.get(u).add("  `---' /  ;    ") ;
        cap.get(u).add("    /  ;  /     ") ;
        cap.get(u).add("   ;  /  /      ") ;
        cap.get(u).add("  /  /  /       ") ;
        cap.get(u).add("./__;  /        ") ;
        cap.get(u).add("|   : /         ") ;
        cap.get(u).add(";   |/          ") ;
        cap.get(u).add("`---'           ") ;
        cap.get(u).add("                ") ;

        captcha.put( 7 , cap.get(u) ) ;
        u = 8 ;
        cap.get(u).add("   ,---.-,    ") ;
        cap.get(u).add("  '   ,'  '.  ") ;
        cap.get(u).add(" /   /      \\ ") ;
        cap.get(u).add(".   ;  ,/.  : ") ;
        cap.get(u).add("'   |  | :  ; ") ;
        cap.get(u).add("'   |  ./   : ") ;
        cap.get(u).add("|   :       , ") ;
        cap.get(u).add(" \\   \\     /  ") ;
        cap.get(u).add("  ;   ,   '\\  ") ;
        cap.get(u).add(" /   /      \\ ") ;
        cap.get(u).add(".   ;  ,/.  : ") ;
        cap.get(u).add("'   |  | :  ; ") ;
        cap.get(u).add("'   |  ./   : ") ;
        cap.get(u).add("|   :      /  ") ;
        cap.get(u).add(" \\   \\   .'   ") ;
        cap.get(u).add("  `---`-'     ") ;

        captcha.put( 8 , cap.get(u) ) ;
        u = 9 ;
        cap.get(u).add("              ") ;
        cap.get(u).add("   ,---.-,    ") ;
        cap.get(u).add("  '   ,'  '.  ") ;
        cap.get(u).add(" /   /      \\ ") ;
        cap.get(u).add(".   ;  ,/.  : ") ;
        cap.get(u).add("'   |  | :  ; ") ;
        cap.get(u).add("'   |  ./   : ") ;
        cap.get(u).add("|   :       , ") ;
        cap.get(u).add(" \\   \\      | ") ;
        cap.get(u).add("  `---`---  ; ") ;
        cap.get(u).add("     |   |  | ") ;
        cap.get(u).add("     '   :  ; ") ;
        cap.get(u).add("     |   |  ' ") ;
        cap.get(u).add("     ;   |.'  ") ;
        cap.get(u).add("     '---'    ") ;
        cap.get(u).add("              ") ;

        captcha.put( 9 , cap.get(u) ) ;


    }

    public static void addQuestion(String S)
    {
        questions.add(S);
    }

    public static void showCaptcha(Integer x)
    {
        ArrayList < String > cur = captcha.get(x);
        if(cur == null) return;
        for(String now : cur)
        {
            System.out.println(now);
        }
    }
    public static boolean runCaptcha(Scanner scanner)
    {
        System.out.println("Beep Boop Bop!");
        System.out.println("What is the number??");

        int height = 15 ;

        Random random = new Random() ;
        int n = Math.abs(random.nextInt()) % 5 + 4 ;
        int[] digit = new int[n] ;
        digit[0] = (Math.abs(random.nextInt())%9) + 1 ;
        for(int i = 1 ; i < n ; i++)
            digit[i] = (Math.abs(random.nextInt())%10) ;

        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < n ; j++){
                System.out.print( captcha.get(digit[j]).get(i) + " " ) ;
            }
            System.out.print("\n") ;
        }

        Integer x = 0 ;
        for(int i = 0 ; i < n ; i++)
            x += (int)Math.pow( 10 , n - i - 1 ) * digit[i] ;

        String input = scanner.nextLine() ;
        Integer ans ;
        try{
            ans = Integer.parseInt( input ) ;
        }
        catch( NumberFormatException e ){
            System.out.println( "You have not entered a number!" );
            return false ;
        }

        if(!ans.equals(x))
            return false;

        return true;
    }
    public static String getRandomSlogan()
    {
        Random random = new Random() ;
        return slogans.get( Math.abs(random.nextInt()) % slogans.size() );
    }
}