package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-06-16.
 */

public class Bullet extends Sprite {


    public Body body;
    public FixtureDef fixtureDef;

    private boolean setToDestroy;
    private boolean destroyed;

    private TextureAtlas atlasBullet;
    private TextureRegion animationBullet;

    private int stars;
    private float timerBullet;
    private short noOneBullet_bit;

    private boolean standingRightLeft;
    private boolean drivingUP;
    private boolean standingRight;

    private World world;


    public Bullet(PlayScreen screen, float x, float y, boolean standingRightLeft, boolean drivingUP,
                  boolean standingRight, short noOneBullet_bit)
    {
        this.world = screen.getWorld();
        this.standingRightLeft = standingRightLeft;
        this.drivingUP = drivingUP;
        this.standingRight = standingRight;
        this.noOneBullet_bit = noOneBullet_bit;
        atlasBullet = new TextureAtlas("pocisk_assets.pack");
        animationBullet = new TextureRegion(atlasBullet.findRegion("pocisk_assetss"), 64,32,15,15);
        setBounds(x,y,10/Tanks.PPM, 10/ Tanks.PPM);  // gdzie ma być ciało_2 i jak duże ma być
        setRegion(animationBullet);


        defineBullet();
        setPosition(body.getPosition().x - getWidth()/2 +(1/Tanks.PPM), body.getPosition().y - getHeight() /2 - (1/Tanks.PPM));
    }

    public Bullet(PlayScreen screen, float x, float y, boolean standingRightLeft, boolean drivingUP,
                  boolean standingRight, short noOneBullet_bit, int stars){

        this.world = screen.getWorld();
        this.standingRightLeft = standingRightLeft;
        this.drivingUP = drivingUP;
        this.standingRight = standingRight;
        this.noOneBullet_bit = noOneBullet_bit;
        this.stars = stars;
        atlasBullet = new TextureAtlas("pocisk_assets.pack");
        animationBullet = new TextureRegion(atlasBullet.findRegion("pocisk_assetss"), 64,32,15,15);
        setBounds(x,y,10/Tanks.PPM, 10/ Tanks.PPM);
        setRegion(animationBullet);

        
        defineBullet();
        setPosition(body.getPosition().x - getWidth()/2 +(1/Tanks.PPM), body.getPosition().y - getHeight() /2 - (1/Tanks.PPM));
    }

    public void defineBullet()
    {

            BodyDef bodyDef = new BodyDef();

            if (standingRightLeft) {
                bodyDef.position.set(standingRight ? getX() + 9 / Tanks.PPM : getX() - 9 / Tanks.PPM, getY());
            } else
                bodyDef.position.set(getX(), drivingUP ? getY() + 9 / Tanks.PPM : getY() - 9 / Tanks.PPM);

            bodyDef.type = BodyDef.BodyType.DynamicBody;
            body = world.createBody(bodyDef);
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(3 / Tanks.PPM);
            fixtureDef = new FixtureDef();

            fixtureDef.shape = circleShape;

            fixtureDef.filter.categoryBits = noOneBullet_bit;
                    if(fixtureDef.filter.categoryBits== Tanks.OUR_BULLET_BIT) { // our bullet

                        fixtureDef.filter.maskBits = Tanks.ENEMY_BIT |
                                Tanks.IRON_BIT |
                                Tanks.WALL_BIT |
                                Tanks.ENEMY_BULLET_BIT |
                                Tanks.EAGLE_BIT |
                                Tanks.OUR_TANK_BIT |
                                Tanks.EAGLE_FENCE_BIT |
                                Tanks.END_OF_MAP_BIT;
                    }
                     else {// enemy bullet
                            fixtureDef.filter.maskBits = Tanks.ENEMY_BIT |
                                Tanks.IRON_BIT |
                                Tanks.WALL_BIT |
                                Tanks.OUR_BULLET_BIT |
                                Tanks.EAGLE_BIT |
                                Tanks.OUR_TANK_BIT | Tanks.EAGLE_FENCE_BIT |
                                Tanks.END_OF_MAP_BIT;
                    }
            body.createFixture(fixtureDef).setUserData(this);

            if (standingRightLeft)
                body.setLinearVelocity(new Vector2(standingRight ? 2f : -2f, 0));
            else
                body.setLinearVelocity(new Vector2(0, drivingUP ? 2f : -2f));
            
            circleShape.dispose();
    }
    
    public void update (float dt)
    {
        timerBullet += dt;
        setRegion(animationBullet);
        setPosition(body.getPosition().x - getWidth()/2 +(1/Tanks.PPM), body.getPosition().y - getHeight() /2 - (1/Tanks.PPM));
        
        if ((setToDestroy || timerBullet >1.2) && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public boolean isAlive(){
        return destroyed;
    }
    public void setToDestroyed(){ setToDestroy = true; }
    public int getStars(){ return stars; }

}
