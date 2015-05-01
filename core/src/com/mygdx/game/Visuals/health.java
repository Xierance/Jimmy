package com.mygdx.game.Visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.worldHandler;

/**
 * Created by Cian on 01/05/2015.
 */


public class health{


    public static Sprite tendysilhouette = new Sprite(new Texture("textures/dick.png"));
    public static Sprite tendy = new Sprite(new Texture("tiles/block_pack.png"));

    public static void displayMax(SpriteBatch secondBatch){
       // worldHandler.currentHealth
        tendysilhouette.setCenter(-((Gdx.graphics.getWidth()/2)*0.9f), ((Gdx.graphics.getHeight()/2)*0.9f));
        tendysilhouette.draw(secondBatch);


    }

}
