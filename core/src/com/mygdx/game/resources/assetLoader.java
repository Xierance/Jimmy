package com.mygdx.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.worldHandler;

/**
 * Created by forexamplejohn on 03/05/15.
 */
public class assetLoader {
    public static TextureAtlas hellBlocks;
    public static TextureAtlas blocks;
    public static Sprite playerSprite;
    public static Sprite tendy;
    public static Sprite tendysilhouette;
    public static Sprite hotBar;
    public static Sprite hotBarSelected;

    public static void loadAssets(){
        hellBlocks = new TextureAtlas("maps/hellBlocks.pack");
        blocks = new TextureAtlas("tiles/block_pack.pack");
        playerSprite = new Sprite(new Texture("img/player.png"));
        tendysilhouette = new Sprite(new Texture("ui/heart.empty.png"));
        tendy = new Sprite(new Texture("ui/heart.full.png"));
        hotBar = new Sprite(new Texture("ui/hotbar.png"));
        hotBarSelected = new Sprite(new Texture("ui/hotbar.selected.png"));

        //loadPref
        worldHandler.fps = Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps");

    }
}
