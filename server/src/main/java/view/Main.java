package view;


import controller.PortDistributor;
import controller.LoginRegisterThread;
import controller.GameRoomController;
import model.DataBase;
import model.GameRoomConnection;
import controller.*;
import model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.zip.CheckedInputStream;

public class Main extends Application
{
    /*
    * port list
    * 2019 : asking for free port ( for GameConnectionReader/Writer class in client )
    * 2020 : asking for register/login/forget/getInfo/getScoreBoard/changeAccount
    * 3051+: for chatting
    * 4000+: for the actual game
    *
    * */
    public static Stage stage;
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage _stage) throws Exception
    {
        DataBase.wakeUp();
        ChatLog.WAKEUP();
        stage = _stage;
        (new PortDistributor()).start() ;
        (new LoginRegisterThread()).start() ;
        (new ChatPortDistributor()).start() ;
        GameRoomController.AddServer(new GameRoomConnection("Test", "1234", 8888, 8));

        /*FriendsDB.AddNewUser("Ariya");
        FriendsDB.AddNewUser("Danial");
        FriendsDB.AddNewUser("Kiani");

        FriendsDB.AddFriend("Ariya", "Danial");

        FriendsDB.AddRequest("Ariya", "Kiani");

        FriendsDB.AddRequest("Kiani", "Danial");
        System.out.println(FriendsDB.getFriendList("Ariya"));
        System.out.println(FriendsDB.getSentList("Ariya"));
        System.out.println(FriendsDB.getReceivedList("Ariya"));

        System.out.println(FriendsDB.getFriendList("Danial"));
        System.out.println(FriendsDB.getSentList("Danial"));
        System.out.println(FriendsDB.getReceivedList("Danial"));

        System.out.println(FriendsDB.getFriendList("Kiani"));
        System.out.println(FriendsDB.getSentList("Kiani"));
        System.out.println(FriendsDB.getReceivedList("Kiani"));
         */

        new MainMenu().start(stage);
    }
}
