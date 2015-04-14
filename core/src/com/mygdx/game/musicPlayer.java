package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Cian on 19/03/2015.
 */
public class musicPlayer {


    public static Music menuMusic;
    public static Music secretMusic;


    public static void sdmInput() {

        int spaceCounter = 0;

        if (Input.Space) {

            spaceCounter++;
            Gdx.app.log(MyGdxGame.title, String.valueOf(spaceCounter));

            if ((spaceCounter % 2) == 0) {
                sdmPause();
                menuMusicPlayer();
            } else sdmPlayer();

        }


    }

    public static void menuMusicPlayer() {
        menuMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/Jimmy-theme.mp3"));
        menuMusic.play();
        menuMusic.setLooping(true);
        // menuMusic.isLooping();

    }


    public static void sdmPlayer() {
        secretMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/dankMusic.mp3"));
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
