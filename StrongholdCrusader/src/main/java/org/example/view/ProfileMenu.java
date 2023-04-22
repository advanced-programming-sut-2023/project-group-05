package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.Commands;

import java.util.regex.Matcher;

public class ProfileMenu {
    public ProfileMenuController profileMenuController;
    public ProfileMenu(ProfileMenuController profileMenuController){
        this.profileMenuController = profileMenuController;
    }

    public void run(){
        Matcher matcher;
        String input;
        while (true){
            input = Menu.getScanner().nextLine();
            if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_USERNAME))!=null)
                System.out.println(profileMenuController.changeUsername(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_PASSWORD))!=null)
                System.out.println(profileMenuController.changePassword(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_NICKNAME))!=null)
                System.out.println(profileMenuController.changeNickname(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_EMAIL))!=null)
                System.out.println(profileMenuController.changeEmail(matcher));
            else if((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_SLOGAN))!=null)
                System.out.println(profileMenuController.changeSlogan(matcher));
            else
                System.out.println("Invalid Command");
        }
    }
}
