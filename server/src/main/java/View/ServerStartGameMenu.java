package View;

import Controller.ServerController;
import Controller.URLFinder;
import Model.ClientConnection;
import Model.ServerConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerStartGameMenu extends Application
{
    public BorderPane curPane;
    public VBox vbox;
    ServerConnection serverConnection;
    public ServerStartGameMenu(ServerConnection _serverConnection)
    {
        serverConnection = _serverConnection;
    }

    public void getVbox(VBox vBox)
    {
        vBox.getChildren().clear();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        for(int i = 0; i < serverConnection.getClients().size(); i ++)
        {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(15);
            ClientConnection clientConnection = serverConnection.getClients().get(i);
            Label label = new Label("Client " + i + ": " + clientConnection.userName + " joined the served");
            /// System.out.println(label.getText());
            Button button = new Button("Kick This Client");
            hBox.getChildren().addAll(label, button);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    ServerController.RemoveClient(clientConnection);
                    vBox.getChildren().remove(hBox);
                }
            });
            vBox.getChildren().add(hBox);
        }

        Button button = new Button("Start The Game");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if(serverConnection.getClients().isEmpty())
                {
                    try {
                        new ErrorWindow("There Are No Clients To Start The Game", "I'll Wait!").start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Start The Game !!");
            }
        });
        Button TestButton = new Button("Add New Virtual Client");
        TestButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ServerController.AddClient(new ClientConnection("Amir", serverConnection.getServerName(), serverConnection.getServerPort()));
            }
        });
        vBox.getChildren().addAll(button, TestButton);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setWidth(500);
        stage.setHeight(600);
        serverConnection.AddNewClient(new ClientConnection("Master", serverConnection.getServerName(), serverConnection.getServerPort()));
        serverConnection.AddNewClient(new ClientConnection("Slave", serverConnection.getServerName(), serverConnection.getServerPort()));
        stage.setTitle("Start Game Menu");
        curPane = new BorderPane();
        this.vbox = new VBox();
        getVbox(this.vbox);
        curPane.setCenter(vbox);
        Scene scene = new Scene(curPane);
        stage.setScene(scene);
        stage.show();
    }
}
