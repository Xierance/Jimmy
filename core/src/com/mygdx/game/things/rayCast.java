package com.mygdx.game.things;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Visuals.gameParticles.Flame;
import com.mygdx.game.screens.TestClass;

/**
 * Created by for example John on 4/11/2015.
 */
public class rayCast {
    static Fixture rayfixture;
    public static Fixture rayFixture(World world ,Vector2 point1,Vector2 point2){

        RayCastCallback rayCastCallback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if( fixture.getBody().getType() != BodyDef.BodyType.StaticBody )rayfixture = fixture;
                return 1;
            }
        };
        world.rayCast(rayCastCallback,point1,point2);
        return rayfixture;

    }

    public static void clearBodies(Array<Body> toDestroy,World world){
        for(Body b : toDestroy)if(world.isLocked() == false && b.getType()!= BodyDef.BodyType.StaticBody && b.getUserData() != TestClass.playerSprite){
            if(b.getUserData() instanceof ParticleEffectPool.PooledEffect)projectiles.explode(b.getPosition());
            world.destroyBody(b);

        }
        toDestroy.clear();

    }

}
