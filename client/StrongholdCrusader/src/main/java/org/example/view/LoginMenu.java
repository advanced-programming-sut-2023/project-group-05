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
import org.example.controller.GameController;
import org.example.controller.SignupLoginMenuController;
import org.example.controller.graphicalMenuController.CaptchaController;

public class LoginMenu extends Application {
    public TextField usernameTextField;
    public PasswordField passwordField;
    public static Stage stage ;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(StartMenu.class.getResource("/fxml/LoginMenu.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
        LoginMenu.stage = stage ;
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
                    try {
                        (new LoginMenu()).start( stage ) ;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else if ( output != null ) {
                    Alert alert = new Alert( Alert.AlertType.ERROR ) ;
                    alert.setTitle( "ERROR" ) ;
                    alert.setContentText( output ) ;
                    alert.showAndWait() ;
                    try {
                        (new LoginMenu()).start( stage ) ;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Alert alert = new Alert( Alert.AlertType.INFORMATION ) ;
                    alert.setTitle( "Signup successful" );
                    GameController.currentUsername = usernameTextField.getText() ;
                    String[] info = SignupLoginMenuController.getInfo(GameController.currentUsername);
                    GameController.currentNickname = info[1] ;
                    GameController.currentSlogan = info[2] ;
                    GameController.currentEmail = info[3] ;
                    GameController.currentPassword = passwordField.getText() ;
                    MainMenu.username = info[0] ;
                    alert.setContentText( "you have successfully logged in, " + usernameTextField.getText()
                        + "\nYour Username : " + info[0]
                            + "\nYour nickname : " + info[1]
                            + "\nYour slogan : " + info[2]
                            + "\nYour email : " + info[3]
                    );
                    alert.showAndWait() ;
                    try {
                        /// System.out.println("nigg");
                        new MainMenu().start( stage );
                    } catch( Exception e ){
                        e.printStackTrace();
                    }
                }
            }
        } ;

        (new Captcha()).run( stage , eventHandler ) ;

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new StartMenu().start(StartMenu.stage);
    }
}
