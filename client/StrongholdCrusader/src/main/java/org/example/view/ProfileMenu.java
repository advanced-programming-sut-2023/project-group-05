package org.example.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.GameController;
import org.example.controller.SignupLoginMenuController;
import org.example.controller.SoundController;
import org.example.model.enums.Icons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ProfileMenu extends Application {
    interface MyInterface {
        boolean myMethod(String input);
    }

    private String username;
    private Stage stage;
    private Image avatar;
    private boolean alterSlogan = false;
    String nickname;
    Circle avt ;
    String email;
    String slogan;
    private Button back;
    //Image image;
    private static Pane bufferPane;
    Text welcome;
    VBox change;
    VBox data;
    private Scene scene;
    HBox sloganBox = new HBox();
    @Override
    public void start(Stage stage) throws Exception {
        Background background = new Background(new BackgroundFill(Icons.BG.getImagePattern(), null,null));
        username = GameController.currentUsername;
        nickname = GameController.currentNickname;
        email = GameController.currentEmail;
        slogan = GameController.currentSlogan;
        avatar = GameController.currentAvatar == null ? new Image(MainMenu.class.getResource("/images/Avatars/avatar1.png").toString()): GameController.currentAvatar;
        Text welcome = new Text("Welcome To Profile Menu");
        welcome.setStyle("-fx-font-size: 32px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #05c2c7;");

        BorderPane pane = new BorderPane();
        pane.setPrefHeight(700);
        pane.setPrefWidth(700);
        VBox data = new VBox();
        VBox change = new VBox();
        HashMap<Button, Runnable> setOn = new HashMap<>();
        Scene scene = new Scene(pane, 700, 700, Color.WHITE);
        initData(data);
        this.data = data;
        initButtons(setOn, change, pane);
        this.change = change;
        pane.setLeft(data);
        pane.setRight(change);
        welcome.setTextAlignment(TextAlignment.JUSTIFY);
        this.welcome = welcome;
        this.stage = stage;
        BorderPane.setAlignment(welcome, Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));  // Create some padding from top
        pane.setTop(welcome);
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        //bufferPane.getChildren().addAll(pane.getChildren());
        pane.setBackground(background);
        stage.setScene(scene);
        this.scene = scene;
        stage.show();
    }

    private void initButtons(HashMap<Button, Runnable> setOn, VBox change, BorderPane pane) {
        Button changeUsername = new Button();
        Button changeNickname = new Button();
        Button changeEmail = new Button();
        Button changeAvatar = new Button();
        Button changePassword = new Button();
        Button changeSlogan = new Button();
        Button showScoreboard = new Button();
        Circle avt = new Circle(pane.getLayoutX()+pane.getWidth()-60,pane.getLayoutY()+120,50);
        avt.setFill(new ImagePattern(avatar));
        this.avt = avt;
        pane.getChildren().add(avt);
        {
            setOn.put(changeUsername, () -> changeThings(pane, 1));
            setOn.put(changeNickname, () -> changeThings(pane, 2));
            setOn.put(changeEmail, () -> changeThings(pane,3)) ;
            setOn.put(changeAvatar, () -> changeAvatar(pane));
            setOn.put(changePassword, () -> changePassword(pane));
            setOn.put(changeSlogan, () -> changeThings(pane,4));
            setOn.put(showScoreboard,() -> showScoreboard(pane));
        }
        change.getChildren().addAll(changeUsername, changePassword, changeNickname, changeEmail, changeAvatar,showScoreboard);
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
        showScoreboard.setText("Show Scoreboard");
        change.setAlignment(Pos.CENTER_RIGHT);
        change.setSpacing(15);
    }

    private void initData(VBox data) {
        Text user = new Text("Username : " + username);
        user.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n");
        user.setFill(Color.WHITE);
        Text nick = new Text("Nickname : " + nickname);
        nick.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #6af334;");
        nick.setFill(Color.WHITE);
        Text mail = new Text("Email : " + email);
        mail.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #a874ee;");
        mail.setFill(Color.WHITE);
        Text slg = new Text(slogan == null ? "Empty Slogan" : "Slogan : "+slogan);
        slg.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n");
        slg.setFill(Color.GOLD);
        Button sloganDisplay = new Button();
        sloganBox.getChildren().addAll(sloganDisplay);
        sloganBox.setSpacing(25);
        sloganDisplay.setPrefWidth(40);
        sloganDisplay.setPrefHeight(40);
        slg.setY(sloganDisplay.getLayoutY()+25);
        sloganDisplay.setBackground(new Background(new BackgroundFill(Icons.SHOW.getImagePattern(),null,null)));
        sloganDisplay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!alterSlogan) {
                    sloganBox.getChildren().add(slg);
                    sloganDisplay.setBackground(new Background(new BackgroundFill(Icons.HIDE.getImagePattern(), null, null)));
                }
                else {
                    sloganBox.getChildren().remove(slg);
                    sloganDisplay.setBackground(new Background(new BackgroundFill(Icons.SHOW.getImagePattern(), null, null)));
                }
                alterSlogan = !alterSlogan;
            }
        });
        data.setAlignment(Pos.CENTER_LEFT);
        data.setSpacing(50);
        data.getChildren().addAll(user, nick, mail,sloganBox);
    }


    //mode : 1-username  2-nickname 3-email 4-slogan , password and avatar should be handled in a different manner
    private void setSubmitButton(Pane hBox, Text error, int mode, TextField textField) {
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
                if (mode !=4 && hBox.getChildren().contains(error))
                    new Alert(Alert.AlertType.ERROR, "YOU SHOULD ENTER A VALID INPUT").showAndWait();
                else if (!SignupLoginMenuController.changeThing(textField.getText(),mode)) {
                    switch (mode) {
                        case 1 -> new Alert(Alert.AlertType.ERROR,"Username Already Exists.").showAndWait();
                        case 2 -> new Alert(Alert.AlertType.ERROR,"Nickname Already Exists").showAndWait();
                        case 3 -> new Alert(Alert.AlertType.ERROR,"Email Already Exists").showAndWait();
                        case 4 -> new Alert(Alert.AlertType.ERROR,"Slogan Had An Error").showAndWait();
                        case 5 -> new Alert(Alert.AlertType.ERROR,"Old Password Is Wrong").showAndWait();
                    }
                }
                else
                    new Alert(Alert.AlertType.INFORMATION, "Changed Successfully").showAndWait();
            }
        });
    }

    private void setBackButton(BorderPane pane) {
        Button back = new Button();
        back.setAlignment(Pos.TOP_LEFT);
        back.setPrefHeight(50);
        back.setPrefWidth(50);
        back.setBackground(new Background(new BackgroundFill(Icons.BACK.getImagePattern(),null,null)));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().clear();
                pane.getChildren().addAll(welcome, change, data,avt);
            }
        });
        this.back = back;
        pane.setTop(back);
    }

    private void changeThings(BorderPane pane, int mode) {
        pane.getChildren().clear();
        setBackButton(pane);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        Text error = new Text();
        TextField input = new TextField();
        error.setStyle("-fx-font-size: 14px;\n" +
                "-fx-font-weight: bold;\n");
        error.setFill(Color.RED);
        input.setStyle("-fx-prompt-text-fill: #19e0a3;\n" +
                "    -fx-text-fill: #0da2ef;");
        MyInterface checker = null;
        switch (mode) {
            case 1 -> {
                checker = SignupLoginMenuController::validUserName;
                error.setText("Invalid Username");
            }
            case 2 -> {
                checker = SignupLoginMenuController::validNickname;
                error.setText("Invalid Nickname");
            }
            case 3 -> {
                checker = SignupLoginMenuController::validEmail;
                error.setText("Invalid Email");
            }
        }
        final MyInterface checking = checker;
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(boolean) checking.myMethod(input.getText())) {
                if (!hBox.getChildren().contains(error))
                    hBox.getChildren().add(error);
            } else if (hBox.getChildren().contains(error))
                hBox.getChildren().remove(error);
        });
        hBox.getChildren().add(input);
        setSubmitButton(hBox, error, mode,input);
        pane.setCenter(hBox);
    }


    private void changePassword(BorderPane pane) {
        pane.getChildren().clear();
        setBackButton(pane);
        TextField oldPass = new TextField();
        oldPass.setAlignment(Pos.CENTER);
        oldPass.setPromptText("Old Password");
        TextField newPass = new TextField();
        newPass.setPrefHeight(60);
        newPass.setAlignment(Pos.CENTER);
        newPass.setPrefWidth(pane.getPrefWidth());
        oldPass.setPrefHeight(newPass.getPrefHeight());oldPass.setPrefWidth(newPass.getPrefWidth());
        newPass.setPromptText("New Password");
        Text error = new Text("Invalid Password");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        hBox.getChildren().add(newPass);
        newPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!SignupLoginMenuController.validPassword(newPass.getText())) {
                if (!hBox.getChildren().contains(error))
                    hBox.getChildren().add(error);
            } else if (hBox.getChildren().contains(error))
                hBox.getChildren().remove(error);
        });
        VBox vBox = new VBox();
        error.setStyle("-fx-font-size: 14px;\n" +
                "-fx-font-weight: bold;\n");
        error.setFill(Color.RED);
        newPass.setStyle("-fx-prompt-text-fill: #19e0a3;\n" +
                "    -fx-text-fill: #0da2ef;");
        oldPass.setStyle("-fx-prompt-text-fill: #cae32b;\n" +
                "    -fx-text-fill: rgba(33,175,238,0.84);");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        vBox.getChildren().addAll(oldPass,hBox);
        setSubmitButton(vBox,error,5,oldPass);
        pane.setCenter(vBox);
    }

    private void changeAvatar(BorderPane pane) {
        pane.getChildren().clear();
        setBackButton(pane);
        loadAvatars(pane);
    }

    private void loadAvatars(BorderPane pane)
    {
        int cnt = 0;
        Image image = null;
        ImageView dragDrop = new ImageView();
        dragDrop.setX(pane.getLayoutX()+pane.getWidth()*0.4);dragDrop.setY(pane.getLayoutY()+40);dragDrop.setFitHeight(100);dragDrop.setFitWidth(100);
        dragDrop.setImage(new Image(MainMenu.class.getResource("/images/icons/drag.png").toString()));
        dragDrop.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getDragboard().hasFiles()) {
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                }
            }
        });
        dragDrop.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                ArrayList<File> files = (ArrayList<File>) dragEvent.getDragboard().getFiles();
                try {
                    Image buf = new Image(new FileInputStream(files.get(0)));
                    avatar = buf;
                    new Alert(Alert.AlertType.INFORMATION,"Avatar Changed Successfully").showAndWait();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pane.getChildren().add(dragDrop);
        for (int i = 1;i<=9;++i)
        {
            Circle circle = new Circle(pane.getLayoutX()+(cnt%3)*120+200,pane.getLayoutY()+Math.floor(cnt++/3)*120+200,50);
            circle.setFill(new ImagePattern(image = new Image(MainMenu.class.getResource("/images/Avatars/avatar"+i+".png").toExternalForm())));
            pane.getChildren().add(circle);
            Image a = image;
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    avatar = a;
                    new Alert(Alert.AlertType.INFORMATION,"Avatar Changed Successfully").showAndWait();
                }
            });
        }
    }

    private void showScoreboard(BorderPane pane) {
        pane.getChildren().clear();
        setBackButton(pane);
        pane.setBackground(new Background(new BackgroundFill(Icons.WHITE.getImagePattern(),null,null)));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ProfileMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        String style = "-fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #18cb2b;";
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        Scene scene1 = new Scene(scrollPane);
        stage.setScene(scene1);
        stage.close();
        pane.setCenter(vBox);
        String[] scores = SignupLoginMenuController.getScoreboard();
        ArrayList<String> array = new ArrayList<>();
        Collections.addAll(array, scores);
        sortScoreBoard(array);
        ArrayList<Text> scoreBoard = new ArrayList<>();
        int cnt = 1;
        for (int i = 0; i < array.size() - 2; i += 2) {
            Text text = new Text(cnt + ". Username : " + array.get(i) + " Score : " + array.get(i + 1));
            text.setFill(Color.BLACK);
            text.setStyle(style);
            if (Integer.parseInt(array.get(i + 3)) < Integer.parseInt(array.get(i + 1)))
                ++cnt;
            vBox.getChildren().add(text);
        }
        if (Integer.parseInt(array.get(array.size() - 1)) < Integer.parseInt(array.get(array.size() - 3)))
            ++cnt;
        Text text = new Text(cnt + ". Username : " + array.get(array.size() - 2) + " Score : " + array.get(array.size() - 1));
        text.setFill(Color.WHITE);
        text.setStyle(style);
        vBox.getChildren().add(text);
//        for (int i = 1;i<30;i++)
//            vBox.getChildren().add(new Text(i+"123"));
        stage.show();
    }

    private void sortScoreBoard(ArrayList<String> scores) {
        int size = scores.size();
        for (int i = 1; i < size; i += 2) {
            for (int j = 1; j < size - i - 1; j += 2) {
                if (Integer.parseInt(scores.get(j)) > Integer.parseInt(scores.get(j + 2))) {
                    Collections.swap(scores, j, j + 2);
                    Collections.swap(scores, j - 1, j + 1);
                }
            }
        }
    }
}