package org.example.model.animations;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import org.example.controller.GameGraphicalController;
import org.example.model.unit.Unit;
import org.example.view.MainMenu;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class WalkingAnimation extends Transition {

    private Unit unit ;
    private int unitRow ;
    private int unitColumn ;
    private int size ;
    private int lastNum = -1 ;
    ArrayList <ImagePattern> imagePatterns = new ArrayList<>() ;
    double[] target ;
    int targetRow ;
    int targetColumn ;
    private boolean updated ;

    public WalkingAnimation( String name , int size , Unit unit , int durationMillis ){
        this.unit = unit ;
        for(int i = 0 ; i < size ; i++){
            imagePatterns.add( new ImagePattern( new Image ( MainMenu.class.getResource( "/animationSprites/" + name + "/" + i + ".png" ).toExternalForm())) ) ;
        }
        this.size = size ;
        setCycleCount( 1 );
        setCycleDuration( Duration.millis( durationMillis ) );
        target = new double[]{0, 0};
        WalkingAnimation finalThis = this ;
        this.setInterpolator( Interpolator.LINEAR );
        this.setOnFinished( new EventHandler <ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ){
                if(unit.getTargetRow() != unit.getRow() || unit.getTargetColumn() != unit.getColumn() )
                    finalThis.play() ;
            }
        } );
    }

    public void setTarget(){
        targetRow = unit.getNextRow() ;
        targetColumn = unit.getNextColumn() ;
    }

    @Override
    public void play(){
        if(!unit.getIsMoving()) return ;
        setTarget() ;
        updated = false ;
        unitRow = unit.getRow() ;
        unitColumn = unit.getColumn() ;
        if(unitRow == targetRow && unitColumn == targetColumn){
            unit.setIsMoving( false ) ;
            unit.walkingReason = -1 ;
            return ;
        }
        super.play() ;
    }

    @Override
    protected void interpolate( double v ){
        double[] unitPos = GameGraphicalController.camera.getUnitCoordinates( unit.getRow() , unit.getColumn() , unit ) ;
        double unitX = unitPos[0] ;
        double unitY = unitPos[1] ;
        target = GameGraphicalController.camera.getUnitCoordinates( targetRow, targetColumn , unit );
        unit.getShape().setX( unitX + v * (target[0] - unitX) ) ;
        unit.getShape().setY( unitY + v * (target[1] - unitY) ) ;
        if(v > 0.999 && !updated){
            unit.setRow( targetRow ) ;
            unit.setColumn( targetColumn ) ;
            GameGraphicalController.gameController.getGameMap().getCell( unitRow , unitColumn ).getUnits().remove( unit ) ;
            GameGraphicalController.gameController.getGameMap().getCell( targetRow , targetColumn ).getUnits().add( unit ) ;
            updated = true ;
        }
        int num = (int)Math.floor(v * 7) ;
        if( num == lastNum ) return ;
        lastNum = num ;
        unit.getShape().setFill( imagePatterns.get( num ) ) ;
    }
}
