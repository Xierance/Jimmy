package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.resources.*;
import com.mygdx.game.worldHandler;

/**
 * Created by for example John on 3/14/2015.
 */
public class TestClass extends InputAdapter implements Screen {




    private ui UI;
    public static Vector2 temp = new Vector2();
    public static Body tempBody;

    public static Array<Body> toDestroy = new Array<Body>();
    public static Array<ParticleEffect> flames = new Array<ParticleEffect>();
    public static TiledMap map;
    public static boolean Up;
    public static boolean Down;
    //input keys
    public static boolean Left;
    public static boolean Right;
    public static boolean Space;
    public static boolean Escape;
    public static boolean M;
    public static boolean O;
    public static boolean R;
    public static boolean E;
    public static boolean S;
    public static boolean A;
    public static boolean N;
    public static boolean I;
    public static boolean C;
    public static boolean P;
    public static boolean L;
    public static boolean W;
    public static boolean D;
    public static boolean Ctrl_left;
    public static boolean Ctrl_right;

    private static boolean Q;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    public Player player = new Player();

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera orthographicCamera;
    private OrthographicCamera secondCamera;
    private SpriteBatch batch;
    private SpriteBatch secondBatch;
    private MouseJointDef jointDef;
    private MouseJoint joint;

    private Sprite block1 = new Sprite();

    private enemyPrototype test;
    enemyPrototype test2;
    healthDrop test3;
    healthDrop test4;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private MyContactListener cl;
    private Array<Body> tmpBodies = new Array<Body>();
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();

    private QueryCallback queryCallbackMouse = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (!fixture.testPoint(tmp.x, tmp.y))
                return true;

            if (fixture.getBody().getType() != BodyDef.BodyType.KinematicBody) {
                jointDef.bodyB = fixture.getBody();

                jointDef.target.set(tmp.x, tmp.y);
                joint = (MouseJoint) world.createJoint(jointDef);
            }
            return false;
        }
    };

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

                if (tileCell != null && tileCell.getTile() != null ){
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


    @Override
    public void show() {

        assetLoader.loadAssets();

        UI = new ui();
        worldHandler.fps = Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps");

        jointDef = new MouseJointDef();

        batch = new SpriteBatch();
        secondBatch = new SpriteBatch();


        world = new World(new Vector2(0f, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth() / 32, Gdx.graphics.getHeight() / 32);
        secondCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //32:1

        //input

        Gdx.input.setInputProcessor((this));

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
        if (!M) {
            if (orthographicCamera.position.x != player.getPlayerBody().getPosition().x) {
                orthographicCamera.position.x += (player.getPlayerBody().getPosition().x - orthographicCamera.position.x) * lerp;
            }
            if (orthographicCamera.position.y != player.getPlayerBody().getPosition().y) {
                orthographicCamera.position.y += (player.getPlayerBody().getPosition().y - orthographicCamera.position.y) * lerp;
            }
            orthographicCamera.update();
        }

        if (Q) {
            orthographicCamera.zoom += .010f;
            orthographicCamera.update();
        }
        if (E) {
            orthographicCamera.zoom -= .010f;
            orthographicCamera.update();
        }

        if (Up) orthographicCamera.position.y += 0.5;
        orthographicCamera.update();
        if (Left) orthographicCamera.position.x -= .5;
        orthographicCamera.update();
        if (Down) orthographicCamera.position.y -= .5;
        orthographicCamera.update();
        if (Right) orthographicCamera.position.x += .5;
        orthographicCamera.update();

    }

    public void handleInput() {

        if (Ctrl_right)
            projectiles.shootDick(player.getPlayerBody().getPosition(), projectiles.angle2(player.getPlayerBody().getPosition(), new Vector2(tmp.x, tmp.y)), world);
        if (Ctrl_left)
            if (rayCast.rayFixture(world, player.getPlayerBody().getPosition(), new Vector2(getmouseCoords().x, getmouseCoords().y)) != null)
                toDestroy.add(rayCast.rayFixture(world,
                        player.getPlayerBody().getPosition(),
                        new Vector2(getmouseCoords().x, getmouseCoords().y)).getBody());

        //move
        if (this.Space && player.isPlayerGrounded(world, player)) {
            player.getPlayerBody().applyLinearImpulse(new Vector2(0, 3), new Vector2(), true);
        }

        if (this.S) {
            player.getPlayerBody().applyForceToCenter(new Vector2(0, -20), true);
        }

        if (this.A) {
            player.getPlayerBody().applyForceToCenter(new Vector2(-20, 0), true);

        }

        if (this.D) {
            player.getPlayerBody().applyForceToCenter(new Vector2(20, 0), true);
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
                if (A && !((Sprite) body.getUserData()).isFlipX() && (Sprite) body.getUserData() == player.getPlayerSPrite())
                    ((Sprite) body.getUserData()).setFlip(true, false);
                if (D && ((Sprite) body.getUserData()).isFlipX() && (Sprite) body.getUserData() == player.getPlayerSPrite())
                    ((Sprite) body.getUserData()).setFlip(false, false);

                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

                sprite.draw(batch);
            }
        }
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        orthographicCamera.unproject(tmp.set(screenX, screenY, 0));

        if (!Ctrl_left) {
            if (!Ctrl_right)

                projectiles.shootFire(player.getPlayerBody().getPosition(), projectiles.angle2(player.getPlayerBody().getPosition(), getmouseCoords()), world);
        }


        if (Ctrl_left) world.QueryAABB(queryCallbackMouse, tmp.x, tmp.y, tmp.x, tmp.y);
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (joint == null) return false;
        orthographicCamera.unproject(tmp.set(screenX, screenY, 0));
        joint.setTarget(tmp2.set(tmp.x, tmp.y));

        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (joint == null)
            return false;
        world.destroyJoint(joint);
        joint = null;
        return true;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 19:
                Up = true;
                break;
            case 20:
                Down = true;
                break;
            case 21:
                Left = true;
                break;
            case 22:
                Right = true;
                break;
            case 62:
                Space = true;
                break;
            case 41:
                M = true;
                break;
            case 43:
                O = true;
                break;
            case 46:
                R = true;
                break;
            case 33:
                E = true;
                break;
            case 47:
                S = true;
                break;
            case 29:
                A = true;
                break;
            case 42:
                N = true;
                break;
            case 37:
                I = true;
                break;
            case 31:
                C = true;
                break;
            case 44:
                P = true;
                break;
            case 40:
                L = true;
                break;
            case 131:
                Escape = true;
                break;
            case 51:
                W = true;
                break;
            case 32:
                D = true;
                break;
            case 129:
                Ctrl_left = true;
                break;
            case 130:
                Ctrl_right = true;
                break;
            case 45:
                Q = true;
                break;
        }
        return true;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case 19:
                Up = false;
                break;
            case 20:
                Down = false;
                break;
            case 21:
                Left = false;
                break;
            case 22:
                Right = false;
                break;
            case 62:
                Space = false;
                break;
            case 41:
                M = false;
                break;
            case 43:
                O = false;
                break;
            case 46:
                R = false;
                break;
            case 33:
                E = false;
                break;
            case 47:
                S = false;
                break;
            case 29:
                A = false;
                break;
            case 42:
                N = false;
                break;
            case 37:
                I = false;
                break;
            case 31:
                C = false;
                break;
            case 44:
                P = false;
                break;
            case 40:
                L = false;
                break;
            case 131:
                Escape = false;
                break;
            case 51:
                W = false;
                break;
            case 32:
                D = false;
            case 129:
                Ctrl_left = false;
                break;
            case 130:
                Ctrl_right = false;
                break;
            case 45:
                Q = false;
                break;

        }

        return true;
    }

    public Vector2 getmouseCoords() {
        Vector3 temp = new Vector3();
        orthographicCamera.unproject(temp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(temp.x, temp.y);
    }
}