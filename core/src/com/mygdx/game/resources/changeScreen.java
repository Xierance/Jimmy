package com.mygdx.game.resources;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.TestClass;

/**
 * Created by for example John on 5/10/2015.
 */
public class changeScreen {

    public static void clearMap(World world){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body: bodies){
            if(body.getUserData() != assetLoader.playerSprite && body.getType() != BodyDef.BodyType.StaticBody) TestClass.toDestroy.add(body);
        }

    }

    public static void loadNewMap(){

    }

}
