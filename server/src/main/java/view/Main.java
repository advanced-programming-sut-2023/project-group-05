package view;

import com.google.gson.Gson;
import controller.ChatThread;
import controller.LoginRegisterThread;
import controller.GameRoomController;
import model.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static Stage stage;
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage _stage) throws Exception {
        DataBase.wakeUp();
        ChatLog.WAKEUP();
        stage = _stage;
        (new LoginRegisterThread()).start() ;
        (new ChatThread()).start();

        /// System.out.println((new ChatPacket("Me", "Suck My Cock", "You", "{'size':'big'}")).toJson());

        /*Chat chat = new Chat("Ariya", "Danial");
        Chat chat1 = new Chat("Kiani", "Yazid");
        chat.AddMessage(new Message("Ariya", "kir!"));
        chat1.AddMessage(new Message("Kiani", "alat be kamat"));
        ChatLog.AddChat(chat);
        ChatLog.AddChat(chat1);
         */
        /// new Account("Ariya", "AH", "prefix.suffix.aria@gmail.com", 1010, 10, "KIR", 0, 0);
        GameRoomController.AddServer(new GameRoomConnection("Test", "1234", 8888, 8));
        /// new MainMenu().start(stage);
    }
}
