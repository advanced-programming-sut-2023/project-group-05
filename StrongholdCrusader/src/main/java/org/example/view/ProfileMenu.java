package org.example.view;

import com.sun.org.apache.xpath.internal.operations.Neg;
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
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_HIGHSCORE))!=null)
                System.out.println(profileMenuController.displayHighScore(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_RANK))!=null)
                System.out.println(profileMenuController.displayRank(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_SLOGAN))!=null)
                System.out.println(profileMenuController.displaySlogan(matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_WHOLE_PROFILE))!= null)
                System.out.println(profileMenuController.displayProfile(matcher));
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.REMOVE_SLOGAN))!=null)
                System.out.println(profileMenuController.removeSlogan(matcher));
            else
                System.out.println("Invalid Command");
        }
    }
}
