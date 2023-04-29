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

            else if( input.equals("enter profile menu") )
                ProfileMenu.run( account ) ;

            else if( Commands.getMatchingMatcher( input , Commands.START_GAME ) != null ){
                GameMenu.run( Commands.getMatchingMatcher( input , Commands.START_GAME ) ) ;
            }

            else System.out.println( "invalid command" ) ;

        }

    }

}
