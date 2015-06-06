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
    static Array<enemyPrototype> enemies = new Array<enemyPrototype>();

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

    public static void clearBodies(World world) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body b : bodies) {
            if (b.getUserData()    instanceof objectUserData && ((objectUserData)b.getUserData()).getId() == "destroyed") {
                if (world.isLocked() == false && b.getType() != BodyDef.BodyType.StaticBody) {

                    for(Fixture f : b.getFixtureList())
                    {
                        b.destroyFixture(f);
                    }

                    b.setUserData(null );
                    world.destroyBody(b);

                }
            }else if(b.getUserData()    instanceof objectUserData && ((objectUserData)b.getUserData()).getId() == "dead"){
                ((objectUserData) b.getUserData()).getTarget().setAlive(false);
                ((objectUserData)b.getUserData()).setId("destroyed");
            }

            for(Fixture fixture:b.getFixtureList()) {
                if (fixture.getFilterData().categoryBits == 0x0004) {
                    if( ((objectUserData)b.getUserData()).getId() == "fireBall" ) {
                        ((objectUserData) b.getUserData()).getEffect().free();
                        EffectPools.FireTestPool.pooledEffects.removeValue(((objectUserData)b.getUserData()).getEffect(), true);

                    }
                    b.setUserData(null);
                    world.destroyBody(b);
                    projectiles.explode(b.getPosition());
                    projectiles.explode2(b.getPosition(), 9, world, 60);

                }
            }

            if(b.getUserData()    instanceof objectUserData && ((objectUserData)b.getUserData()).getId() == "enemy"){
                enemies.add(((objectUserData)b.getUserData()).getTarget());

            }

            if(b.getUserData()    instanceof objectUserData && ((objectUserData)b.getUserData()).getId() == "scone"){
                if(((objectUserData)b.getUserData()).getTime() > 0){
                    ((objectUserData)b.getUserData()).setId("destroyed");
                }
                else((objectUserData)b.getUserData()).setTime(((objectUserData)b.getUserData()).getTime()+1);
                float angle = projectiles.angle2(b.getPosition(),((objectUserData)b.getUserData()).getTarget().getPosition());

                b.applyForceToCenter((float)(10f*(Math.cos(angle))),(float)(10f*(Math.sin(angle))),true);

            }
        }
    }
}
