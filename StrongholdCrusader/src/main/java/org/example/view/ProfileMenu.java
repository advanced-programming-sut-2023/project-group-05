package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.Account;
import org.example.model.Commands;

import java.util.regex.Matcher;

public class ProfileMenu {

    public static void run( Account account ){
        System.out.println( "YOU ARE NOW IN PROFILE MENU ( enter exit to get back )" ) ;
        ProfileMenuController profileMenuController = new ProfileMenuController( account ) ;
        Matcher matcher;
        String input;
        while (true){
            input = Menu.getScanner().nextLine();
            if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_USERNAME))!=null)
                profileMenuController.changeUsername(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_PASSWORD))!=null)
                profileMenuController.changePassword(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_NICKNAME))!=null)
                profileMenuController.changeNickname(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_EMAIL))!=null)
                profileMenuController.changeEmail(matcher);
            else if((matcher = Commands.getMatchingMatcher(input,Commands.CHANGE_SLOGAN))!=null)
                profileMenuController.changeSlogan(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_HIGHSCORE))!=null)
                profileMenuController.displayHighScore(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_RANK))!=null)
                profileMenuController.displayRank(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_SLOGAN))!=null)
                profileMenuController.displaySlogan(matcher);
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.DISPLAY_WHOLE_PROFILE))!= null)
                profileMenuController.displayProfile(matcher);
            else if ((matcher =Commands.getMatchingMatcher(input,Commands.REMOVE_SLOGAN))!=null)
                profileMenuController.removeSlogan(matcher);
            else if ( input.equals("exit") )
                return ;
            else
                System.out.println("Invalid Command");
        }
    }
}
