package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.game.MyGdxGame;


/**
 * Created by for example John on 3/14/2015.
 */
public class levelThree extends InputAdapter implements Screen {

    public static Vector2 playerLocation = new Vector2(15, -3);
    public static TiledMap map;
    //input keys
    public static boolean Up;
    public static boolean Down;
    public static boolean Left;
    public static boolean Right;
    public static boolean Space;
    public static boolean w;
    public static boolean s;
    public static boolean q;
    public static boolean e;
    public static boolean a;
    public static boolean d;
    public static boolean Ctrl_left;
    public static boolean Escape;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    Body player;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera orthographicCamera;
    private OrthographicCamera secondCamera;
    private Fixture playerSensorFixture;
    private SpriteBatch batch;
    private SpriteBatch secondBatch;
    private BitmapFont font;
    private MouseJointDef jointDef;
    private MouseJoint joint;
    private Sprite playerSprite;
    private Array<Body> tmpBodies = new Array<Body>();
    private Vector3 tmp = new Vector3();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public static void mapToBox2d(TiledMap map, World world) {
        map = new TmxMapLoader().load("maps/testMap.tmx");
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("butt");
        Sprite tileSprite = new Sprite(new Texture("textures/jew.jpg"));
        tileSprite.setSize(1f, 1f);

        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell tileCell = tileLayer.getCell(x, y);

                if (tileCell != null && tileCell.getTile() != null) {


                    Object property = tileCell.getTile().getProperties().get("player");
                    if (property != null) {
                        playerLocation = new Vector2(x, y);
                    } else {

                        BodyDef tileBodyDef = new BodyDef();
                        tileBodyDef.type = BodyDef.BodyType.KinematicBody;
                        tileBodyDef.gravityScale = 0;
                        tileBodyDef.position.set(x, y);

                        PolygonShape tileShape = new PolygonShape();
                        tileShape.setAsBox(.5f, .5f);

                        Body body = world.createBody(tileBodyDef);
                        body.createFixture(tileShape, 0f);

                        body.setUserData(tileSprite);

                        tileShape.dispose();

                    }


                }
            }

        }


    }

    @Override
    public void show() {


        jointDef = new MouseJointDef();

        batch = new SpriteBatch();
        secondBatch = new SpriteBatch();

        font = new BitmapFont(false);

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
        ground = lineAlt(new Vector2(-300f, -10f), 0, 3000, 0.25f, ground);
        line(new Vector2(-30, -10), 90, 100, 0.5f);

        //player/////////////////////////////////

        //sprite
        playerSprite = new Sprite(new Texture("textures/player.png"));
        playerSprite.setSize(1f, 2f);
        playerSprite.setOrigin(.5f, 1f);
        //body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerLocation.x, playerLocation.y);
        bodyDef.fixedRotation = true;

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(.5f, 1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0.25f;

        player = world.createBody(bodyDef);
        player.setUserData("player");
        player.createFixture(fixtureDef);

        //sprite
        player.setUserData(playerSprite);

        blockShape.dispose();
        playerSensor();
        ////////////////////////////////////

        castle(new Vector2(12, -10), 25, 1f);
        castle(new Vector2(20, -10), 25, 2);
        castle2(new Vector2(5, -10), 15, 1, 2);
        castle2(new Vector2(5, -10), 12, 2, 3.5f);
        isosceles(new Vector2(30, 0), 1, 1.5f, 0.2f, 0.25f);
        castle2(new Vector2(30, -10), 30, 1, 9);

        //////////////////////mouse joint
        jointDef.bodyA = ground;
        jointDef.collideConnected = true;
        jointDef.maxForce = 50;

        mapToBox2d(new TiledMap(), world);///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        player.setTransform(playerLocation, 0);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        debugRenderer.render(world, orthographicCamera.combined);

        batch.setProjectionMatrix(orthographicCamera.combined);

        batch.begin();
        drawSprites();

        batch.end();

        secondBatch.setProjectionMatrix(secondCamera.combined);
        secondCamera.update();
        secondBatch.begin();
        font.draw(secondBatch, "Fps" + Gdx.graphics.getFramesPerSecond(), -590, 350);
        secondBatch.end();

        handleInput();
        cameraFollow();

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);


    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.viewportWidth = width / 32;
        orthographicCamera.viewportHeight = height / 32;

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {

        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        font.dispose();
        secondBatch.dispose();
        playerSprite.getTexture().dispose();

    }

    public void square(Vector2 location, float size, float density, float friction) {

        //blocks
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        PolygonShape blockShape = new PolygonShape();
        blockShape.set(new Vector2[]{new Vector2(0, 0), new Vector2(0, size), new Vector2(size, size), new Vector2(size, 0)});
        fixtureDef.shape = blockShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = density;
        world.createBody(bodyDef).createFixture(fixtureDef);

        blockShape.dispose();
    }

    public void bullet(Vector2 location, float density, float friction, Vector2 velocity) {
        //ball

        Body bulletBody;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        bodyDef.linearVelocity.set(velocity);
        bodyDef.bullet = true;

        PolygonShape bulletShaft = new PolygonShape();
        bulletShaft.set(new Vector2[]{
                new Vector2(-.8f, -.3f), new Vector2(-.6f, -0.3f), new Vector2(-.6f, -.2f), new Vector2(0.5f, -.2f), new Vector2(.5f, .2f), new Vector2(-.6f, .2f), new Vector2(-.6f, .3f), new Vector2(-.8f, .3f)
        });
        FixtureDef shaftFixDef = new FixtureDef();
        shaftFixDef.shape = bulletShaft;

        bulletBody = world.createBody(bodyDef);

        shaftFixDef.density = density / 3;
        shaftFixDef.friction = friction;
        shaftFixDef.restitution = 0.25f;
        bulletBody.createFixture(shaftFixDef);

        PolygonShape bulletHead = new PolygonShape();
        bulletHead.set(new Vector2[]{
                new Vector2(.5f, -0.2f), new Vector2(.9f, -.2f), new Vector2(.9f, .2f), new Vector2(.5f, .2f)
        });
        FixtureDef headFixDef = new FixtureDef();
        headFixDef.shape = bulletHead;

        headFixDef.density = density;
        headFixDef.friction = friction;
        headFixDef.restitution = 0.75f;
        bulletBody.createFixture(headFixDef);

        Sprite dick = new Sprite(new Texture("textures/dick.png"));
        dick.setSize(1.69f, .69f);
        dick.setOrigin((float) 1.69 / 2, (float) .69 / 2);

        bulletBody.setUserData(dick);

        //direction
        bulletBody.setTransform(bulletBody.getPosition().x, bulletBody.getPosition().y, (float) Math.atan2((double) (velocity.y), (double) (velocity.x)));

        bulletShaft.dispose();
    }

    public void isosceles(Vector2 location, float width, float height, float density, float friction) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        PolygonShape blockShape = new PolygonShape();
        blockShape.set(new Vector2[]{new Vector2(0, 0), new Vector2(width, 0), new Vector2(width / 2, height)});
        fixtureDef.shape = blockShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = density;
        world.createBody(bodyDef).createFixture(fixtureDef);

        blockShape.dispose();

    }

    public void line(Vector2 location, float angle, float length, float friction) {
        //ground
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(location.x, location.y);
        ChainShape lineShape = new ChainShape();
        float Angle = 0;

        if (angle >= 0 && angle <= 360) Angle = angle;

        lineShape.createChain(new Vector2[]{new Vector2(0, 0), new Vector2((length * (float) Math.cos(Math.toRadians(Angle))), length * (float) Math.sin(Math.toRadians(Angle)))});
        fixtureDef.shape = lineShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0;
        world.createBody(bodyDef).createFixture(fixtureDef);

        lineShape.dispose();
    }

    public Body lineAlt(Vector2 location, float angle, float length, float friction, Body body) {
        //ground
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(location.x, location.y);
        ChainShape lineShape = new ChainShape();
        float Angle = 0;

        if (angle >= 0 && angle <= 360) Angle = angle;

        lineShape.createChain(new Vector2[]{new Vector2(0, 0), new Vector2((length * (float) Math.cos(Math.toRadians(Angle))), length * (float) Math.sin(Math.toRadians(Angle)))});
        fixtureDef.shape = lineShape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        lineShape.dispose();

        return body;
    }

    public void castle(Vector2 location, int height, float size) {
        if (size > 25) size = 25;
        float oldHeight = 0;
        float blockheight = size;
        for (int i = 0; i <= height; i++) {
            square(new Vector2(location.x - (blockheight * 0.9f) / 2, location.y + oldHeight), blockheight * 0.9f, 0.2f, 0.75f);
            blockheight *= 0.9;
            oldHeight += blockheight;
        }

    }

    public void castle2(Vector2 location, int height, float size, float gap) {
        if (height > 25) height = 25;
        if (size < 1) size = 1;
        float oldHeight = 0;
        float blockheight = size;
        for (int i = 0; i <= height; i++) {
            square(new Vector2(location.x - (blockheight * 0.9f) / 2 - gap / 2 - size / 2, location.y + oldHeight), blockheight * 0.9f, 0.2f, 0.75f);

            square(new Vector2(location.x - (blockheight * 0.9f) / 2 + gap / 2 + size / 2, location.y + oldHeight), blockheight * 0.9f, 0.2f, 0.75f);

            blockheight *= 0.9;
            oldHeight += blockheight;
        }
        isosceles(new Vector2(location.x - (blockheight) / 2 - gap / 2 - size / 2, location.y + oldHeight), blockheight, blockheight * 1.5f, 0.25f, 0.75f);
        isosceles(new Vector2(location.x - (blockheight) / 2 + gap / 2 + size / 2, location.y + oldHeight), blockheight, blockheight * 1.5f, 0.25f, 0.75f);
    }

    public float angle2(Vector2 vector1, Vector2 vector2) {
        float angle = ((float) Math.atan2(vector2.y - vector1.y, vector2.x - vector1.x));
        return angle;
    }

    public void shoot(Vector2 location, float angleRad) {

        float i = (float) Math.cos(angleRad);
        float j = (float) Math.sin(angleRad);
        Vector2 newLocation = new Vector2(location.x + i, location.y + j);
        bullet(newLocation, 10f, 0.75f, new Vector2(69 * i, 69 * j));

    }

    public void cameraFollow() {
        float lerp = .1f;
        if (!Ctrl_left) {
            if (orthographicCamera.position.x != player.getPosition().x) {
                orthographicCamera.position.x += (player.getPosition().x - orthographicCamera.position.x) * lerp;
            }
            if (orthographicCamera.position.y != player.getPosition().y) {
                orthographicCamera.position.y += (player.getPosition().y - orthographicCamera.position.y) * lerp;
            }
            orthographicCamera.update();
        }

        if (q) {
            orthographicCamera.zoom += .010f;
            orthographicCamera.update();
        }
        if (e) {
            orthographicCamera.zoom -= .010f;
            orthographicCamera.update();

        }
        if (w) orthographicCamera.position.y += 0.5;
        orthographicCamera.update();
        if (a) orthographicCamera.position.x -= .5;
        orthographicCamera.update();
        if (s) orthographicCamera.position.y -= .5;
        orthographicCamera.update();
        if (d) orthographicCamera.position.x += .5;
        orthographicCamera.update();

    }

    private void playerSensor() {
        FixtureDef fDef = new FixtureDef();

        //Sensor setup
        PolygonShape baseRectangle = new PolygonShape();
        baseRectangle.setAsBox(1, .2f, new Vector2(0, -2.2f), 0);

        fDef.shape = baseRectangle;
        fDef.friction = 0.1f;
        fDef.restitution = 0;
        fDef.density = .1f;
        playerSensorFixture = player.createFixture(fDef);
        playerSensorFixture.setSensor(true);

        baseRectangle.dispose();
    }

    private boolean isPlayerGrounded() {
        Array<Contact> contactList = new Array<Contact>(world.getContactList());

        for (int i = 0; i < contactList.size; i++) {
            Contact contact = contactList.get(i);
            if (contact.isTouching() && (contact.getFixtureA() == playerSensorFixture || contact.getFixtureB() == playerSensorFixture))
                return true;
        }
        return false;
    }

    public void handleInput() {

        //move
        if (this.Space && isPlayerGrounded()) {
            player.applyForceToCenter(new Vector2(0, 50), true);
        }

        if (this.Down) {
            player.applyForceToCenter(new Vector2(0, -20), true);
        }

        if (this.Left) {
            player.applyForceToCenter(new Vector2(-20, 0), true);
        }

        if (this.Right) {
            player.applyForceToCenter(new Vector2(20, 0), true);
        }
        if (this.Escape) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());
            Gdx.app.log(MyGdxGame.title, "finally!");
        }

        if (player.getLinearVelocity().x > 12f) player.setLinearVelocity(12, player.getLinearVelocity().y);
        if (player.getLinearVelocity().x < -12f) player.setLinearVelocity(-12, player.getLinearVelocity().y);
        if (player.getLinearVelocity().y > 12f) player.setLinearVelocity(player.getLinearVelocity().x, 12);
        if (player.getLinearVelocity().y > 12f) player.setLinearVelocity(player.getLinearVelocity().x, -12f);


    }

    public void drawSprites() {
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {

            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                //rotating player sprite
                if (Left && !((Sprite) body.getUserData()).isFlipX() && (Sprite) body.getUserData() == playerSprite)
                    ((Sprite) body.getUserData()).setFlip(true, false);
                if (Right && ((Sprite) body.getUserData()).isFlipX() && (Sprite) body.getUserData() == playerSprite)
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

        if (!Ctrl_left) shoot(player.getPosition(), angle2(player.getPosition(), new Vector2(tmp.x, tmp.y)));

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
            case 51:
                w = true;
                break;
            case 47:
                s = true;
                break;
            case 45:
                q = true;
                break;
            case 33:
                e = true;
                break;
            case 29:
                a = true;
                break;
            case 32:
                d = true;
                break;
            case 129:
                Ctrl_left = true;
                break;
            case 131:
                Escape = true;
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
            case 51:
                w = false;
                break;
            case 47:
                s = false;
                break;
            case 45:
                q = false;
                break;
            case 33:
                e = false;
                break;
            case 29:
                a = false;
                break;
            case 32:
                d = false;
                break;
            case 129:
                Ctrl_left = false;
                break;
            case 131:
                Escape = false;
                break;
        }

        return true;
    }

    public Vector3 getmouseCoords() {
        Vector3 temp = new Vector3();
        orthographicCamera.unproject(temp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return temp;
    }

}