package org.example.model.animations;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import org.example.model.unit.Unit;
import org.example.view.MainMenu;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class WalkingAnimation extends Transition {

    private Unit unit ;
    private int size ;
    private int lastNum = -1 ;
    ArrayList <ImagePattern> imagePatterns = new ArrayList<>() ;

    public WalkingAnimation( String name , int size , Unit unit , int durationMillis ){
        this.unit = unit ;
        for(int i = 0 ; i < size ; i++){
            imagePatterns.add( new ImagePattern( new Image ( MainMenu.class.getResource( "/animationSprites/" + name + "/" + i + ".png" ).toExternalForm())) ) ;
        }
        this.size = size ;
        setCycleCount( -1 );
        setCycleDuration( Duration.millis( durationMillis ) );
    }

    @Override
    protected void interpolate( double v ){
        int num = (int)Math.floor(v * 7) ;
        if( num == lastNum ) return ;
        lastNum = num ;
        double width = unit.getShape().getWidth() ;
        double height = unit.getShape().getHeight() ;
        unit.getShape().setFill( imagePatterns.get( num ) ) ;
        unit.getShape().setWidth( 50 ) ;
        unit.getShape().setHeight( 50 ) ;
    }
}
