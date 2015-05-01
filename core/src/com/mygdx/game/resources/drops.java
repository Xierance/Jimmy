package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Visuals.gameParticles.Explosion;

/**
 * Created by for example John on 5/1/2015.
 */


public class drops {


    public static Sprite healthSprite = new Sprite(new Texture("img/player.png"));

    public class healthDrop {
        public healthDrop(){


        }


        public void createHealthDrop(World world,Vector2 position){



            CircleShape shape = new CircleShape();
            FixtureDef fixtureDef = new FixtureDef();
            BodyDef bodyDef   =  new BodyDef();
            Body body = world.createBody(bodyDef);

            shape.setRadius(0.5f);
            fixtureDef.shape = shape;
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setSensor(true);

            body.setTransform(position,0);
            shape.dispose();

            body.setUserData(healthSprite);

        }

    }
}
