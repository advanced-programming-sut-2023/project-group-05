package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.controller.SecurityQuestions;
import org.example.controller.SignupLoginMenuController;
import org.example.controller.graphicalMenuController.ForgetPasswordController;
import org.example.model.Account;

public class ForgetPassword extends Application {

    public TextField newPasswordField;
    @FXML
    private Text securityQuestionText;
    @FXML
    private TextField answerTextField;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(StartMenu.class.getResource("/fxml/ForgetPassword.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Forget password") ;
        stage.show();
    }

    public void initialize( Account account ) throws Exception {
        ForgetPasswordController.setAccount( account ) ;
        this.start( LoginMenu.stage ) ;
    }

    public void showQuestion(){
        securityQuestionText.setText( SecurityQuestions.questions.get((int)ForgetPasswordController.getAccount().getQuestion()) ) ;
    }

    public void submit( MouseEvent mouseEvent ){
        String answer = answerTextField.getText() ;
        long answerLong ;
        try{
            answerLong = (long) Integer.parseInt( answer ) ;
        } catch( Exception e ) {
            Alert alert = new Alert( Alert.AlertType.ERROR ) ;
            alert.setTitle( "error" );
            alert.setContentText( "invalid input" );
            alert.showAndWait() ;
            return ;
        }
        if( answerLong != ForgetPasswordController.getAccount().getAnswer() ){
            Alert alert = new Alert( Alert.AlertType.ERROR ) ;
            alert.setTitle( "error" ) ;
            alert.setContentText( "You entered wrong answer." );
            alert.showAndWait() ;
            return ;
        }
        String newPassword = newPasswordField.getText() ;
        if( !SignupLoginMenuController.validPassword( newPassword ) ){
            Alert alert = new Alert( Alert.AlertType.ERROR );
            alert.setTitle( "ERROR" ) ;
            alert.setContentText( "your password is too simple." );
            alert.showAndWait() ;
            return ;
        }
        ForgetPasswordController.getAccount().setPassword( Hash.encode(newPassword) );
    }
}
