package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Timer;

/**
 * Created by for example John on 5/22/2015.
 */
public class objectUserData {
    public objectUserData(){

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    private Sprite sprite;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ParticleEffectPool.PooledEffect getEffect() {
        return effect;
    }

    public void setEffect(ParticleEffectPool.PooledEffect effect) {
        this.effect = effect;
    }

    private ParticleEffectPool.PooledEffect effect;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }

    public void upTimer() {
        this.timer++;
    }

    private int timer;

    public boolean shard;



}
