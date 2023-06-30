package View;

import Controller.ServerController;
import Controller.URLFinder;
import Model.ServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ServerMainMenu extends Application
{
    public static int limit = 2;
    public static Stage stage;
    public TextField serverName;
    public TextField portNumber;
    public TextField serverPassword;
    public TextField maxCapacity;

    @Override
    public void start(Stage _stage) throws Exception
    {
        stage = _stage;
        stage.setTitle("Server Main Menu");
        BorderPane curPane = FXMLLoader.load(URLFinder.run("/fxml/ServerMainMenu.fxml"));
        Scene scene = new Scene(curPane);
        stage.setScene(scene);
        stage.show();
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
        int port = Integer.parseInt(portNumber.getText());
        int max = Integer.parseInt(maxCapacity.getText());
        if(port > 65535 || port <= 0)
        {
            new ErrorWindow("Port Number Out of Range", "Ok").start(new Stage());
            return;
        }
        if(max > 8 || max < 1)
        {
            new ErrorWindow("Server Capacity is Invalid", "Retry!").start(new Stage());
            return;
        }
        ServerConnection serverConnection = new ServerConnection(serverName.getText(), serverPassword.getText(), port, max);
        if(ServerController.AddServer(serverConnection))
        {
            /// System.out.println("Server Created Successfully");
            ServerController.serverStartGameMenu = new ServerStartGameMenu(serverConnection);
            ServerController.serverStartGameMenu.start(stage);
        }
        else
        {
            new ErrorWindow("This Server Name Already Exists", "Change Server Name").start(new Stage());
        }
    }
}
