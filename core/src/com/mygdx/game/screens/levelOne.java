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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Input;

/**
 * Created by for example John on 3/14/2015.
 */
public class levelOne extends InputAdapter implements Screen {

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
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    Body player;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera orthographicCamera;
    private SpriteBatch batch;
    private BitmapFont font;
    private MouseJointDef jointDef;
    private MouseJoint joint;
    private Sprite playerSprite;
    private Array<Body> tmpBodies = new Array<Body>();
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        line(new Vector2(-30, -10), 90, 100, 0.5f);

        //player/////////////////////////////////
        //sprite
        playerSprite = new Sprite(new Texture("Texture.png"));
        playerSprite.setSize(1f,1f);
        playerSprite.setOrigin(.5f, .5f);
//body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(15, -3);

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(.5f,.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0.75f;

        player = world.createBody(bodyDef);

        //head

        player.createFixture(fixtureDef);
        CircleShape circleshape = new CircleShape();
        circleshape.setRadius(.5f);
        circleshape.setPosition(new Vector2(0,.5f));
        fixtureDef.shape = circleshape;
        fixtureDef.density = 0;
        fixtureDef.restitution =.5f;
        player.createFixture(fixtureDef);
//sprite
        player.setUserData(playerSprite);

        blockShape.dispose();
        ////////////////////////////////////

        castle(new Vector2(12, -10), 25, 1f);
        castle(new Vector2(20, -10), 25, 2);
        castle2(new Vector2(5, -10), 15, 1, 2);
        castle2(new Vector2(5, -10), 12, 2, 3.5f);
        isosceles(new Vector2(30, 0), 1, 1.5f, 0.2f, 0.25f);

        //////////////////////mouse joint
        jointDef.bodyA = ground;
        jointDef.collideConnected = true;
        jointDef.maxForce = 50;


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        debugRenderer.render(world, orthographicCamera.combined);


        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        drawSprites();
        font.draw(batch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 100, 700);
        batch.end();


        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());

        }

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




//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        font.dispose();



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

    public void bullet(Vector2 location, float radius, float density, float friction, Vector2 velocity) {
        //ball
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location.x, location.y);
        bodyDef.linearVelocity.set(velocity);
        bodyDef.bullet = true;
        bodyDef.gravityScale = 0f;
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

    public float angle2(Vector2 vector1,Vector2 vector2){
        float angle = ((float) Math.atan2(vector2.y - vector1.y, vector2.x - vector1.x));
        return angle;
    }

    public void shoot( Vector2 location,float angleRad){
        float i = (float)Math.cos(angleRad);
        float j = (float)Math.sin(angleRad);
        Vector2 newLocation = new Vector2(location.x + i,location.y + j);
        bullet(newLocation, 0.2f, 0.5f, 0, new Vector2(100 * i, 100 * j));

    }

    public void cameraFollow() {
        float lerp = .1f;
        if(!Ctrl_left) {
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
        if(w)orthographicCamera.position.y += 0.5 ;
        orthographicCamera.update();
        if(a)orthographicCamera.position.x -= .5 ;
        orthographicCamera.update();
        if(s)orthographicCamera.position.y -= .5 ;
        orthographicCamera.update();
        if(d)orthographicCamera.position.x += .5 ;
        orthographicCamera.update();

    }

    public void handleInput() {

        //move
        if (this.Up) {
            player.applyForceToCenter(new Vector2(0, 5), true);
        }

        if (this.Down) {
            player.applyForceToCenter(new Vector2(0, -5), true);
        }

        if (this.Left) {
            player.applyForceToCenter(new Vector2(-5, 0), true);
        }

        if (this.Right) {
            player.applyForceToCenter(new Vector2(5, 0), true);
        }
        if (Input.Escape){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());
        }

        if (player.getLinearVelocity().x > 12f) player.setLinearVelocity(12, player.getLinearVelocity().y);
        if (player.getLinearVelocity().x < -12f) player.setLinearVelocity(-12, player.getLinearVelocity().y);
        if (player.getLinearVelocity().y > 12f) player.setLinearVelocity(player.getLinearVelocity().x, 12);
        if (player.getLinearVelocity().y > 12f) player.setLinearVelocity(player.getLinearVelocity().x, -12f);


    }

    public void drawSprites(){
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {

            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite)body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() /2,body.getPosition().y- sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

                sprite.draw(batch);
            }

        }
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        orthographicCamera.unproject(tmp.set(screenX, screenY, 0));
        shoot(player.getPosition(), angle2(player.getPosition(), new Vector2(tmp.x,tmp.y)));
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
        }

        return true;
    }


}

