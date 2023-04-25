package org.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SecurityQuestions
{
    public ArrayList < String > questions = new ArrayList< String >();
    public ArrayList < String > slogans = new ArrayList< String >();
    private static HashMap < Integer, ArrayList < String > > captcha = new HashMap < Integer, ArrayList < String > >();
    public void addQuestion(String S)
    {
        questions.add(S);
    }
    public void addSlogan(String S)
    {
        slogans.add(S);
    }
    public void addCaptcha(Integer x, ArrayList < String > y)
    {
        captcha.put(x, y);
    }
    public static void showCaptcha(Integer x)
    {
        ArrayList < String > cur = captcha.get(x);
        if(cur == null) return;
        for(String now : cur)
        {
            System.out.println(now);
        }
    }
    public static boolean runCaptcha(Scanner scanner)
    {
        System.out.println("Beep Boop Bop!");
        System.out.println("What is the number??");
        Random random = new Random() ;
        Integer x = random.nextInt() % 3 ;
        showCaptcha(x);
        Integer ans = scanner.nextInt();
        if(!ans.equals(x))
            return false;

        return true;
    }
    public static String askRandom() /// here I have to add random function
    {
        return "You Shall Not Pass My Allies." ;
    }
}
