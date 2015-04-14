package com.mygdx.game.things;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Visuals.gameParticles.Flame;

/**
 * Created by for example John on 4/13/2015.
 */
public class FireBallPool {
    public static class TestPool {

        public static Array<ParticleEffectPool.PooledEffect> pooledEffects = new Array<ParticleEffectPool.PooledEffect>();

        public static ParticleEffectPool flamePoolTest = new ParticleEffectPool(new Flame(new Vector2()).getFlame(), 0, 300);

    }
}