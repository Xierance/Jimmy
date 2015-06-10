package com.mygdx.game.Visuals.gameParticles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.resources.EffectPools;

/**
 * Created by for example John on 4/12/2015.
 */
public class deathEffect {


    private ParticleEffect deathEffect;

    public deathEffect() {
        deathEffect = new ParticleEffect();
        deathEffect.load(Gdx.files.local("effects/blood.p"), Gdx.files.local("effects"));
        deathEffect.scaleEffect(0.01f);
        deathEffect.start();
    }

    public static void drawDeaths(SpriteBatch batch, Float delta) {
        for (ParticleEffectPool.PooledEffect effect : EffectPools.deathEffectPool.pooledEffects){
            effect.draw(batch, delta);
            if(effect.isComplete()){
                EffectPools.deathEffectPool.pooledEffects.removeValue(effect,true);
                effect.free();
            }
        }
    }

    public ParticleEffect getDeathEffect() {
        return deathEffect;
    }


    public void update(SpriteBatch batch, Float delta, Vector2 position) {
        deathEffect.setPosition(position.x, position.y);
        deathEffect.draw(batch, delta);

    }

    public void setPosition(Vector2 position) {
        deathEffect.setPosition(position.x, position.y);
    }


}
