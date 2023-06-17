package org.example.controller;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.example.model.Minimap;
import org.example.model.Player;
import org.example.model.unit.Unit;

import java.util.ArrayList;

public class Camera {

    private Polygon[][] map ; // the map that the camera is viewing
    private int[] pos ; // the position for reference cell
    private Pane pane ; // where we draw things
    private int size ;
    private final int TILE_WIDTH = 42 ;
    private final int TILE_HEIGHT = 22 ;
    private final int viewSize = 50 ;
    private GameController gameController;
    private int unitCount ;

    public Camera( Polygon[][] map , Pane pane , GameController gameController ){
        this.map = map ;
        this.pane = pane ;
        this.pos = new int[]{0, 0};
        this.gameController = gameController ;
        this.size = map.length ;
        this.unitCount = 0 ;
    }

    public int getViewSize(){
        return this.viewSize ;
    }

    public void draw(){

        Paint grassPaint = new ImagePattern( new Image( getClass().getResource("/images/tiles/grass.jpg").toExternalForm() ) ) ;
        Paint waterPaint = new ImagePattern( new Image( getClass().getResource("/images/tiles/water.jpg").toExternalForm() ) ) ;
        Paint groundPaint = new ImagePattern( new Image( getClass().getResource("/images/tiles/ground.jpg").toExternalForm() ) ) ;


        for(int i = 0 ; i < size ; i++){
            for( int j = 0; j < size ; j++ ){
                // highest point on the tile is ( x , y )
                // the +1 is only for debugging and should be removed
                //int x = ( i - j ) * ( TILE_WIDTH / 2 + 1 ) ;
                //int y = ( i + j ) * ( TILE_HEIGHT / 2 + 1 ) ;
                int x = 0 , y = 0 ;
                map[i][j] = new Polygon( x , y , x + TILE_WIDTH/2 , y + TILE_HEIGHT/2 , x , y + TILE_HEIGHT , x - TILE_WIDTH/2 , y + TILE_HEIGHT/2 ) ;
                Paint paint = Color.BLACK ;
                switch( gameController.getGameMap().getCell( i , j ).getCellType() ){
                    case GROUND :
                        paint = groundPaint ;
                        break ;
                    case SEA :
                        paint = waterPaint ;
                        break ;
                    case GRASS :
                        paint = grassPaint ;
                        break ;
                }
                map[i][j].setFill( paint );
                int finalI = i ;
                int finalJ = j ;
                map[i][j].setOnMouseClicked( new EventHandler <MouseEvent>() {
                    @Override
                    public void handle( MouseEvent mouseEvent ){
                        for( Player player : gameController.getPlayers() )
                            for(Unit unit : player.getSelectedUnits()){
                                unit.moveTo(finalI, finalJ) ;
                            }
                    }
                } );
            }
        }

        for( int i = 0 ; i < viewSize ; i++ )
            for( int j = 0 ; j < viewSize ; j++ ){
                pane.getChildren().add( map[i][j] ) ;
            }
        for( int i = 0 ; i < viewSize ; i++)
            for( int j = 0 ; j < viewSize ; j++ )
                for( Unit unit : gameController.getGameMap().getCell( i , j ).units )
                    pane.getChildren().add( unit.getShape() ) ;

    }

    public void move( int mask ){
        // mask will be from 0 to 3 showing each movement
        int dx = 0 , dy = 0 ;

        switch( mask ){
            case 0 :
                dx = 1 ;
                break ;
            case 1 :
                dx = -1 ;
                break ;
            case 2 :
                dy = 1 ;
                break ;
            case 3 :
                dy = -1 ;
                break ;
        }

        if( size <= Math.max(this.pos[0] + dx,this.pos[1]+dy) || Math.min(this.pos[0] + dx , this.pos[1] + dy) < 0 )
            return ;

        if( size <= Math.max( this.pos[0] + viewSize - 1 + dx , this.pos[1] + dy + viewSize - 1 )
                || Math.min( this.pos[0] + viewSize - 1 + dx , this.pos[1] + dy + viewSize - 1 ) < 0 )
            return ;

        moveMinimap(dx, dy) ;

        // removing tiles & units exiting our view, and removing tiles entering our view :
        if( dx != 0 ){
            int xRemove = dx == 1 ? this.pos[0] : this.pos[0] + viewSize - 1;
            int xAdd = dx == - 1 ? this.pos[0] - 1 : this.pos[0] + viewSize;
            for( int j = this.pos[1]; j < this.pos[1] + viewSize; j++ ){
                pane.getChildren().remove( map[xRemove][j] );
                ArrayList <Shape> units = new ArrayList<>() ;
                for( Unit unit : gameController.getGameMap().getCell( xRemove , j ).getUnits() ){
                    units.add( unit.getShape() ) ;
                }
                pane.getChildren().removeAll( units );
            }
            if( xAdd >= 0 && xAdd < size ) for( int j = this.pos[1]; j < this.pos[1] + viewSize; j++ ){
                ArrayList <Shape> units = new ArrayList<>() ;
                for( Unit unit : gameController.getGameMap().getCell( xAdd , j ).getUnits() ){
                    units.add( unit.getShape() ) ;
                }
                // pane.getChildren().add( pane.getChildren().size() - 1 - GameGraphicalController.reservedShapes.size(), map[xAdd][j] );
                pane.getChildren().addAll( pane.getChildren().size() - 1 - GameGraphicalController.reservedShapes.size() , units ) ;
                // pane.getChildren().addAll( 0, units ) ;
                pane.getChildren().add( 0, map[xAdd][j] );
            }
        } else {
            int yRemove = dy == 1 ? this.pos[1] : this.pos[1] + viewSize - 1 ;
            int yAdd = dy == -1 ? this.pos[1] - 1 : this.pos[1] + viewSize ;
            for( int i = this.pos[0] ; i < this.pos[0] + viewSize ; i++ ){
                pane.getChildren().remove( map[i][yRemove] ) ;
                ArrayList <Shape> units = new ArrayList<>() ;
                for( Unit unit : gameController.getGameMap().getCell( i , yRemove ).getUnits() ){
                    units.add( unit.getShape() ) ;
                }
                pane.getChildren().removeAll( units ) ;
            }
            if( yAdd >= 0 && yAdd < size ) for( int i = this.pos[0] ; i < this.pos[0] + viewSize ; i++ ){
                ArrayList <Shape> units = new ArrayList<>() ;
                for( Unit unit : gameController.getGameMap().getCell( i , yAdd ).getUnits() ){
                    units.add( unit.getShape() ) ;
                }
                pane.getChildren().addAll( pane.getChildren().size() - 1 - GameGraphicalController.reservedShapes.size() , units ) ;
                // pane.getChildren().addAll( 0 , units ) ;
                pane.getChildren().add( 0 , map[i][yAdd] ) ;
            }
        }


        this.pos[0] += dx ;
        this.pos[1] += dy ;

        // moving to selected location
        for(int i = this.pos[0] ; i < Math.min ( size , this.pos[0] + viewSize ) ; i++){
            for( int j = this.pos[1] ; j < Math.min ( this.pos[1] + viewSize , size ) ; j++ ){
                // putting the tile on it's place
                int I = i - this.pos[0] ;
                int J = j - this.pos[1] ;
                // there was initially a + 1 after TILE_WIDTH / 2 and TILE_HEIGHT / 2 due debugging, which is removed.
                // you can add it if needed and change back TILE_WIDTH and TILE_HEIGHT to 20 and 40
                int x = ( I - J ) * ( TILE_WIDTH / 2) + 500 ;
                int y = ( I + J ) * ( TILE_HEIGHT / 2 ) - 270 ;
                map[i][j].setLayoutX( x ) ;
                map[i][j].setLayoutY( y ) ;
                // putting the units of that tile there
                for( Unit unit : gameController.getGameMap().getCell( i , j ).getUnits() ){
                    unit.getShape().setX( x - unit.getShape().getWidth() / 2 ) ;
                    unit.getShape().setY( y + TILE_HEIGHT / 2 - unit.getShape().getHeight() )  ;
                }

            }
        }
    }

    public double[] getUnitCoordinates(int x, int y, Unit unit){
//
//        unit.getShape().setX( x - unit.getShape().getWidth() / 2 ) ;
//        unit.getShape().setY( y + TILE_HEIGHT / 2 - unit.getShape().getHeight() )  ;
        int I = x - this.pos[0] ;
        int J = y - this.pos[1] ;
        return new double[]{( I - J ) * ( TILE_WIDTH / 2 + 1 ) + 500 - unit.getShape().getWidth() / 2 ,
                            ( I + J ) * ( TILE_HEIGHT / 2 + 1 ) - 270 + TILE_HEIGHT / 2 - unit.getShape().getHeight() };
    }

    public int[] getPos(){
        return this.pos ;
    }


    // minimap related methods

    private Minimap minimap ;
    public void setMinimap(Minimap minimap){
        this.minimap = minimap ;
    }

    private void moveMinimap(int dx, int dy){
        if(minimap == null) return;
        minimap.move(dx, dy) ;
    }

}
