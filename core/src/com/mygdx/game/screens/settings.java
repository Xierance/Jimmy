package com.mygdx.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class settings implements Screen {

    public static int fpsOn = 0;
    private Stage stage;
    private Table table;
    private Skin skin;

    /**
     * @return the directory the levels will be saved to and read from
     */
    public static FileHandle levelDirectory() {
        String prefsDir = Gdx.app.getPreferences(MyGdxGame.title).getString("leveldirectory").trim();
        if (prefsDir != null && !prefsDir.equals(""))
            return Gdx.files.absolute(prefsDir);
        else
            return Gdx.files.absolute(Gdx.files.external(MyGdxGame.title + "/levels").path()); // return default level directory
    }

    /**
     * @return if vSync is enabled
     */
    public static boolean vSync() {

        return Gdx.app.getPreferences(MyGdxGame.title).getBoolean("vsync");
    }

    public static boolean FPS() {
        return Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps");
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
        stage.getViewport().update(width, height, true);
        table.invalidateHierarchy();
        table.setSize(width, height);

    }


    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        final CheckBox vSyncCheckBox = new CheckBox("vSync", skin);
        vSyncCheckBox.setChecked(vSync());

        final TextField levelDirectoryInput = new TextField(levelDirectory().path(), skin); // creating a new TextField with the current level directory already written in it
        levelDirectoryInput.setMessageText("level directory"); // set the text to be shown when nothing is in the TextField

        final TextButton controlRebind = new TextButton("Rebind Controls", skin);
        controlRebind.pad(10);

        final CheckBox checkFPS = new CheckBox("Show FPS", skin);
        checkFPS.setChecked(FPS());
        //checkFPS.setChecked(FPS());

        final TextButton back = new TextButton("Back", skin);
        back.pad(10);

        ClickListener buttonHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (event.getListenerActor() == checkFPS) {
                    Gdx.app.getPreferences(MyGdxGame.title).putBoolean("fps", checkFPS.isChecked()); //Sets "fps" boolean to what the check box says
                    fpsOn++;

                    Gdx.app.log(MyGdxGame.title, "fps " + (FPS() ? "enabled" : "disabled"));
                }

                // event.getListenerActor() returns the source of the event, e.g. a button that was clicked
                if (event.getListenerActor() == vSyncCheckBox) {
                    // save vSync
                    Gdx.app.getPreferences(MyGdxGame.title).putBoolean("vsync", vSyncCheckBox.isChecked());

                    // set vSync
                    Gdx.graphics.setVSync(vSync());

                    Gdx.app.log(MyGdxGame.title, "vSync " + (vSync() ? "enabled" : "disabled"));
                }

                if (event.getListenerActor() == back) {
                    // save level directory
                    String actualLevelDirectory = levelDirectoryInput.getText().trim().equals("") ? Gdx.files.getExternalStoragePath() + MyGdxGame.title + "/levels" : levelDirectoryInput.getText().trim(); // shortened form of an if-statement: [boolean] ? [if true] : [else] // String#trim() removes spaces on both sides of the string
                    Gdx.app.getPreferences(MyGdxGame.title).putString("leveldirectory", actualLevelDirectory);

                    // save the settings to preferences file (Preferences#flush() writes the preferences in memory to the file)
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

        vSyncCheckBox.addListener(buttonHandler);
        checkFPS.addListener(buttonHandler);

        back.addListener(buttonHandler);

        // putting everything in the table
        table.add(new Label("SETTINGS", skin, "big")).spaceBottom(50).colspan(3).expandX().row();
        table.add();
        table.add("Level directory:").height(100).bottom();
        table.add().row();
        table.add(vSyncCheckBox).top().left();
        table.add(levelDirectoryInput).top().fillX();
        table.row();
        table.add(checkFPS).left();
        table.add();
        table.row();
        table.add().height(350);
        table.row();
        table.add();
        table.add().height(150);
        table.add(back).bottom().right();

        stage.addActor(table);

        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}