package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.utils.Array;

/**
 * Created by for example John on 4/9/2015.
 */
public class Player {
    private Vector2 startLocation;
    private Body playerBody;
    private Sprite playerSprite;
    private Fixture playerSensorFixture;


    public Vector2 getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Vector2 startLocation) {
        this.startLocation = startLocation;
    }

    ////////////////////////////////////////////////
    public Body getPlayerBody() {
        return playerBody;
    }

    public void setPlayerBody(Body playerBody) {
        this.playerBody = playerBody;
    }

    //////////////////////////////////////////////////
    public Sprite getPlayerSPrite() {
        return playerSprite;
    }

    public void setPlayerSPrite(Sprite playerSPrite) {
        this.playerSprite = playerSPrite;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public boolean isPlayerGrounded(World world, Player player) {
        Array<Contact> contactList = new Array<Contact>(world.getContactList());

        for (Contact contact : contactList) {
            if (contact.isTouching() && (contact.getFixtureA() == player.playerSensorFixture || contact.getFixtureB() == playerSensorFixture))
                return true;
        }
        return false;

    }

    public void createPLayer(World world, Vector2 startLocation, Sprite playerSprite) {
        //playerBody/////////////////////////////////

        //sprite
        this.setPlayerSPrite(playerSprite);

        playerSprite.setSize(1f, 2f);
        playerSprite.setOrigin(.5f, 1.5f);
        //body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startLocation.x, startLocation.y);
        bodyDef.fixedRotation = true;

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(.5f, 1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits = 0x0001;

        playerBody = world.createBody(bodyDef);
        playerBody.createFixture(fixtureDef);
        playerBody.setUserData(playerSprite);
        playerBody.setGravityScale(2);


        FixtureDef fDef = new FixtureDef();

        //Sensor setup
        PolygonShape baseRectangle = new PolygonShape();
        baseRectangle.setAsBox(.49f, .1f, new Vector2(0, -1f), 0);

        fDef.shape = baseRectangle;
        fDef.friction = 0.1f;
        fDef.restitution = 0;
        fDef.density = .1f;
        playerSensorFixture = playerBody.createFixture(fDef);
        playerSensorFixture.setSensor(true);

        baseRectangle.dispose();

    }
}
