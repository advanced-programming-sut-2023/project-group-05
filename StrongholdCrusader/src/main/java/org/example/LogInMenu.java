package org.example;

import org.json.simple.JSONObject;

import java.util.Scanner;

public class LogInMenu
{
    public String userName, password;
    public void run(Scanner scanner, SecurityQuestions securityQuestions)
    {
        if(! SignUpLoginMenu.validUserName(userName) || ! SignUpLoginMenu.validPassword(password))
        {
            System.out.println("Invalid input");
        }
        if(!securityQuestions.runCaptcha(scanner))
        {
            return;
        }
        if(DataBase.getFromDataBase("userName", userName) == null)
        {
            System.out.println("This username does not exist");
        }
        JSONObject cur = DataBase.getFromDataBase("userName", userName);
        long pass = (long) cur.get("password");
        if(pass != MyHash.Encode(password))
        {
            System.out.println("Wrong Password!");
            return;
        }
        System.out.println("User Logged In! horay!!");
    }
    public void runWithoutPassword(Scanner scanner, SecurityQuestions securityQuestions)
    {
        if(DataBase.getFromDataBase("userName", userName) == null)
        {
            System.out.println("Wrong username!");
            return;
        }
        JSONObject cur = DataBase.getFromDataBase("userName", userName);
        int question = (int)cur.get("question");
        int answer = (int)cur.get("answer");
        System.out.println(securityQuestions.questions.get(question));
        int ans = scanner.nextInt();
        if(ans != answer)
        {
            System.out.println("Wrong backup question answer! Get the hell out of hear NOW!");
            return;
        }
        changePassword(scanner, securityQuestions);
        System.out.println("User Looged In! horay!!");
    }
    private void changePassword(Scanner scanner, SecurityQuestions securityQuestions)
    {
        /// handle it later! you have to literraly delete and change the database for this shit
    }
}
