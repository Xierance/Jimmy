package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Input;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.resources.keyCodes;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Cian on 13/04/2015.
 */
public class controlScheme2 implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private int newForwardBind;
    private int newBackwardBind;
    private int newJumpBind;
    private int newFireBind;



    @Override
    public void show() {
        newForwardBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("forwardBind");
        newBackwardBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("backwardBind");
        newJumpBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("jumpBind");
        newFireBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("fireBind");


        stage = new Stage();

        final Input input = new Input();
        final InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));
        table = new Table(skin);
        table.setFillParent(true);

        //Button set up
        final TextButton back = new TextButton("Back", skin);
        back.pad(10);
        final TextButton forwardInput = new TextButton(keyCodes.keycodeNames.get(newForwardBind), skin);
        back.pad(10);
        forwardInput.setText("forwardInput");
        final TextButton backwardInput = new TextButton(keyCodes.keycodeNames.get(newBackwardBind), skin);
        back.pad(10);
        backwardInput.setText("backwardinput");
        final TextButton jumpInput = new TextButton(keyCodes.keycodeNames.get(newJumpBind), skin);
        back.pad(10);
        jumpInput.setText("jumpinput");
        final TextButton weaponInput = new TextButton(keyCodes.keycodeNames.get(newFireBind), skin);
        back.pad(10);


        //Input handling and setting controls
        ClickListener settingsHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (event.getListenerActor() == forwardInput) {
                    System.out.println(input.currentKey);
                    newForwardBind = input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newForwardBind);
                }

                if (event.getListenerActor() == backwardInput) {

                    newBackwardBind = input.currentKey;

                }
                if (event.getListenerActor() == jumpInput) {

                    newJumpBind = input.currentKey;
                }

                if (event.getListenerActor() == weaponInput) {

                }

                if (event.getListenerActor() == back) {

                    Gdx.app.getPreferences(MyGdxGame.title).flush();

                    stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new mainMenu());
                        }
                    })));
                }
            }
        };


        back.addListener(settingsHandler);
        forwardInput.addListener(settingsHandler);
        backwardInput.addListener(settingsHandler);
        jumpInput.addListener(settingsHandler);
        weaponInput.addListener(settingsHandler);

        table.add(forwardInput);
        table.add(backwardInput);
        table.add(jumpInput);
        table.add(weaponInput);
        table.add(back);

        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();

    }


}

