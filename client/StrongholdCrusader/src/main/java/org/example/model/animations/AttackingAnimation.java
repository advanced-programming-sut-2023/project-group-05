package org.example.model.animations;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.controller.Camera;
import org.example.controller.GameGraphicalController;
import org.example.model.unit.Unit;
import org.example.view.MainMenu;

public class AttackingAnimation extends Transition {
    private Unit unit ;
    private static ImagePattern swordImagePattern = new ImagePattern( new Image( MainMenu.class.getResource("/images/units/weapons/sword.png").toExternalForm() ) ) ;
    private Unit enemy ;
    private Rectangle sword ;
    private boolean finished = false ;
    private static AttackingAnimation instance ;

    public AttackingAnimation(Unit unit , Unit enemy){
        this.setCycleCount( 1 );
        this.unit = unit ;
        this.enemy = enemy ;
        this.sword = new Rectangle( unit.getShape().getX() - Unit.UNIT_SIZE / 5 , unit.getShape().getY() , Unit.UNIT_SIZE / 2 , Unit.UNIT_SIZE / 2 ) ;
        sword.setFill( swordImagePattern ) ;
        GameGraphicalController.getPane().getChildren().add( sword ) ;
        this.setCycleDuration( Duration.millis( 1000 ) ) ;
    }

    @Override
    protected void interpolate( double v ){
        if( v < 0.999 ){
            sword.setRotate( v * 360 ) ;
        }
        else if ( !finished ){
            finished = true ;
            GameGraphicalController.getPane().getChildren().remove( sword ) ;
            unit.getShape().setRotate( 0 ) ;
            unit.attack() ;
        }
    }
}
