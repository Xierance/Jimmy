package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.mygdx.game.MyGdxGame;


/**
 * Created by for example John on 3/14/2015.
 */
public class levelTest extends InputAdapter implements Screen {

    public static boolean Up;
    public static boolean Down;
    public static boolean Left;
    public static boolean Right;
    public static boolean Space;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    Body circle;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private BitmapFont font;
    private MouseJointDef jointDef;
    private MouseJoint joint;
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    private QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (!fixture.testPoint(tmp.x, tmp.y))
                return true;

            jointDef.bodyB = fixture.getBody();
            jointDef.target.set(tmp.x, tmp.y);
            joint = (MouseJoint) world.createJoint(jointDef);

            return false;
        }
    };

    @Override
    public void show() {
        jointDef = new MouseJointDef();
        batch = new SpriteBatch();
        font = new BitmapFont(false);
        world = new World(new Vector2(0f, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth() / 32, Gdx.graphics.getHeight() / 32);
        //32:1

        //input

        Gdx.input.setInputProcessor((this));

        //ground
        BodyDef tempdef = new BodyDef();
        Body ground = world.createBody(tempdef);
        ground = lineAlt(new Vector2(-300f, -10f), 0, 3000, 0.25f, ground);


        //ball/////////////////////////////////
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(15, -3);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.25f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0.75f;
        circle = world.createBody(bodyDef);
        circle.createFixture(fixtureDef);

        shape.dispose();
        ////////////////////////////////////

        castle(new Vector2(12, -10), 25, 1f);
        castle(new Vector2(20, -10), 25, 2);
        castle2(new Vector2(5, -10), 15, 1, 2);
        castle2(new Vector2(5, -10), 12, 2, 3.5f);
        line(new Vector2(-30, -10), 90, 100, 0.5f);
        isosceles(new Vector2(30, 0), 1, 1.5f, 0.2f, 0.25f);

        //////////////////////mouse joint
        jointDef.bodyA = ground;
        jointDef.collideConnected = true;
        jointDef.maxForce = 500;


    }

    public void displayFps(SpriteBatch batch, BitmapFont font2) {
        font2.draw(batch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 10.0F, 700.0F);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        debugRenderer.render(world, orthographicCamera.combined);

        handleInput();
        cameraFollow();


        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());

        }

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        batch.begin();
        if ((Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps")) == true) {
            displayFps(batch, font);
        }
        batch.end();

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

    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {

        world.dispose();
        debugRenderer.dispose();


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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void circle(Vector2 location, float radius, float density, float friction) {
        //ball
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0.75f;
        world.createBody(bodyDef).createFixture(fixtureDef);

        shape.dispose();
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

    public void handleInput() {

        //move
        if (this.Up) {
            circle.applyForceToCenter(new Vector2(0, 5), true);
        }

        if (this.Down) {
            circle.applyForceToCenter(new Vector2(0, -5), true);
        }

        if (this.Left) {
            circle.applyForceToCenter(new Vector2(-5, 0), true);
        }

        if (this.Right) {
            circle.applyForceToCenter(new Vector2(5, 0), true);
        }

        if (circle.getLinearVelocity().x > 12f) circle.setLinearVelocity(12, circle.getLinearVelocity().y);
        if (circle.getLinearVelocity().x < -12f) circle.setLinearVelocity(-12, circle.getLinearVelocity().y);
        if (circle.getLinearVelocity().y > 12f) circle.setLinearVelocity(circle.getLinearVelocity().x, 12);
        if (circle.getLinearVelocity().y > 12f) circle.setLinearVelocity(circle.getLinearVelocity().x, -12f);
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

    public void cameraFollow() {
        float lerp = 0.1f;
        if (orthographicCamera.position.x != circle.getPosition().x) {
            orthographicCamera.position.x += (circle.getPosition().x - orthographicCamera.position.x) * lerp;
        }
        if (orthographicCamera.position.y != circle.getPosition().y) {
            orthographicCamera.position.y += (circle.getPosition().y - orthographicCamera.position.y) * lerp;
        }
        orthographicCamera.update();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        orthographicCamera.unproject(tmp.set(screenX, screenY, 0));
        world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
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
        }

        return true;
    }


}

