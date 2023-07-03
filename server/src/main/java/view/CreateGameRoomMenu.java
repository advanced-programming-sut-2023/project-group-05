package view;

import controller.GameMaster.GameRoom;
import controller.URLFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CreateGameRoomMenu extends Application
{
    public static int limit = 2;
    public static Stage stage;
    public TextField serverName;
    public TextField roomName;
    public TextField serverPassword;
    public TextField maxCapacity;

    @Override
    public void start(Stage _stage) throws Exception
    {
        stage = _stage;
        stage.setTitle("Server Main Menu");
        BorderPane curPane = FXMLLoader.load(URLFinder.run("/fxml/CreateGameRoomMenu.fxml"));
        Scene scene = new Scene(curPane);
        stage.setScene(scene);
        stage.show();
        curPane.requestFocus() ;
    }

    public void startTheServer(MouseEvent mouseEvent) throws Exception {
        /// System.out.println(serverName.getText() + " " + serverPassword.getText() + " " + portNumber.getText());
        if(serverName.getText().isEmpty())
        {
            new ErrorWindow("Empty Server Namme", "Ok").start(new Stage());
            return;
        }
        if(serverPassword.getText().length() < limit)
        {
            new ErrorWindow("Short Password", "Ok").start(new Stage());
            return;
        }
        int max = Integer.parseInt(maxCapacity.getText());
        if(max > 8 || max < 1)
        {
            new ErrorWindow("Server Capacity is Invalid", "Retry!").start(new Stage());
            return;
        }

        if( GameRoom.gameRoomHashMap.get(roomName.getText()) == null ){
            new GameRoom( roomName.getText() , serverPassword.getText() , max ) ;
            new MainMenu().start(stage) ;
        } else {
            Alert alert = new Alert( Alert.AlertType.ERROR) ;
            alert.setContentText( "this server name is already taken, please choose another one." );
            alert.setTitle( "invalid name" ) ;
            alert.showAndWait() ;
        }

    }
}
