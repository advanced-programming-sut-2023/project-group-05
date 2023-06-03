package org.example.controller;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import org.example.Main;

public class Camera {

    private Polygon[][] map ; // the map that the camera is viewing
    private int[] pos ; // the position for reference cell
    private Pane pane ; // where we draw things
    private int size ;
    private final int TILE_WIDTH = 40 ;
    private final int TILE_HEIGHT = 20 ;
    private final int viewSize = 50 ;

    public Camera( Polygon[][] map , Pane pane ){
        this.map = map ;
        this.pane = pane ;
        this.pos = new int[]{0, 0};
        this.size = map.length ;
    }

    public void draw(){
        //ImagePattern imagePattern = new ImagePattern( new Image( Main.class.getResource( "/images/tiles/desert_tile.jpg" ).toExternalForm() ) ) ;

        for(int i = 0 ; i < size ; i++){
            for( int j = 0; j < size ; j++ ){
                // highest point on the tile is ( x , y )
                // the +1 is only for debugging and should be removed
                //int x = ( i - j ) * ( TILE_WIDTH / 2 + 1 ) ;
                //int y = ( i + j ) * ( TILE_HEIGHT / 2 + 1 ) ;
                int x = 0 , y = 0 ;
                map[i][j] = new Polygon( x , y , x + TILE_WIDTH/2 , y + TILE_HEIGHT/2 , x , y + TILE_HEIGHT , x - TILE_WIDTH/2 , y + TILE_HEIGHT/2 ) ;
                Paint paint = Color.BLACK ;
                switch( (i/4 + j/4) % 4 ){
                    case 0 :
                        paint = Color.BLUE ;
                        break ;
                    case 1 :
                        paint = Color.YELLOW ;
                        break ;
                    case 2 :
                        paint = Color.RED ;
                        break ;
                }
                map[i][j].setFill( paint );
            }
        }

        for( int i = 0 ; i < viewSize ; i++ )
            for( int j = 0 ; j < viewSize ; j++ )
                pane.getChildren().add( map[i][j] ) ;

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

        if( size < Math.max(this.pos[0] + dx,this.pos[1]+dy) || Math.min(this.pos[0] + dx , this.pos[1]+dy) < 0 )
            return ;

        // removing tiles exiting our view, and removing tiles entering our view :
        if( dx != 0 ){
            int xRemove = dx == 1 ? this.pos[0] : this.pos[0] + viewSize - 1 ;
            int xAdd = dx == -1 ? this.pos[0] - 1 : this.pos[0] + viewSize ;
            for( int j = this.pos[1] ; j < this.pos[1] + viewSize ; j++ )
                pane.getChildren().remove( map[xRemove][j] ) ;
            if( xAdd >= 0 && xAdd < size ) for( int j = this.pos[1] ; j < this.pos[1] + viewSize ; j++ )
                pane.getChildren().add( pane.getChildren().size() - 1 - GameGraphicalController.reservedShapes.size() , map[xAdd][j] ) ;
        } else {
            int yRemove = dy == 1 ? this.pos[1] : this.pos[1] + viewSize - 1 ;
            int yAdd = dy == -1 ? this.pos[1] - 1 : this.pos[1] + viewSize ;
            for( int i = this.pos[0] ; i < this.pos[0] + viewSize ; i++ )
                pane.getChildren().remove( map[i][yRemove] ) ;
            if( yAdd >= 0 && yAdd < size ) for( int i = this.pos[0] ; i < this.pos[0] + viewSize ; i++ )
                pane.getChildren().add( pane.getChildren().size() - 1 - GameGraphicalController.reservedShapes.size() , map[i][yAdd] ) ;
        }


        this.pos[0] += dx ;
        this.pos[1] += dy ;
        System.out.println( this.pos[0] ) ;
        System.out.println( this.pos[1] ) ;

        // moving to selected location
        for(int i = this.pos[0] ; i < Math.min ( size , this.pos[0] + viewSize ) ; i++){
            for( int j = this.pos[1] ; j < Math.min ( this.pos[1] + viewSize , size ) ; j++ ){
                int I = i - this.pos[0] ;
                int J = j - this.pos[1] ;
                int x = ( I - J ) * ( TILE_WIDTH / 2 + 1 ) + 500 ;
                int y = ( I + J ) * ( TILE_HEIGHT / 2 + 1 ) - 270 ;
                map[i][j].setLayoutX( x ) ;
                map[i][j].setLayoutY( y ) ;
            }
        }
    }

    public int getX(){
        return this.pos[0] ;
    }

    public int getY(){
        return this.pos[1] ;
    }

}
