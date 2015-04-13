package com.mygdx.game.Visuals.gameParticles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.TestClass;

/**
 * Created by for example John on 4/9/2015.
 */
public class Flame {

    private static ParticleEffect flame;

    public  Flame(Vector2 location){
        flame = new ParticleEffect();
        flame.load(Gdx.files.local("effects/Flame.p"), Gdx.files.local("effects"));
        flame.scaleEffect(0.01f);
        flame.start();
    }

    public ParticleEffect getFlame() {
        return flame;
    }

}
