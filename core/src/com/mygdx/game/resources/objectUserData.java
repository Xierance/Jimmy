package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by for example John on 5/22/2015.
 */
public class objectUserData {
    public boolean shard;
    public enemyPrototype target;
    private Sprite sprite;
    private String id;
    private ParticleEffectPool.PooledEffect effect;
    private int time;

    public objectUserData(){

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getId() {
        return id;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setId(String id) {
        this.id = id;
    }

    public ParticleEffectPool.PooledEffect getEffect() {
        return effect;
    }

    public void setEffect(ParticleEffectPool.PooledEffect effect) {
        this.effect = effect;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void upTimer() {
        this.time++;
    }

    public enemyPrototype getTarget() {
        return target;
    }

    public void setTarget(enemyPrototype target) {
        this.target = target;
    }





}
