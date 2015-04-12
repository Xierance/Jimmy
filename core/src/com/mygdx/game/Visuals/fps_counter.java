//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mygdx.game.Visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class fps_counter {
    public fps_counter() {
    }

    public static void fps(SpriteBatch batch, BitmapFont font, float x, float y) {
        font.draw(batch, "Fps: " + Gdx.graphics.getFramesPerSecond(), x, y);
    }
}
