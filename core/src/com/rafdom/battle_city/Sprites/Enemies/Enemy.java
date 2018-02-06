package com.rafdom.battle_city.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.rafdom.battle_city.Screens.PlayScreen;

/**
 * Created by Rafał on 2017-06-16.
 */

public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;

    public Body body;
    public BodyDef bodyDef;
    public FixtureDef fixtureDef;


    public Enemy(PlayScreen screen)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        defineEnemy();
    }

    public abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hit();
    public abstract boolean tankExists();
    public abstract boolean redTankExists();
    public abstract void redAlive();
    public abstract void setToDestroyed();
    public abstract void explosion();


}
