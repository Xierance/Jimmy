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

    public boolean keyDown(int keycode) {
        keycode = key;
        return false;
    }

    @Override
    public void show() {
        stage = new Stage();


        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);


        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        final TextField moveForwardInput = new TextField(moveForward(), skin);
        moveForwardInput.setMessageText("Move Right:");

        final TextField moveBackwardInput = new TextField(moveBackward(), skin);
        moveBackwardInput.setMessageText("Move Left:");

        /*final TextField jumpInput = new TextField(jumpUp().path(), skin);
        jumpInput.setMessageText("Jump:");

        final TextField weaponInput = new TextField(jumpUp().path(), skin);
        weaponInput.setMessageText("Weapon:");*/


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
                    Gdx.app.log(MyGdxGame.title, "Apparently it's working" + key);
                    switch (key){
                        case 19:
                            bindForward();
                            break;
                        case 20:
                            bindForward();
                            break;
                        case 21:
                            bindForward();
                            break;
                        case 22:
                            bindForward();
                            break;
                        case 57:
                            bindForward();
                            break;
                        case 58:
                            bindForward();
                            break;
                        case 62:
                            bindForward();
                            break;
                        case 131:
                            bindForward();
                            break;
                        case 41:
                            bindForward();
                            break;
                        case 43:
                            bindForward();
                            break;
                        case 46:
                            bindForward();
                            break;
                        case 33:
                            bindForward();
                            break;
                        case 47:
                            bindForward();
                            break;
                        case 29:
                            bindForward();
                            break;
                        case 42:
                            bindForward();
                            break;
                        case 37:
                            bindForward();
                            break;
                        case 31:
                            bindForward();
                            break;
                        case 44:
                            bindForward();
                            break;
                        case 40:
                            bindForward();
                            break;
                        case 51:
                            bindForward();
                            break;
                        case 32:
                            bindForward();
                            break;
                        case 30:
                            bindForward();
                            break;
                        case 34:
                            bindForward();
                            break;
                        case 35:
                            bindForward();
                            break;
                        case 36:
                            bindForward();
                            break;
                        case 38:
                            bindForward();
                            break;
                        case 39:
                            bindForward();
                            break;
                        case 45:
                            bindForward();
                            break;
                        case 48:
                            bindForward();
                            break;
                        case 49:
                            bindForward();
                            break;
                        case 50:
                            bindForward();
                            break;
                        case 52:
                            bindForward();
                            break;
                        case 53:
                            bindForward();
                            break;
                        case 54:
                            bindForward();
                            break;
                        case 7:
                            bindForward();
                            break;
                        case 8:
                            bindForward();
                            break;
                        case 9:
                            bindForward();
                            break;
                        case 10:
                            bindForward();
                            break;
                        case 11:
                            bindForward();
                            break;
                        case 12:
                            bindForward();
                            break;
                        case 13:
                            bindForward();
                            break;
                        case 14:
                            bindForward();
                            break;
                        case 15:
                            bindForward();
                            break;
                        case 16:
                            bindForward();
                            break;
                        case 61:
                            bindForward();

                    }


                }


                if (event.getListenerActor() == backwardInput)
                {

                }

                if (event.getListenerActor() == jumpInput)
                {

                }

                if (event.getListenerActor() == weaponInput)
                {

                }

                if (event.getListenerActor() == back)
                {

                    String newForwardBind = moveForwardInput.getText().trim().equals("") ? "d" : moveForwardInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("forwardBind", newForwardBind);

                    String newBackwardBind = moveBackwardInput.getText().trim().equals("") ? "a" : moveBackwardInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("backwardBind", newBackwardBind);

                    /*String newJumpBind = jumpInput.getText().trim().equals("") ? "w" : jumpInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("jumpBind", newJumpBind);

                    String newWeaponBind = jumpInput.getText().trim().equals("") ? "left_ctrl" : jumpInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("jumpBind", newJumpBind);*/

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

    public void bindForward(){
        int newForwardBind = key;
        Gdx.app.getPreferences(MyGdxGame.title).putInteger("forwardBind", newForwardBind);
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

