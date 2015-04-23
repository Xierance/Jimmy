package com.mygdx.game.things;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by for example John on 4/23/2015.
 */
public class enemyPrototype {

    private Vector2 startlocation;
    private boolean direction;
    Body enemyBody;
    public boolean alive;

    public enemyPrototype(Vector2 startLocation){
        this.startlocation = startLocation;
        direction = true;


    }

    public void createEnemy(World world){
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

        blockShape.dispose();

    }

    public void update(){
        if(direction == true && enemyBody.getPosition().x - startlocation.x > 5)direction = false;
        if(direction == false && startlocation.x - enemyBody.getPosition().x > 5)direction = true;
        if(direction == true)
            enemyBody.setLinearVelocity(new Vector2(5f  , 0));
        if(direction == false)
            enemyBody.setLinearVelocity(new Vector2( -5f,0));
        }




    }


