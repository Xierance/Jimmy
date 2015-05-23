package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.TestClass;
import com.mygdx.game.worldHandler;

/**
 * Created by for example John on 5/10/2015.
 */
public class changeScreen {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////warning do not use/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void clearMap(World world) {
        //do not use will crash game
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != assetLoader.playerSprite && body.getType() != BodyDef.BodyType.StaticBody) {
                body.setUserData("destroyed");
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////warning do not use/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void mapToBox2d(String Map, World world) {
        TiledMap map = new TmxMapLoader().load(Map);
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("butt");
        Sprite tileSprite = new Sprite(new Texture("jew.jpg"));

        Sprite leftTile = new Sprite(assetLoader.hellBlocks.createSprite("Left"));
        leftTile.setSize(1f, 1f);
        Sprite rightTile = new Sprite(assetLoader.hellBlocks.createSprite("Right"));
        rightTile.setSize(1f, 1f);
        Sprite centerTile = new Sprite(assetLoader.hellBlocks.createSprite("Center"));
        centerTile.setSize(1f, 1f);
        Sprite topTile = new Sprite(assetLoader.hellBlocks.createSprite("Top"));
        topTile.setSize(1f, 1f);

        tileSprite.setSize(1f, 1f);


        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell tileCell = tileLayer.getCell(x, y);

                if (tileCell != null && tileCell.getTile() != null) {
                    if (tileCell.getTile().getProperties().get("playerBody") != null) {
                        worldHandler.temp = new Vector2(x, y);
                    }

                    if (tileCell.getTile().getProperties().get("enemy") != null) {
                        enemyPrototype test = new enemyPrototype(new Vector2(x, y));
                        test.createEnemy(world, 32f, 32f, 200);
                    }


                    if (tileCell.getTile().getProperties().get("block") != null) {
                        BodyDef tileBodyDef = new BodyDef();
                        tileBodyDef.type = BodyDef.BodyType.KinematicBody;
                        tileBodyDef.gravityScale = 0;
                        tileBodyDef.position.set(x, y);

                        PolygonShape tileShape = new PolygonShape();
                        tileShape.setAsBox(.5f, .5f);

                        Body body = world.createBody(tileBodyDef);
                        FixtureDef fixdef = new FixtureDef();
                        fixdef.shape = tileShape;
                        fixdef.filter.categoryBits = 0x0003;
                        body.createFixture(fixdef);

                        tileShape.dispose();

                        objectUserData userData = new objectUserData();
                        if (tileCell.getTile().getProperties().get("left") != null) {
                            userData.setSprite(leftTile);
                        } else if (tileCell.getTile().getProperties().get("right") != null) {
                            userData.setSprite(rightTile);
                        } else if (tileCell.getTile().getProperties().get("center") != null) {
                            userData.setSprite(centerTile);
                        } else if (tileCell.getTile().getProperties().get("top") != null) {
                            userData.setSprite(topTile);
                        }


                        body.setUserData(userData);
                    }
                }
            }
        }
    }

}

