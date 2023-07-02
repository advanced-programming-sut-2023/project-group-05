package view;

import controller.PortDistributor;
import controller.LoginRegisterThread;
import controller.ChatController;
import controller.GameRoomController;
import model.Chat;
import model.DataBase;
import model.GameRoomConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    /*
    * port list
    * 2019 : asking for free port ( for GameConnectionReader/Writer class in client )
    * 2020 : asking for register/login/forget/getInfo
    * 2021+: for chatting
    * 4000+: for the actual game
    *
    * */
    public static Stage stage;
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage _stage) throws Exception {
        DataBase.wakeUp() ;
        stage = _stage;
        (new PortDistributor()).start() ;
        (new LoginRegisterThread()).start() ;
        ChatController.AddChat(new Chat("Ali", "ghasem"));
        GameRoomController.AddServer(new GameRoomConnection("Test", "1234", 8888, 8));
        new MainMenu().start(stage);
    }
}
