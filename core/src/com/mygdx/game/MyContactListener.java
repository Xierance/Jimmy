package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.things.EffectPools;
import com.mygdx.game.things.projectiles;

public class MyContactListener implements ContactListener {

    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect && fb.getBody().getUserData() != TestClass.playerSprite && fa.isSensor() == false) {
            if (fb.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect) {
            } else {
                TestClass.getToDestroy().add(fa.getBody());
                EffectPools.FireTestPool.pooledEffects.removeValue((ParticleEffectPool.PooledEffect) fa.getBody().getUserData(), true);
                ((ParticleEffectPool.PooledEffect) fa.getBody().getUserData()).free();

            }
        }
        if (fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect && fa.getBody().getUserData() != TestClass.playerSprite && fb.isSensor() == false) {
            if (fa.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect) {
            } else {
                TestClass.getToDestroy().add(fb.getBody());
                EffectPools.FireTestPool.pooledEffects.removeValue((ParticleEffectPool.PooledEffect) fb.getBody().getUserData(), true);
                ((ParticleEffectPool.PooledEffect) fb.getBody().getUserData()).free();

            }
        }
    }

    public void endContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

    }

    public void preSolve(Contact c, Manifold m) {
    }

    public void postSolve(Contact c, ContactImpulse ci) {
    }

}
