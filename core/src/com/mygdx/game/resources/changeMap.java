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
public class changeMap {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////warning do not use/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void clearMap(World world) {
        //do not use will crash game
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() instanceof objectUserData && ((objectUserData) body.getUserData()).getId() == "block" && body.getType() != BodyDef.BodyType.StaticBody) {
                for (Fixture fixture : body.getFixtureList()) {
                    body.destroyFixture(fixture);
                    body.setUserData("free");
                }
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////warning do not use/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Sprite leftTile = new Sprite(assetLoader.hellBlocks.createSprite("Left"));
    public static Sprite rightTile = new Sprite(assetLoader.hellBlocks.createSprite("Right"));
    public static Sprite centerTile = new Sprite(assetLoader.hellBlocks.createSprite("Center"));
    public static Sprite topTile = new Sprite(assetLoader.hellBlocks.createSprite("Top"));



    public static void mapToBox2d(String Map, World world) {


        TiledMap map = new TmxMapLoader().load(Map);
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("butt");
        Sprite tileSprite = new Sprite(new Texture("jew.jpg"));

        leftTile.setSize(1f, 1f);
        rightTile.setSize(1f, 1f);
        centerTile.setSize(1f, 1f);
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

                        FixtureDef fixdef = new FixtureDef();
                        fixdef.shape = tileShape;
                        fixdef.filter.categoryBits = 0x0003;
                        Body body = world.createBody(tileBodyDef);

                        body.createFixture(fixdef);

                        tileShape.dispose();

                        objectUserData userData = new objectUserData();
                        userData.setId("block");
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////warning do not use/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Array<Body> Bodies = new Array<Body>();
    public static Array<Body> Blocks = new Array<Body>();

    public static void rearrangeMap(String Map, World world) {

        TiledMap map = new TmxMapLoader().load(Map);
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("butt");

        leftTile.setSize(1f, 1f);
        rightTile.setSize(1f, 1f);
        centerTile.setSize(1f, 1f);
        topTile.setSize(1f, 1f);

        world.getBodies(Bodies);

        for (Body body : Bodies) {
            if (body.getUserData() instanceof objectUserData && ((objectUserData) body.getUserData()).getId() == "block" && body.getType() != BodyDef.BodyType.StaticBody) {
                for (Fixture fixture : body.getFixtureList()) {
                    body.destroyFixture(fixture);
                    body.setUserData(null);
                }
                Blocks.add(body);
            }
        }

        int temp = 0;

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

                        FixtureDef fixdef = new FixtureDef();
                        fixdef.shape = tileShape;
                        fixdef.filter.categoryBits = 0x0003;


                        objectUserData userData = new objectUserData();
                        userData.setId("block");
                        if (tileCell.getTile().getProperties().get("left") != null) {
                            userData.setSprite(leftTile);
                        } else if (tileCell.getTile().getProperties().get("right") != null) {
                            userData.setSprite(rightTile);
                        } else if (tileCell.getTile().getProperties().get("center") != null) {
                            userData.setSprite(centerTile);
                        } else if (tileCell.getTile().getProperties().get("top") != null) {
                            userData.setSprite(topTile);
                        }


                        Body body;
                        if (temp + 1 < Blocks.size && Blocks.get(temp) != null) {
                            Blocks.get(temp).setTransform(x, y, Blocks.get(temp).getAngle());
                            temp++;
                            Blocks.get(temp).createFixture(fixdef);
                            Blocks.get(temp).setUserData(userData);

                        } else {

                            body = world.createBody(tileBodyDef);
                            body.createFixture(fixdef);
                            body.setUserData(userData);
                        }
                        tileShape.dispose();

                        Blocks.clear();

                    }
                }
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////warning do not use/////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void addMap(int xOffset,int yOffset, String Map, World world){

        {


            TiledMap map = new TmxMapLoader().load(Map);
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("butt");

            leftTile.setSize(1f, 1f);
            rightTile.setSize(1f, 1f);
            centerTile.setSize(1f, 1f);
            topTile.setSize(1f, 1f);


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
                            tileBodyDef.position.set(x+xOffset, y+yOffset);

                            PolygonShape tileShape = new PolygonShape();
                            tileShape.setAsBox(.5f, .5f);

                            FixtureDef fixdef = new FixtureDef();
                            fixdef.shape = tileShape;
                            fixdef.filter.categoryBits = 0x0003;
                            Body body = world.createBody(tileBodyDef);

                            body.createFixture(fixdef);

                            tileShape.dispose();

                            objectUserData userData = new objectUserData();
                            userData.setId("block");
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

    public static Vector2 getMapDimensions(String Map){
        TiledMap map = new TmxMapLoader().load(Map);
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("butt");

        return new Vector2(tileLayer.getWidth(),tileLayer.getHeight());

    }

}



