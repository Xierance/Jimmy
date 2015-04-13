package com.mygdx.game.things;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Visuals.gameParticles.Flame;

/**
 * Created by for example John on 4/13/2015.
 */
public class PoolTest extends Pool <Flame>{

        @Override
        protected Flame newObject() {

            return new Flame(new Vector2());
        }

}
