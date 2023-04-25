package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        SecurityQuestions securityQuestions = new SecurityQuestions();
        securityQuestions.addQuestion("How old are you?");
        securityQuestions.addSlogan("We are gonna win! yayy!!");
        ArrayList < String > cap0 = new ArrayList< String >();
        cap0.add("    ,----..    ");
        cap0.add("   /   /   \\  ");
        cap0.add("  /   .     :  ");
        cap0.add(" .   /   ;.  \\ ");
        cap0.add(".   ;   /  ` ; ");
        cap0.add(";   |  ; \\ ; | ");
        cap0.add("|   :  | ; | ' ");
        cap0.add(".   |  ' ' ' : ");
        cap0.add("'   ;  \\; /  | ");
        cap0.add(" \\   \\  ',  /  ");
        cap0.add("  ;   :    /   ");
        cap0.add("   \\   \\ .'    ");
        cap0.add("    `---`      ");
        securityQuestions.addCaptcha(0, cap0);

        ArrayList < String > cap1 = new ArrayList< String >();
        cap1.add("     ,---, ");
        cap1.add("  ,`--.' | ");
        cap1.add(" /    /  : ");
        cap1.add(":    |.' ' ");
        cap1.add("`----':  | ");
        cap1.add("   '   ' ; ");
        cap1.add("   |   | | ");
        cap1.add("   '   : ; ");
        cap1.add("   |   | ' ");
        cap1.add("   '   : | ");
        cap1.add("   ;   |.' ");
        cap1.add("   '---'   ");
        cap1.add("");
        cap1.add("");
        securityQuestions.addCaptcha(1, cap1);

        ArrayList < String > cap2 = new ArrayList< String >();
        cap2.add("      ,----,   ");
        cap2.add("    .'   .' \\  ");
        cap2.add("  ,----,'    | ");
        cap2.add("  |    :  .  ; ");
        cap2.add("  ;    |.'  /  ");
        cap2.add("  `----'/  ;   ");
        cap2.add("    /  ;  /    ");
        cap2.add("   ;  /  /-,   ");
        cap2.add("  /  /  /.`|   ");
        cap2.add("./__;      :   ");
        cap2.add("|   :    .'    ");
        cap2.add(";   | .'       ");
        cap2.add("`---'          ");
        securityQuestions.addCaptcha(2, cap2);

        MyHash.setMOD(1000000007);
        MyHash.setBASE(5021);

        Account test = new Account("Ariya", "AH", "prefix.suffix.aria@gmail.com", "hi", 0, 0, 0, 0);

        SignUpMenu signUpMenu = new SignUpMenu();
        signUpMenu.run(scanner, securityQuestions);

        ///DataBase.addNewAccount(test);
    }
}