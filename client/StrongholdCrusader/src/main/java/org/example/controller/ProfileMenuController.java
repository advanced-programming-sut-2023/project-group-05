package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuController {
/*
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
        Account.removeAccount(account);
        account.setUserName( username );
        Account.addAccount(account);
        System.out.println("your username successfully changed."); ;
    }

    public void changeNickname(Matcher matcher){
        String nickname = matcher.group("nickname") ;
        if( !SignupLoginMenuController.validNickname( nickname ) ){
            System.out.println( "nickname is not valid" ) ;
            return ;
        }
        Account.removeAccount(account);
        account.setNickname( nickname ) ;
        Account.addAccount(account);
        System.out.println("your nickname successfully changed.");
    }

    public void changePassword(Matcher matcher) {
        String password = matcher.group( "password" ) ;
        if( !SignupLoginMenuController.validPassword(password) ){
            System.out.println( "your password is not valid" ) ;
            return ;
        }
        long passwordHash = Hash.encode( password ) ;
        Account.removeAccount(account);
        account.setPassword( passwordHash ) ;
        Account.addAccount(account);
        System.out.println("your password successfully changed.") ;
    }

    public void changeEmail(Matcher matcher){
        String email = matcher.group( "email" ) ;
        if( !SignupLoginMenuController.validEmail(email) ){
            System.out.println("Email is not valid.");
            return ;
        }
        Account.removeAccount(account);
        account.setEmail( email ) ;
        Account.addAccount(account);
        System.out.println("your email successfully changed.") ;
    }

    public void changeSlogan(Matcher matcher)
    {
        Account.removeAccount(account);
        account.setSlogan( matcher.group("slogan") ) ;
        Account.addAccount(account);
        System.out.println("your slogan successfully changed.") ;
    }

    public void removeSlogan(Matcher matcher)
    {
        Account.removeAccount(account);
        System.out.println("your slogan was successfully removed.") ;
        Account.addAccount(account);
        account.setSlogan( SecurityQuestions.getRandomSlogan() ) ;
    }

    public void displayHighScore(Matcher matcher)
    {
        System.out.println("your high score is " + account.getHighScore()) ;
    }

    public void displayRank(Matcher matcher)
    {
        int rank = 1 ;
        for( Account acc : Account.getAccountsMap().values() )
        {
            if( acc.getHighScore() > account.getHighScore() ) rank++ ;
        }
        System.out.println( "your rank based on high score is : " ) ;
    }

    public void displaySlogan(Matcher matcher){
        System.out.println( "your slogan is : " + account.getSlogan() ) ;
    }

    public void displayProfile(Matcher matcher)
    {
        System.out.println( "username : " + account.getUserName() ) ;
        System.out.println( "nickname : " + account.getNickName() ) ;
        System.out.println( "slogan : " + account.getSlogan() ) ;
        System.out.println( "highest score : " + account.getHighScore() ) ;
    }*/

    public void scoreBoardMenu(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        VBox vBox = new VBox();
        ScrollBar scrollBar = new ScrollBar();
        pane.getChildren().addAll(vBox,scrollBar);
        ArrayList<String> scores = SignupLoginMenuController.getAccountList();
        HashMap<Integer,ArrayList<String>> rankToAccount = new HashMap<>();
        int size = scores.size();
        Text[] ranking = new Text[size / 2];
        String[] users = new String[size / 2];
        int[] ranks = new int[size];
        ArrayList<String> buffer;
        for (int i = 0; i < size; ++i) {
            if (i % 2 == 1)
            {
                buffer = rankToAccount.get(ranks[i]);
                buffer.add(users[i-1]);
                rankToAccount.replace(ranks[i],rankToAccount.get(ranks[i]),buffer);
            }
        }
        int cnt = 0;
        for (int rank : rankToAccount.keySet()){
            for (String account : rankToAccount.get(rank))
                ranking[cnt] = new Text(5,5+cnt++*50,rank+". Username : "+account);
        }
        scrollBar.setLayoutX(scene.getWidth()-scrollBar.getWidth());
        scrollBar.setMin(0);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(180);
        scrollBar.setMax(360);
        for (Text text : ranking)
            vBox.getChildren().add(text);
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                vBox.setLayoutY(-new_val.doubleValue());
            }
        });
        stage.setScene(scene);
        stage.show();
    }
}

