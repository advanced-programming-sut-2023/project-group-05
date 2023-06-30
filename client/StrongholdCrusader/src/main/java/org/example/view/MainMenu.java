package org.example.view;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.ProfileMenuController;
import org.example.model.Account;
import org.example.model.Commands;

import java.util.Scanner;

public class MainMenu extends Application {

    public static void run( Scanner scanner , Account account ) throws Exception {
        System.out.println("You are currently in MainMenu") ;
        String input ;
        while( true ){
            input = scanner.nextLine() ;

            if( input.equals("logout") )
                return ;
            else if( input.equals("enter profile menu") )
                ProfileMenu.run( account ) ;

            else if( Commands.getMatchingMatcher( input , Commands.START_GAME ) != null ){
                GameMenu.run( Commands.getMatchingMatcher( input , Commands.START_GAME ) , account ) ;
            }

            else System.out.println( "invalid command" ) ;

        }

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
