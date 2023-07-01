package org.example.model.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import org.example.model.unit.Unit;

public class AttackingAnimation extends Transition {
    private Unit unit ;
    private Unit enemy ;
    private static AttackingAnimation instance ;

    private AttackingAnimation(){
        this.setCycleCount( 1 );
        this.setCycleDuration( Duration.millis( 1000 ) ) ;
    }
    private static AttackingAnimation getInstance(){
        if(instance == null){
            instance = new AttackingAnimation() ;
        }
        return instance ;
    }

    public static AttackingAnimation setUnit( Unit unit , Unit enemy ){
        getInstance().unit = unit ;
        getInstance().enemy = enemy ;
        return instance ;
    }

    @Override
    protected void interpolate( double v ){
        if( v < 0.999 ){
            unit.getShape().setRotate( v * 360 ) ;
        } else {
            unit.getShape().setRotate( 0 ) ;
            unit.attack() ;
        }
    }
}
