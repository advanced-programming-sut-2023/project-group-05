package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class ProfileMenu extends Application {

    public VBox mainVBox;
    public Text text;

    @Override
    public void start( Stage stage) throws Exception {
        BorderPane pane = FXMLLoader.load( new URL( MainMenu.class.getResource( "/fxml/ProfileMenu.fxml" ).toExternalForm()) );
        Scene scene = new Scene( pane ) ;
        stage.setScene( scene ) ;
        stage.show() ;
    }
}
