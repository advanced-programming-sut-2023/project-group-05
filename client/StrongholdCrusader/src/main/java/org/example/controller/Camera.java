package org.example.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.model.BuildingEnum;
import org.example.model.BuildingImages;
import org.example.model.Minimap;
import org.example.model.Player;
import org.example.model.animations.ClownAnimation;
import org.example.model.building.Building;
import org.example.model.unit.Unit;

import java.util.ArrayList;


public class Camera {
    // a brief view of the pane children :
    // {
    // all map[i][j]s ,
    // units & buildings in order of non-decreasing ( i + j ) ~ y ,
    // reserved nodes ( menus, ..., etc, things that should be on top of every thing
    // }
    private Polygon[][] map ; // the map that the camera is viewing
    private ArrayList<Polygon> buildings ; // buildings in our view
    private int[] pos ; // the position for reference cell
    private Pane pane ; // where we draw things
    private int size ;
    private final int TILE_HEIGHT = 22 ;
    private final int TILE_WIDTH = TILE_HEIGHT * 2 ;
    private final int viewSize = 50 ;
    private final int xShift = TILE_WIDTH * 11 ;
    private final int yShift = TILE_HEIGHT * 12 ;
    private GameController gameController;
    private int unitCount ;

    public Camera(Polygon[][] map, Pane pane, GameController gameController){
        this.map = map ;
        this.pane = pane ;
        this.pos = new int[]{0, 0};
        this.buildings = new ArrayList<>() ;
        this.gameController = gameController ;
        this.size = map.length ;
        this.unitCount = 0 ;
    }

    public Polygon[][] getMap(){
        return this.map ;
    }

    public int getViewSize(){
        return this.viewSize ;
    }

    public Polygon addBuilding(int row , int column , String chosenBuildingName){
        ImagePattern imagePattern = BuildingImages.getImagePattern( chosenBuildingName ) ;
        int width = BuildingEnum.getBuildingWidthByName( chosenBuildingName ) ;
        int height = BuildingEnum.getBuildingHeightByName( chosenBuildingName ) ;
        // fact : every building is in shape of a hexagon-like.
        int buildingSize = width ;
        int tallness = buildingSize ;
        // special cases :
        if( chosenBuildingName.equals("lookouttower") ) tallness = 8 ;
        if( chosenBuildingName.equals("perimetertower") ) tallness = 6 ;
        if( chosenBuildingName.equals("squaretower") ) tallness = 6 ;
        if( chosenBuildingName.equals("roundtower") ) tallness = 6 ;
        Polygon hexagon = new Polygon(
                0, 0,
                buildingSize * TILE_WIDTH / 2 , buildingSize * TILE_HEIGHT / 2,
                buildingSize * TILE_WIDTH / 2 , buildingSize * TILE_HEIGHT / 2 + tallness * TILE_HEIGHT,
                0 ,  buildingSize * TILE_HEIGHT + tallness * TILE_HEIGHT ,
                -buildingSize * TILE_WIDTH / 2 , buildingSize * TILE_HEIGHT / 2 + tallness * TILE_HEIGHT,
                -buildingSize * TILE_WIDTH / 2 , buildingSize * TILE_HEIGHT / 2
        ) ;
        Text text = new Text( "name :" + chosenBuildingName + "\nHP=" +
                GameGraphicalController.gameController.getGameMap().getCell(row,column).getBuilding().getHitPoint()
                + "\nrow=" + row + "\ncolumn=" + column+"\nlittle tornado in 5" ) ;
        Rectangle rect = new Rectangle( 0 , 0 , 190 , 140 ) ;
        text.setFont( new Font(20) ) ;
        text.setFill( Color.RED ) ;
        pane.getChildren().add( hexagon ) ;
        text.setVisible( false ) ;
        rect.setVisible( false ) ;
        text.setOpacity( 0.85 ) ;
        rect.setOpacity( 0.6 ) ;
        ClownAnimation animation = (new ClownAnimation( text , rect )) ;
        hexagon.setOnMouseMoved( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                text.setX( hexagon.getLayoutX() + mouseEvent.getX() + 5 ) ;
                text.setY( hexagon.getLayoutY() + mouseEvent.getY() + 20 ) ;
                rect.setX( hexagon.getLayoutX() + mouseEvent.getX() + 1 ) ;
                rect.setY( hexagon.getLayoutY() + mouseEvent.getY() ) ;
                text.setText( text.getText().replaceAll( "HP=\\d+" , "HP=" + GameGraphicalController.gameController.getGameMap().getCell(row,column).getBuilding().getHitPoint() ) ) ;
                if(!text.isVisible()){
                    text.setVisible( true );
                    rect.setVisible( true );
                    GameGraphicalController.getPane().getChildren().add( rect ) ;
                    GameGraphicalController.getPane().getChildren().add( text ) ;
                    animation.playFromStart();
                }
            }
        } ) ;
        hexagon.setOnMouseExited( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                text.setVisible( false ) ;
                rect.setVisible( false ) ;
                GameGraphicalController.getPane().getChildren().remove( rect ) ;
                GameGraphicalController.getPane().getChildren().remove( text ) ;
                animation.stop() ;
            }
        } );
        double x = map[row][column].getLayoutX() ;
        double y = map[row][column].getLayoutY() - TILE_HEIGHT * tallness ;
        hexagon.setLayoutX(x) ;
        hexagon.setLayoutY(y) ;
        hexagon.setFill( imagePattern ) ;
        buildings.add( hexagon ) ;
        return hexagon ;
    }

    public void draw(){

        Paint grassPaint = new ImagePattern( new Image( getClass().getResource("/images/tiles/grass.jpg").toExternalForm() ) ) ;
        Paint waterPaint = new ImagePattern( new Image( getClass().getResource("/images/tiles/water.jpg").toExternalForm() ) ) ;
        Paint groundPaint = new ImagePattern( new Image( getClass().getResource("/images/tiles/ground.jpg").toExternalForm() ) ) ;

        for(int i = 0 ; i < size ; i++){
            for( int j = 0; j < size ; j++ ){
                // highest point on the tile is ( x , y )
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
                        if(mouseEvent.getButton() == MouseButton.SECONDARY){
                            Player player = GameGraphicalController.getPlayer() ;
                            for(Unit unit : player.getSelectedUnits()) unit.getShape().setEffect(null) ;
                            player.getSelectedUnits().clear() ;

                            for( Shape shape : GameGraphicalController.selectedBuildingsShapes ){
                                shape.setEffect( null );
                            }
                            GameGraphicalController.selectedBuildingsShapes.clear() ;
                        }
                        else
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
                pane.getChildren().add( 0 , map[i][j] ) ;
            }
        for( int i = 0 ; i < viewSize ; i++)
            for( int j = 0 ; j < viewSize ; j++ )
                for( Unit unit : gameController.getGameMap().getCell( i , j ).units )
                    pane.getChildren().addAll( unit.getShape() , unit.getHealthBar() ) ;

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
                    units.add( unit.getHealthBar() ) ;
                }
                pane.getChildren().removeAll( units );
            }
            if( xAdd >= 0 && xAdd < size ) for( int j = this.pos[1]; j < this.pos[1] + viewSize; j++ ){
                ArrayList <Shape> units = new ArrayList<>() ;
                for( Unit unit : gameController.getGameMap().getCell( xAdd , j ).getUnits() ){
                    units.add( unit.getShape() ) ;
                    units.add( unit.getHealthBar() ) ;
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
                    units.add( unit.getHealthBar() ) ;
                }
                pane.getChildren().removeAll( units ) ;
            }
            if( yAdd >= 0 && yAdd < size ) for( int i = this.pos[0] ; i < this.pos[0] + viewSize ; i++ ){
                ArrayList <Shape> units = new ArrayList<>() ;
                for( Unit unit : gameController.getGameMap().getCell( i , yAdd ).getUnits() ){
                    units.add( unit.getShape() ) ;
                    units.add( unit.getHealthBar() ) ;
                }
                pane.getChildren().addAll( pane.getChildren().size() - 1 - GameGraphicalController.reservedShapes.size() , units ) ;
                // pane.getChildren().addAll( 0 , units ) ;
                pane.getChildren().add( 0 , map[i][yAdd] ) ;
            }
        }


        this.pos[0] += dx ;
        this.pos[1] += dy ;

        // moving to selected location
        for(Polygon building : buildings){
            building.setLayoutX( building.getLayoutX() + TILE_WIDTH * (-dx+dy) / 2 );
            building.setLayoutY( building.getLayoutY() + TILE_HEIGHT * (-dx-dy) / 2 );
        }

        for(int i = this.pos[0] ; i < Math.min ( size , this.pos[0] + viewSize ) ; i++)
            for( int j = this.pos[1] ; j < Math.min ( this.pos[1] + viewSize , size ) ; j++ ){
                // putting the tile on it's place
                int I = i - this.pos[0] ;
                int J = j - this.pos[1] ;
                // there was initially a + 1 after TILE_WIDTH / 2 and TILE_HEIGHT / 2 due debugging, which is removed.
                // you can add it if needed and change back TILE_WIDTH and TILE_HEIGHT to 20 and 40
                int x = ( I - J ) * ( TILE_WIDTH / 2) + xShift ;
                int y = ( I + J ) * ( TILE_HEIGHT / 2 ) - yShift ;
                map[i][j].setLayoutX( x ) ;
                map[i][j].setLayoutY( y ) ;
                // putting the units of that tile there
                for( Unit unit : gameController.getGameMap().getCell( i , j ).getUnits() ){
                    unit.getShape().setX( x - unit.getShape().getWidth() / 2 ) ;
                    unit.getShape().setY( y + TILE_HEIGHT / 2 - unit.getShape().getHeight() )  ;
                }

            }

    }

    public double[] getUnitCoordinates(int x, int y, Unit unit){
        int I = x - this.pos[0] ;
        int J = y - this.pos[1] ;
        return new double[]{( I - J ) * ( TILE_WIDTH / 2 ) + xShift - unit.getShape().getWidth() / 2 ,
                            ( I + J ) * ( TILE_HEIGHT / 2 ) - yShift + TILE_HEIGHT / 2 - unit.getShape().getHeight() };
    }

    public int[] getMapPos( double x , double y ){
        int[] ret = new int[2] ;

        return ret ;
    }

    public int[] getPos(){
        return this.pos ;
    }

    // minimap related methods

    private Minimap minimap ;
    public void setMinimap(Minimap minimap){
        this.minimap = minimap ;
        GameGraphicalController.minimap = minimap ;
    }

    private void moveMinimap(int dx, int dy){
        if(minimap == null) return;
        minimap.move(dx, dy) ;
    }

}
