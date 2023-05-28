package org.example.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.controller.SecurityQuestions;
import org.example.controller.SignupLoginMenuController;
import org.example.model.Account;

public class ForgetPassword extends Application {
    //TODO : CAPTCHA
    public TextField username;
    public Text securityText;
    public TextField answerTextField;
    public Text alertText;
    public BorderPane mainPane;
    public VBox vbox;

    @Override
    public void start(Stage stage) throws Exception {
        Hash.setMOD(1000000007);
        Account account = new Account("amin","bakeri","moaminkiani1@gmail.com",Hash.encode("danial"),0,"man",1,1);
        BorderPane borderPane = FXMLLoader.load(StartMenu.class.getResource("/fxml/ForgetPassword.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public void update(KeyEvent keyEvent) {
        if (Account.getAccountsMap().get(username.getText()) != null) {
            alertText.setVisible(false);
            securityText.setText(SecurityQuestions.questions.get((int) Account.getAccountsMap().get(username.getText()).getQuestion()));
            securityText.setVisible(true);
            answerTextField.setVisible(true);
        } else {
            alertText.setVisible(true);
            securityText.setVisible(false);
            answerTextField.setVisible(false);
        }
    }

    public void confirm(MouseEvent mouseEvent) {
        long answer = Account.getAccountsMap().get(username.getText()).getAnswer();
        if (answerTextField.getText().equals(Integer.toString((int) answer))) {
            Pane resetPassword = new Pane();
            resetPassword.setPrefHeight(200);
            resetPassword.setPrefWidth(200);
            resetPassword.setOpacity(0.8);
            VBox vBox = new VBox();
            vBox.setSpacing(20);
            vBox.setAlignment(Pos.CENTER);

            Text warnText = new Text();
            warnText.setFill(Color.RED);
            warnText.setVisible(false);

            TextField newPassword = new TextField();
            newPassword.setPromptText("Enter Your New Password");
            newPassword.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (!SignupLoginMenuController.validPassword(newPassword.getText())) {
                        warnText.setVisible(true);
                        warnText.setText("Password Invalid");
                    }
                    else{
                        warnText.setVisible(false);
                    }
                }
            });

            vBox.getChildren().addAll(newPassword,warnText);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(20);
            Button backButton = new Button();
            backButton.setText("Back");
            backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        new LoginMenu().start(StartMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            Button confirmButton = new Button();
            confirmButton.setText("Confirm");
            confirmButton.setTextFill(Color.BLACK);
            confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (SignupLoginMenuController.validPassword(newPassword.getText())) {
                        Account.getAccountsMap().get(username.getText()).setPassword(Hash.encode(newPassword.getText()));
                        try {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Password Changed Successfully");
                            alert.show();
                            new LoginMenu().start(StartMenu.stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            hBox.getChildren().addAll(backButton,confirmButton);
            vBox.getChildren().add(hBox);
            resetPassword.getChildren().add(vBox);
            this.vbox.getChildren().add(resetPassword);
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(StartMenu.stage);
    }
}
