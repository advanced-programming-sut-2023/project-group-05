package org.example;

import org.example.controller.DataBase;
import org.example.controller.SecurityQuestions;
import org.example.model.Account;
import org.example.view.SignupLoginMenu;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        init();
        DataBase.wakeUp();
        SignupLoginMenu.run( scanner ) ;

    }

    public static void uploadDataFromDataBase()
    {
        DataBase.wakeUp();
    }
    public static void init()
    {
        MyHash.setMOD(1000000007);
        MyHash.setBASE(5021);
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
        SecurityQuestions.addCaptcha(0, cap0);

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
        SecurityQuestions.addCaptcha(1, cap1);

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
        SecurityQuestions.addCaptcha(2, cap2);

        ///DataBase.addNewAccount(test);

        /// testing();
    }
    public static void testing()
    {
        Account test = new Account("Ariya4", "AH", "prefix.suffix.aria@gmail.com", new MyHash("hi").getHsh(), 0, "im king", 0, 0);
        JSONObject cur = DataBase.getFromDataBase("nickName", "AH");
        if(cur != null)
        {
            System.out.println(cur);
        }
    }
}