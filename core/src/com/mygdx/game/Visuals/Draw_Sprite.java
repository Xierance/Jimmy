//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mygdx.game.Visuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Draw_Sprite {
    public Draw_Sprite() {
    }

    public void DrawSprite(SpriteBatch batch, Texture texture, float X, float Y, float rotation) {
        Sprite sprite = new Sprite(texture);
        sprite.setPosition(X, Y);
        sprite.setRotation(rotation);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
}
