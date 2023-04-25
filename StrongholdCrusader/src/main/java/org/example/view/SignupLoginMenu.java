package org.example.view;

import org.example.controller.SignupLoginMenuController;
import org.example.model.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;
public class SignupLoginMenu {

    public static void run ( Scanner scanner ) {
        Matcher matcher ;
        while (true){
            String input = Menu.getScanner().nextLine();
            if ((matcher = Commands.getMatchingMatcher(input,Commands.FIND_USER))!=null)
                System.out.println(SignupLoginMenuController.findUser(matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.CREATE_USER))!=null)
                System.out.println(SignupLoginMenuController.createUser( scanner , matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.ANSWER_QUESTION))!=null)
                System.out.println(SignupLoginMenuController.questionPick(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.USER_LOGIN))!=null){
                SignupLoginMenuController.loginUser(scanner , matcher) ;
            }
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.FORGET_PASSWORD))!=null)
                System.out.println(SignupLoginMenuController.forgetPassword(matcher));
            else if ( input.equals("exit") )
                return ;
            else
                System.out.println("Invalid Command");
        }

    }
}
