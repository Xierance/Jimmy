package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Input;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.things.keyCodes;

import javax.print.DocFlavor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Cian on 13/04/2015.
 */
public class controlScheme extends InputAdapter implements Screen {

    private BitmapFont font;
    private Stage stage;
    private Table table;
    private Skin skin;
    private int toggleControl = -1;
    private int newForwardBind;
    private int newBackwardBind;
    private int newJumpBind;
    private int newFireBind;





    public String moveForward() {
        String prefsForward = Gdx.app.getPreferences(MyGdxGame.title).getString("forwardBind").trim();
        if (prefsForward != null && !prefsForward.equals(""))
            return prefsForward;
        else
            return "d";
    }

    public String moveBackward() {
        String prefsBackward = Gdx.app.getPreferences(MyGdxGame.title).getString("backwardBind").trim();
        if (prefsBackward != null && !prefsBackward.equals(""))
            return prefsBackward;
        else
            return "a";
    }

    public String jumpUp() {
        String prefsJump = Gdx.app.getPreferences(MyGdxGame.title).getString("jumpBind").trim();
        if (prefsJump != null && !prefsJump.equals(""))
            return prefsJump;
        else
            return "w";
    }

    public String weaponFire() {
        String prefsDir = Gdx.app.getPreferences(MyGdxGame.title).getString("jumpBind").trim();
        if (prefsDir != null && !prefsDir.equals(""))
            return prefsDir;
        else
            return "ctrl_left";
    }


    @Override
    public void show(){
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
        final TextButton backwardInput = new TextButton(keyCodes.keycodeNames.get(newBackwardBind), skin);
        back.pad(10);
        final TextButton jumpInput = new TextButton(keyCodes.keycodeNames.get(newJumpBind), skin);
        back.pad(10);
        final TextButton weaponInput = new TextButton(keyCodes.keycodeNames.get(newFireBind), skin);
        back.pad(10);


        //Input handling and setting controls
        ClickListener settingsHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                int toggleControl2 = 0;
                int toggleControl3 = -1;

                if (event.getListenerActor() == forwardInput) {
                    toggleControl++;

                    while (toggleControl % 2 == 0) {

                        Gdx.app.log(MyGdxGame.title, "Controls can now be bound" + toggleControl);
                        Gdx.app.log(MyGdxGame.title, String.valueOf(Input.currentKey));

                        newForwardBind = Input.currentKey;
                        Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newForwardBind);

                        if (newForwardBind > 0 /*&& Input.currentKey == newForwardBind*/) {
                            toggleControl2 = newForwardBind;
                            Gdx.app.log(MyGdxGame.title, String.valueOf(newForwardBind));

                            if (toggleControl2 == newForwardBind && toggleControl == 0){
                                toggleControl++;
                                Gdx.app.log(MyGdxGame.title, "First time only: " + toggleControl + toggleControl2);
                                break;


                            } else /*(Input.currentKey > 0 && Input.currentKey != toggleControl2)*/ {
                                toggleControl++;
                                Gdx.app.log(MyGdxGame.title, "Should be working from now on: " + toggleControl);
                                break;
                            }
                        }


                    }

                }

                if (event.getListenerActor() == backwardInput) {

                    toggleControl3++;

                    newBackwardBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("backwardBind", newBackwardBind);

                    while (toggleControl3 % 2 == 0 ) {
                        Gdx.app.log(MyGdxGame.title, String.valueOf(newBackwardBind));
                        if (Gdx.input.isKeyJustPressed(newBackwardBind)) {
                            toggleControl3++;
                            Gdx.app.log(MyGdxGame.title, String.valueOf(toggleControl3));
                            break;


                        }
                    }
                }
                if (event.getListenerActor() == jumpInput) {
                    newJumpBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("jumpBind", newJumpBind);
                }

                if (event.getListenerActor() == weaponInput) {
                    newFireBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("fireBind", newFireBind);
                }

                if (event.getListenerActor() == back) {

                    Gdx.app.getPreferences(MyGdxGame.title).flush();
                    Gdx.app.log(MyGdxGame.title, "settings saved");
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

