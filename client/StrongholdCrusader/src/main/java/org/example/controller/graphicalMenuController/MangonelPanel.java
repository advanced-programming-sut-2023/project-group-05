package org.example.controller.graphicalMenuController;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.example.Main;
import org.example.controller.Camera;
import org.example.controller.GameGraphicalController;
import org.example.model.Cell;
import org.example.model.animations.FireAnimation;
import org.example.model.animations.ShootingAnimation;

public class MangonelPanel {

    private static double BUTTON_SIZE = 40 ;
    private static MangonelPanel instance ;
    private static ImagePattern poopImagePattern = new ImagePattern( new Image( Main.class.getResource( "/animationSprites/poopball.png" ).toExternalForm() ) ) ;
    private static ImagePattern fireImagePattern = new ImagePattern( new Image( Main.class.getResource( "/animationSprites/flameball.png" ).toExternalForm() ) );
    private static ImagePattern targetImagePattern = new ImagePattern( new Image( Main.class.getResource( "/images/icons/target_icon.png" ).toExternalForm() ) ) ;
    private static ImagePattern shootImagePattern = new ImagePattern( new Image( Main.class.getResource( "/images/icons/shoot_icon.jpg" ).toExternalForm() ) ) ;
    private int row ;
    private int column ;
    private Camera camera ;
    private Polygon buildingShape ;
    private Rectangle panelShape ;
    private Group group ;
    private static int targetColumn ;
    private static int targetRow ;
    private static boolean active ; // when a mangonel is selected
    private static boolean isTargetChosen = false ;
    private Rectangle changeModeButton ;
    private Rectangle shootButton ;
    private Rectangle setTargetButton ;
    private boolean flameMode = true ;

    public static MangonelPanel getInstance(){
        if( instance == null ){
            instance = new MangonelPanel() ;
        }
        return instance ;
    }

    public Group getShape(){
        return this.group ;
    }

    private MangonelPanel(){
        this.panelShape = new Rectangle( 0 , 0 , BUTTON_SIZE * 3 , BUTTON_SIZE ) ;
        this.panelShape.setFill( Color.GREY ) ;
        this.panelShape.setOpacity( 0.75 );
        this.group = new Group() ;
        this.group.getChildren().addAll( panelShape ) ;
        initGraphics();
        initButtons() ;
    }

    private void initGraphics(){
        changeModeButton = new Rectangle( 0 , 0 , BUTTON_SIZE , BUTTON_SIZE ) ;
        shootButton = new Rectangle( BUTTON_SIZE , 0 , BUTTON_SIZE , BUTTON_SIZE ) ;
        setTargetButton = new Rectangle( BUTTON_SIZE * 2 , 0 , BUTTON_SIZE , BUTTON_SIZE ) ;
        shootButton.setFill( shootImagePattern ) ;
        setTargetButton.setFill( targetImagePattern ) ;
        changeModeButton.setFill( fireImagePattern ) ;
        this.group.getChildren().addAll( shootButton , changeModeButton , setTargetButton ) ;
    }

    private void initButtons(){
        changeModeButton.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                flameMode = !flameMode ;
                if( flameMode ) changeModeButton.setFill( fireImagePattern ) ;
                else changeModeButton.setFill( poopImagePattern ) ;
            }
        } );
        setTargetButton.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                isTargetChosen = false ;
                GameGraphicalController.setMangonelMouse( true ) ;
            }
        } );
        shootButton.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                shoot() ;
            }
        } );
    }

    public static void setMangonel( Polygon buildingShape , int row , int column , Camera camera ){
        getInstance().buildingShape = buildingShape ;
        getInstance().row = row ;
        getInstance().column = column ;
        getInstance().camera = camera ;
    }

    public static boolean hasTarget(){
        return isTargetChosen ;
    }

    public static void setActive( boolean active ){
        MangonelPanel.active = active;
    }

    public static boolean isActive(){
        return active;
    }

    public static void setTarget( int row , int column ){
        isTargetChosen = true ;
        targetColumn = column ;
        targetRow = row ;
    }

    public static void forgetTarget(){
        isTargetChosen = true ;
    }

    private void shoot(){
        if(!isTargetChosen) return ;
        double[] targetCoordinates = camera.getCellCoordinates( targetRow , targetColumn ) ;
        ( new ShootingAnimation( buildingShape.getLayoutX() , buildingShape.getLayoutY() , targetCoordinates[0] , targetCoordinates[1] , targetRow , targetColumn , GameGraphicalController.weaponsNode , flameMode , camera )).play() ;
    }

}
