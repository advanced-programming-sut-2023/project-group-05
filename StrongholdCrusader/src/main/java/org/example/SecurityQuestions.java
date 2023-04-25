package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SecurityQuestions
{
    public ArrayList < String > questions = new ArrayList< String >();
    public ArrayList < String > slogans = new ArrayList< String >();
    HashMap < Integer, ArrayList < String > > Captcha = new HashMap < Integer, ArrayList < String > >();
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
        Captcha.put(x, y);
    }
    public void showCaptcha(Integer x)
    {
        ArrayList < String > cur = Captcha.get(x);
        if(cur == null) return;
        for(String now : cur)
        {
            System.out.println(now);
        }
    }
    public boolean runCaptcha(Scanner scanner)
    {
        System.out.println("Beep Boop Bop!");
        System.out.println("What is the number??");
        Integer x = this.askRandom() % 3;
        this.showCaptcha(x);
        Integer ans = scanner.nextInt();
        if(!ans.equals(x))
        {
            System.out.println("I knew it! You are a damn robot :(");
            return false;
        }
        System.out.println("You passed!");
        return true;
    }
    public int askRandom() /// here I have to add random function
    {
        return 0;
    }
}
