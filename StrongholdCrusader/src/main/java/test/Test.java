package test;
import org.example.Main;
import org.example.controller.Hash;
import org.example.controller.SignupLoginMenuController;
import org.example.model.Account;
import org.example.model.Commands;
import org.example.view.Menu;
import org.junit.Assert;
import org.junit.BeforeClass;

import javax.print.DocFlavor;
import javax.swing.plaf.PanelUI;
import java.awt.geom.RectangularShape;
import java.util.regex.Matcher;


import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
    @BeforeClass
    public static void testSetup(){
        Hash.setMOD(1000000007);
        Hash.setBASE(5021);
        String password = "APkimoam03$";
        long hash = new Hash(password).getHsh();
        Account account = new Account("username","bakeri","moaminkiani1@gmail.com",hash,0,"let's conquer",1,1);
    }
    @org.junit.Test
    public void testCreateUser(){
        String leftSlogan = "user create -u sth -p APkimoam03$ APkimoam03$ -email moaminkiani1@gmail.com -n bakeri -s";
        Matcher matcher = Commands.getMatchingMatcher(leftSlogan,Commands.CREATE_USER);
        String result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("you left slogan field empty",result);

        String leftAField = "user create -u -p APkimoam03$ APkimoam03$ -email moaminkiani1@gmail.com -n bakeri -s myslogan";
        matcher = Commands.getMatchingMatcher(leftAField,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("You left a field empty!",result);

        String invalidUsername = "user create -u @sa,am -p APkimoam03$ APkimoam03$ -email moaminkiani1@gmail.com -n bakeri";
        matcher = Commands.getMatchingMatcher(invalidUsername,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("Invalid Username!",result);

        String invalidPassword = "user create -u sth -p kimoam03$ kimoam03$ -email moaminkiani1@gmail.com -n bakeri";
        matcher = Commands.getMatchingMatcher(invalidPassword,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("Invalid password",result);

        String invalidEmail =  "user create -u sth -p APkimoam03$ APkimoam03$ -email ali@cesharif -n bakeri";
        matcher = Commands.getMatchingMatcher(invalidEmail,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("Invalid Email address!!",result);


        String createExistingEmail = "user create -u username -p APkimoam03$ APkimoam03$ -email moaminkiani1@gmail.com -n bakeri -s \"let's conquer\"";
        matcher = Commands.getMatchingMatcher(createExistingEmail,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("Email already exists",result);

        String createExistingUsername = "user create -u username -p APkimoam03$ APkimoam03$ -email test@gmail.com -n bakeri -s slogan";
        matcher = Commands.getMatchingMatcher(createExistingUsername,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("create user failed : Username Already Exists",result);

        String passwordConfirmationIncorrect = "user create -u newuser -p APkimoam03$ APkimoam$ -email test@gmail.com -n bakeri -s \"let's conquer\"";
        matcher = Commands.getMatchingMatcher(passwordConfirmationIncorrect,Commands.CREATE_USER);
        result = SignupLoginMenuController.createUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("password confirmation does not match.",result);

    }

    @org.junit.Test
    public void testLogin(){
        String invalidUsername = "user login -u sa@am -p APkimoam03$";
        Matcher matcher = Commands.getMatchingMatcher(invalidUsername,Commands.USER_LOGIN);
        String result = SignupLoginMenuController.loginUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("login failed : Invalid username / password\n",result);

        String invalidPassword = "user login -u username -p kimo";
        matcher = Commands.getMatchingMatcher(invalidUsername,Commands.USER_LOGIN);
        result = SignupLoginMenuController.loginUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("login failed : Invalid username / password\n",result);

        String usernameNotExist = "user login -u fake -p APkimoam03$";
        matcher = Commands.getMatchingMatcher(usernameNotExist,Commands.USER_LOGIN);
        result = SignupLoginMenuController.loginUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("This username does not exist\n",result);

        String wrongPassword = "user login -u username -p APkimoam03$wrong";
        matcher = Commands.getMatchingMatcher(wrongPassword,Commands.USER_LOGIN);
        result = SignupLoginMenuController.loginUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("Wrong Password!\n",result);

        String success = "user login -u username -p APkimoam03$";
        matcher = Commands.getMatchingMatcher(success,Commands.USER_LOGIN);
        result = SignupLoginMenuController.loginUser(Menu.getScanner(),matcher,false);
        Assert.assertEquals("User Logged In! hooray !",result);

        String logout = "logout";
        matcher = Commands.getMatchingMatcher(logout,Commands.LOGOUT);
        result = SignupLoginMenuController.logout(matcher);
        Assert.assertEquals("logged out",result);
    }

}
