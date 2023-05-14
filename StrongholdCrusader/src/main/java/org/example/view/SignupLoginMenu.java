package org.example.view;

import org.example.controller.SignupLoginMenuController;
import org.example.model.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;
public class SignupLoginMenu {
    public static int wrongPassCounter = 0;
    public static void run ( Scanner scanner ) {
        Matcher matcher ;

        while (true){
            String input = Menu.getScanner().nextLine();
            if ((matcher =Commands.getMatchingMatcher(input,Commands.CREATE_USER))!=null) {
                System.out.println(SignupLoginMenuController.createUser(scanner, matcher, true));
                wrongPassCounter = 0;
            }
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.USER_LOGIN))!=null)
                System.out.print(SignupLoginMenuController.loginUser(scanner , matcher,true));
            else if ((matcher = Commands.getMatchingMatcher(input, Commands.STAY_LOGGED_IN)) != null)
                System.out.println(SignupLoginMenuController.loginUserStayLoggedIn(scanner, matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.FORGET_PASSWORD))!=null) {
                System.out.println(SignupLoginMenuController.forgetPassword(matcher));
                wrongPassCounter = 0;
            }
            else if ( input.equals("exit") )
                return ;
            else
                System.out.println("Invalid Command");
        }
    }
}