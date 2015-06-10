package com.mygdx.game.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.MyGdxGame;
//import com.mygdx.game.Secrets;
import com.mygdx.game.Secrets;
import com.mygdx.game.inputHandler;
import com.mygdx.game.musicPlayer;
import com.mygdx.game.tween.ActorAccessor;


/**
 * Created by Cian on 09/03/2015.
 */
public class mainMenu implements Screen {

    public Music menuMusic;
    public Music secretDankMusic;
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Skin skin;
    private Label heading;
    private Sprite background;
    private TweenManager tm;
    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void show() {

        //Music set up
        musicPlayer.menuMusicPlayer();
        secretDankMusic = new Gdx().audio.newMusic(Gdx.files.internal("music/dankMusic.mp3"));
        // secretDankMusic.setOnCompletionListener(musicPlayer.menuMusic);

        //Stage and Table set up
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        inputHandler inputHandler = new inputHandler();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //fps attempt
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/white16.fnt"), false);


        //Background image
        Texture backGround = new Texture(Gdx.files.internal("textures/Space2.jpg"));
        background = new Sprite(backGround);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //Heading
        heading = new Label(MyGdxGame.title, skin, "big");

        //Play button
        buttonPlay = new TextButton("Play     ", skin);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());
            }
        });
        buttonPlay.pad(15);

        //Settings button
        TextButton buttonSettings = new TextButton("Settings", skin);
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new settings());
            }
        });
        buttonSettings.pad(15);


        //Exit Button
        buttonExit = new TextButton("Exit     ", skin); // There is no name so it will look for the default in the json. It could have been ("Exit", skin, "default");
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15);


        //Adding everything to the table
        table.setBackground(new SpriteDrawable(background));
        table.add(heading);
        table.getCell(heading).spaceBottom(300);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(20);
        table.row();
        table.add(buttonSettings);
        table.getCell(buttonSettings).spaceBottom(20);
        table.row();
        table.add(buttonExit);
        stage.addActor(table);

        //Creating animations
        tm = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        //Heading colour animation
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tm);


        //Headings and buttons fade in
        Timeline.createSequence().beginSequence() //Sequence implied everything is done one after another, in a parallel everything is done at the same time
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, 0.25f).target(0)) //Heading should adjust its ALPHA value for half a second from 0
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, 0.25f).target(1))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, 0.25f).target(1))
                .end().start(tm);

        //Table fade in
        Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0).start(tm);
        Tween.from(table, ActorAccessor.Y, 0.5f).target(Gdx.graphics.getHeight() / 8).start(tm);

        tm.update(Gdx.graphics.getDeltaTime());


    }


    public void displayFps(SpriteBatch batch, BitmapFont font2) {
        font2.draw(batch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 10.0F, 700.0F);

    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1); //Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tm.update(delta);
        stage.act(delta); //updates everything in the table
        stage.draw(); //Things are visibly rendered onto the screen

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());

        }

        batch.begin();
        if ((Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps")) == true) {
            displayFps(batch, font);
        }
        batch.end();

        Secrets.menuSecret();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.invalidateHierarchy(); //Everything will be recalculated
        table.setSize(width, height);

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
        stage.dispose();
        skin.dispose();
        secretDankMusic.dispose();
        batch.dispose();
    }

}