package com.mygdx.game.Visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.worldHandler;

/**
 * Created by Cian on 01/05/2015.
 */


public class ui {


    public  Sprite tendysilhouette = new Sprite(new Texture("textures/dick.png"));
    public  Sprite tendy = new Sprite(new Texture("tiles/block_pack.png"));
    private  BitmapFont font = new BitmapFont(false);
    private  int locationControl = 0;


    public  void displayMax(SpriteBatch secondBatch){
       // worldHandler.currentHealth

        for (int i = 0; i < worldHandler.maxHealth; i++) {
            this.tendysilhouette.setCenter((-((Gdx.graphics.getWidth() / 2) * 0.9f) + ((tendysilhouette.getWidth() / 2) * locationControl)), ((Gdx.graphics.getHeight() / 2) * 0.9f));
            this.tendysilhouette.draw(secondBatch);
            locationControl++;
        }
        locationControl = 0;

        for (int i = 0; i < worldHandler.maxHealth; i++) {
            this.tendysilhouette.setCenter((-((Gdx.graphics.getWidth() / 2) * 0.9f) + ((tendysilhouette.getWidth() / 2) * locationControl)), ((Gdx.graphics.getHeight() / 2) * 0.9f));
            this.tendysilhouette.draw(secondBatch);
            locationControl++;
        }
        locationControl = 0;
    }

    public  void displayFps(SpriteBatch secondBatch) {

        if ((Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps"))) {
            this.font.draw(secondBatch, "Fps" + Gdx.graphics.getFramesPerSecond(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

    }
}