package com.mygdx.game.things;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by for example John on 4/23/2015.
 */
public class enemyPrototype {

    private Vector2 startlocation;
    private boolean direction;
    Body enemyBody;
    public boolean alive;

    private Fixture leftSensor;
    private Fixture rightSensor;

    private boolean leftGround = false;
    private boolean rightGround = false;


    public enemyPrototype(Vector2 startLocation) {
        this.startlocation = startLocation;
        direction = true;


    }

    public void createEnemy(World world) {
        alive = true;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startlocation.x, startlocation.y);
        bodyDef.fixedRotation = true;

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(.5f, 1f);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .25f;

        enemyBody = world.createBody(bodyDef);
        enemyBody.createFixture(fixtureDef);
        //enemyBody.setUserData(Sprite);

        //Sensor setup
        PolygonShape baseRectangle = new PolygonShape();
        baseRectangle.setAsBox(.224f, .05f, new Vector2(-.25f, -1.06f), 0);

        fixtureDef.shape = baseRectangle;
        leftSensor = enemyBody.createFixture(fixtureDef);
        leftSensor.setSensor(true);

        baseRectangle.setAsBox(.224f, .05f, new Vector2(.26f, -1.06f), 0);
        fixtureDef.shape = baseRectangle;
        rightSensor = enemyBody.createFixture(fixtureDef);
        rightSensor.setSensor(true);

        baseRectangle.dispose();
        blockShape.dispose();

    }

    public void update(World world) {

        leftGround = false;
        rightGround = false;

        Array<Contact> contactList = new Array<Contact>(world.getContactList());

        for (Contact contact : contactList) {
            if (contact.isTouching() && (contact.getFixtureA() == leftSensor || contact.getFixtureB() == leftSensor))
            leftGround = true;

            if (contact.isTouching() && (contact.getFixtureA() == rightSensor || contact.getFixtureB() == rightSensor))

            rightGround = true;
        }

        if (direction == true && enemyBody.getPosition().x - startlocation.x > 5 && rightGround) direction = false;
        if (direction == false && startlocation.x - enemyBody.getPosition().x > 5 && leftGround) direction = true;


        if (direction == true)
            enemyBody.setLinearVelocity(new Vector2(5f, enemyBody.getLinearVelocity().y));
        if (direction == false)
            enemyBody.setLinearVelocity(new Vector2(-5f, enemyBody.getLinearVelocity().y));
    }


}


