package View;

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
        new ServerMainMenu().start(stage);
        /// new ErrorWindow().start(new Stage());
    }
}
