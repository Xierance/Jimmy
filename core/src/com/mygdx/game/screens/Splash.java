package com.mygdx.game.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.tween.SpriteAccessor;


/**
 * Created by Cian on 09/03/2015.
 */
public class Splash implements Screen {

    private SpriteBatch batch; //Spritebatchs are like the page you draw the texture on
    private Sprite splash;
    private TweenManager tm;
    private Music introMusic;


    @Override
    public void show() {
        introMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/introTheme.mp3"));
        introMusic.play();

        Gdx.graphics.setVSync(settings.vSync());

        batch = new SpriteBatch();
        tm = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Texture splashTexture = new Texture(Gdx.files.internal("textures/introScreen2.jpg")); //Internal files are read only
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Set's the image to the size of the window

        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tm);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new mainMenu());

            }
        }).start(tm);

        tm.update(Float.MIN_VALUE);

        //Tween.to(splash, SpriteAccessor.ALPHA, 5).target(0).delay(10).start(tweenManager);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1); //Sets RGB values followed by opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Performs action using set values

        batch.begin();
        splash.draw(batch);
        batch.end();
        tm.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        splash.setSize(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();
        introMusic.dispose();

    }
}
