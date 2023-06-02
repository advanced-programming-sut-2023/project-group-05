package org.example.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.controller.SignupLoginMenuController;
import org.example.controller.graphicalMenuController.CaptchaController;
import org.example.model.Account;

public class LoginMenu extends Application {
    public TextField usernameTextField;
    public PasswordField passwordField;
    public static Stage stage ;

    @Override
    public void start(Stage stage) throws Exception {
        DataBase.wakeUp() ;
        BorderPane borderPane = FXMLLoader.load(StartMenu.class.getResource("/fxml/LoginMenu.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
        LoginMenu.stage = stage ;
    }

    public void forgetPassword(MouseEvent mouseEvent) throws Exception {
        String username = usernameTextField.getText() ;
        Account account = Account.getAccountsMap().get( username );

        if( account == null ){
            Alert alert = new Alert( Alert.AlertType.ERROR ) ;
            alert.setTitle( "ERROR" ) ;
            alert.setContentText( "account not found." );
            alert.showAndWait() ;
            return ;
        }

        (new ForgetPassword()).initialize( account ) ;
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        String username = usernameTextField.getText() ;
        String password = passwordField.getText() ;
        String output = SignupLoginMenuController.loginUser( username , password ) ;

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle( Event event ){
                if( !CaptchaController.isPassed() ){
                    Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                    alert.setTitle( "Captcha failed." ) ;
                    alert.setContentText( "You have entered wrong captcha." );
                    alert.showAndWait() ;
                }
                else if ( output != null ) {
                    Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                    alert.setTitle( "ERROR" ) ;
                    alert.setContentText( output ) ;
                    alert.showAndWait() ;
                }
                else {
                    Alert alert = new Alert( Alert.AlertType.INFORMATION ) ;
                    alert.setTitle( "Signup successful" );
                    alert.setContentText( "you have successfully logged in." );
                    alert.showAndWait() ;
                }
            }
        } ;

        (new Captcha()).run( stage , eventHandler ) ;

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new StartMenu().start(StartMenu.stage);
    }
}
