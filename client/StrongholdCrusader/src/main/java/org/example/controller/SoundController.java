package org.example.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.view.MainMenu;

public class SoundController {

    public static boolean isMusicMute ;
    private static MediaPlayer mediaPlayer = new MediaPlayer( new Media( MainMenu.class.getResource("/musics/gameMusic.mp3").toExternalForm() ) );

    static{
        setMusicMute( true ) ;
        mediaPlayer.play() ;
        mediaPlayer.setAutoPlay( true );
    }

    public static void setMusicMute( boolean mute ){
        isMusicMute = mute ;
        mediaPlayer.setMute( mute ) ;
    }

}
