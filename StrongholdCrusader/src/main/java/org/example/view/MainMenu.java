package org.example.view;

import org.example.model.Account;

import java.util.Scanner;

public class MainMenu {

    public static void run( Scanner scanner , Account account ){

        System.out.println("You are currently in MainMenu") ;
        String input ;
        while( true ){
            input = scanner.nextLine() ;
            if( input.equals("logout") )
                return ;
        }

    }

}
