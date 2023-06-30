package view;

import controller.ChatController;
import controller.GameRoomController;
import model.Chat;
import model.GameRoomConnection;
import javafx.application.Application;
import javafx.stage.Stage;

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
