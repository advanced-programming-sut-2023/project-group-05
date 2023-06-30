package view;

import controller.GameRoomController;
import model.ClientConnection;
import model.GameRoomConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameRoomMenu extends Application
{
    public BorderPane curPane;
    public VBox vbox;
    GameRoomConnection serverConnection;

    public GameRoomMenu( GameRoomConnection _serverConnection)

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
            hBox.setStyle( "-fx-border-color: black ; -fx-max-width: 400" );
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(15);
            ClientConnection clientConnection = serverConnection.getClients().get(i);
            Label label = new Label("Client " + i + ": " + clientConnection.userName + " joined the served");
            /// System.out.println(label.getText());
            Button button = new Button("Kick This Client");
            button.setStyle( "-fx-background-color: black ; -fx-text-fill: red " ) ;
            hBox.getChildren().addAll(label, button);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    GameRoomController.RemoveClient(clientConnection);
                    vBox.getChildren().remove(hBox);
                }
            });
            vBox.getChildren().add(hBox);
        }

        Button startButton = new Button("Start The Game");
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        vBox.getChildren().add( startButton ) ;
    }

    public void addClient(){

    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setWidth(500);
        stage.setHeight(600);
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
