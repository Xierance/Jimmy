package com.mygdx.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.TestClass;

/**
 * Created by for example John on 4/9/2015.
 */
public class Player {
    public void setStartLocation(Vector2 startLocation) {
        this.startLocation = startLocation;
    }

    public Vector2 getStartLocation() {
        return startLocation;
    }

    private Vector2 startLocation;
////////////////////////////////////////////////
    public Body getPlayerBody() {
        return playerBody;
    }

    public void setPlayerBody(Body playerBody) {
        this.playerBody = playerBody;
    }

    private Body playerBody;
    //////////////////////////////////////////////////
    public Sprite getPlayerSPrite() {
        return playerSprite;
    }

    public void setPlayerSPrite(Sprite playerSPrite) {
        this.playerSprite = playerSPrite;
    }


    private Sprite playerSprite;

    public Body getPlayerWheelBody() {
        return playerWheelBody;
    }

    public void setPlayerWheelBody(Body playerWheelBody) {
        this.playerWheelBody = playerWheelBody;
    }

    private Body playerWheelBody;

    public WheelJoint getWheelJoint() {
        return wheelJoint;
    }

    public void setWheelJoint(WheelJoint wheelJoint) {
        this.wheelJoint = wheelJoint;
    }

    private WheelJoint wheelJoint;

    public Fixture getPlayerSensorFixture() {
        return playerSensorFixture;
    }

    public void setPlayerSensorFixture(Fixture playerSensorFixture) {
        this.playerSensorFixture = playerSensorFixture;
    }

    private Fixture playerSensorFixture;

    ////////////////////////////////////////////////////////////////////////////////////////


    public void setPlayerGrounded(boolean isPlayerGrounded) {
        this.isPlayerGrounded = isPlayerGrounded;
    }

    //////////////////////////////////////////////////
    private boolean isPlayerGrounded;

    public boolean isPlayerGrounded(World world,Player player) {
        Array<Contact> contactList = new Array<Contact>(world.getContactList());

        for (int i = 0; i < contactList.size; i++) {
            Contact contact = contactList.get(i);
            if (contact.isTouching() && (contact.getFixtureA() == player.playerSensorFixture || contact.getFixtureB() == playerSensorFixture))
                return true;
        }
        return false;
    }

    public void createPLayer(World world,Vector2 startLocation,Sprite playerSprite){
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
        fixtureDef.restitution = .25f;

        playerBody = world.createBody(bodyDef);
        playerBody.createFixture(fixtureDef);
        playerBody.setUserData(playerSprite);

        FixtureDef fDef = new FixtureDef();

        //Sensor setup
        PolygonShape baseRectangle = new PolygonShape();
        baseRectangle.setAsBox(1, .2f, new Vector2(0, -2.2f), 0);

        fDef.shape = baseRectangle;
        fDef.friction = 0.1f;
        fDef.restitution = 0;
        fDef.density = .1f;
        playerSensorFixture = playerBody.createFixture(fDef);
        playerSensorFixture.setSensor(true);

        baseRectangle.dispose();

    }
}
