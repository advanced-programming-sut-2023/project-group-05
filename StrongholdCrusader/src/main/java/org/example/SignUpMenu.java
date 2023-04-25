package org.example;
import java.util.HashMap;
import java.util.Scanner;

public class SignUpMenu
{
    private String userName, nickName, email, password, passwordConfirm;
    private int slogan, question, answer;

    public boolean validUserName(String S)
    {
        for(int i = 0; i < S.length(); i ++) {
            char c = S.charAt(i);
            if (!(('A' <= c && c <= 'Z') || ('0' <= c && c <= '9') || (c == '_')))
            {
                return false;
            }
        }
        return true;
    }
    public Integer haveFrom(String s, char l, char r)
    {
        Integer ret = 0;
        for(int i = 0; i < s.length(); i ++)
        {
            char c = s.charAt(i);
            if(l <= c && c <= r)
            {
                ret += 1;
            }
        }
        return ret;
    }
    public boolean validPassword(String S)
    {
        int n = S.length(), a = 0;
        if(n < 6) return false;
        if((a = haveFrom(S, 'A', 'Z')) == 0) return false;
        n -= a;
        if((a = haveFrom(S, 'a', 'z')) == 0) return false;
        n -= a;
        if((a = haveFrom(S, '0', '9')) == 0) return false;
        n -= a;
        if(n == 0) return false;
        return true;
    }
    public boolean validEmail(String S)
    {
        int ats = 0;
        for(int i = 1; i < S.length() - 1; i ++)
        {
            char c = S.charAt(i);
            if(c == '.' && ats != 0 && ats != i - 1) return true;
            if(c == '@') ats = i;
        }
        return false;
    }
    public void run(Scanner scanner, SecurityQuestions securityQuestions)
    {
        if(userName.length() == 0 || nickName.length() == 0 || email.length() == 0)
        {
            System.out.println("You left a filed empty!");
            return;
        }
        if(!validUserName(userName))
        {
            System.out.println("Invalid Username!");
            return;
        }
        if(!validPassword(email))
        {
            System.out.println("Invalid Email address!!");
            return;
        }
        if(DataBase.getFromDataBase("userName", userName) != null)
        {
            System.out.println("This username already exists :/");
            /// do random suggestion afterwards;
            return;
        }
        if(slogan == -1)
        {
            slogan = securityQuestions.askRandom();
            System.out.println("Your slogan is: " + securityQuestions.slogans.get(slogan));
        }
        if(password.equals("random")) /// here again you have to generate random strings
        {
            password = "abracadabra";
            System.out.println("Your random password is here! " + password + "Please re-enter your password ;)");
            /// input the re-entered password for confirmation
        }
        if(!validPassword(password))
        {
            System.out.println("Invalid Password, good luck");
            return;
        }
        if(!password.equals(passwordConfirm))
        {
            System.out.println("You entered your password wrong, It's not confirmed yet");
            return;
        }
        System.out.println("Beep Boop Bop!");
        System.out.println("What is the number??");
        Integer x = securityQuestions.askRandom() % 3;
        securityQuestions.showCaptcha(x);
        Integer ans = scanner.nextInt();
        if(!ans.equals(x))
        {
            System.out.println("I knew it! You are a damn robot :(");
            return;
        }
        runSignedUp(scanner, securityQuestions);
    }
    private void runSignedUp(Scanner scanner, SecurityQuestions securityQuestions)
    {
        System.out.println("Pick your security question:");
        for(int i = 1; i <= securityQuestions.questions.size(); i ++)
        {
            System.out.print(i);
            System.out.println(securityQuestions.questions.get(i - 1));
        }
        /// here you actually get the input :)
        Account account = new Account(userName, nickName, email, password, 0, slogan, question, answer);
    }
}
