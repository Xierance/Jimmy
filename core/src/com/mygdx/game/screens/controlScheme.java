package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.inputHandler;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.resources.keyCodes;

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
    private int newForwardBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("forwardBind");
    private int newBackwardBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("backwardBind");
    private int newJumpBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("jumpBind");
    private int newFireBind = Gdx.app.getPreferences(MyGdxGame.title).getInteger("fireBind");
    private int toggleControl6 = 0;





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

        final inputHandler inputHandler = new inputHandler();
        final InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(inputHandler);
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



        //inputHandler handling and setting controls
        ClickListener settingsHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                int toggleControl2 = 0;
                int toggleControl3 = -1;

                if (event.getListenerActor() == forwardInput) {
                    toggleControl++;

                    while (toggleControl % 2 == 0) {

                        Gdx.app.log(MyGdxGame.title, "Controls can now be bound" + toggleControl);
                        Gdx.app.log(MyGdxGame.title, String.valueOf(inputHandler.currentKey));

                        newForwardBind = inputHandler.currentKey;
                        Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newForwardBind);

                        if (newForwardBind > 0 /*&& inputHandler.currentKey == newForwardBind*/) {
                            toggleControl2 = newForwardBind;
                            Gdx.app.log(MyGdxGame.title, String.valueOf(newForwardBind));
                            toggleControl++;
                            if (toggleControl2 == newForwardBind && toggleControl == 0){
                               // toggleControl++;
                                Gdx.app.log(MyGdxGame.title, "First time only: " + toggleControl + toggleControl2);
                                break;


                            } else /*(inputHandler.currentKey > 0 && inputHandler.currentKey != toggleControl2)*/ {
                               // toggleControl++;
                                Gdx.app.log(MyGdxGame.title, "Should be working from now on: " + toggleControl);
                                break;
                            }
                        }


                    }

                }

                if (event.getListenerActor() == backwardInput) {

                    toggleControl3++;

                    newBackwardBind = inputHandler.currentKey;


                    while (toggleControl3 % 2 == 0 ) {
                        Gdx.app.log(MyGdxGame.title, String.valueOf(newBackwardBind));
                        if (Gdx.input.isKeyJustPressed(newBackwardBind)) {
                            toggleControl3++;
                            Gdx.app.log(MyGdxGame.title, String.valueOf(toggleControl3));
                            break;


                        }
                    }
                    int timmy = inputHandler.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("backwardBind", timmy);
                }
                if (event.getListenerActor() == jumpInput) {
                    long millis = System.currentTimeMillis();



                    long timeDif = 0;
                    while (timeDif<200){
                        timeDif = System.currentTimeMillis()-millis;
                        System.out.println("TESt");

                        newJumpBind = inputHandler.currentKey;
                    }

                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("jumpBind", newJumpBind);
                    table.invalidateHierarchy();
                }

                if (event.getListenerActor() == weaponInput) {
                    newFireBind = inputHandler.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("fireBind", newFireBind);
                }

                if (event.getListenerActor() == back) {

                    Gdx.app.getPreferences(MyGdxGame.title).flush();
                    Gdx.app.log(MyGdxGame.title, "settings saved");
                    stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new settings());
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
        table.debug();

        table.add("Move Forward:").left();
        table.add(forwardInput).left();
        table.row();
        table.add("Move Backward:").left();
        table.add(backwardInput).left();
        table.row();
        table.add("Jump:").left();
        table.add(jumpInput).left();
        table.row();
        table.add("Fire Weapon:").left();
        table.add(weaponInput).left();
        table.add(back).bottom().right();

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

