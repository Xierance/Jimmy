package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Cian on 19/03/2015.
 */
public class musicPlayer {

    public static Music menuMusic;
    public static Music secretMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/dankmusic.mp3"));

    public static void menuMusicPlayer() {
        menuMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/jimmyTheme.mp3"));
        menuMusic.play();
       // menuMusic.setLooping(true);
        menuMusic.isLooping();

    }

    public static void sdmPlayer(String song) {
        secretMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/"+song+".mp3"));
        menuMusic.pause();
        secretMusic.play();

    }

    public static void sdmPause() {
        secretMusic.stop();
    }

    public static void menuMusicPause() {
        menuMusic.pause();
    }
}
