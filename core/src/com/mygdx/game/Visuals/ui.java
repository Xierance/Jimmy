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



    private BitmapFont font = new BitmapFont(false);
    private int locationControl = 0;
    private Sprite tendysilhouette = assetLoader.tendysilhouette;
    private Sprite tendy = assetLoader.tendy;
    private Sprite hotBar = assetLoader.hotBar;
    private Sprite hotBarSelected = assetLoader.hotBarSelected;
    private int scrollLocation = 1;




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
    public void hotBar(SpriteBatch secondBatch){
        if (inputHandler.currentScrollValue == 0){

            hotBar.setSize(64, 64);
            hotBarSelected.setSize(64, 64);
            hotBarSelected.setCenter(-6-(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBarSelected.draw(secondBatch);

            hotBar.setCenter(-2-(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBar.setCenter(2+(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBar.setCenter(6+(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);
            inputHandler.currentScrollValue = 0;
        }
        if (inputHandler.currentScrollValue == 1 && scrollLocation == 1){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 4;
        }
        if (inputHandler.currentScrollValue == -1 && scrollLocation == 1){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 2;
        }
        if (scrollLocation == 2){
            hotBar.setSize(64, 64);
            hotBarSelected.setSize(64, 64);
            hotBar.setCenter(-6-(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBarSelected.setCenter(-2-(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBarSelected.draw(secondBatch);

            hotBar.setCenter(2+(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBar.setCenter(6+(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);
        }
        if (inputHandler.currentScrollValue == 1 && scrollLocation == 2){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 1;
        }
        if (inputHandler.currentScrollValue == -1 && scrollLocation == 2) {
            inputHandler.currentScrollValue = 0;
            scrollLocation = 3;
        }
        if (scrollLocation == 3){
            hotBar.setSize(64, 64);
            hotBarSelected.setSize(64, 64);
            hotBar.setCenter(-6-(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBar.setCenter(-2-(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBarSelected.setCenter(2+(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBarSelected.draw(secondBatch);

            hotBarSelected.setCenter(6+(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);
        }
        if (inputHandler.currentScrollValue == 1 && scrollLocation == 3){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 2;
        }
        if (inputHandler.currentScrollValue == -1 && scrollLocation == 3){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 4;
        }
        if (scrollLocation == 4){
            hotBar.setSize(64, 64);
            hotBarSelected.setSize(64, 64);
            hotBar.setCenter(-6-(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBar.setCenter(-2-(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBar.setCenter(2+(hotBar.getWidth()/2), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBar.draw(secondBatch);

            hotBarSelected.setCenter(6+(hotBar.getWidth()*1.5f), ((-Gdx.graphics.getHeight()/2)+ 35));
            hotBarSelected.draw(secondBatch);
        }
        if (inputHandler.currentScrollValue == 1 && scrollLocation == 4){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 3;
        }
        if (inputHandler.currentScrollValue == -1 && scrollLocation == 4){
            inputHandler.currentScrollValue = 0;
            scrollLocation = 1;
        }

        /*if (inputHandler.betterScrolled(50)){
            Gdx.app.log(MyGdxGame.title, String.valueOf(com.mygdx.game.inputHandler.currentScrollValue));
        }*/

        //Gdx.app.log(MyGdxGame.title, String.valueOf(com.mygdx.game.inputHandler.currentScrollValue));

    }
    public void testHotbar(SpriteBatch batch){

        int[] hotBarNum = {1,2,3,4,5,6};

        scrollLocation -= inputHandler.currentScrollValue;
        if(scrollLocation == hotBarNum.length + 1)scrollLocation = 1;
        if(scrollLocation == 0)scrollLocation = hotBarNum.length;
        inputHandler.currentScrollValue = 0;

        hotBar.setSize(64,64);
        hotBarSelected.setSize(64, 64);

             /*   for(int i: hotBarNum){
            if(i  != scrollLocation){
                hotBar.setPosition( - ((hotBarNum.length*32)) + (1+i )*64,((-Gdx.graphics.getHeight()/2)));
                hotBar.draw(batch);

            }else{
                hotBarSelected.setPosition( - ((hotBarNum.length*32)) + (1+i )*64,((-Gdx.graphics.getHeight()/2)));
                hotBarSelected.draw(batch);
            }
        }*/

        System.out.println(scrollLocation);


        for(int i: hotBarNum){
            if (hotBarNum.length % 2 == 0) {
                if (i != scrollLocation) {
                    hotBar.setCenter(-((hotBar.getWidth()+4)*i)+2, ((-Gdx.graphics.getHeight()/2)+ 35));
                    hotBar.draw(batch);

                    hotBar.setCenter(((hotBar.getWidth()+4)*i)-2, ((-Gdx.graphics.getHeight() / 2) + 35));
                    hotBar.draw(batch);

                } else {
                    hotBarSelected.setCenter(-((hotBarNum.length * 32)) + (1 + i) * 64, ((-Gdx.graphics.getHeight() / 2) + 35));
                    hotBarSelected.draw(batch);
                }
            }
        /*  else {
                if (i != scrollLocation) {
                    hotBar.setPosition(-((hotBarNum.length * 32)) + (1 + i) * 64, ((-Gdx.graphics.getHeight() / 2)));
                    hotBar.draw(batch);

                } else {
                    hotBarSelected.setPosition(-((hotBarNum.length * 32)) + (1 + i) * 64, ((-Gdx.graphics.getHeight() / 2)));
                    hotBarSelected.draw(batch);
                }
            }*/
            }

        }


}
