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
import org.example.controller.Camera;
import org.example.controller.GameGraphicalController;
import org.example.model.GameMap;
import org.example.model.unit.Unit;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {

    private static ImagePattern flameBallImagePattern = new ImagePattern( new Image( Main.class.getResource( "/animationSprites/flameball.png" ).toExternalForm() ) ) ;
    private static ImagePattern poopBallImagePattern = new ImagePattern( new Image( Main.class.getResource( "/animationSprites/poopball.png" ).toExternalForm() ) ) ;;
    private Group group ;
    private double x ;
    private double y ;
    private double X ;
    private double Y ;
    // ( xx , yy ) are coordinated on an inclined coordinate system set at them beginning of the path
    // ( XX , YY ) end of the path in that coordinate system
    private double xx ;
    private double yy ;
    private double XX ;
    private double YY ;
    private double curveFactor ;
    private Rectangle rectangle ;
    private double rectangleSize = 100 ;

    public ShootingAnimation( double x , double y , double X , double Y , int targetRow , int targetColumn , Group group , boolean isFlame , Camera camera ){
        this.x = x ;
        this.y = y ;
        this.xx = 0 ;
        this.yy = 0 ;
        this.XX = Math.sqrt( Math.pow( Y - y , 2) + Math.pow( X - x , 2 ) ) ;
        this.YY = 0 ;
        this.Y = Y ;
        this.X = X ;
        this.group = group ;
        this.setCycleCount( 1 );
        this.setCycleDuration( Duration.millis( 1000 ) );
        this.rectangle = new Rectangle( 0 , 0 , rectangleSize , rectangleSize ) ;
        ImagePattern imagePattern = ( isFlame ? flameBallImagePattern : poopBallImagePattern ) ;
        this.rectangle.setFill( imagePattern );
        this.group.getChildren().add( rectangle ) ;
        curveFactor = 800 ;
        if( X - x > 0 ) curveFactor *= -1 ;
        this.setOnFinished( new EventHandler <ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ){
                group.getChildren().remove( rectangle );
                GameMap gameMap = GameGraphicalController.getGameMap() ;

                ArrayList<Unit> units = new ArrayList<Unit>() ;

                for(int i = -2 ; i < 3 ; i++)
                    for(int j = -2 ; j < 3 ; j++){
                        if( Math.abs( i ) + Math.abs( j ) == 4 ) continue ;
                        units.addAll(gameMap.getCell( targetRow + i , targetColumn + j ).getUnits()) ;
                        if( isFlame ){
                            double[] fireAnimation = camera.getCellCoordinates( targetRow + i, targetColumn + j );
                            new FireAnimation( GameGraphicalController.weaponsNode, fireAnimation[0], fireAnimation[1] ).play();
                        } else {
                            double[] fireAnimation = camera.getCellCoordinates( targetRow + i, targetColumn + j );
                            new PoopAnimation( GameGraphicalController.weaponsNode, fireAnimation[0], fireAnimation[1] ).play();
                        }
                    }

                for(Unit unit : units){
                    unit.getDamaged( 50 , gameMap ) ;
                }
            }
        } );
    }

    @Override
    protected void interpolate( double v ){
        this.xx = v * this.XX ;
        this.yy = ( - Math.pow( ( 0.5 - v ) , 2 ) + 0.25 ) * curveFactor ;
        rectangle.setX( x + xx * ( X - x ) / XX - yy * ( Y - y ) / XX - rectangleSize / 2 ) ;
        rectangle.setY( y + xx * ( Y - y ) / XX + yy * ( X - x ) / XX - rectangleSize / 2 ) ;
    }
}
