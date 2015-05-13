package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.worldHandler;

/**
 * Created by for example John on 4/11/2015.
 */
public class rayCast {
    static Fixture rayfixture;

    public static Fixture rayFixture(World world, Vector2 point1, Vector2 point2) {

        RayCastCallback rayCastCallback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getBody().getType() != BodyDef.BodyType.StaticBody) rayfixture = fixture;
                return 1;
            }
        };
        world.rayCast(rayCastCallback, point1, point2);
        return rayfixture;

    }

    public static void clearBodies( World world) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body b: bodies) {
            if(b.getUserData() instanceof String){
            if (world.isLocked() == false && b.getType() != BodyDef.BodyType.StaticBody && b.getUserData() != assetLoader.playerSprite) {
                if (b.getUserData() == "explode") {
                    projectiles.explode(b.getPosition());
                    projectiles.explode2(b.getPosition(), 36, world, 30);
                    ((ParticleEffectPool.PooledEffect) b.getUserData()).free();
                }

                final Array<JointEdge> list = b.getJointList();
                while (list.size > 0) {
                    world.destroyJoint(list.get(0).joint);
                }
                final Array<Fixture> fixtures = b.getFixtureList();
                while (fixtures.size > 0) {
                    b.destroyFixture(fixtures.get(0));
                }
                world.destroyBody(b);

            }
            }

        }
}}
