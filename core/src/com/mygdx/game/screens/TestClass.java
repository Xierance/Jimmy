package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.Visuals.gameParticles.Explosion;
import com.mygdx.game.Visuals.ui;
import com.mygdx.game.inputHandler;
import com.mygdx.game.resources.*;
import com.mygdx.game.worldHandler;

import java.util.Random;

/**
 * Created by for example John on 3/14/2015.
 */
public class TestClass implements Screen {


    public static Body tempBody;
    public static Array<ParticleEffect> flames = new Array<ParticleEffect>();
    public static Player player = new Player();
    public static World world;
    public static OrthographicCamera orthographicCamera;
    public static Vector3 tmp = new Vector3();
    public static boolean justJumped = false;
    public static Random randomGenerator;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    healthDrop test3;
    healthDrop test4;
    private ui UI;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera secondCamera;
    private SpriteBatch batch;
    private SpriteBatch secondBatch;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private MyContactListener cl;
    private Array<Body> tmpBodies = new Array<Body>();
    private boolean tempb = true;

    public static Vector2 getmouseCoords() {
        Vector3 temp = new Vector3();
        orthographicCamera.unproject(temp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(temp.x, temp.y);
    }

    @Override
    public void show() {
        randomGenerator = new Random();

        assetLoader.loadAssets();

        UI = new ui();


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

        //changeMap.mapToBox2d("maps/testMap.tmx", world);///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        changeMap.multipleMaps(new String[]{"maps/testMap.tmx", "maps/testMap2.tmx"}, world);


        player.createPLayer(world, worldHandler.temp, assetLoader.playerSprite);

        cl = new MyContactListener();
        world.setContactListener(cl);


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
        System.out.println(String.valueOf(justJumped));

        enemyPrototype.updateenemies(world);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, orthographicCamera.combined);

        batch.setProjectionMatrix(orthographicCamera.combined);

        batch.begin();
        drawSprites(delta);
        Explosion.drawExplosions(batch, delta);
        batch.end();

        secondBatch.setProjectionMatrix(secondCamera.combined);
        secondCamera.update();
        secondBatch.begin();
        UI.displayMax(secondBatch);
        UI.displayFps(secondBatch);
        UI.testHotbar(secondBatch);
        secondBatch.end();

        handleInput();
        cameraFollow();

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        if (world.isLocked() == false) {
            world.getBodies(tmpBodies);
            for (Body body : tmpBodies) projectiles.clearShards(body, 20);

            rayCast.clearBodies(world);
        }

        if(worldHandler.currentHealth == 0 && !world.isLocked()){
            world.clearForces();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new mainMenu());
        }
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
        debugRenderer.dispose();
        world.dispose();

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
        }

        if (inputHandler.Q) {
            orthographicCamera.zoom += .010f;
        }
        if (inputHandler.E) {
            orthographicCamera.zoom -= .010f;
        }

        if (inputHandler.Up) orthographicCamera.position.y += 0.5;
        if (inputHandler.Left) orthographicCamera.position.x -= .5;
        if (inputHandler.Down) orthographicCamera.position.y -= .5;
        if (inputHandler.Right) orthographicCamera.position.x += .5;
        orthographicCamera.update();

    }

    public void handleInput() {

        //move
        if (inputHandler.Space && player.isPlayerGrounded(world, player)) {
            justJumped = true;
            player.getPlayerBody().applyLinearImpulse(new Vector2(0, 5), new Vector2(), true);
        }
        if (inputHandler.Space && justJumped){
            justJumped = false;
            player.getPlayerBody().applyLinearImpulse(new Vector2(0, 5), new Vector2(), true);
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

        if (inputHandler.mouse) {
            projectiles.dickStone(player.getPlayerBody().getPosition(), getmouseCoords(), world);
        }
    }

    public void drawSprites(float delta) {
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {

            if (body.getUserData() instanceof objectUserData) {


                if (body.getUserData() instanceof objectUserData && ((objectUserData) body.getUserData()).getEffect() != null) {
                    ((objectUserData) body.getUserData()).getEffect().setPosition(body.getPosition().x, body.getPosition().y);
                    ((objectUserData) body.getUserData()).getEffect().draw(batch, delta);

                }

                if (((objectUserData) body.getUserData()).getSprite() != null) {
                    //rotating playerBody sprite
                    if (((objectUserData) body.getUserData()).getId() == "player") {
                        if (inputHandler.A && !((objectUserData) body.getUserData()).getSprite().isFlipX()) {
                            ((objectUserData) body.getUserData()).getSprite().setFlip(true, false);
                        }
                        if (inputHandler.D && ((objectUserData) body.getUserData()).getSprite().isFlipX()) {
                            ((objectUserData) body.getUserData()).getSprite().setFlip(false, false);
                        }
                    }
                    Sprite sprite = ((objectUserData) body.getUserData()).getSprite();
                    sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

                    sprite.draw(batch);
                }
            }
        }
    }
}