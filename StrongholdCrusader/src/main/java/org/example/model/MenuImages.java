package org.example.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.example.controller.bottomMenu;
import org.example.view.MainMenu;

public enum MenuImages {
    PAUSE_MENU("pauseMenu.jpg"),
    SOUND_ON("soundOn.jpeg"),
    SOUND_OFF("soundOff.png"),
    EXIT("exit.jpeg"),
    RESUME("resume.jpeg");
    private Image image;
    private ImagePattern imagePattern;
    MenuImages(String menuName)
    {
        this.image = new Image(MainMenu.class.getResource("/images/MenuBackGrounds/"+menuName).toString());
        bottomMenu.menuImages.add(image);
    }
    public Image getImage(){
        return this.image;
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}
