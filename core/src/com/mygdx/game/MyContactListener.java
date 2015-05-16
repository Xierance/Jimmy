package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.resources.assetLoader;
import com.mygdx.game.resources.healthDrop;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.resources.EffectPools;

public class MyContactListener implements ContactListener {

    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect && fb.getBody().getUserData() != assetLoader.playerSprite) {
            if (fb.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect) {
            } else {
                //((ParticleEffectPool.PooledEffect) fa.getUserData()).free();
                //EffectPools.FireTestPool.pooledEffects.removeValue((ParticleEffectPool.PooledEffect) fa.getBody().getUserData(), true);
                //fa.getBody().setUserData("explode");
                Filter filter = fb.getFilterData();
                filter.categoryBits = 0x0004;
                fa.setFilterData(filter);


            }
        }
        if (fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect && fa.getBody().getUserData() != assetLoader.playerSprite) {
            if (fa.getBody().getUserData() instanceof ParticleEffectPool.PooledEffect) {
            } else {

               // EffectPools.FireTestPool.pooledEffects.removeValue((ParticleEffectPool.PooledEffect) fb.getBody().getUserData(), true);
                //((ParticleEffectPool.PooledEffect) fb.getUserData()).free();
                // fb.getBody().setUserData("explode");
                Filter filter = fb.getFilterData();
                filter.categoryBits = 0x0004;
                fb.setFilterData(filter);


            }
        }


        //health stuff
        if (fa.getBody().getUserData() != null && fa.getBody().getUserData() == healthDrop.healthSprite && fb.getBody().getUserData() == assetLoader.playerSprite) {
            worldHandler.currentHealth++;
            Filter filter = fa.getFilterData();
            filter.categoryBits = 0x0004;
            fb.setFilterData(filter);
        }

        if (fb.getBody().getUserData() != null && fb.getBody().getUserData() == healthDrop.healthSprite && fa.getBody().getUserData() == assetLoader.playerSprite) {
            worldHandler.currentHealth++;
            Filter filter = fb.getFilterData();
            filter.categoryBits = 0x0004;
            fb.setFilterData(filter);

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
