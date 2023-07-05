package org.example.controller;

import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.model.Chat;
import org.example.model.Message;
import org.example.view.ChatPage;

import java.io.IOException;

public class ChatUpdator extends Transition {

    public ChatUpdator(){
        this.setCycleCount( -1 ) ;
        this.setCycleDuration( Duration.millis(1000) );
    }

    @Override
    protected void interpolate( double v ){
        if( v == 0 ) try {
            ChatPage.updatePane() ;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
