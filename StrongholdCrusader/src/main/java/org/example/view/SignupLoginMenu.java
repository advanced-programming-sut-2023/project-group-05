package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controller.SignupLoginMenuController;
import org.example.model.Commands;

import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
public class SignupLoginMenu extends Application {
    public static int wrongPassCounter = 0;

    @Override
    public void start( Stage stage ) throws Exception{
        BorderPane borderPane = FXMLLoader.load(SignupLoginMenu.class.getResource("/fxml/SignupLoginMenu.fxml"));
        Scene scene = new Scene( borderPane ) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    public static void run ( Scanner scanner ) throws Exception {
        Matcher matcher ;
        while (true){
            String input = Menu.getScanner().nextLine();
            if ((matcher =Commands.getMatchingMatcher(input,Commands.CREATE_USER))!=null) {
                //System.out.println(SignupLoginMenuController.createUser(scanner, matcher, true));
                wrongPassCounter = 0;
            }
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.USER_LOGIN))!=null)
                System.out.print(SignupLoginMenuController.loginUser(scanner , matcher,true));
            else if ((matcher = Commands.getMatchingMatcher(input, Commands.STAY_LOGGED_IN)) != null)
                System.out.println(SignupLoginMenuController.loginUserStayLoggedIn(scanner, matcher));
            else if ((matcher = Commands.getMatchingMatcher(input,Commands.FORGET_PASSWORD))!=null) {
                System.out.println(SignupLoginMenuController.forgetPassword(matcher));
                wrongPassCounter = 0;
            }
            else if ( input.equals("exit") )
                return ;
            else
                System.out.println("Invalid Command");
        }
    }

}