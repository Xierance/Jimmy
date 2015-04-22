package com.mygdx.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Visuals.gameParticles.Explosion;
import com.mygdx.game.Visuals.gameParticles.Flame;
import com.mygdx.game.Visuals.gameParticles.Rainboom;

/**
 * Created by for example John on 4/7/2015.
 */
public class b2dStructures {

    public static void square(Vector2 location, float size, float density, float friction, TextureAtlas blocks, World world) {

        //blocks

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(size / 2, size / 2);
        fixtureDef.shape = blockShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = density;

        Sprite blockSprite = new Sprite(blocks.createSprite("Block Four"));

        blockSprite.setSize(size, size);
        blockSprite.setOrigin(size / 2, size / 2);

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(blockSprite);

        blockShape.dispose();
    }

    public static class explosionShard {
        public explosionShard(Vector2 location, float radius, float density, World world, Vector2 velocity,BodyDef bodyDef,FixtureDef fixtureDef,CircleShape circleShape){

            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(location.x, location.y);
            circleShape.setRadius(radius);
            fixtureDef.shape = circleShape;
            fixtureDef.restitution = 1f;
            fixtureDef.density = density;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(new Vector2());
            body.setGravityScale(0);

            body.setLinearVelocity(velocity);

        }
    }


    public static void isosceles(Vector2 location, float width, float height, float density, float friction, World world) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        PolygonShape blockShape = new PolygonShape();
        blockShape.set(new Vector2[]{new Vector2(0, 0), new Vector2(width, 0), new Vector2(width / 2, height)});
        fixtureDef.shape = blockShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = density;
        world.createBody(bodyDef).createFixture(fixtureDef);

        blockShape.dispose();

    }

    public static void line(Vector2 location, float angle, float length, float friction, World world) {
        //ground
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(location.x, location.y);
        ChainShape lineShape = new ChainShape();
        float Angle = 0;

        if (angle >= 0 && angle <= 360) Angle = angle;

        lineShape.createChain(new Vector2[]{new Vector2(0, 0), new Vector2((length * (float) Math.cos(Math.toRadians(Angle))), length * (float) Math.sin(Math.toRadians(Angle)))});
        fixtureDef.shape = lineShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0;
        world.createBody(bodyDef).createFixture(fixtureDef);

        lineShape.dispose();
    }

    public static Body lineAlt(Vector2 location, float angle, float length, float friction, Body body, World world) {
        //ground
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(location.x, location.y);
        ChainShape lineShape = new ChainShape();
        float Angle = 0;

        if (angle >= 0 && angle <= 360) Angle = angle;

        lineShape.createChain(new Vector2[]{new Vector2(0, 0), new Vector2((length * (float) Math.cos(Math.toRadians(Angle))), length * (float) Math.sin(Math.toRadians(Angle)))});
        fixtureDef.shape = lineShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        lineShape.dispose();

        return body;
    }

    public static void castle(Vector2 location, int height, float size, TextureAtlas blocks, World world) {

        if (size > 25) size = 25;
        float oldHeight = 0;
        float blockheight = size;
        for (int i = 0; i <= height; i++) {
            b2dStructures.square(new Vector2(location.x - (blockheight * 0.9f) / 2, location.y + oldHeight), blockheight * 0.9f, 0.2f, 0.75f, blocks, world);
            blockheight *= 0.9;
            oldHeight += blockheight;
        }

    }

    public static void castle2(Vector2 location, int height, float size, float gap, TextureAtlas blocks, World world) {
        if (height > 25) height = 25;
        if (size < 1) size = 1;
        float oldHeight = 0;
        float blockheight = size;
        for (int i = 0; i <= height; i++) {
            square(new Vector2(location.x - (blockheight * 0.9f) / 2 - gap / 2 - size / 2, location.y + oldHeight), blockheight * 0.9f, 0.2f, 0.75f, blocks, world);

            square(new Vector2(location.x - (blockheight * 0.9f) / 2 + gap / 2 + size / 2, location.y + oldHeight), blockheight * 0.9f, 0.2f, 0.75f, blocks, world);

            blockheight *= 0.9;
            oldHeight += blockheight;
        }
        isosceles(new Vector2(location.x - (blockheight) / 2 - gap / 2 - size / 2, location.y + oldHeight), blockheight, blockheight * 1.5f, 0.25f, 0.75f, world);
        isosceles(new Vector2(location.x - (blockheight) / 2 + gap / 2 + size / 2, location.y + oldHeight), blockheight, blockheight * 1.5f, 0.25f, 0.75f, world);
    }

}
