package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.resources.assetLoader;
import com.mygdx.game.resources.healthDrop;
import com.mygdx.game.resources.projectiles;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.resources.EffectPools;

public class MyContactListener implements ContactListener {

    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect && fb.getBody().getUserData() != assetLoader.playerSprite && fa.isSensor() == false) {
            if (fb.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect) {
            } else {
                TestClass.getToDestroy().add(fa.getBody());
                EffectPools.FireTestPool.pooledEffects.removeValue((ParticleEffectPool.PooledEffect) fa.getBody().getUserData(), true);

            }
        }
        if (fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect && fa.getBody().getUserData() != assetLoader.playerSprite && fb.isSensor() == false) {
            if (fa.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect) {
            } else {
                TestClass.getToDestroy().add(fb.getBody());
                EffectPools.FireTestPool.pooledEffects.removeValue((ParticleEffectPool.PooledEffect) fb.getBody().getUserData(), true);

            }
        }


        //health stuff
        if(fa.getBody().getUserData()!= null && fa.getBody().getUserData() == healthDrop.healthSprite && fb.getBody().getUserData() == assetLoader.playerSprite){
            worldHandler.currentHealth++;
            TestClass.toDestroy.add(fa.getBody());
        }
        if(fb.getBody().getUserData()!= null && fb.getBody().getUserData() == healthDrop.healthSprite && fa .getBody().getUserData() == assetLoader.playerSprite){
            worldHandler.currentHealth++;
            TestClass.toDestroy.add(fb.getBody());
        }
        if(fa.getBody().getUserData()!= null && fa.getBody().getUserData() == projectiles.fireBallBody && fb.getBody().getUserData() == assetLoader.playerSprite){
            worldHandler.currentHealth--;
            TestClass.toDestroy.add(fa.getBody());
        }
        if(fb.getBody().getUserData()!= null && fb.getBody().getUserData() ==  projectiles.fireBallBody && fa .getBody().getUserData() == assetLoader.playerSprite){
            worldHandler.currentHealth--;
            TestClass.toDestroy.add(fb.getBody());
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
