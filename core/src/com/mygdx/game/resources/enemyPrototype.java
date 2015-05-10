package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.TestClass;

/**
 * Created by for example John on 4/23/2015.
 */
public class enemyPrototype {
    public static Array<enemyPrototype> enemies = new Array<enemyPrototype>();
    public boolean alive;
    Body enemyBody;
    private Vector2 startlocation;
    private boolean direction;
    private Fixture leftSensor;
    private Fixture rightSensor;
    private Fixture leftSideSensor;
    private Fixture rightSideSensor;
    private boolean leftGround = false;
    private boolean rightGround = false;
    private boolean leftSide = false;
    private boolean rightSide = false;
    private Sprite sprite = new Sprite(assetLoader.shrek);

    public enemyPrototype(Vector2 startLocation) {
        this.startlocation = startLocation;
        direction = true;


    }

    public Vector2 getPosition() {
        return enemyBody.getPosition();

    }

    public static void updateenemies(World world) {
        for (enemyPrototype enemy : enemies) {
            enemy.update(world);
        }

    }

    public void createEnemy(World world, Float Width, float Height) {
        direction = true;
        alive = true;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startlocation.x, startlocation.y);
        bodyDef.fixedRotation = true;

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(Width / 2, Height / 2);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .25f;

        enemyBody = world.createBody(bodyDef);
        enemyBody.createFixture(fixtureDef);
        //enemyBody.setUserData(Sprite);

        //Sensor setup
        PolygonShape baseRectangle = new PolygonShape();
        baseRectangle.setAsBox((Width - 0.01f) / 4, .05f, new Vector2(-Width / 4 - 0.01f, -Height / 2 - 0.05f), 0);

        fixtureDef.shape = baseRectangle;
        leftSensor = enemyBody.createFixture(fixtureDef);
        leftSensor.setSensor(true);

        baseRectangle.setAsBox((Width - 0.01f) / 4, .05f, new Vector2(Width / 4 + 0.01f, -Height / 2 - 0.05f), 0);
        fixtureDef.shape = baseRectangle;
        rightSensor = enemyBody.createFixture(fixtureDef);
        rightSensor.setSensor(true);

        //side sensors
        PolygonShape sideRectangle = new PolygonShape();

        sideRectangle.setAsBox(.05f, Height / 2 - 0.05f, new Vector2(-Width / 2 - 0.02f, 0), 0);
        fixtureDef.shape = sideRectangle;
        leftSideSensor = enemyBody.createFixture(fixtureDef);
        leftSideSensor.setSensor(true);

        sideRectangle.setAsBox(.05f, Height / 2 - 0.05f, new Vector2(Width / 2 + 0.02f, -0), 0);
        fixtureDef.shape = sideRectangle;
        rightSideSensor = enemyBody.createFixture(fixtureDef);
        rightSideSensor.setSensor(true);

        //dispose
        sideRectangle.dispose();
        baseRectangle.dispose();
        blockShape.dispose();

        sprite.setSize(Width, Height);
        enemyBody.setUserData(sprite);

        enemyPrototype.enemies.add(this);

    }

    public void update(World world) {
        if (alive) {

            leftGround = false;
            rightGround = false;
            rightSide = true;
            leftSide = true;

            Array<Contact> contactList = new Array<Contact>(world.getContactList());

            for (Contact contact : contactList) {
                if (contact.isTouching() && (contact.getFixtureA() == leftSensor && contact.getFixtureB().getBody() != contact.getFixtureA().getBody() || contact.getFixtureB().getBody() != contact.getFixtureA().getBody() && contact.getFixtureB() == leftSensor)) {
                    leftGround = true;
                }

                if (contact.isTouching() && (contact.getFixtureA() == rightSensor && contact.getFixtureB().getBody() != contact.getFixtureA().getBody() || contact.getFixtureB().getBody() != contact.getFixtureA().getBody() && contact.getFixtureB() == rightSensor)) {
                    rightGround = true;
                }

                if (contact.isTouching() && (contact.getFixtureA() == leftSideSensor && contact.getFixtureB().getBody() != contact.getFixtureA().getBody() || contact.getFixtureB().getBody() != contact.getFixtureA().getBody() && contact.getFixtureB() == leftSideSensor)) {
                    leftSide = false;
                }

                if (contact.isTouching() && (contact.getFixtureA() == rightSideSensor && contact.getFixtureB().getBody() != contact.getFixtureA().getBody() || contact.getFixtureB().getBody() != contact.getFixtureA().getBody() && contact.getFixtureB() == rightSideSensor)) {
                    rightSide = false;
                }

            }

            if (direction && rightSide && rightGround) {
                enemyBody.setLinearVelocity(5f, enemyBody.getLinearVelocity().y);

            } else if (!direction && leftSide && leftGround) {
                enemyBody.setLinearVelocity(-5f, enemyBody.getLinearVelocity().y);

            } else if (rightSide && rightGround) {
                direction = true;
                enemyBody.setLinearVelocity(5f, enemyBody.getLinearVelocity().y);

            } else if (leftSide && leftGround) {
                direction = false;
                enemyBody.setLinearVelocity(-5f, enemyBody.getLinearVelocity().y);

            } else if (!rightSide || !rightGround) {
                enemyBody.setLinearVelocity(new Vector2(-5f, enemyBody.getLinearVelocity().y));
                direction = false;

            } else if (!leftSide || !leftGround) {
                enemyBody.setLinearVelocity(new Vector2(5f, enemyBody.getLinearVelocity().y));
                direction = true;
            }

            if (!leftGround && !rightGround) enemyBody.applyLinearImpulse(new Vector2(0, -5), new Vector2(), true);

        }else{
            leftSensor.setSensor(false);
            leftSideSensor.setSensor(false);
            rightSensor.setSensor(false);
            TestClass.toDestroy.add(enemyBody);
        }
    }

    public static enemyPrototype getClosestEnemy(Array<enemyPrototype> enemies, Player player) {
        Array<enemyPrototype> newEnemies = new Array<enemyPrototype>();
        float[] enemyDistance = new float[enemies.size];
        int i = 0;
        for (enemyPrototype enemy : enemies) {
            float x = enemy.getPosition().x - player.getPlayerBody().getPosition().x;
            float y = enemy.getPosition().y - player.getPlayerBody().getPosition().y;
            enemyDistance[i] = (x * x + y * y);
            i++;
        }

        float shortest = 1000000000;
        int index = 0;
        i = 0;
        for (float f : enemyDistance) {
            if (f < shortest) {
                shortest = f;
                index = i;
            }
            i++;
        }

        return enemies.get(index);
    }

}





