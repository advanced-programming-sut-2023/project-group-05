package org.example.view;

import org.example.controller.SignupMenuController;
import org.example.model.Commands;

import java.util.regex.Matcher;
public class SignupMenu {
    SignupMenuController signupMenuController;
    SignupMenu(SignupMenuController signupMenuController){
        this.signupMenuController = signupMenuController;
    }
    public void run () {
        Matcher matcher ;
        while (true){
            String input = Menu.getScanner().nextLine();
            if ((matcher = Commands.getMatchingMatcher(input,Commands.FIND_USER)).matches())
                System.out.println(signupMenuController);
        }

    }
}
