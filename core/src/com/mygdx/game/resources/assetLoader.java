package com.mygdx.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.worldHandler;

public class assetLoader {
    public static TextureAtlas hellBlocks;
    public static TextureAtlas blocks;
    public static Sprite playerSprite;
    public static Sprite tendy;
    public static Sprite tendysilhouette;
    public static Sprite hotBar;
    public static Sprite hotBarSelected;
    public static Sprite shrek;
    public static Sprite dick;
    public static Sprite dickMenu;
    public static Sprite shrekMenu;
    public static Sprite test;
    public static Sprite block;

    public static void loadAssets(){

        block = new Sprite(new Texture("img/block.png"));
        hellBlocks = new TextureAtlas("maps/hellBlocks.pack");
        blocks = new TextureAtlas("tiles/block_pack.pack");
        playerSprite = new Sprite(new Texture("img/player.png"));
        tendysilhouette = new Sprite(new Texture("ui/heart.empty.png"));
        tendy = new Sprite(new Texture("ui/heart.full.png"));
        hotBar = new Sprite(new Texture("ui/hotbar.png"));
        hotBarSelected = new Sprite(new Texture("ui/hotbar.selected.png"));
        shrek = new Sprite(new Texture("img/Shrek.png"));
        dick = new Sprite(new Texture("textures/dick.png"));
        test = new Sprite(new Texture("img/sean.png"));

        dickMenu = new Sprite(new Texture("textures/dick.png"));
        dickMenu.setSize(64,64);
        shrekMenu = new Sprite(new Texture("img/Shrek.png"));
        shrekMenu.setSize(64,64);

        //loadPref
        worldHandler.fps = Gdx.app.getPreferences(MyGdxGame.title).getBoolean("fps");

    }
}
