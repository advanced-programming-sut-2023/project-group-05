package org.example;
import org.example.controller.SecurityQuestions;
import org.example.model.Account;

import java.util.Scanner;

public class SignUpLoginMenu
{
    private String userName, nickName, email, password, passwordConfirm;
    private int slogan, question, answer;

    static public boolean validUserName(String S)
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
    static public Integer haveFrom(String s, char l, char r)
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
    static public boolean validPassword(String S)
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
    static public boolean validEmail(String S)
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


        /*


        */
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
        //Account account = new Account(userName, nickName, email, password, 0, "slogan", question, answer);
        System.out.println("User SignedUp hooray!!");
    }
}
