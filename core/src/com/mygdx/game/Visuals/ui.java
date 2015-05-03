package com.mygdx.game.Visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.resources.assetLoader;
import com.mygdx.game.worldHandler;

import java.nio.channels.spi.SelectorProvider;

/**
 * Created by Cian on 01/05/2015.
 */


public class ui {



    private BitmapFont font = new BitmapFont(false);
    private int locationControl = 0;
    private Sprite tendysilhouette = assetLoader.tendysilhouette;
    private Sprite tendy = assetLoader.tendy;
    private Sprite hotBar = assetLoader.hotBar;
    private Sprite hotBarSelected = assetLoader.hotBarSelected;



    public  void displayMax(SpriteBatch secondBatch){
       // worldHandler.currentHealth

        for (int i = 0; i < worldHandler.maxHealth; i++) {
            tendysilhouette.setCenter((-((Gdx.graphics.getWidth() / 2) * 0.9f) + ((tendysilhouette.getWidth() / 2) * locationControl)), ((Gdx.graphics.getHeight() / 2) * 0.9f));
            tendysilhouette.draw(secondBatch);
            locationControl++;
        }
        locationControl = 0;

        if(worldHandler.currentHealth > worldHandler.maxHealth)worldHandler.currentHealth = worldHandler.maxHealth;

        for (int i = 0; i < worldHandler.currentHealth; i++) {
            tendy.setCenter((-((Gdx.graphics.getWidth() / 2) * 0.9f) + ((tendysilhouette.getWidth() / 2) * locationControl)) + 10, ((Gdx.graphics.getHeight() / 2) * 0.9f + 10));
            tendy.draw(secondBatch);
            locationControl++;
        }
        locationControl = 0;
    }
    public void displayFps(SpriteBatch secondBatch) {

        //GG 10/10 not silly way to do this \/
        if (worldHandler.fps) {
            this.font.draw(secondBatch, "Fps" + Gdx.graphics.getFramesPerSecond(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

    }
    public void hotBar(SpriteBatch secondBatch){
        switch (com.mygdx.game.inputHandler.currentScrollValue){
            case 0:
                hotBar.setSize(64, 64);
                hotBarSelected.setSize(64, 64);
                hotBar.setCenter(-6-(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
                hotBar.draw(secondBatch);

                hotBar.setCenter(-2-(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
                hotBar.draw(secondBatch);

                hotBar.setCenter(2+(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
                hotBar.draw(secondBatch);

                hotBar.setCenter(6+(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
                hotBar.draw(secondBatch);
                break;






        }

        Gdx.app.log(MyGdxGame.title, String.valueOf(com.mygdx.game.inputHandler.currentScrollValue));
    }
}
