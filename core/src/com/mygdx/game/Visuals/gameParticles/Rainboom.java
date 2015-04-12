package com.mygdx.game.Visuals.gameParticles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by for example John on 4/9/2015.
 */
public class Rainboom {

    private ParticleEffect rainboom;

    public  Rainboom(Vector2 location){
        rainboom = new ParticleEffect();
        rainboom.load(Gdx.files.local("effects/rainboom.p"), Gdx.files.local("effects"));
        rainboom.scaleEffect(0.0069f);
        rainboom.start();
    }

    public ParticleEffect getRainboom() {
        return rainboom;
    }

    public void setRainboom(ParticleEffect rainboom) {
        this.rainboom = rainboom;
    }



    public void update(SpriteBatch batch,Float delta,Vector2 position){
        rainboom.setPosition(position.x,position.y);
        rainboom.draw(batch,delta);

    }
    public void setPosition(Vector2 position){
        rainboom.setPosition(position.x,position.y);
    }
}
