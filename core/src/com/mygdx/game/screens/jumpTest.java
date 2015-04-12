package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Input;
import com.mygdx.game.MyGdxGame;


/**
 * Created by Cian on 19/03/2015.
 */
public class jumpTest implements Screen {

    public static boolean Ctrl_left;
    public World world;
    boolean tempb = true;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera; //Camera deals with perspective, where things are drawn (camera can move without object movement)
    private Body box;
    private Fixture playerSensorFixture;

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.807f), true);  //Contructor takes Vector2 (An X and Y value) and if the body is allowed to sleep (inactive bodies no longer need to be calculated)
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/50, Gdx.graphics.getHeight()/50);

        Gdx.input.setInputProcessor(new Input());

        createBall();
        createPlayer();
        createGround();
        createPlayerSensor();


    }

    private void createBall(){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fDef = new FixtureDef(); //Takes the physical properties of the the fixture


        //ball definition
        bodyDef.type = BodyDef.BodyType.DynamicBody;  //Static bodies do not move, kinematic bodies cannot be influenced by external physics and dynamic bodies can be influenced and influence. Kinematic > dynamic
        bodyDef.position.set(0, 1); //1 Metre

        //ball shape
        CircleShape cs = new CircleShape();
        cs.setRadius(.25f);

        //ball fixture
        //fixture is the physical part of the body. The body is just a blank template to which properties can be added. The fixture does not even need to be attached to the body.
        //Multiple fixtures can be added to a body to create a model with different properties for different components
        fDef.shape = cs;
        fDef.density = 2.5f;
        fDef.friction = 0.1f;
        fDef.restitution = 0.999999f; //coefficient of restitution

        box= world.createBody(bodyDef);

        box.createFixture(fDef);  //World create a body, create fixture on that body
        cs.dispose();


    }

    private void createPlayer(){

        BodyDef bodyDef = new BodyDef();
        FixtureDef fDef = new FixtureDef(); //Takes the physical properties of the the fixture

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f, 10);
        PolygonShape square = new PolygonShape();
        square.setAsBox(1, 2);

        fDef.shape = square;
        fDef.friction = 0.1f;
        fDef.restitution = 0;
        fDef.density = .1f;

        box = world.createBody(bodyDef);
        box.createFixture(fDef);

        square.dispose();

    }

    private void createGround(){

        BodyDef bodyDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();

        //Ground
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        ChainShape groundShape = new ChainShape(); //basically a line
        groundShape.createChain(new Vector2[]{new Vector2(-500, 0), new Vector2(500, 0)});
        fDef.shape = groundShape;
        fDef.friction = 0.5f;
        fDef.restitution = 0;
        world.createBody(bodyDef).createFixture(fDef);
        groundShape.dispose();

    }

    private void createPlayerSensor(){
        FixtureDef fDef = new FixtureDef();

        //Sensor setup
        PolygonShape baseRectangle = new PolygonShape();
        baseRectangle.setAsBox(1, .2f, new Vector2(0, -2.2f), 0);

        fDef.shape = baseRectangle;
        fDef.friction = 0.1f;
        fDef.restitution = 0;
        fDef.density = .1f;
        playerSensorFixture = box.createFixture(fDef);
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
        if (Input.Space && isPlayerGrounded()) {
            box.applyForceToCenter(new Vector2(0, 50), true);
        }

        if (Input.Down) {
            box.applyForceToCenter(new Vector2(0, -50), true);
        }

        if (Input.Left) {
            box.applyForceToCenter(new Vector2(-50, 0), true);
        }

        if (Input.Right) {
            box.applyForceToCenter(new Vector2(50, 0), true);
        }
        if (Input.Escape && tempb){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());
            tempb = false;
        }
    }

    public Body lineAlt(Vector2 location, float angle, float length, float friction,Body body) {
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

    public void cameraFollow() {
        float lerp = .1f;
        if(!Ctrl_left) {
            if (camera.position.x != box.getPosition().x) {
                camera.position.x += (box.getPosition().x - camera.position.x) * lerp;
            }
            if (camera.position.y != box.getPosition().y) {
                camera.position.y += (box.getPosition().y - camera.position.y) * lerp;
            }
            camera.update();
        }

        if (Input.O) {
            camera.zoom += .010f;
            camera.update();
        }
        if (Input.M) {
            camera.zoom -= .010f;
            camera.update();

        }
        if(Input.W)camera.position.y += .5 ;
        camera.update();
        if(Input.A)camera.position.x -= .5 ;
        camera.update();
        if(Input.D)camera.position.y -= .5 ;
        camera.update();
        if(Input.S)camera.position.x += .5 ;
        camera.update();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


      Gdx.app.log(MyGdxGame.title, String.valueOf(isPlayerGrounded()));

        debugRenderer.render(world, camera.combined); // Projection matrices values are taken from the camera (combined takes everything we need and puts it together)
        world.step((1 / 60f), 8, 3); //Velocity iterations and position iterations determines the quality/accuracy of the simulation
        handleInput();
        cameraFollow();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 50;
        camera.viewportHeight = height / 50;
        camera.update();

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
}
