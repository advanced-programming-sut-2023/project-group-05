package View;

import Controller.ChatController;
import Controller.GameRoomController;
import Model.Chat;
import Model.GameRoomConnection;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Map;

public class Main extends Application
{
    public static Stage stage;
    public static void Main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage _stage) throws Exception
    {
        stage = _stage;
        ChatController.AddChat(new Chat("Ali", "ghasem"));
        GameRoomController.AddServer(new GameRoomConnection("Test", "1234", 8888, 8));
        /// new CreateRoomMenu().start(stage);
        /// new ErrorWindow().start(new Stage());
        new MainMenu().start(stage);
    }
}
