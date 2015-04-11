package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.screens.Splash;

//import com.badlogic.gdx.ApplicationListener; Organises imports, deletes unnecessarry ones


public class MyGdxGame extends Game {

    public static final String title = "Jimmy 2: Electric Shoe";



    @Override
    public void create() {
        setScreen(new Splash());
        // Do not have any other methods here as it "cant deal with that dank shit"

    }

}