package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Cian on 13/04/2015.
 */
public class controlScheme implements Screen {

    private BitmapFont font;
    private Stage stage;
    private Table table;
    private Skin skin;


    public static FileHandle moveForward() {
        String prefsDir = Gdx.app.getPreferences(MyGdxGame.title).getString("forwardBind").trim();
        if (prefsDir != null && !prefsDir.equals(""))
            return Gdx.files.absolute(prefsDir);
        else
            return Gdx.files.absolute(Gdx.files.external(MyGdxGame.title + "d").path());
    }

    public static FileHandle moveBackward() {
        String prefsDir = Gdx.app.getPreferences(MyGdxGame.title).getString("backwardBind").trim();
        if (prefsDir != null && !prefsDir.equals(""))
            return Gdx.files.absolute(prefsDir);
        else
            return Gdx.files.absolute(Gdx.files.external(MyGdxGame.title + "a").path());
    }

    public static FileHandle jumpUp() {
        String prefsDir = Gdx.app.getPreferences(MyGdxGame.title).getString("jumpBind").trim();
        if (prefsDir != null && !prefsDir.equals(""))
            return Gdx.files.absolute(prefsDir);
        else
            return Gdx.files.absolute(Gdx.files.external(MyGdxGame.title + "w").path());
    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        final TextField moveForwardInput = new TextField(moveForward().path(), skin);
        moveForwardInput.setMessageText("Move Right:");

        final TextField moveBackwardInput = new TextField(moveBackward().path(), skin);
        moveBackwardInput.setMessageText("Move Left:");

        final TextField jumpInput = new TextField(jumpUp().path(), skin);
        jumpInput.setMessageText("Jump:");


        final TextButton back = new TextButton("Back", skin);
        back.pad(10);


        ClickListener settingsHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (event.getListenerActor() == back) {

                    String newForwardBind = moveForwardInput.getText().trim().equals("") ? "d" : moveForwardInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("forwardBind", newForwardBind);

                    String newBackwardBind = moveBackwardInput.getText().trim().equals("") ? "d" : moveBackwardInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("backwardBind", newBackwardBind);

                    String newJumpBind = jumpInput.getText().trim().equals("") ? "d" : jumpInput.getText().trim();
                    Gdx.app.getPreferences(MyGdxGame.title).putString("jumpBind", newJumpBind);

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
    }

            @Override
            public void render(float delta) {

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

            }
        }

