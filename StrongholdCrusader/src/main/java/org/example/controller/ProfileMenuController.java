package org.example.controller;

import org.example.model.Account;

import java.util.regex.Matcher;

public class ProfileMenuController {

    Account account ;

    public ProfileMenuController( Account account ){
        this.account = account ;
    }

    public void changeUsername(Matcher matcher){
        String username = matcher.group( "username" ) ;
        if( !SignupLoginMenuController.validUserName( username )){
            System.out.println( "your username is not valid" ) ;
            return ;
        }
        account.setUserName( username );
        System.out.println("your username successfully changed."); ;
    }

    public void changeNickname(Matcher matcher){
        String nickname = matcher.group("nickname") ;
        if( !SignupLoginMenuController.validNickname( nickname ) ){
            System.out.println( "nickname is not valid" ) ;
            return ;
        }
        account.setNickname( nickname ) ;
        System.out.println("your nickname successfully changed.");
    }

    public void changePassword(Matcher matcher) {
        String password = matcher.group( "password" ) ;
        if( !SignupLoginMenuController.validPassword(password) ){
            System.out.println( "your password is not valid" ) ;
            return ;
        }
        long passwordHash = Hash.encode( password ) ;
        account.setPassword( passwordHash ) ;
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
