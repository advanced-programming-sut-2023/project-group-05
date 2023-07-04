package org.example.model.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import org.example.Main;

public class PoopAnimation extends Transition {

    private double x ;
    private double y ;
    private Group group ;
    private int last ;
    private Rectangle rectangle ;
    private Scale scale ;
    private static ImagePattern imagePattern = new ImagePattern( new Image( Main.class.getResource( "/animationSprites/poopball.png" ).toExternalForm() ) );
    private static double imageSize = 30 ;
    public PoopAnimation( Group group , double x , double y ){
        this.setCycleCount( 10 );
        this.x = x;
        this.y = y;
        this.group = group;
        this.setCycleDuration( Duration.millis( 500 ) );
        this.rectangle = new Rectangle( x - imageSize / 2 , y - imageSize / 2, imageSize, imageSize);
        rectangle.setFill( imagePattern ) ;
        scale = new Scale() ;
        scale.setPivotX( x - 22 );
        scale.setPivotY( y - 22 ) ;
        rectangle.getTransforms().add( scale ) ;
        this.last = - 1;
        this.group.getChildren().add( rectangle );
        this.setOnFinished( new EventHandler <ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ){
                group.getChildren().remove( rectangle );
            }
        } );
        this.setInterpolator( Interpolator.LINEAR );
    }

    @Override
    protected void interpolate( double v ){
        scale.setX( 1 + 0.25 * Math.sin( Math.PI * v ) ) ;
        scale.setY( 1 + 0.25 * Math.sin( Math.PI * v ) ) ;
        //rectangle.setRotate( 360 * v ) ;
    }

}
