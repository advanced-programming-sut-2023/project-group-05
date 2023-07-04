package org.example.model.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.Main;
import org.example.model.Cell;

public class FireAnimation extends Transition {


    private double x ;
    private double y ;
    private Group group ;
    private int last ;
    private Rectangle rectangle ;
    private static ImagePattern[] fireSprites = new ImagePattern[16];

    static {
        for(int i = 0  ; i < 16 ; i++)
            fireSprites[i] = new ImagePattern( new Image( Main.class.getResource( "/animationSprites/fireAnimation/0_0img"+i+".png" ).toExternalForm() ) ) ;
    }

    public FireAnimation( Group group , double x , double y ){
        this.setCycleCount( 10 );
        this.x = x ;
        this.y = y ;
        this.group = group ;
        this.setCycleDuration( Duration.millis( 500 ) );
        this.rectangle = new Rectangle( x - 22 , y - 22 , 44 , 44 ) ;
        this.last = -1 ;
        this.group.getChildren().add( rectangle ) ;
        this.setOnFinished( new EventHandler <ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ){
                group.getChildren().remove( rectangle ) ;
            }
        } );
    }

    @Override
    protected void interpolate( double v ){
        int step = (int)Math.floor( v * 16 ) ;
        if( step != last ){
            last = step ;
            if( last == 16 ) return ;
            rectangle.setFill( fireSprites[last] ) ;
        }
    }
}
