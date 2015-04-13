package com.mygdx.game.Visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by edward on 3/1/2015.
 */
public class buttonClass {
    private static SpriteBatch batch;
    private static BitmapFont font;
    Stage stage;
    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    Table table;

    public void createButton(BitmapFont font) {

        stage = new Stage();

        skin = new Skin();

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        buttonAtlas = new TextureAtlas("ui/button-pack.pack");
        skin.addRegions(buttonAtlas);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;

        button = new TextButton("Text", textButtonStyle);
        button.pad(20);
        table.add(button);
        table.debug();
        stage.addActor(table);

    }

    public void drawButton(Stage stage, float delta) {
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear((GL20.GL_COLOR_BUFFER_BIT));

        stage.act(delta);
        stage.draw();

    }
}
