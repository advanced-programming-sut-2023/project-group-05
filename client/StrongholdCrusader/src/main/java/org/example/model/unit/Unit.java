package org.example.model.unit;

import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.controller.GameGraphicalController;
import org.example.controller.PathFinder;

import org.example.model.*;
import org.example.model.animations.AttackingAnimation;
import org.example.model.animations.WalkingAnimation;
import org.example.model.animations.gettingDamageAnimation;
import org.example.model.building.Building;
import org.example.model.enums.UnitImagesEnum;
import org.example.view.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    private WalkingAnimation playingWalkingAnimation ;
    protected PathFinder pathFinder ;
    protected boolean isOnHighGround ;
    private final String name ;
    private int hitPoint;
    private final int movingSpeed ;
    private final int initialHitpoint ;
    private final Player owner ;
    private final int range ;
    private Cost cost ;
    public static ArrayList <Unit> units = new ArrayList<Unit>() ;
    private UnitModeEnum unitMode ;
    // TODO : private Building building ;
    protected boolean isMoving ;
    public static double UNIT_SIZE = 40 ;

    protected int row ;
    protected int column ;

    private static HashMap<String , UnitTypeEnum> unitTypeEnumMap = new HashMap<>();
    private static HashMap<String , UnitModeEnum> unitModeEnumMap = new HashMap<>();

    private boolean selectable ;
    protected int targetRow = -1 ;
    protected int targetColumn = -1 ;
    private Rectangle shape ;
    private Rectangle healthBar ;
    private Text HPText = new Text() ;

    int[][] adjacencyArray = { { 0 , -1 } , { 1 , 0 } , { -1 , 0 } , { 0 , 1 } } ;
    private static GameMap gameMap ;

    public Unit( String name , Player owner , int hitPoint,int movingSpeed , int range , int row , int column , boolean selectable ){
        this.name = name ;
        this.hitPoint = hitPoint;
        this.owner = owner ;
        this.row = row ;
        this.column = column ;
        this.isOnHighGround = false ;
        this.selectable = selectable ;
        this.movingSpeed = movingSpeed ;
        this.range = range ;
        this.initialHitpoint = this.hitPoint ;
        units.add(this) ;
        owner.setPopulation( owner.getPopulation() + 1 );
        owner.getUnits().add(this) ;
        pathFinder = new PathFinder() ;
    }

    public static void setGameMap( GameMap gameMap ){
        Unit.gameMap = gameMap ;
    }

    public void setPathFinder(GameMap gameMap){
        pathFinder.setGameMap( gameMap.getMaskedMap() , gameMap.getMaskedMapUpperGround() , 400 ) ;
    }

    public Rectangle getShape(){
        return this.shape ;
    }
    public Rectangle getHealthBar(){ return this.healthBar ;}

    public void setShape(){
        this.shape = new Rectangle( 0 , 0 , UNIT_SIZE , UNIT_SIZE ) ;
        this.healthBar = new Rectangle( 0 , 0 , 15 , 2 ) ;
        healthBar.setFill( Color.GREEN ) ;
        healthBar.setVisible( false ) ;
        Image image = null ;

        switch( this.name ){
            case "archer" :
                image = UnitImagesEnum.SWORDSMAN.image ;
                break ;
            case "swordsman" :
                image = UnitImagesEnum.SWORDSMAN.image ;
                break ;
            case "operator" :
                image = UnitImagesEnum.SWORDSMAN.image ;
                break ;

                // TODO : complete
        }

        this.shape.setWidth( 15 ) ;
        this.shape.setHeight( this.shape.getWidth() * image.getHeight() / image.getWidth() ) ;
        this.shape.setFill( new ImagePattern( image ) ) ;

        // setting getting selected
        Player player = this.owner ;
        Unit unit = this ;
        this.shape.setOnMouseClicked( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                if( mouseEvent.getButton() != MouseButton.PRIMARY ) return ;
                if( player == GameGraphicalController.getPlayer() ){
                    player.selectUnit( unit );
                    Glow glow = new Glow() ;
                    glow.setLevel( 200 );
                    unit.getShape().setEffect( glow ) ;
                    return ;
                }
                if( GameGraphicalController.attackingMouse.isVisible() ){
                    for( Unit u : GameGraphicalController.getPlayer().getSelectedUnits() ){
                        u.moveToAttackTo( unit ) ;
                    }
                }
            }
        } ) ;
        Unit finalThis = this ;
        Rectangle rect = new Rectangle( 0 , 0 , 110 , 40 ) ;
        HPText.setText( "HP=" + this.hitPoint+ "\n" + this.name );
        HPText.setStyle( "-fx-font-weight: bold" ) ;
        rect.setVisible( false ) ;
        rect.setFill( Color.PURPLE ) ;
        rect.setOpacity( 0.5 ) ;
        HPText.setVisible( false ) ;
        this.shape.setOnMouseMoved( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                healthBar.setVisible( true ) ;
                healthBar.setX( finalThis.getShape().getX() ) ;
                healthBar.setY( finalThis.getShape().getY() - 5 ) ;
                if( !rect.isVisible() ){
                    rect.setVisible( true ) ;
                    HPText.setVisible( true ) ;
                    GameGraphicalController.getPane().getChildren().add( rect );
                    GameGraphicalController.getPane().getChildren().add( HPText );
                }
                rect.setX( mouseEvent.getX() + 16 ) ;
                HPText.setY( mouseEvent.getY() + 15 ) ;
                HPText.setX( mouseEvent.getX() + 20 ) ;
                rect.setY( mouseEvent.getY() + 1 ) ;
                GameGraphicalController.attackingMouse.setX( mouseEvent.getX() - GameGraphicalController.attackingMouse.getWidth() - 1 ) ;
                GameGraphicalController.attackingMouse.setY( mouseEvent.getY() - GameGraphicalController.attackingMouse.getHeight() - 1 ) ;
                if( owner != GameGraphicalController.getPlayer() && !GameGraphicalController.getPlayer().getSelectedUnits().isEmpty() )
                    GameGraphicalController.attackingMouse.setVisible( true ) ;

            }
        } ) ;
        this.shape.setOnMouseExited( new EventHandler <MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ){
                healthBar.setVisible( false ) ;
                HPText.setVisible( false ) ;
                rect.setVisible( false ) ;
                GameGraphicalController.getPane().getChildren().removeAll( rect , HPText ) ;
                GameGraphicalController.attackingMouse.setVisible( false ) ;
            }
        } );
    }

    private ArrayList<WalkingAnimation> walkingAnimations ;

    public void setWalkingAnimations(){
        walkingAnimations = new ArrayList <>() ;
        walkingAnimations.add( new WalkingAnimation( "swordsman/up" , 8 , this , 1000 ) ) ;
        walkingAnimations.add( new WalkingAnimation( "swordsman/down" , 8 , this , 1000 ) ) ;
        walkingAnimations.add( new WalkingAnimation( "swordsman/right" , 8 , this , 1000 ) ) ;
        walkingAnimations.add( new WalkingAnimation( "swordsman/left" , 8 , this , 1000 ) ) ;
    }

    public ArrayList<WalkingAnimation> getWalkingAnimations(){
        return this.walkingAnimations ;
    }

    public boolean getSelectable(){
        return this.selectable ;
    }

    public boolean getIsOnHighGround(){
        return this.isOnHighGround ;
    }

    public void setIsOnHighGround(boolean highGround){
        this.isOnHighGround = highGround ;
    }
    public int walkingReason = -1 ; // 0 : move , 1 : attack
    public Unit enemy = null ;

    public void die(GameMap gameMap){
        this.owner.getSelectedUnits().remove(this) ;
        this.owner.getUnits().remove(this) ;
        this.owner.setPopulation( this.owner.getPopulation() - 1 );
        Unit.getUnits().remove(this) ;
        gameMap.getCell(this.getRow() , this.getColumn()).getUnits().remove(this) ;
        GameGraphicalController.middleNode.getChildren().remove( this.shape ) ;
        GameGraphicalController.middleNode.getChildren().remove( this.healthBar ) ;
        GameGraphicalController.camera.removeUnit( this ) ;
    }

    public void getDamaged(int x , GameMap gameMap){
        this.hitPoint -= x ;
        this.HPText.setText( "HP=" + this.hitPoint + "\n" + this.name ) ;
        this.healthBar.setWidth( 15 * this.hitPoint / this.initialHitpoint ) ;
        if(this.hitPoint <= 0)
            this.die(gameMap) ;
        //( new gettingDamageAnimation( shape ) ).playFromStart() ;
    }

    public boolean moveTo(int x, int y){
        if( this.playingWalkingAnimation != null ) this.playingWalkingAnimation.stop() ;
        this.isMoving = true ;
        pathFinder.Run(x, y) ;
        targetColumn = y ;
        targetRow = x ;
        int dir = pathFinder.goInDirectionFrom( this.row , this.column ) ;
        if(dir == -1){
            this.targetColumn = this.column ;
            this.targetRow = this.row ;
            return false ;
        }
        walkingReason = 0 ;
        this.playingWalkingAnimation = this.walkingAnimations.get(dir) ;
        this.walkingAnimations.get(dir).play() ;
        return true ;
    }

    public void moveToAttackTo( Unit unit ){ // this will move until the enemy is in range
        boolean canAttack = false ;
        for(int i = 0 ; i < 4 ; i++){
            if( moveTo(adjacencyArray[i][0] + unit.getRow() , adjacencyArray[i][1] + unit.getColumn() ) ){
                canAttack = true ;
                break ;
            }
        }
        if( !canAttack ) return ;
        walkingReason = 1 ;
        enemy = unit ;
    }

    public void attack(){ // this will deal damage to the enemy in range
        boolean canAttack = false ;
        for( int i = 0 ; i < 4 ; i++){
            if( enemy.getHitPoint() > 0 && this.hitPoint > 0 && this.row == enemy.getRow() + adjacencyArray[i][0] && this.column == enemy.getColumn() + adjacencyArray[i][1] ){
                canAttack = true ;
                break ;
            }
        }
        if(!canAttack) return ;
        enemy.getDamaged( 10 , gameMap ) ;
        if( enemy.getHitPoint() <= 0 ){
            enemy = null ;
            return ;
        } else if( enemy.enemy != this ) {
            enemy.enemy = this ;
            enemy.attack() ;
        }
        System.out.println("attacking");
        (new AttackingAnimation( this , enemy )).playFromStart(); ;
    }

    public void moveToIfNeeded(){
        if( targetColumn != this.column && targetRow != this.row ){
            System.out.println( "here" );
            moveTo(targetRow, targetColumn);
        }
    }

    public static ArrayList<Unit> getUnits(){
        return units ;
    }

    public void setTarget( int row , int column , GameMap gameMap ){
        if(this.row == row && this.column == column){
            this.isMoving = false ;
        }
        this.targetRow = row ;
        this.targetColumn = column ;
        // pathFinder.setGameMap( gameMap.getMaskedMap() , gameMap.getMaskedMapUpperGround() , 400 ) ;
        pathFinder.Run( row , column ) ;
        this.isMoving = true;
    }

    public int getNextRow(){
        int nextMoveMask = pathFinder.goInDirectionFrom(this.row , this.column)  ;
        /// up, down, left, right (0, 1, 2, 3) and -1 if it is fucked!
        switch(nextMoveMask){
            case 0 :
                return this.row - 1 ;
            case 1 :
                return this.row + 1  ;
            case 2 :
            case 3 :
                return this.row ;
        }
        return this.row ;
    }

    public int getNextColumn(){
        int nextMoveMask = pathFinder.goInDirectionFrom(this.row , this.column)  ;
        /// up, down, left, right (0, 1, 2, 3) and -1 if it is fucked!
        switch(nextMoveMask){
            case 0 :
            case 1 :
                return this.column ;
            case 2 :
                return this.column -1 ;
            case 3 :
                return this.column + 1 ;
        }
        return this.column ;
    }


    public ArrayList<Building> getAdjacantBuildings(GameMap gameMap){
        ArrayList<Building> ret = new ArrayList<Building>() ;
        int[][] adj = { {0,1} , {0,-1} , {1,0} , {-1,0} } ;
        for(int i = 0 ; i < 4 ; i++){
            Building building = gameMap.getCell(this.row+adj[i][0] , this.column+adj[i][1]).getBuilding() ;
            if(null != building)
                ret.add(building) ;
        }
        return ret ;
    }

    public int getHitPoint(){
        return this.hitPoint ;
    }

    public int getTargetColumn(){
        return this.targetColumn ;
    }

    public int getTargetRow(){
        return this.targetRow ;
    }

    public boolean getIsMoving(){
        return this.isMoving ;
    }

    public static HashMap<String,UnitTypeEnum> getUnitTypeEnumMap(){
        return unitTypeEnumMap ;
    }

    public void setIsMoving( boolean isMoving ){
        this.isMoving = isMoving ;
        if(this.isMoving==false){
            this.targetColumn = -1 ;
            this.targetRow = -1 ;
        }
    }

    public UnitModeEnum getUnitMode(){
        return this.unitMode ;
    }
    public void setUnitMode (UnitModeEnum unitMode){
        this.unitMode = unitMode;
    }

    public Cost getCost(){
        return this.cost ;
    }

    public int getRange(){
        return this.range ;
    }

    public Player getOwner(){
        return this.owner ;
    }

    public int getMovingSpeed(){
        return this.movingSpeed ;
    }

    public String getName(){
        return this.name ;
    }


    public static HashMap<String,UnitModeEnum> getUnitModeEnumMap() {
        return unitModeEnumMap ;
    }

    public static Unit createUnitByName(String type,Player owner,int row , int column){
        UnitTypeEnum unitType = UnitTypeEnum.getUnitTypeByName(type);
        Unit newUnit = null ;
        if (unitType == UnitTypeEnum.ARCHER)
            newUnit = new Warrior(type,owner,10,100,10,20,10,10,10,true,false,false,false,false,false,false,row , column);
        if (unitType == UnitTypeEnum.CROSSBOWMEN)
            newUnit = new Warrior(type,owner,10,150,7,7,15,15,7,false,false,false,false,false,false,false,row,column);
        if (unitType == UnitTypeEnum.SPEARMEN)
            newUnit = new Warrior(type,owner,10,50,15,10,0,3,10,false,true,false,true,false,false,true,row,column);
        if (unitType == UnitTypeEnum.PIKEMEN)
            newUnit = new Warrior(type,owner,10,50,7,10,15,20,10,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.MACEMEN)
            newUnit = new Warrior(type,owner,10,150,15,10,20,13,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.SWORDSMEN)
            newUnit = new Warrior(type,owner,10,250,7,10,25,10,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.KNIGHT)
            newUnit = new Warrior(type,owner,10,250,20,10,25,20,5,false,false,true,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.TUNNELER)
            newUnit = new Tunneler(owner,5,row,column);
        if (unitType == UnitTypeEnum.LADDERMEN)
            newUnit = new LadderMan(owner,5,row,column);
        if (unitType == UnitTypeEnum.ENGINEER)
            newUnit = new Engineer(owner,5,row,column);
        if (unitType == UnitTypeEnum.BLACKMONK)
            newUnit = new Warrior(type,owner,10,50,7,5,10,10,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.ARCHERBOW)
            newUnit = new Warrior(type,owner,10,100,15,15,10,10,10,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.SLAVES)
            newUnit = new Warrior(type,owner,10,50,15,10,5,5,5,true,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.SLINGERS)
            newUnit = new Warrior(type,owner,10,50,15,5,7,5,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.ASSASSINS)
            newUnit = new Warrior(type,owner,10,150,10,10,10,10,10,false,false,false,false,true,true,true,row,column);
        if (unitType == UnitTypeEnum.HORSE_ARCHERS)
            newUnit = new Warrior(type,owner,10,100,25,12,10,15,10,false,false,true,false,false, false,false,row,column);
        if (unitType == UnitTypeEnum.ARABIAN_SWORDSMEN)
            newUnit = new Warrior(type,owner,10,200,20,5,20,20,5,false,false,false,false,false,false,true,row,column);
        if (unitType == UnitTypeEnum.FIRE_THROWERS)
            newUnit = new Warrior(type,owner,10,50,20,5,15,7,5,true,false,false,false,false,false,true,row,column);
        if (type.equals("jobless"))
            newUnit = new Jobless(owner , 2,row , column);
        if (type.equals("trap"))
            newUnit = new Warrior(type,owner,1000,5,0,1,1000,0,0,false,false,false,false,false,false,false,row,column);
        if (type.equals("manjenigh"))
            newUnit = new Warrior(type,owner,20,50,2,40,20,0,0,false,false,false,false,false,false,true,row,column);
        if (type.equals("dejkub"))
            newUnit = new Warrior(type,owner,20,50,4,1,20,0,0,false,false,false,false,false,false,true,row,column);
        if (type.equals("sangandazatashin"))
            newUnit = new Warrior(type,owner,20,50,2,10,20,0,0,false,false,false,false,false,false,true,row,column);

        if(type.equals("ox"))
            return new Unit("ox",owner,1,1,0,row,column,false) ;

        return newUnit ;
    }

    public static Cost getCostByName(String name){

        if(name.equals("archer")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 1 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("crossbowman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 1 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("spearman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 1 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("pikeman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                1 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("maceman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 1 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("swordsman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 1 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("knight")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 1 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("tunneler")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("ladderman")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("engineer")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("blackmonk")) return new Cost( 0 , 0 , 0 , 0 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("archerbow")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("slave")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("slinger")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("assassin")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("horsearcher")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("arabianswordsman")) return new Cost( 0 , 0 , 0 , 100 , 0,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;
        if(name.equals("firethrower")) return new Cost( 0 , 0 , 0 , 0 , 100,  0, 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0, 0 , 0,
                0 , 0 , 0 , 0 , 0, 0) ;

        return null ;
    }

    public int getRow(){
        return this.row ;
    }

    public int getColumn(){
        return this.column ;
    }

    public void setColumn(int column){
        this.column = column ;
    }

    public void setRow(int row){
        this.row = row ;
    }

}
