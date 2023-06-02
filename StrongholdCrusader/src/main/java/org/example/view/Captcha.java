package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controller.graphicalMenuController.CaptchaController;

public class Captcha extends Application {

    @FXML
    private ImageView imageView;

    @Override
    public void start( Stage stage ) throws Exception{
        BorderPane borderPane = FXMLLoader.load( getClass().getResource( "/fxml/Captcha.fxml" ) ) ;
        Scene scene = new Scene( borderPane ) ;
        stage.setTitle( "Captcha" ) ;
        stage.setScene( scene ) ;
        stage.show() ;
        CaptchaController.initialize( borderPane ) ;
    }

}
