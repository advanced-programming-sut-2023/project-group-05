package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.Account;
import org.example.model.Commands;

import java.util.Scanner;

public class MainMenu {

    public static void run( Scanner scanner , Account account ){

        System.out.println("You are currently in MainMenu") ;
        String input ;
        while( true ){
            input = scanner.nextLine() ;

            if( input.equals("logout") )
                return ;

            if( input.equals("enter profile menu") )
                ProfileMenu.run( account ) ;

            if( Commands.getMatchingMatcher( input , Commands.START_GAME ) ){
                
            }

        }

    }

}
