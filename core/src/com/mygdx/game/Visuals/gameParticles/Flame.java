package com.mygdx.game.Visuals.gameParticles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by for example John on 4/9/2015.
 */
public class Flame {

    private ParticleEffect flame;

    public  Flame(Vector2 location){
        flame = new ParticleEffect();
        flame.load(Gdx.files.local("effects/Flame.p"), Gdx.files.local("effects"));
        flame.scaleEffect(0.01f);
        flame.start();
    }

    public ParticleEffect getFlame() {
        return flame;
    }

    public void setFlame(ParticleEffect Flame) {
        this.flame = Flame;
    }

    public void update(SpriteBatch batch,Float delta,Vector2 position){
        flame.setPosition(position.x,position.y);
        flame.draw(batch,delta);
        flame.start();
    }

    public void setPosition(Vector2 position){
        flame.setPosition(position.x,position.y);
    }
    public Vector2 getPosition(){
        return new Vector2(flame.getBoundingBox().getCenterX(),flame.getBoundingBox().getCenterY());

    }
}
