package View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorWindow extends Application
{
    public String labelText = "Wrong Input";
    public String buttonText = "Ok I Will Try Again";

    ErrorWindow ()
    {

    }

    ErrorWindow (String _labelText, String _buttonText)
    {
        labelText = _labelText;
        buttonText = _buttonText;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Invalid Input");
        stage.setHeight(300);
        stage.setWidth(300);
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        Label label = new Label(labelText);
        Button button = new Button(buttonText);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                stage.close();
            }
        });
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(label, button);
        borderPane.setCenter(vBox);
        stage.setScene(new Scene(borderPane));
        stage.show();
    }
}
