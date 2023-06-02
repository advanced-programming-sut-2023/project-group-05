package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.controller.SignupLoginMenuController;

public class SignupMenu extends Application {
    public TextField usernameTextField;
    public PasswordField passwordField ;
    public TextField emailTextField;
    public TextField nicknameTextField;
    public TextField sloganTextField;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load( getClass().getResource( "/fxml/SignupMenu.fxml" ) ) ;
        stage.setScene( new Scene(borderPane) ) ;
        stage.setTitle( "Signup Menu" ) ;
        stage.show() ;
    }

    public void submit( MouseEvent mouseEvent ){

        boolean validUsername = SignupLoginMenuController.validUserName( usernameTextField.getText() ) ;
        boolean validEmail = SignupLoginMenuController.validEmail( emailTextField.getText() ) ;
        boolean validPassword = SignupLoginMenuController.validPassword( passwordField.getText() ) ;
        boolean validNickname = SignupLoginMenuController.validNickname( nicknameTextField.getText() ) ;
        String slogan = sloganTextField.getText() ;

        if( !validUsername ){
            (new Alert( Alert.AlertType.ERROR , "INVALID USERNAME" )).showAndWait() ;
            return ;
        }
        if( !validEmail ){
            (new Alert( Alert.AlertType.ERROR , "INVALID EMAIL" )).showAndWait() ;
            return ;
        }
        if( !validPassword ){
            (new Alert( Alert.AlertType.ERROR , "INVALID PASSWORD" )).showAndWait() ;
            return ;
        }
        if( !validNickname ){
            (new Alert( Alert.AlertType.ERROR , "INVALID NICKNAME" )).showAndWait() ;
            return ;
        }
        if( slogan.equals("") ){
            (new Alert( Alert.AlertType.ERROR , "INVALID SLOGAN" )).showAndWait() ;
            return ;
        }

    }
}
