package org.example.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.SecurityQuestions;
import org.example.controller.SignupLoginMenuController;
import org.example.controller.graphicalMenuController.CaptchaController;

import java.util.concurrent.atomic.AtomicInteger;

public class SignupMenu extends Application { // where you signup
    public static Stage stage ;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField ;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField sloganTextField;
    @FXML
    private PasswordField passwordConfirmField;

    @Override
    public void start(Stage stage) throws Exception {
        SignupMenu.stage = stage ;
        BorderPane borderPane = FXMLLoader.load( getClass().getResource( "/fxml/SignupMenu.fxml" ) ) ;
        stage.setScene( new Scene(borderPane) ) ;
        stage.setTitle( "Signup Menu" ) ;
        stage.show() ;
    }


    public void randomSlogan( MouseEvent mouseEvent ){
        sloganTextField.setText( SecurityQuestions.getRandomSlogan() ) ;
    }

    public void submit() throws Exception{
        String username = usernameTextField.getText() ;
        String password = passwordField.getText() ;
        String slogan = sloganTextField.getText() ;
        String nickname = nicknameTextField.getText() ;
        String email = emailTextField.getText() ;
        String passwordConfirm = passwordConfirmField.getText() ;

        EventHandler eventHandler = new EventHandler<>() {
            @Override
            public void handle( Event event ){
                if( CaptchaController.isPassed() ){
                    String output = SignupLoginMenuController.createUser( username , password , nickname , passwordConfirm , email , slogan ) ;
                    if( !CaptchaController.isPassed() ){
                        Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                        alert.setTitle( "Captcha failed." ) ;
                        alert.setContentText( "You have entered wrong captcha." );
                        alert.showAndWait() ;
                        try {
                            (new StartMenu()).start( SignupMenu.stage ) ;
                        } catch(Exception e) {
                            e.printStackTrace() ;
                        }
                    }
                    else if( output != null ){
                        Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                        alert.setTitle( "ERROR" ) ;
                        alert.setContentText( output );
                        alert.showAndWait() ;
                        try {
                            (new StartMenu()).start( SignupMenu.stage ) ;
                        } catch(Exception e) {
                            e.printStackTrace() ;
                        }
                    } else {
                        Alert alert = new Alert( Alert.AlertType.INFORMATION ) ;
                        alert.setTitle( "Signup successful" );
                        alert.setContentText( "you have successfully registered your account." );
                        alert.showAndWait() ;
                    }

                } else {
                    Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                    alert.setTitle( "Wrong captcha" ) ;
                    alert.setContentText( "you have entered wrong captcha" ) ;
                    alert.showAndWait() ;
                    try {
                        ( new SignupMenu() ).start(stage) ;
                    } catch( Exception e ){
                        e.printStackTrace();
                    }
                }
            }
        } ;

        new Captcha().run( stage , eventHandler ) ;


    }

}
