package org.example.controller.graphicalMenuController;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.view.Captcha;
import org.example.view.MainMenu;

import java.io.File;

public class CaptchaController {

    private static boolean isPassed = false ;
    private static Button submitButton ;
    private static TextField textField ;
    private static int number = 2254 ;
    private static EventHandler eventHandler ;

    public static void setEventHandler( EventHandler eventHandler ){
        CaptchaController.eventHandler = eventHandler ;
    }

    public static void initialize( BorderPane pane ){
        selectNumber() ;
        isPassed = false ;
        VBox vbox = (VBox) pane.getChildren().get( 0 ) ;
        vbox.setAlignment( Pos.CENTER ) ;
        submitButton = new Button() ;
        submitButton.setText( "SUBMIT" ) ;
        textField = new TextField() ;
        pane.setStyle( "-fx-background-color: grey" ) ;
        System.out.println( submitButton.getStyleClass() ) ;
        ImageView imageView = new ImageView( new Image( MainMenu.class.getResource( "/images/captcha/" + number + ".png" ).toExternalForm() ) ) ;
        vbox.getChildren().addAll( imageView , textField , submitButton ) ;
        initSubmitButton() ;
    }

    private static void selectNumber(){
        int[] numbers  =  { 1181, 1381, 1491, 1722, 1959, 2163, 2177, 2723, 2785, 3541, 3847, 3855, 3876, 3967, 4185,
                            4310, 4487, 4578, 4602, 4681, 4924, 5326, 5463, 5771, 5849, 6426, 6553, 6601, 6733, 6960,
                            7415, 7609, 7755, 7825, 7905, 8003, 8070, 8368, 8455, 8506, 8555, 8583, 8692, 8776, 8972,
                            8996, 9061, 9386, 9582, 9633 } ;
        number = numbers[ (int)(numbers.length*Math.random()) ] ;
    }

    private static void initSubmitButton(){
        submitButton.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                boolean passed = true ;
                int input = 0 ;
                try{
                    input = Integer.parseInt( textField.getText() ) ;
                    if( input != number ) passed = false ;
                } catch ( Exception e ){
                    passed = false ;
                }
                isPassed = passed ;
                eventHandler.handle( mouseEvent ) ;
            }
        } );
    }

    public static boolean isPassed(){
        return isPassed ;
    }

}
