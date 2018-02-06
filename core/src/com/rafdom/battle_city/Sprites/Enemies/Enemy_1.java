package com.rafdom.battle_city.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Sprites.Bullet;
import com.rafdom.battle_city.Tanks;

import java.util.Random;

/**
 * Created by lenovo on 2017-08-01.
 */



public class Enemy_1 extends  Enemy{

    private boolean setToDestroy;
    private boolean destroyed;
    private boolean destroyedRed;
    private boolean explosion;

    private PolygonShape square;

    public enum State {DRIVING_UP_DOWN, STANDING_UP_DOWN, STANDING_RIGHT_LEFT, DRIVING_RIGHT_LEFT, DESTROYED}

    private State currentState;
    private State previousState;
    private Animation<TextureRegion> tankDrive_UP_DOWN;
    private Animation<TextureRegion> tankDrive_RIGHT_LEFT;
    private float timer;
    private float timer1;

    private int tankType;
    private int tankPosition;
    private int rC; // rc = tank type
    private int area;
    private boolean standingUP;
    private boolean standingRightLeft;
    private boolean standingRight;
    private TextureRegion tankStandRight;
    private TextureRegion tankStandUP;
    private TextureRegion tank_ded;

    private Array<Bullet> bullets;

    public Enemy_1(PlayScreen screen) {

        super(screen);
        currentState = State.STANDING_UP_DOWN;
        previousState = State.STANDING_UP_DOWN;

        tankPosition =0;
        area =0;
        timer = 0;
        timer1 = 0;
        standingUP = true;
        standingRightLeft = false;
        standingRight = true;
        destroyedRed = false;
        explosion = false;

       Random generator1 = new Random();
        area = generator1.nextInt(2)+1;

        if (area ==2) {
            tankPosition = 128;
            tankType = generator1.nextInt(12) + 4;
            if(tankType >= 8 && tankType <=11) // exclusion red our tank
            {
                tankType -=4; // to silver not our tank
            }
        }
        else { // area==1

            tankPosition = 0;
            tankType = generator1.nextInt(12) + 4;

            if(tankType >= 8 && tankType <=11)
            {
                tankType +=4;
            }
            if(tankType >= 8 && tankType <=11) // exclusion green our tank
            {
                tankType +=4; // to green not our tank
            }
        }

        rC = tankType;

        if(area ==2 && tankType >11 && tankType <=15)
        {}
        // animation up & down
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), i * 16 + tankPosition, rC * 16, 16, 16));
            if(area ==2 && tankType >11 && tankType <=15 )
            {rC-=8;}
        }
        tankDrive_UP_DOWN = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        // animation right & left
        Array<TextureRegion> frames_2 = new Array<TextureRegion>();
        for (int i = 6; i < 8; i++) {
            if(area ==2 && tankType >11 && tankType <=15)
            {rC+=8;}
            frames_2.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), i* 16+ tankPosition, rC * 16, 16, 16));
        }
        tankDrive_RIGHT_LEFT = new Animation<TextureRegion>(0.1f, frames_2);
        frames_2.clear();

        // animation standing up
        tankStandUP = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), 96+ tankPosition, rC *16, 16, 16);


        // animation standing right
        tankStandRight = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),96+ tankPosition, rC *16, 16, 16);

        // ded
        tank_ded = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),256 , 128, 16, 16);

        // explosion
        Array<TextureRegion> frames_3 = new Array<TextureRegion>();
        for (int i = 16; i<19; i++)
        {
            frames_3.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), i*16,128,16,16));
        }

        setBounds(0, 0, 16 / Tanks.PPM, 16 / Tanks.PPM);
        setRegion(tankStandUP);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        bullets = new Array<Bullet>();
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DRIVING_UP_DOWN:
                region = tankDrive_UP_DOWN.getKeyFrame(timer, true);
                break;
            case DRIVING_RIGHT_LEFT:
                region = tankDrive_RIGHT_LEFT.getKeyFrame(timer, true);
                break;
            case STANDING_RIGHT_LEFT:
                region = tankStandRight;
                break;

            default:
                region = tankStandUP;
                break;
        }


        // down
        if ((body.getLinearVelocity().y < 0 || !standingUP) && !region.isFlipY()) {
            region.flip(false, true);
            // zapamiętywany jest poprzedni kierunek
            standingUP = false;

        }
        // up
        else if ((body.getLinearVelocity().y > 0 || standingUP) && region.isFlipY()) {
            region.flip(false, true);
            standingUP = true;
        }
        // left
        else if ((body.getLinearVelocity().x < 0 || !standingRight) && !region.isFlipX()) {
            region.flip(true, false);
            standingRight = false;
        }
        // right
        else if ((body.getLinearVelocity().x > 0 || standingRight) && region.isFlipX()) {
            region.flip(true, false);
            standingRight = true;
        }

        timer = currentState == previousState ? timer + delta : 0;
        previousState = currentState;

        return region;
    }


    public State getState() {


        if (body.getLinearVelocity().y != 0) {
            standingRightLeft = false;
            return State.DRIVING_UP_DOWN;
        } else if (body.getLinearVelocity().y == 0 && !standingRightLeft && body.getLinearVelocity().x == 0) {
            // standingRightLeft = false;
            return State.STANDING_UP_DOWN;
        } else if (body.getLinearVelocity().x != 0) {
            standingRightLeft = true;
            return State.DRIVING_RIGHT_LEFT;
        }
        else return State.STANDING_RIGHT_LEFT;
    }


    @Override
    public void defineEnemy() {
        bodyDef = new BodyDef();
        Random generator = new Random();

        tankPosition = generator.nextInt(3);

        if (tankPosition ==0)
        bodyDef.position.set(9/ Tanks.PPM, 231 / Tanks.PPM);
        else if (tankPosition ==1)
            bodyDef.position.set(115/ Tanks.PPM, 231 / Tanks.PPM);
        else if (tankPosition == 2)
            bodyDef.position.set(230/ Tanks.PPM, 231 / Tanks.PPM);

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();

        square = new PolygonShape();
        square.setAsBox(10 / 2 / Tanks.PPM, 10 / 2 / Tanks.PPM);

        fixtureDef.shape = square;
        fixtureDef.filter.categoryBits = Tanks.ENEMY_BIT;
        fixtureDef.filter.maskBits =
                Tanks.OUR_TANK_BIT |
                        Tanks.IRON_BIT |
                        Tanks.WALL_BIT |
                        Tanks.WATER_BIT |
                        Tanks.END_OF_MAP_BIT |
                        Tanks.ENEMY_BIT |
                        Tanks.OUR_BULLET_BIT |
                        Tanks.EAGLE_FENCE_BIT |
                        Tanks.ICE_BIT;

        body.createFixture(fixtureDef).setUserData(this);

        if (standingRightLeft)
            body.setLinearVelocity(new Vector2(standingRight ? 0.2f : -0.2f, 0));
        else
            body.setLinearVelocity(new Vector2(0, standingUP ? 0.2f : -0.2f));

        square.dispose();

    }

    private void movementsEnemyTank()
    {


        if (body.getLinearVelocity().y < 0 && timer > 4 ) {
            body.setLinearVelocity(-0.2f, 0); // left
            timer = 0;
        }
        else if (body.getLinearVelocity().y > 0 && timer >3) {
            body.setLinearVelocity(0.2f, 0);
            timer =0;
        }
        else if (body.getLinearVelocity().x > 0  && timer >2) {
            body.setLinearVelocity(0,-0.2f);
            timer = 0;
        }
        else if (body.getLinearVelocity().x<0 && timer >2) {
            body.setLinearVelocity(0,0.2f);
            timer = 0;
        }
        else if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y ==0) // !
            body.setLinearVelocity(0.2f,0);
        else if (body.getLinearVelocity().x != 0 && body.getLinearVelocity().y !=0)
            body.setLinearVelocity(0.2f,0);
    }

    public void fire (){

        timer1 += Gdx.graphics.getDeltaTime();
        if ( bullets.size <1 && timer1 >1) {
            timer1 =0;
            bullets.add(new Bullet(screen, body.getPosition().x, body.getPosition().y,
                    standingRightLeft ? true : false,
                    standingUP ? true : false,
                    standingRight ? true : false
                    ,Tanks.ENEMY_BULLET_BIT
                    ));
        }
    }

    @Override
    public void update(float dt) {

        if ((setToDestroy || explosion)&& !destroyed  ){
            world.destroyBody(body);
            destroyed = true;
            if( !explosion &&!destroyedRed && area ==2 && tankType >11 && tankType <=15 )
            {
                destroyedRed =true;
            }
            setRegion(tank_ded);
            Tanks.manager.get("explosion.wav", Sound.class).play();
            explosion =false;
        }
        else if (!destroyed) {
            setRegion(getFrame(dt));
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            movementsEnemyTank();
            fire();
        }
        for (Bullet bullet : bullets) {
             bullet.update(dt);

            if (bullet.isAlive()  ) {
                bullets.removeValue(bullet, true);
            }
        }
    }

    public void draw (Batch batch) {
           super.draw(batch);

            for (Bullet bullet : bullets)
                bullet.draw(batch);
    }

    @Override
    public void hit() {

        if (body.getLinearVelocity().y < 0) {
            body.setLinearVelocity(0, 0.2f);
            timer =0;
        }
        else if (body.getLinearVelocity().y > 0) {
             body.setLinearVelocity(0, -0.2f);
        }
        else if (body.getLinearVelocity().x > 0 )
        {
            body.setLinearVelocity(-0.2f,0);
        }
        else if (body.getLinearVelocity().x<0)
        {
            body.setLinearVelocity(0,0.2f);
            timer=0;
        }
    }

    @Override
    public boolean tankExists() {
        return destroyed;
    }

    @Override
    public boolean redTankExists() {
        return destroyedRed;
    }

    @Override
    public void redAlive() {
         destroyedRed =false;
    }
    @Override
    public void setToDestroyed() {
        setToDestroy = true;
    }

    @Override
    public void explosion() {
        explosion =true;
    }

}