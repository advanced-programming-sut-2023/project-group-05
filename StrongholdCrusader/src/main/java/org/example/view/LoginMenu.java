package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.model.Account;

public class LoginMenu extends Application {
    public TextField username;
    public PasswordField password;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(StartMenu.class.getResource("/fxml/LoginMenu.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public void forgetPassword(MouseEvent mouseEvent) throws Exception {
        new ForgetPassword().start(StartMenu.stage);
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        Account account = Account.getAccountsMap().get(username.getText());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        if (account== null)
        {
            alert.setContentText("No Such Username Exists");
            alert.showAndWait();
        }
        else if (Hash.encode(password.getText()) != account.getPasswordHash())
        {
            alert.setContentText("Wrong Password");
            alert.showAndWait();
        }
        //TODO : CAPTCHA
        else
        {
             new MainMenu().start(StartMenu.stage);
        }

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new StartMenu().start(StartMenu.stage);
    }
}
