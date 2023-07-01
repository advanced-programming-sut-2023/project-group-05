package org.example.model.animations;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

public class gettingDamageAnimation extends Transition {
    static final int CONST = 3 ;
    Shape shape ;
    Scale scale ;

    public gettingDamageAnimation( Shape shape ){
        this.shape = shape ;
        this.setCycleCount( 1 );
        this.setCycleDuration( Duration.millis( 1000 ) );
        scale = new Scale() ;
        shape.getTransforms().add( scale );
    }

    @Override
    protected void interpolate( double v ){
        scale.setX( 1 + 0.5 * CONST - CONST * Math.floor(v-0.5) ) ;
        scale.setY( 1 + 0.5 * CONST - CONST * Math.floor(v-0.5) ) ;
    }
}
