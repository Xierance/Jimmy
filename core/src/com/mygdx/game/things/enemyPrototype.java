package com.mygdx.game.things;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by for example John on 4/23/2015.
 */
public class enemyPrototype {
    public static  Array<enemyPrototype>  enemies = new Array<enemyPrototype>();
    public static  void updateenemies(World world){
        for(enemyPrototype enemy:enemies){
            enemy.update(world);
        }

    }

    private Vector2 startlocation;
    private boolean direction;
    Body enemyBody;
    public boolean alive;

    private Fixture leftSensor;
    private Fixture rightSensor;
    private Fixture leftSideSensor;
    private Fixture rightSideSensor;

    private boolean leftGround = false;
    private boolean rightGround = false;
    private boolean leftSide = false;
    private boolean rightSide = false;


    public enemyPrototype(Vector2 startLocation) {
        this.startlocation = startLocation;
        direction = true;


    }

    public void createEnemy(World world,Float Width, float Height) {
        direction = true;
        alive = true;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startlocation.x, startlocation.y);
        bodyDef.fixedRotation = true;

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(Width/2, Height/2);


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
        baseRectangle.setAsBox((Width - 0.01f)/4 , .05f, new Vector2(-Width/4  - 0.01f, -Height/2 - 0.05f), 0);

        fixtureDef.shape = baseRectangle;
        leftSensor = enemyBody.createFixture(fixtureDef);
        leftSensor.setSensor(true);

        baseRectangle.setAsBox((Width- 0.01f )/ 4 , .05f, new Vector2(Width/4 + 0.01f, -Height/2 - 0.05f), 0);
        fixtureDef.shape = baseRectangle;
        rightSensor = enemyBody.createFixture(fixtureDef);
        rightSensor.setSensor(true);

        //side sensors
        PolygonShape sideRectangle = new PolygonShape();

        sideRectangle.setAsBox(.05f, Height/2 - 0.05f, new Vector2(-Width/2 -0.02f, 0), 0);
        fixtureDef.shape = sideRectangle;
        leftSideSensor = enemyBody.createFixture(fixtureDef);
        leftSideSensor.setSensor(true);

        sideRectangle.setAsBox(.05f, Height/2 - 0.05f, new Vector2(Width/2 +0.02f, -0), 0);
        fixtureDef.shape = sideRectangle;
        rightSideSensor = enemyBody.createFixture(fixtureDef);
        rightSideSensor.setSensor(true);

        //dispose
        sideRectangle.dispose();
        baseRectangle.dispose();
        blockShape.dispose();

        enemyPrototype.enemies.add(this);

    }

    public void update(World world) {

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


        if(direction && rightSide && rightGround){
            enemyBody.setLinearVelocity(5f,enemyBody.getLinearVelocity().y);

        }else if (!direction && leftSide && leftGround){
            enemyBody.setLinearVelocity(-5f, enemyBody.getLinearVelocity().y);


        }else if(rightSide && rightGround){
            direction = true;
            enemyBody.setLinearVelocity(5f,enemyBody.getLinearVelocity().y);

        }else if (leftSide && leftGround){
            direction = false;
            enemyBody.setLinearVelocity(-5f, enemyBody.getLinearVelocity().y);


        } else if (!rightSide || !rightGround) {
            enemyBody.setLinearVelocity(new Vector2(-5f, enemyBody.getLinearVelocity().y));
            direction = false;


        }else if (!leftSide || !leftGround) {
            enemyBody.setLinearVelocity(new Vector2(5f, enemyBody.getLinearVelocity().y));
            direction = true;

//
//        }else if (direction && rightGround){
//            enemyBody.setLinearVelocity(5f,enemyBody.getLinearVelocity().y);
//
//        }else if (!direction && leftGround){
//            enemyBody.setLinearVelocity(-5f,enemyBody.getLinearVelocity().y);
        }

    }

}





