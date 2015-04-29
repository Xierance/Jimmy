package com.mygdx.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.worldHandler;

/**
 * Created by for example John on 4/11/2015.
 */
public class projectiles {




    public static void dickBullet(Vector2 location, float density, float friction, Vector2 velocity, World world, Sprite dick) {
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

        worldHandler.fireBallCount++;

    }

    public static void explode(Vector2 location) {
        ParticleEffectPool.PooledEffect explosion = EffectPools.ExplosionTestPool.explosionPoolTest.obtain();
        EffectPools.ExplosionTestPool.pooledEffects.add(explosion);
        explosion.setPosition(location.x, location.y);
        explosion.start();

    }

    public static void explode2(Vector2 location, int bits, World world, float speed) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        for (int i = 0; i <= bits; i++) {
            Vector2 velocity = new Vector2();
            velocity.x = speed * MathUtils.cosDeg((360 / bits) * i);
            velocity.y = speed * MathUtils.sinDeg((360 / bits) * i);
            b2dStructures.explosionShard shard = new b2dStructures.explosionShard(new Vector2(MathUtils.cosDeg((360 / bits) * i) / 10 + location.x, MathUtils.sinDeg((360 / bits) * i) / 10 + location.y), 0.01f, 1000, world, velocity, bodyDef, fixtureDef, circleShape);
        }
        circleShape.dispose();
    }

    public static void clearShards(Body body, float tolerance) {

        if (body.getUserData() instanceof Vector2) {
            if (((Vector2) (body.getUserData())).x > tolerance) {
                TestClass.toDestroy.add(body);
            } else {
                body.setUserData(new Vector2(((Vector2) body.getUserData()).x + 1, ((Vector2) body.getUserData()).y));
            }
        }
    }

    public static void shootDick(Vector2 location, float angleRad,World world) {

        float i = (float) Math.cos(angleRad);
        float j = (float) Math.sin(angleRad);
        Vector2 newLocation = new Vector2(location.x + i, location.y + j);
        projectiles.dickBullet(newLocation, 10f, 0.75f, new Vector2(69 * i, 69 * j), world, new Sprite(new Texture("textures/dick.png")));

    }


    public static void shootFire(Vector2 location, float angleRad,World world) {
        if(worldHandler.fireBallCount < 1) {

            float i = (float) Math.cos(angleRad);
            float j = (float) Math.sin(angleRad);
            Vector2 newLocation = new Vector2(location.x + i, location.y + j);
            projectiles.fireBall(newLocation, new Vector2(20 * i, 20 * j), world);

        }
    }

    public static float angle2(Vector2 vector1, Vector2 vector2) {
        float angle = ((float) Math.atan2(vector2.y - vector1.y, vector2.x - vector1.x));
        return angle;
    }
}
