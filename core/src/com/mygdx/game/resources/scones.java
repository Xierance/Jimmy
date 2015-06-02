package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.sun.xml.internal.bind.v2.model.core.ID;

/**
 * Created by Cian on 28/05/2015.
 */
public class scones  {
    objectUserData userData;
    Sprite sprite = assetLoader.test;
    Body body;
    BodyDef bodyDef = new BodyDef();
    FixtureDef  fixDef = new FixtureDef();
    CircleShape shape;
    String ID = "scone";

    public scones() {

        shape = new CircleShape();
        userData = new objectUserData();

    }

    public void createScone(Vector2 location, World world) {
        shape.setRadius(.5f);
        fixDef.density = 1;
        fixDef.restitution = 1;
        fixDef.shape = shape;

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        body = world.createBody(bodyDef);
        body.createFixture(fixDef);
        body.setGravityScale(0.5f);

        sprite.setSize(1, 1);
        sprite.setOrigin(.5f, .5f);
        userData.setSprite(sprite);
        userData.setId("scone");
        userData.setTime(-1000);

        userData.setTarget(enemyPrototype.getClosestEnemy(rayCast.enemies, location));
        body.setUserData(userData);

        shape.dispose();

    }
}