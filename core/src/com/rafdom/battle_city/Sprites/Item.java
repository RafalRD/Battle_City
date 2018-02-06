package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

import java.util.Random;

/**
 * Created by Rafał on 2017-09-25.
 */

public class Item extends Sprite {


    public Body body;
    public BodyDef bodyDef;
    public FixtureDef fixtureDef;

    private World world;
    private PolygonShape square;

    private TextureRegion textureRegion;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean setToDestroyed_anotherItem;
    private float timerItem;
    private int itemGenerator;
    private int x_item;
    private int y_item;

    private Animation<TextureRegion> animation;

    public Item(PlayScreen screen){

        this.world = screen.getWorld();
        Random generator = new Random();

        itemGenerator =0;
        destroyed=false;

        // ony 5 item for now
        itemGenerator = generator.nextInt(5)+18; // 7+16 for all item
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // item animation
        for(int i = 0; i<7; i+=6)
        {
           frames.add( new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), itemGenerator *16,(i*16)+7*16,16,16));
        }
        animation = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();

        x_item = generator.nextInt(208)+16;
        y_item = generator.nextInt(208)+16;
        defineItem();
        setBounds(16 , 0,16/Tanks.PPM,16/Tanks.PPM);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        textureRegion = animation.getKeyFrame(timerItem,true);
        setRegion(textureRegion);
    }

    private void defineItem(){

        bodyDef = new BodyDef();
        bodyDef.position.set(x_item / Tanks.PPM, y_item / Tanks.PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        fixtureDef = new FixtureDef();
        square = new PolygonShape();
        square.setAsBox(15/2/ Tanks.PPM,15/2/Tanks.PPM);
        fixtureDef.shape = square;

        fixtureDef.filter.categoryBits = Tanks.Item_BIT;
        fixtureDef.filter.maskBits = Tanks.OUR_TANK_BIT;
        body.createFixture(fixtureDef).setUserData(this);

        square.dispose();
    }

    public void update( float dt){

        timerItem += dt;
        if(body != null)
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        TextureRegion textureRegion = animation.getKeyFrame(timerItem,true);
        setRegion(textureRegion);

        if ((setToDestroyed_anotherItem || timerItem > 15) && !destroyed)
        {
            world.destroyBody(body);
            destroyed = true;
            timerItem =0;
        }

        if ((setToDestroy) && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
            Tanks.manager.get("powerup_pick.wav", Sound.class).play();
            timerItem =0;
        }
    }

    public boolean alive (){
        return destroyed;
    }
    public void setToDestroyed(){
        setToDestroy = true;
    }
    public void setToDestroed_anotherItem(){ setToDestroyed_anotherItem = true; }
    public int getItem(){ return itemGenerator; }

}
