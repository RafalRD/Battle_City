package com.rafdom.battle_city;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.rafdom.battle_city.Screens.PlayScreen;

public class Tanks extends Game {

	public static final short NOTHING_BIT = 0;
	public static final short OUR_TANK_BIT = 2;
	public static final short WATER_BIT = 4;
	public static final short WALL_BIT = 8;
	public static final short ICE_BIT = 16;
	public static final short BUSH_BIT = 32;
	public static final short OUR_BULLET_BIT = 64;
	public static final short IRON_BIT = 128;
	public static final short ENEMY_BIT = 256;
	public static final short END_OF_MAP_BIT = 512 ;
	public static final short Item_BIT = 1024;
	public static final short EAGLE_BIT = 2048;
	public static final short ENEMY_BULLET_BIT = 4096;
	public static final short EAGLE_FENCE_BIT = 8192;



	public static final int height = 240;
	public static final int wight = 280;
	public static final float PPM = 100;


	public SpriteBatch batch;
	public World world;

	private PlayScreen playScreen;



	public static AssetManager manager;

	@Override
	public void create ()
	{

		batch = new SpriteBatch();


		playScreen = new PlayScreen(this);

		manager = new AssetManager();
		manager.load("explosion.wav", Sound.class);
		manager.load("userMoving.wav", Sound.class);
		manager.load("shot.wav", Sound.class);
		manager.load("stage_start.wav",Music.class);
		manager.load("bullet_hit_1.wav",Sound.class);
		manager.load("powerup_appear.wav",Sound.class);
		manager.load("powerup_pick.wav", Sound.class);
		manager.finishLoading();

		setScreen(playScreen);



	}

	@Override
	public void render () {



		super.render();




	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}


}
