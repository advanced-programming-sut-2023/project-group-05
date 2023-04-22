package org.example.view;

import org.example.controller.SignupLoginMenuController;
import org.example.model.Commands;

import java.util.regex.Matcher;
public class SignupLoginMenu {
    SignupLoginMenuController signupLoginMenuController;
    SignupLoginMenu(SignupLoginMenuController signupMenuController){
        this.signupLoginMenuController = signupMenuController;
    }
    public void run () {
        Matcher matcher ;
        while (true){
            String input = Menu.getScanner().nextLine();
            if ((matcher = Commands.getMatchingMatcher(input,Commands.FIND_USER))!=null)
                System.out.println(signupLoginMenuController.findUser(matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.CREATE_USER))!=null)
                System.out.println(signupLoginMenuController.createUser(matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.ANSWER_QUESTION))!=null)
                System.out.println(signupLoginMenuController.questionPick(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.USER_LOGIN))!=null)
                System.out.println(signupLoginMenuController.loginUser(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.FORGET_PASSWORD))!=null)
                System.out.println(signupLoginMenuController.forgetPassword(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.LOGOUT))!=null)
                System.out.println(signupLoginMenuController.logout(matcher));
            else
                System.out.println("Invalid Command");
        }

    }
}
