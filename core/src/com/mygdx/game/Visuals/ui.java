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
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.inputHandler;
import com.mygdx.game.resources.assetLoader;
import com.mygdx.game.worldHandler;

import java.nio.channels.spi.SelectorProvider;

/**
 * Created by Cian on 01/05/2015.
 */


public class ui {

    public static int scrollLocation = 1;
    private BitmapFont font = new BitmapFont(false);
    private int locationControl = 0;
    private Sprite tendysilhouette = assetLoader.tendysilhouette;
    private Sprite tendy = assetLoader.tendy;
    private Sprite hotBar = assetLoader.hotBar;
    private Sprite hotBarSelected = assetLoader.hotBarSelected;
    private int gap = 4;
    private Sprite[] spriteArray = {assetLoader.dickMenu,assetLoader.shrekMenu};

    public  void displayMax(SpriteBatch secondBatch){
       // worldHandler.currentHealth

        for (int i = 0; i < worldHandler.maxHealth; i++) {
            tendysilhouette.setSize(32, 32);
            tendysilhouette.setCenter((-((Gdx.graphics.getWidth() / 2) * 0.9f) + (6 * locationControl)+ ((tendysilhouette.getWidth()) * locationControl)), ((Gdx.graphics.getHeight() / 2) * 0.9f));
            tendysilhouette.draw(secondBatch);
            locationControl++;
        }
        locationControl = 0;

        if(worldHandler.currentHealth > worldHandler.maxHealth)worldHandler.currentHealth = worldHandler.maxHealth;

        for (int i = 0; i < worldHandler.currentHealth; i++) {
            tendy.setSize(32, 32);
            tendy.setCenter((-((Gdx.graphics.getWidth() / 2) * 0.9f) + (6 * locationControl)+ ((tendysilhouette.getWidth()) * locationControl)), ((Gdx.graphics.getHeight() / 2) * 0.9f));
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

    public void testHotbar(SpriteBatch batch){

        int[] hotBarNum = {1,2,3,4,5,6};

        scrollLocation -= inputHandler.currentScrollValue;
        if(scrollLocation == hotBarNum.length + 1)scrollLocation = 1;
        if(scrollLocation == 0)scrollLocation = hotBarNum.length;
        inputHandler.currentScrollValue = 0;

        hotBar.setSize(64, 64);
        hotBar.setCenter(32,32);
        hotBarSelected.setCenter(32, 32);
        hotBarSelected.setSize(64,64);

        for(int i: hotBarNum){
            if(i  != scrollLocation){
                hotBar.setCenter(-((hotBarNum.length*hotBar.getWidth()/2))+(i*hotBar.getWidth())+(gap*i)-(gap*hotBarNum.length),((-Gdx.graphics.getHeight()/2)+hotBar.getWidth()/2)+6);
                hotBar.draw(batch);
                if(i <= spriteArray.length ){
                    spriteArray[i-1].setCenter(-((hotBarNum.length*hotBar.getWidth()/2))+(i*hotBar.getWidth())+(gap*i)-(gap*hotBarNum.length),((-Gdx.graphics.getHeight()/2)+hotBar.getWidth()/2)+6);
                    spriteArray[i-1].draw(batch);
                }

            }else{
                hotBarSelected.setCenter(-((hotBarNum.length*hotBar.getWidth()/2))+(i*hotBar.getWidth())+(gap*i)-(gap*hotBarNum.length),((-Gdx.graphics.getHeight()/2)+hotBar.getWidth()/1.5f));
                hotBarSelected.draw(batch);
                if(i <= spriteArray.length ){
                    spriteArray[i-1].setCenter(-((hotBarNum.length * hotBar.getWidth() / 2)) + (i * hotBar.getWidth()) + (gap * i) - (gap * hotBarNum.length), ((-Gdx.graphics.getHeight() / 2) + hotBar.getWidth() / 1.5f));
                    spriteArray[i-1].draw(batch);
                }
            }
        }
    }
}
