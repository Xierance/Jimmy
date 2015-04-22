package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Created by Cian on 16/03/2015.
 */
public class levelMenu implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;
    private List<String> list;
    private ScrollPane sp;
    private TextButton play, back;


    @Override
    public void show() {


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setFillParent(true);

        list = new List(skin);
        list.setItems("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "Dank","controltest");
        sp = new ScrollPane(list, skin);


        play = new TextButton("Play", skin);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //   ((Game) Gdx.app.getApplicationListener()).setScreen(new levelTwo());
                if (list.getSelected() == "one") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new jumpTest());
                }
                if (list.getSelected() == "two") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new levelTest());
                }
                if (list.getSelected() == "three") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new levelOne());
                }
                if (list.getSelected() == "four") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new levelTwo());
                }
                if (list.getSelected() == "five") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new levelThree());
                }
                if (list.getSelected() == "Dank") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new TestClass());
                }
                if (list.getSelected() == "controltest") {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new controlScheme2());
                }

            }
        });


        play.pad(15);

        //Back button
        back = new TextButton("Back", skin, "small");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new mainMenu());
            }
        });
        back.pad(10);


        table.add().width(table.getWidth() / 3);
        table.add("       Select Level").width(table.getWidth() / 3); // colspan means the cell will span itself over 2 collums
        table.add().width(table.getWidth() / 3).row();
        table.add(sp).left().expandY();
        table.add(play);
        table.add(back).bottom().right();


        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {

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

    }
}
