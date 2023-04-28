package org.example.controller;

import org.example.model.Account;
import org.example.view.Menu;
import org.example.view.ProfileMenu;

import java.util.regex.Matcher;

public class ProfileMenuController {

    Account account ;

    public ProfileMenuController( Account account ){
        this.account = account ;
    }

    public void changeUsername(Matcher matcher){
        System.out.println("your username successfully changed."); ;
    }

    public void changeNickname(Matcher matcher){
        System.out.println("your nickname successfully changed.");
    }

    public void changePassword(Matcher matcher) {
        System.out.println("your password successfully changed.") ;
    }

    public void changeEmail(Matcher matcher){
        String email = matcher.group( "email" ) ;
        if( !SignupLoginMenuController.validEmail(email) ){
            System.out.println("Email is not valid.");
            return ;
        }
        account.setEmail( email ) ;
        System.out.println("your email successfully changed.") ;
    }

    public void changeSlogan(Matcher matcher){
        account.setSlogan( matcher.group("slogan") ) ;
        System.out.println("your slogan successfully changed.") ;
    }

    public void removeSlogan(Matcher matcher){
        System.out.println("your slogan was successfully removed.") ;
        account.setSlogan( SecurityQuestions.getRandomSlogan() ) ;
    }

    public void displayHighScore(Matcher matcher){
        System.out.println("your high score is " + account.getHighScore()) ;
    }

    public void displayRank(Matcher matcher){
        int rank = 1 ;
        for( Account acc : Account.getAccountsMap().values() ){
            if( acc.getHighScore() > account.getHighScore() ) rank++ ;
        }
        System.out.println( "your rank based on high score is : " ) ;
    }

    public void displaySlogan(Matcher matcher){
        System.out.println( "your slogan is : " + account.getSlogan() ) ;
    }

    public void displayProfile(Matcher matcher){
        System.out.println( "username : " + account.getUserName() ) ;
        System.out.println( "nickname : " + account.getNickName() ) ;
        System.out.println( "slogan : " + account.getSlogan() ) ;
        System.out.println( "highest score : " + account.getHighScore() ) ;
    }
}
