package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.TestClass;

import java.util.Random;

/**
 * Created by for example John on 4/23/2015.
 */

public class enemyPrototype {
    public static Array<enemyPrototype> enemies = new Array<enemyPrototype>();

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean alive;
    Body enemyBody;
    private Sprite sprite = new Sprite(assetLoader.shrek);
    private int timer;
    private int frequency;
    Vector2 startLocation;

    public static void updateenemies(World world) {
        for (enemyPrototype enemy : enemies) {

            enemy.update();
        }
    }

    public enemyPrototype(Vector2 startlocation){
        startLocation = startlocation;
    }

    public static enemyPrototype getClosestEnemy(Array<enemyPrototype> enemies, Vector2 origin) {

        float[] enemyDistance = new float[enemies.size];
        int i = 0;
        for (enemyPrototype enemy : enemies) {
            float x = enemy.enemyBody.getPosition().x - origin.x;
            float y = enemy.enemyBody.getPosition().y - origin.y;
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

    public Vector2 getPosition() {
        return enemyBody.getPosition();

    }

    public void createEnemy(World world, Float Width, float Height, int Frequency) {

        timer = 0;
        frequency = Frequency;
        alive = true;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startLocation.x, startLocation.y);
        bodyDef.fixedRotation = true;

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(Width / 2, Height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .25f;

        enemyBody = world.createBody(bodyDef);
        fixtureDef.filter.categoryBits = 0x0001;
        enemyBody.createFixture(fixtureDef);
        blockShape.dispose();

        sprite.setSize(Width, Height);

        objectUserData userData = new objectUserData();
        userData.setSprite(sprite);
        userData.setId("enemy");
        userData.setTarget(this);
        enemyBody.setUserData(userData);

        enemyPrototype.enemies.add(this);

    }

    public void update() {



        if (alive) {
            int movement = TestClass.randomGenerator.nextInt(2);

            timer++;
            if (timer > frequency) {
                timer = 0;

                switch (movement) {
                    case 0:
                        enemyBody.applyLinearImpulse(10,10,0,0,true);
                        break;
                    case 1:
                        enemyBody.applyLinearImpulse(-10,12,0,0,true);
                        break;
                }
            }
        }
    }
}
