package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Visuals.gameParticles.Explosion;
import com.mygdx.game.Visuals.gameParticles.Flame;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.things.Player;
import com.mygdx.game.things.projectiles;

public class MyContactListener implements ContactListener {

    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof Flame && fb.getBody().getUserData() != TestClass.playerSprite && fa.isSensor() == false) {
            if (fb.getBody().getUserData() instanceof Flame) {
            } else {
                TestClass.getToDestroy().add(fa.getBody());
            }

        }
        if (fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof Flame && fa.getBody().getUserData() != TestClass.playerSprite && fb.isSensor() == false) {
            if (fa.getBody().getUserData() instanceof Flame) {
            } else {
                TestClass.getToDestroy().add(fb.getBody());
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
