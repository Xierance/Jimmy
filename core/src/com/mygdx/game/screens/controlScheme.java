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
    private int key = 0;




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
    public void show() {
        stage = new Stage();



        Input input = new Input();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(inputMultiplexer);


        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        final TextField moveForwardInput = new TextField(moveForward(), skin);
        moveForwardInput.setMessageText("Move Right:");

        final TextField moveBackwardInput = new TextField(moveBackward(), skin);
        moveBackwardInput.setMessageText("Move Left:");

        final TextButton back = new TextButton("Back", skin);
        back.pad(10);
        final TextButton forwardInput = new TextButton("Move Right", skin);
        back.pad(10);
        final TextButton backwardInput = new TextButton("Move Left", skin);
        back.pad(10);
        final TextButton jumpInput = new TextButton("Jump", skin);
        back.pad(10);
        final TextButton weaponInput = new TextButton("Fire Weapon", skin);
        back.pad(10);


        ClickListener settingsHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (event.getListenerActor() == forwardInput) {
                    int newForwardBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newForwardBind);
                }
                if (event.getListenerActor() == backwardInput) {

                    int newBackwardBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newBackwardBind);
                }
                if (event.getListenerActor() == jumpInput) {
                    int newJumpBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newJumpBind);
                }

                if (event.getListenerActor() == weaponInput) {
                    int newFireBind = Input.currentKey;
                    Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newFireBind);
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



        table.add(forwardInput);
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

