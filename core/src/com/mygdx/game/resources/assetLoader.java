package com.mygdx.game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by forexamplejohn on 03/05/15.
 */
public class assetLoader {
    public static TextureAtlas hellBlocks;
    public static TextureAtlas blocks;
    public static Sprite playerSprite;
    public static Sprite tendy;
    public static Sprite tendysilhouette;

    public static void loadAssets(){
        hellBlocks = new TextureAtlas("maps/hellBlocks.pack");
        blocks = new TextureAtlas("tiles/block_pack.pack");
        playerSprite = new Sprite(new Texture("img/player.png"));
        tendysilhouette = new Sprite(new Texture("textures/dick.png"));
        tendy = new Sprite(new Texture("tiles/block_pack.png"));

    }
}
