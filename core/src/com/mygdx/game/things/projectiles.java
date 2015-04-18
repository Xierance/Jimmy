package com.mygdx.game.things;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Visuals.gameParticles.Explosion;
import com.mygdx.game.screens.TestClass;

/**
 * Created by for example John on 4/11/2015.
 */
public class projectiles {
    public static void bullet(Vector2 location, float density, float friction, Vector2 velocity, World world, Sprite dick) {
        //ball

        Body bulletBody;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        bodyDef.linearVelocity.set(velocity);
        bodyDef.bullet = true;

        PolygonShape bulletShaft = new PolygonShape();
        bulletShaft.set(new Vector2[]{
                new Vector2(-.8f, -.3f), new Vector2(-.6f, -0.3f),
                new Vector2(-.6f, -.2f),
                new Vector2(0.5f, -.2f), new Vector2(.5f, .2f),
                new Vector2(-.6f, .2f), new Vector2(-.6f, .3f),
                new Vector2(-.8f, .3f)
        });
        FixtureDef shaftFixDef = new FixtureDef();
        shaftFixDef.shape = bulletShaft;

        bulletBody = world.createBody(bodyDef);

        shaftFixDef.density = density / 3;
        shaftFixDef.friction = friction;
        shaftFixDef.restitution = .25f;
        bulletBody.createFixture(shaftFixDef);

        PolygonShape bulletHead = new PolygonShape();
        bulletHead.set(new Vector2[]{
                new Vector2(.5f, -0.2f), new Vector2(.9f, -.2f), new Vector2(.9f, .2f), new Vector2(.5f, .2f)
        });
        FixtureDef headFixDef = new FixtureDef();
        headFixDef.shape = bulletHead;

        headFixDef.density = density;
        headFixDef.friction = friction;
        headFixDef.restitution = 0.75f;
        bulletBody.createFixture(headFixDef);


        dick.setSize(1.69f, .69f);
        dick.setOrigin((float) 1.69 / 2, (float) .69 / 2);

        bulletBody.setUserData(dick);

        //direction
        bulletBody.setTransform(bulletBody.getPosition().x, bulletBody.getPosition().y, (float) Math.atan2((double) (velocity.y), (double) (velocity.x)));

        bulletShaft.dispose();
        bulletHead.dispose();
    }

    public static void fireBall(Vector2 location, Vector2 velocity, World world) {
        Body fireBallBody;
        BodyDef fireBallDef = new BodyDef();
        fireBallDef.bullet = true;
        fireBallDef.type = BodyDef.BodyType.DynamicBody;
        CircleShape fireBallBall = new CircleShape();
        fireBallBall.setRadius(.1f);
        FixtureDef fireBallFixDef = new FixtureDef();
        fireBallFixDef.shape = fireBallBall;
        fireBallBody = world.createBody(fireBallDef);
        fireBallBody.createFixture(fireBallFixDef);
        fireBallBody.setTransform(location, 0);
        fireBallBody.setLinearVelocity(velocity);
        fireBallBall.dispose();

        ParticleEffectPool.PooledEffect flame = EffectPools.FireTestPool.flamePoolTest.obtain();
        fireBallBody.setUserData(flame);
        EffectPools.FireTestPool.pooledEffects.add(flame);
        flame.dispose();

    }

    public static void explode(Vector2 location) {
        ParticleEffectPool.PooledEffect explosion = EffectPools.ExplosionTestPool.explosionPoolTest.obtain();
        EffectPools.ExplosionTestPool.pooledEffects.add(explosion);
        explosion.setPosition(location.x,location.y);
        explosion.start();

    }
    public static void explode2(Vector2 location,int bits,World world){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        for (int i = 0;i <= bits;i++){
            Vector2 velocity = new Vector2();
            velocity.x = 30* MathUtils.cosDeg((360 / bits) * i);
            velocity.y = 30*MathUtils.sinDeg((360/bits)*i);
            b2dStructures.explosionShard shard  = new b2dStructures.explosionShard(new Vector2(MathUtils.cosDeg((360 / bits) * i)/3 + location.x,MathUtils.sinDeg((360/bits)*i)/3 +location.y),0.01f,1000,0.5f,world,velocity,bodyDef,fixtureDef,circleShape);
        }
        circleShape.dispose();
    }

    public static void clearShards(Body body){

        if(body.getUserData() instanceof Vector2){
            if((Math.pow((double)((Vector2) body.getUserData()).x - body.getPosition().x,2) + Math.pow((double)((Vector2) body.getUserData()).y - body.getPosition().y,2)>=5)){
                TestClass.toDestroy.add(body);
            }
        }
    }
}
