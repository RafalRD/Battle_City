package com.rafdom.battle_city.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rafdom.battle_city.Tools.B2WorldCreator;
import com.rafdom.battle_city.Sprites.Yellow_Tank;
import com.rafdom.battle_city.Tanks;
import com.rafdom.battle_city.Tools.WorldContactListener;


public class PlayScreen implements Screen
{
    private Tanks game;


    private OrthographicCamera camera;
    private Viewport viewport;
    public World world;

    private boolean gameOver;


    private Box2DDebugRenderer b2dr;


    private TiledMap map;
    private TmxMapLoader tmxMapLoader;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;


    private Yellow_Tank yellow_tank;

    private B2WorldCreator creator;
    private TextureAtlas atlas;
    private float timeToGameOver;






    public PlayScreen(Tanks game)
    {

        gameOver = false;
        this.game = game;


        atlas = new TextureAtlas("tanks_texture.pack");

        camera = new OrthographicCamera();

        tmxMapLoader = new TmxMapLoader();

        map = tmxMapLoader.load("level3_tank.tmx");

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map,1/ Tanks.PPM);

        viewport = new StretchViewport((Tanks.wight -14)/Tanks.PPM, Tanks.height /Tanks.PPM,camera);
        camera.position.set((viewport.getWorldWidth())/2, viewport.getWorldHeight()/2,0);


        world = new World(new Vector2(0,0),true);
        yellow_tank = new Yellow_Tank(this);

        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());

    }

    public World getWorld()
    {
        return world;
    }
    public TiledMap getMap() { return map; }
    public TextureAtlas getAtlas() {return atlas; }

    private void handleInput()
    {
        if(yellow_tank.body != null) {

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                yellow_tank.up();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yellow_tank.down();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                yellow_tank.right();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                yellow_tank.left();
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                yellow_tank.fire();
            } else {
                yellow_tank.stop();
            }
        }
    }

    public void gameOver(){
        gameOver = true;}

    @Override
    public void show() {

    }

    public void update (float delta)
    {
        handleInput();
        yellow_tank.cereateEnemy();

        world.step(1/60f,6,2); //update 1 steps per 60 sec

        yellow_tank.update(delta);

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();
        orthogonalTiledMapRenderer.setView(camera);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin(); //batch open

        yellow_tank.draw(game.batch);


        game.batch.end(); // batch close

        if (gameOver) {
        timeToGameOver +=delta;
            if( timeToGameOver >0.5) {
                game.setScreen(new GameOver(game));
                timeToGameOver = 0;
                camera.update();
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
