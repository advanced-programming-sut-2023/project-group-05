package org.example.model;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.example.Main;
import org.example.controller.Camera;
import org.example.controller.GameController;

public class Minimap extends Group {

    private Camera camera ;
    private GameController gameController ;
    private Group viewGroup ;
    private double size = 150 ;
    private Rectangle minimapRectangle ;

    public Minimap( Camera camera, GameController gameController ){
        this.camera = camera ;
        camera.setMinimap( this ) ;
        this.gameController = gameController ;
        minimapRectangle = new Rectangle(0, 0, size, size) ;
        minimapRectangle.setFill( new ImagePattern( new Image( Main.class.getResource("/images/MenuBackGrounds/map.png").toExternalForm() ) ) ) ;
        setMouseClick() ;
        this.getChildren().add( minimapRectangle ) ;

        // adding four thin red lines for showing the view, I'm calling them the viewGroup
        int length = (int) ( camera.getViewSize() * this.size / 400 ) ;
        int width = 1 ;
        Rectangle line0 = new Rectangle(0, 0, length, width) ;
        Rectangle line1 = new Rectangle(0, width, width, length - width * 2) ;
        Rectangle line2 = new Rectangle(0, length - width, length, width) ;
        Rectangle line3 = new Rectangle(length - width, width, width, length - width * 2) ;
        viewGroup = new Group(line0, line1, line2, line3) ;

        for(Node rect : viewGroup.getChildren()){
            ((Rectangle)rect).setFill( Color.RED ) ;
        }
        this.getChildren().add( viewGroup ) ;

        this.draw() ;
    }

    public double getSize(){
        return this.size ;
    }

    public void move(int dx, int dy){
        viewGroup.setLayoutX( viewGroup.getLayoutX() + (double)dx * size / 400 );
        viewGroup.setLayoutY( viewGroup.getLayoutY() + (double)dy * size / 400 );
    }

    private void draw(){
        // here we will draw on the minimap rectangle
        // TODO : when you are finished with the map you can just load some png
        //        right now there is nothing here :)
    }

    private void setMouseClick(){
        this.minimapRectangle.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                int clickedColumn = (int) ( mouseEvent.getY() / size * 400 ) ;
                int clickedRow = (int) ( mouseEvent.getX() / size * 400 ) ;
                clickedRow = Math.max( 0, clickedRow - camera.getViewSize() / 2 ) ;
                clickedColumn = Math.max( 0, clickedColumn - camera.getViewSize() / 2 ) ;
                clickedRow = Math.min( clickedRow, 400 - camera.getViewSize() ) ;
                clickedColumn = Math.min( clickedColumn, 400 - camera.getViewSize() ) ;
                // camera move :
                // row+ : 00
                // row- : 01
                // col+ : 10
                // col- : 11
                // TODO : this is actually slow, optimize it later
                //        ( I know I'm talking to my future self, why are doing this Danial? )
                while(camera.getPos()[0] != clickedRow){
                    int moveMask = ( camera.getPos()[0] > clickedRow ? 1 : 0 ) ;
                    camera.move( moveMask ) ;
                }
                while(camera.getPos()[1] != clickedColumn){
                    int moveMask = ( camera.getPos()[1] > clickedColumn ? 3 : 2 ) ;
                    camera.move( moveMask ) ;
                }
            }
        } ) ;
    }

}
