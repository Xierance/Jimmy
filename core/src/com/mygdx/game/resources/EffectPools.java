package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Visuals.gameParticles.Explosion;
import com.mygdx.game.Visuals.gameParticles.Flame;

/**
 * Created by for example John on 4/13/2015.
 */
public class EffectPools {
    public static class FireTestPool {

        public static Array<ParticleEffectPool.PooledEffect> pooledEffects = new Array<ParticleEffectPool.PooledEffect>();

        public static ParticleEffectPool flamePoolTest = new ParticleEffectPool(new Flame(new Vector2()).getFlame(), 0, 300);

    }
    public static class ExplosionTestPool {

        public static Array<ParticleEffectPool.PooledEffect> pooledEffects = new Array<ParticleEffectPool.PooledEffect>();

        public static ParticleEffectPool explosionPoolTest = new ParticleEffectPool(new Explosion().getExplosion(), 0, 300);

    }
}