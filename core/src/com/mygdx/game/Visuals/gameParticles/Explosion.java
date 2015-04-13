package com.mygdx.game.Visuals.gameParticles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by for example John on 4/12/2015.
 */
public class Explosion {


    private ParticleEffect explosion;

    public Explosion(Vector2 location) {
        explosion = new ParticleEffect();
        explosion.load(Gdx.files.local("effects/Explosion.p"), Gdx.files.local("effects"));
        explosion.setPosition(location.x, location.y);
        explosion.scaleEffect(0.0069f);
        explosion.start();
    }

    public ParticleEffect getExplosion() {
        return explosion;
    }

    public void setExplosion(ParticleEffect explsoion) {
        this.explosion = explosion;
    }


    public void update(SpriteBatch batch, Float delta, Vector2 position) {
        explosion.setPosition(position.x, position.y);
        explosion.draw(batch, delta);

    }

    public void setPosition(Vector2 position) {
        explosion.setPosition(position.x, position.y);
    }

    public static Array<Explosion> Explosions = new Array<Explosion>();

    public static void drawExplosions(SpriteBatch batch, Float delta) {
        for (Explosion explosion : Explosions) explosion.getExplosion().draw(batch, delta);
    }
}
