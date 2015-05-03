package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Visuals.gameParticles.Explosion;
import com.mygdx.game.Visuals.ui;
import com.mygdx.game.inputHandler;
import com.mygdx.game.resources.*;
import com.mygdx.game.worldHandler;

/**
 * Created by for example John on 3/14/2015.
 */
public class TestClass implements Screen {


    public static Vector2 temp = new Vector2();
    public static Body tempBody;
    public static Array<Body> toDestroy = new Array<Body>();
    public static Array<ParticleEffect> flames = new Array<ParticleEffect>();
    public static Player player = new Player();
    public static  World world;
    public static OrthographicCamera orthographicCamera;
    public static Vector3 tmp = new Vector3();
    private static boolean Q;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    enemyPrototype test2;
    healthDrop test3;
    healthDrop test4;
    private ui UI;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera secondCamera;
    private SpriteBatch batch;
    private SpriteBatch secondBatch;
    private MouseJointDef jointDef;
    private Sprite block1 = new Sprite();
    private enemyPrototype test;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private MyContactListener cl;
    private Array<Body> tmpBodies = new Array<Body>();
    private Vector2 tmp2 = new Vector2();
    private boolean tempb = true;

    public static Array<Body> getToDestroy() {
        return toDestroy;
    }

    public static void mapToBox2d(TiledMap map, World world) {
        map = new TmxMapLoader().load("maps/testMap.tmx");
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
                        temp = new Vector2(x, y);
                    }

                    if (tileCell.getTile().getProperties().get("enemy") != null) {
                        enemyPrototype test = new enemyPrototype(new Vector2(x, y));
                        test.createEnemy(world, 32f, 32f);
                    }


                    if (tileCell.getTile().getProperties().get("block") != null) {
                        BodyDef tileBodyDef = new BodyDef();
                        tileBodyDef.type = BodyDef.BodyType.KinematicBody;
                        tileBodyDef.gravityScale = 0;
                        tileBodyDef.position.set(x, y);

                        PolygonShape tileShape = new PolygonShape();
                        tileShape.setAsBox(.5f, .5f);

                        Body body = world.createBody(tileBodyDef);
                        body.createFixture(tileShape, 0f);

                        tileShape.dispose();


                        if (tileCell.getTile().getProperties().get("left") != null) {
                            body.setUserData(leftTile);
                        } else if (tileCell.getTile().getProperties().get("right") != null) {
                            body.setUserData(rightTile);
                        } else if (tileCell.getTile().getProperties().get("center") != null) {
                            body.setUserData(centerTile);
                        } else if (tileCell.getTile().getProperties().get("top") != null) {
                            body.setUserData(topTile);
                        }
                    }
                }
            }
        }
    }

    public static Vector2 getmouseCoords() {
        Vector3 temp = new Vector3();
        orthographicCamera.unproject(temp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(temp.x, temp.y);
    }

    @Override
    public void show() {

        assetLoader.loadAssets();

        UI = new ui();

        jointDef = new MouseJointDef();

        batch = new SpriteBatch();
        secondBatch = new SpriteBatch();


        world = new World(new Vector2(0f, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth() / 32, Gdx.graphics.getHeight() / 32);
        secondCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //32:1

        //input


        inputHandler input = new inputHandler();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(inputMultiplexer);



        //ground
        BodyDef tempdef = new BodyDef();
        Body ground = world.createBody(tempdef);
        ground = b2dStructures.lineAlt(new Vector2(-300f, -10f), 0, 3000, 0.25f, ground, world);
        b2dStructures.line(new Vector2(-30, -10), 90, 100, 0.5f, world);

        b2dStructures.castle(new Vector2(50, -9), 25, 5f, assetLoader.blocks, world);
        b2dStructures.castle(new Vector2(20, -9), 25, 2, assetLoader.blocks, world);
        b2dStructures.castle2(new Vector2(5, -9), 15, 1, 2, assetLoader.blocks, world);
        b2dStructures.castle2(new Vector2(5, -9), 12, 2, 3.5f, assetLoader.blocks, world);
        b2dStructures.castle2(new Vector2(30, -9), 30, 1, 9, assetLoader.blocks, world);
        b2dStructures.isosceles(new Vector2(30, 0), 1, 1.5f, 0.2f, 0.25f, world);

        //////////////////////mouse joint
        jointDef.bodyA = ground;
        jointDef.collideConnected = true;
        jointDef.maxForce = 50;

        mapToBox2d(new TiledMap(), world);///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        player.createPLayer(world, temp, assetLoader.playerSprite);

        cl = new MyContactListener();
        world.setContactListener(cl);

        //test stuff
        test = new enemyPrototype(new Vector2(60, -9));
        test.createEnemy(world, 1f, 2f);
        test2 = new enemyPrototype(new Vector2(20, 12));
        test2.createEnemy(world, 2f, 2f);

        test3 = new healthDrop();
        test3.createHealthDrop(world, new Vector2(10, 10));
        test4 = new healthDrop();
        test4.createHealthDrop(world, new Vector2(10, 30));
        healthDrop test5 = new healthDrop();
        test5.createHealthDrop(world, new Vector2(11, 30));
        healthDrop test6 = new healthDrop();
        test6.createHealthDrop(world, new Vector2(12, 30));


    }

    @Override
    public void render(float delta) {
        System.out.println(worldHandler.currentHealth);
        enemyPrototype.updateenemies(world);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, orthographicCamera.combined);

        batch.setProjectionMatrix(orthographicCamera.combined);

        if (world.isLocked() == false) {
            world.getBodies(tmpBodies);
            for (Body body : tmpBodies) projectiles.clearShards(body, 3);

            rayCast.clearBodies(toDestroy, world);
        }

        batch.begin();
        drawSprites(delta);
        Explosion.drawExplosions(batch, delta);
        batch.end();

        secondBatch.setProjectionMatrix(secondCamera.combined);
        secondCamera.update();
        secondBatch.begin();
        UI.displayMax(secondBatch);
        UI.displayFps(secondBatch);
        UI.hotBar(secondBatch);
        secondBatch.end();

        handleInput();
        cameraFollow();

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.viewportWidth = width / 32;
        orthographicCamera.viewportHeight = height / 32;

        secondCamera.viewportHeight = height;
        secondCamera.viewportWidth = width;

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {

        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        secondBatch.dispose();
        player.getPlayerSPrite().getTexture().dispose();
        for (ParticleEffect effect : flames) effect.dispose();
        for (ParticleEffectPool.PooledEffect effect : EffectPools.ExplosionTestPool.pooledEffects) effect.dispose();
        for (ParticleEffectPool.PooledEffect effect : EffectPools.FireTestPool.pooledEffects) effect.dispose();

    }

    public void cameraFollow() {
        float lerp = .1f;
        if (!inputHandler.M) {
            if (orthographicCamera.position.x != player.getPlayerBody().getPosition().x) {
                orthographicCamera.position.x += (player.getPlayerBody().getPosition().x - orthographicCamera.position.x) * lerp;
            }
            if (orthographicCamera.position.y != player.getPlayerBody().getPosition().y) {
                orthographicCamera.position.y += (player.getPlayerBody().getPosition().y - orthographicCamera.position.y) * lerp;
            }
            orthographicCamera.update();
        }

        if (inputHandler.Q) {
            orthographicCamera.zoom += .010f;
            orthographicCamera.update();
        }
        if (inputHandler.E) {
            orthographicCamera.zoom -= .010f;
            orthographicCamera.update();
        }

        if (inputHandler.Up) orthographicCamera.position.y += 0.5;
        orthographicCamera.update();
        if (inputHandler.Left) orthographicCamera.position.x -= .5;
        orthographicCamera.update();
        if (inputHandler.Down) orthographicCamera.position.y -= .5;
        orthographicCamera.update();
        if (inputHandler.Right) orthographicCamera.position.x += .5;
        orthographicCamera.update();

    }

    public void handleInput() {

        if (inputHandler.Ctrl_right)
            projectiles.shootDick(player.getPlayerBody().getPosition(), projectiles.angle2(player.getPlayerBody().getPosition(), new Vector2(tmp.x, tmp.y)), world);
        if (inputHandler.Ctrl_left)
            if (rayCast.rayFixture(world, player.getPlayerBody().getPosition(), new Vector2(getmouseCoords().x, getmouseCoords().y)) != null)
                toDestroy.add(rayCast.rayFixture(world,
                        player.getPlayerBody().getPosition(),
                        new Vector2(getmouseCoords().x, getmouseCoords().y)).getBody());

        //move
        if (inputHandler.Space && player.isPlayerGrounded(world, player)) {
            player.getPlayerBody().applyLinearImpulse(new Vector2(0, 3), new Vector2(), true);
        }

        if (inputHandler.S) {
            player.getPlayerBody().applyForceToCenter(new Vector2(0, -20), true);
        }

        if (inputHandler.A) {
            player.getPlayerBody().applyForceToCenter(new Vector2(-20, 0), true);

        }

        if (inputHandler.D) {
            player.getPlayerBody().applyForceToCenter(new Vector2(20, 0), true);
        }
        if (inputHandler.Escape && tempb) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());
            tempb = false;
        }
        if (player.getPlayerBody().getLinearVelocity().x > 12f) {
            tempBody = player.getPlayerBody();
            tempBody.setLinearVelocity(12, player.getPlayerBody().getLinearVelocity().y);
            player.setPlayerBody(tempBody);
        }
        if (player.getPlayerBody().getLinearVelocity().x < -12f) {
            tempBody = player.getPlayerBody();
            tempBody.setLinearVelocity(-12, player.getPlayerBody().getLinearVelocity().y);
            player.setPlayerBody(tempBody);
        }
        if (player.getPlayerBody().getLinearVelocity().y > 12f) {
            tempBody = player.getPlayerBody();
            tempBody.setLinearVelocity(player.getPlayerBody().getLinearVelocity().x, 12);
            player.setPlayerBody(tempBody);
        }
        if (player.getPlayerBody().getLinearVelocity().y > 12f) {
            tempBody = player.getPlayerBody();
            tempBody.setLinearVelocity(player.getPlayerBody().getLinearVelocity().x, -12f);
            player.setPlayerBody(tempBody);

        }
    }

    public void drawSprites(float delta) {
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {

            if (body.getUserData() != null && body.getUserData() instanceof ParticleEffectPool.PooledEffect) {
                ((ParticleEffectPool.PooledEffect) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
                ((ParticleEffectPool.PooledEffect) body.getUserData()).draw(batch, delta);

            }

            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                //rotating playerBody sprite
                if (inputHandler.A && !((Sprite) body.getUserData()).isFlipX() && (Sprite) body.getUserData() == player.getPlayerSPrite())
                    ((Sprite) body.getUserData()).setFlip(true, false);
                if (inputHandler.D && ((Sprite) body.getUserData()).isFlipX() && (Sprite) body.getUserData() == player.getPlayerSPrite())
                    ((Sprite) body.getUserData()).setFlip(false, false);

                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

                sprite.draw(batch);
            }
        }
    }
}