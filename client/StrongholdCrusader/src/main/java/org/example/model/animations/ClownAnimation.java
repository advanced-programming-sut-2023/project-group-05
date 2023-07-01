package org.example.model.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ClownAnimation extends Transition {

    Text text ;
    Rectangle rect ;
    int last = - 1;

    public ClownAnimation( Text text , Rectangle rect ){
        this.text = text ;
        this.rect = rect ;
        this.setCycleCount( 1 );
        this.setInterpolator( Interpolator.LINEAR );
        this.setCycleDuration( new Duration( 6000 ) );
        this.setOnFinished( new EventHandler <ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ){
                text.setText( text.getText().replaceAll( "little tornado in 1$" , "little tornado died") ) ;
            }
        } );
    }

    @Override
    protected void interpolate( double v ){
        int a = (int)Math.floor(v * 6) ;
        if( a < 5 ){
            if( a != last ){
            last = a ;
            String s = text.getText() ;
            text.setText( s.replaceAll( "\\d$" , "" + (5 - a) ) ) ;}
        } else {
            rect.setRotate( ( v * 6 - 5 ) * 1800 ) ;
            text.setRotate( ( v * 6 - 5 ) * 1800 ) ;
        }
    }
}
