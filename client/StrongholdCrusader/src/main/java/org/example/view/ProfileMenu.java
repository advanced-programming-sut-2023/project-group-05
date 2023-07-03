package org.example.view ;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.controller.GameController;
import org.example.controller.SignupLoginMenuController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProfileMenu extends Application {
    private String username = GameController.currentUsername ;
    String nickname = GameController.currentNickname ;
    String email = GameController.currentEmail ;
    Image image = null ;
    private static Stage stage;
    private static BorderPane bufferPane;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Text welcome = new Text("Welcome To Profile Menu");
        welcome.setStyle("-fx-font-size: 32px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #05c2c7;");
        BorderPane pane = new BorderPane();
        BorderPane bufferPane = new BorderPane();
        welcome.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(welcome);
        bufferPane.getChildren().add(welcome);
        ProfileMenu.stage = stage;
        ProfileMenu.bufferPane = bufferPane;
        VBox data = new VBox();
        VBox change = new VBox();
        HashMap<Button, Runnable> setOn = new HashMap<>();
        Scene scene = new Scene(pane);
        ProfileMenu.scene = scene;
        initData(data);
        initButtons(setOn, change, pane);
        pane.getChildren().addAll(change, data);
        bufferPane.getChildren().addAll(pane.getChildren());
        stage.setScene(scene);
        stage.show();
    }

    private void initButtons(HashMap<Button, Runnable> setOn, VBox change, BorderPane pane) {
        Button changeUsername = new Button();
        Button changeNickname = new Button();
        Button changeEmail = new Button();
        Button changeAvatar = new Button();
        Button changePassword = new Button();
        Button changeSlogan = new Button();
        {
            setOn.put(changeUsername, () -> {
                try {
                    changeThings(pane, 1);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
            setOn.put(changeNickname, () -> {
                try {
                    changeThings(pane, 2);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
            setOn.put(changeEmail, () -> {
                try {
                    changeThings(pane, 3);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
            setOn.put(changeAvatar, () -> changeAvatar(pane));
            setOn.put(changePassword, () -> changePassword(pane));
            setOn.put(changeSlogan, () -> {
                try {
                    changeThings(pane, 4);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        change.getChildren().addAll(changeUsername, changePassword, changeNickname, changeEmail, changeAvatar);
        for (Node node : change.getChildren()) {
            node.setStyle("-fx-background-color:\n" +
                    "            #3c7fb1,\n" +
                    "            linear-gradient(#fafdfe, #e8f5fc),\n" +
                    "            linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);\n" +
                    "    -fx-background-insets: 0,1,2;\n" +
                    "    -fx-background-radius: 3,2,1;\n" +
                    "    -fx-padding: 3 30 3 30;\n" +
                    "    -fx-text-fill: black;\n" +
                    "    -fx-font-size: 15px;\n" +
                    "    -fx-border-radius: 5px;");
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    setOn.get(node).run();
                }
            });
        }
        changeUsername.setText("Change Username");
        changePassword.setText("Change Password");
        changeAvatar.setText("Change Avatar");
        changeEmail.setText("Change Email");
        changeNickname.setText("Change Nickname");
        change.setAlignment(Pos.CENTER_RIGHT);
    }

    private void initData(VBox data) {
        Text user = new Text("Username : " + username);
        user.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #18cb2b;");
        Text nick = new Text("Nickname : " + nickname);
        nick.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #0ac1bb;");
        Text mail = new Text("Email : " + email);
        mail.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #a874ee;");
        Circle avatarImage = new Circle(20);
        avatarImage.setFill(new ImagePattern(image));
        data.setAlignment(Pos.CENTER_LEFT);
        data.getChildren().addAll(user, nick, mail, avatarImage);
    }


    //mode : 1-username  2-nickname 3-email 4-slogan , password and avatar should be handled in a different manner
    private void setSubmitButton(HBox hBox, Text error, int mode) {
        Button submit = new Button();
        submit.setStyle("-fx-background-color: #4CAF50; \n" +
                "    -fx-text-fill: white; \n" +
                "    -fx-font-size: 14px; \n" +
                "    -fx-padding: 8px 16px; \n" +
                "    -fx-border-radius: 4px; \n" +
                "    -fx-cursor: hand; ");
        submit.setText("SUBMIT");
        hBox.getChildren().add(submit);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mode == 4)
                {
                    System.out.println("slogan");
                    new Alert(Alert.AlertType.INFORMATION, "Changed Successfully").showAndWait();
                }
                else if (hBox.getChildren().contains(error))
                    new Alert(Alert.AlertType.ERROR, "YOU SHOULD ENTER A VALID INPUT").showAndWait();
                else {
                    switch (mode) {
                        case 1 -> System.out.println("username");
                        case 2 -> System.out.println("nickname");
                        case 3 -> System.out.println("email");
                    }
                    new Alert(Alert.AlertType.INFORMATION, "Changed Successfully").showAndWait();
                }
            }
        });
    }

    private void setBackButton(Pane pane) {
        Button back = new Button();
        back.setAlignment(Pos.TOP_LEFT);
        back.setStyle("    -fx-background-size: cover;\n" + "    -fx-background-radius: 50%;");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().removeAll();
                pane.getChildren().addAll(bufferPane.getChildren());
            }
        });
        pane.getChildren().add(back);
    }

    private void changeThings(Pane pane, int mode) throws NoSuchMethodException {
        pane.getChildren().removeAll();
        setBackButton(pane);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        Text error = new Text();
        TextField input = new TextField();
        error.setStyle("-fx-font-size: 10px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #e81a1a;");
        input.setStyle("-fx-prompt-text-fill: #19e0a3;\n" +
                "    -fx-text-fill: #0da2ef;");
        Method buffer = null;
        switch (mode) {
            case 1 -> {
                buffer = SignupLoginMenuController.class.getMethod("validUserName", String.class);
                error.setText("Invalid Username");
            }
            case 2 -> {
                buffer = SignupLoginMenuController.class.getMethod("validNickname", String.class);
                error.setText("Invalid Nickname");
            }
            case 3 -> {
                buffer = SignupLoginMenuController.class.getMethod("validEmail", String.class);
                error.setText("Invalid Email");
            }
        }
        final Method checker = buffer;
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!(boolean) checker.invoke(input.getText()))
                    hBox.getChildren().add(error);
                else if (hBox.getChildren().contains(error))
                    hBox.getChildren().remove(error);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        hBox.getChildren().add(input);
        pane.getChildren().add(hBox);
    }


    private void changePassword(Pane pane) {
        //todo : how to check captcha
    }

    private void changeAvatar(Pane pane) {
        pane.getChildren().removeAll();
        setBackButton(pane);
    }

    private void showScoreboard(Pane pane) {
        pane.getChildren().removeAll();
        setBackButton(pane);
        String style = "-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #18cb2b;";
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        ScrollBar scrollBar = new ScrollBar();
        pane.getChildren().addAll(vBox,scrollBar);
        String[] scores = SignupLoginMenuController.getScoreboard();
        ArrayList<String> array = new ArrayList<>();
        Collections.addAll(array, scores);
        sortScoreBoard(array);
        ArrayList<Text> scoreBoard = new ArrayList<>();
        int cnt = 1;
        for (int i =0;i<array.size()-2;i+=2)
        {
            Text text = new Text(cnt +". Username : "+array.get(i)+ " Score : "+array.get(i+1));
            text.setStyle(style);
            if (Integer.parseInt(array.get(i+3)) < Integer.parseInt(array.get(i+1)))
                ++cnt;
            vBox.getChildren().add(text);
        }
        if (Integer.parseInt(array.get(array.size()-1)) < Integer.parseInt(array.get(array.size()-3)))
            ++cnt;
        Text text = new Text(cnt +". Username : "+array.get(array.size()-2)+ " Score : "+array.get(array.size()-1));
        text.setStyle(style);
        vBox.getChildren().add(text);

        {
            scrollBar.setLayoutX(pane.getScene().getWidth() - scrollBar.getWidth());
            scrollBar.setMin(0);
            scrollBar.setOrientation(Orientation.VERTICAL);
            scrollBar.setPrefHeight(180);
            scrollBar.setMax(360);
            scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                                    Number old_val, Number new_val) {
                    vBox.setLayoutY(-new_val.doubleValue());
                }
            });
        }
    }

    private void sortScoreBoard(ArrayList<String> scores)
    {
        int size = scores.size();
        for (int i =1;i<size;i+=2)
        {
            for (int j=1;j<size - i -1;j+=2)
            {
                if (Integer.parseInt(scores.get(j))> Integer.parseInt(scores.get(j+2)))
                {
                    Collections.swap(scores,j,j+2);
                    Collections.swap(scores,j-1,j+1);
                }
            }
        }
    }
}
