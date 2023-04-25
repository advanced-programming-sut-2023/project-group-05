package org.example.controller;

import java.util.*;

public class SecurityQuestions
{
    public static ArrayList < String > questions = new ArrayList< String >( Arrays.asList(
            "How old are you?" ,
            "How tall are you in centimeters?( floor the answer )" ,
            "How many kids do you have ?" ,
            "How much do you weigh ? ( floor the answer )" ,
            "How many times you committed suicide ?" ) );
    public static ArrayList < String > slogans = new ArrayList< String >();
    private static HashMap < Integer, ArrayList < String > > captcha = new HashMap < Integer, ArrayList < String > >();
    public static void addQuestion(String S)
    {
        questions.add(S);
    }
    public static void addCaptcha(Integer x, ArrayList < String > y)
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
        Integer x = Math.abs(random.nextInt()) % 3 ;
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