package com.rafdom.battle_city.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Sprites.Enemies.Enemy;
import com.rafdom.battle_city.Sprites.Enemies.Enemy_1;
import com.rafdom.battle_city.Tanks;

public class Yellow_Tank extends Sprite
{

    private PlayScreen screen;

    public Body body;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private PolygonShape square;

    private TiledMap map;

    public enum State{DRIVING_UP_DOWN, STANDING_UP_DOWN,STANDING_RIGHT_LEFT, DRIVING_RIGHT_LEFT,DEAD}
    private State currentState;
    private State previousState;

    private Animation <TextureRegion>tankDrive_UP_DOWN;
    private Animation <TextureRegion>tankDrive_UP_DOWN_M;
    private Animation <TextureRegion>tankDrive_UP_DOWN_S;
    private Animation <TextureRegion>tankDrive_UP_DOWN_B;

    private Animation <TextureRegion>tankDrive_RIGHT_LEFT;
    private Animation <TextureRegion>tankDrive_RIGHT_LEFT_M;
    private Animation <TextureRegion>tankDrive_RIGHT_LEFT_S;
    private Animation <TextureRegion>tankDrive_RIGHT_LEFT_B;

    private float timer;
    private float timer1;
    private float timerShovelRaised;
    private float timerEnemyAppear;
    private float timerOurBullet;
    private float howManyTanksToDestroyed;
    private int x;
    private int y;
    private int howManyStarsWeHave;
    private int health;
    private int itemType;
    private int timerFence;

    private boolean standingUP;
    private boolean standingRightLeft;
    private boolean standingRight;
    private boolean grenadeRaised;
    private boolean starRaised;
    private boolean helmRaised;
    private boolean shovelRaised;
    private boolean renderFence;
    private boolean clockRaised;

    private TextureRegion tankStandRight;
    private TextureRegion tankStandRight_M;
    private TextureRegion tankStandRight_S;
    private TextureRegion tankStandRight_B;

    private TextureRegion tankStandUP;
    private TextureRegion tankStandUP_M;
    private TextureRegion tankStandUP_S;
    private TextureRegion tankStandUP_B;

    private TextureRegion tank_ded;
    private Array<Bullet> bullets;
    private Array<Enemy> enemies_1;
    private Array<Item> items;

    public World world;

    private boolean ourTankHiT;
    private boolean playMusic;

    public Yellow_Tank(PlayScreen screen)
    {

        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();

        currentState = State.STANDING_UP_DOWN;
        previousState = State.STANDING_UP_DOWN;
        timer = 0;
        timer1 = 0;
        timerEnemyAppear =0;
        timerOurBullet =0;
        timerShovelRaised =0;
        howManyTanksToDestroyed = 0;

        x = 32;
        y = 18;
        howManyStarsWeHave =0;
        health = 2;
        itemType =0;

        standingUP = true;
        standingRightLeft = false;
        standingRight = true;
        playMusic = true;
        grenadeRaised = false;
        starRaised = false;
        helmRaised = false;
        shovelRaised = false;
        renderFence = false;
        clockRaised = false;

        // animation Up & Down star=0
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i<2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0,16,16));
        tankDrive_UP_DOWN = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        // animation Up & Down star=1
        Array<TextureRegion> frames_M = new Array<TextureRegion>();
        for(int i = 0; i<2; i++)
            frames_M.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0+(16* 1),16,16));
        tankDrive_UP_DOWN_M = new Animation<TextureRegion>(0.1f,frames_M);
        frames_M.clear();

        //  animation Up & Down star=2
        Array<TextureRegion> frames_S = new Array<TextureRegion>();
        for(int i = 0; i<2; i++)
            frames_S.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0+(16* 2),16,16));
        tankDrive_UP_DOWN_S = new Animation<TextureRegion>(0.1f,frames_S);
        frames_S.clear();

        //  animation Up & Down star=3
        Array<TextureRegion> frames_B = new Array<TextureRegion>();
        for(int i = 0; i<2; i++)
            frames_B.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0+(16* 3),16,16));
        tankDrive_UP_DOWN_B = new Animation<TextureRegion>(0.1f,frames_B);
        frames_B.clear();


        // animation left & right star=0
        Array<TextureRegion> frames_2 = new Array<TextureRegion>();
        for (int i = 6; i < 8; i++)
        {
            frames_2.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0,16,16));
        }
        tankDrive_RIGHT_LEFT = new Animation<TextureRegion>(0.1f,frames_2);
        frames_2.clear();

        // animation left & right star=1
        Array<TextureRegion> frames_2_M = new Array<TextureRegion>();
        for (int i = 6; i < 8; i++)
        {
            frames_2_M.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0+(16* 1),16,16));
        }
        tankDrive_RIGHT_LEFT_M = new Animation<TextureRegion>(0.1f,frames_2_M);
        frames_2_M.clear();

        // animation left & right star=2
        Array<TextureRegion> frames_2_S = new Array<TextureRegion>();
        for (int i = 6; i < 8; i++)
        {
            frames_2_S.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0+(16* 2),16,16));
        }
        tankDrive_RIGHT_LEFT_S = new Animation<TextureRegion>(0.1f,frames_2_S);
        frames_2_S.clear();

        // animation left & right star=3
        Array<TextureRegion> frames_2_B = new Array<TextureRegion>();
        for (int i = 6; i < 8; i++)
        {
            frames_2_B.add(new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),i*16,0+(16* 3),16,16));
        }
        tankDrive_RIGHT_LEFT_B = new Animation<TextureRegion>(0.1f,frames_2_B);
        frames_2_B.clear();

        // animation up star = 0
        tankStandUP = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), 0,0,16,16);
        // animation up star = 1
        tankStandUP_M = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), 0,0+(16* 1),16,16);
        // animation up star = 2
        tankStandUP_S = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), 0,0+(16* 2),16,16);
        // animation up star = 3
        tankStandUP_B = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"), 0,0+(16* 3),16,16);

        // animation right star = 0
        tankStandRight = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),96,0,16,16);
        // animation right star = 1
        tankStandRight_M = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),96,0+(16* 1),16,16);
        // animation right star = 2
        tankStandRight_S = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),96,0+(16* 2),16,16);
        // animation right star = 3
        tankStandRight_B = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),96,0+(16* 3),16,16);

        // explosion
        tank_ded = new TextureRegion(screen.getAtlas().findRegion("assets_tanks"),256 , 128, 16, 16);

        defineOurTank();

        setBounds(0,0, 16/Tanks.PPM, 16/Tanks.PPM);
        setRegion(tankStandUP);

        bullets = new Array<Bullet>();
        enemies_1 = new Array<Enemy>();
        items = new Array<Item>();
    }

    public void update (float delta) {

        if(renderFence) {
            for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
                new EagleFence(screen,object);
            }
            renderFence = false;
        }
        timerEnemyAppear +=delta;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        if (ourTankHiT) {
            setRegion(tank_ded);
            timerOurBullet +=delta;
            if(timerOurBullet >0.35) {
                redefineOurTank();
                timerOurBullet =0;
            }
        }

        else setRegion(getFrame(delta));

        if(howManyStarsWeHave >0 && starRaised) {
            redefineForItem();
            starRaised = false;
            setRegion(getFrame(delta));
        }

        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.isAlive()  ) {
                bullets.removeValue(bullet, true);
            }
        }


        for (Enemy enemy : enemies_1) {
                enemy.update(delta);

            if(grenadeRaised) {
                enemy.explosion();
                if(enemies_1.size ==1)
                grenadeRaised =false;
            }

            if (enemy.tankExists()  ){ // if ourTankHiT
                timer1 +=Gdx.graphics.getDeltaTime();
                getCell_tanks(x,y).setTile(null);

                if(enemy.redTankExists() )
                {
                    Tanks.manager.get("powerup_appear.wav", Sound.class).play();
                    if (items.size == 1)
                    {
                        for (Item test : items)
                        {
                            test.setToDestroed_anotherItem();
                        }
                    }

                    if (items.size == 0)
                    {
                        items.add(new Item(screen));
                        enemy.redAlive(); // stop
                    }
                }

                if(timer1>1.3 ) { // after 1.2 (bullet time)
                    enemies_1.removeValue(enemy, true);
                    timer1 =0;

                    // animation tanks counter (upper right corner)
                    if(x==32)
                    {
                        x--;
                    }
                    else {
                        x=32;
                        y++;
                    } // end animation
                }
            }
        }
            for (Item test: items)
            {
                test.update(delta);
                itemType = test.getItem();
                if(test.alive())
                {
                    items.removeValue(test,true);
                }
            }
        if(shovelRaised){
            timerShovelRaised +=delta;
            if (timerShovelRaised >11)
            {
                timerFence =(int) timerShovelRaised;
                if(timerFence %2== 0){
                    replacingTheBlock(34,11,13,0);
                    replacingTheBlock(34,11,13,1);
                    replacingTheBlock(34,11,13,2);
                    replacingTheBlock(34,11,14,2);
                    replacingTheBlock(34,11,15,2);
                    replacingTheBlock(34,11,16,0);
                    replacingTheBlock(34,11,16,1);
                    replacingTheBlock(34,11,16,2);
                }
                else {
                    replacingTheBlock(34,10,13,0);
                    replacingTheBlock(34,10,13,1);
                    replacingTheBlock(34,10,13,2);
                    replacingTheBlock(34,10,14,2);
                    replacingTheBlock(34,10,15,2);
                    replacingTheBlock(34,10,16,0);
                    replacingTheBlock(34,10,16,1);
                    replacingTheBlock(34,10,16,2);
                }
            }
            if(timerShovelRaised >15)
            {
                renderFence =true;
                shovelRaised =false;
                replacingTheBlock(34,11,13,0);
                replacingTheBlock(34,11,13,1);
                replacingTheBlock(34,11,13,2);
                replacingTheBlock(34,11,14,2);
                replacingTheBlock(34,11,15,2);
                replacingTheBlock(34,11,16,0);
                replacingTheBlock(34,11,16,1);
                replacingTheBlock(34,11,16,2);
                timerShovelRaised =0;
            }
        }
    }

    public TextureRegion getFrame(float delta)
    {
        currentState = getState();
        TextureRegion region;

        switch (currentState){
            case DRIVING_UP_DOWN:
                region = tankDrive_UP_DOWN.getKeyFrame(timer,true);
                if (howManyStarsWeHave ==1)
                    region= tankDrive_UP_DOWN_M.getKeyFrame(timer,true);
                if (howManyStarsWeHave ==2)
                    region= tankDrive_UP_DOWN_S.getKeyFrame(timer,true);
                if (howManyStarsWeHave ==3)
                    region= tankDrive_UP_DOWN_B.getKeyFrame(timer,true);
                break;

            case DRIVING_RIGHT_LEFT:
                region = tankDrive_RIGHT_LEFT.getKeyFrame(timer,true);
                if (howManyStarsWeHave ==1)
                    region = tankDrive_RIGHT_LEFT_M.getKeyFrame(timer,true);
                if (howManyStarsWeHave ==2)
                    region = tankDrive_RIGHT_LEFT_S.getKeyFrame(timer,true);
                if (howManyStarsWeHave ==3)
                    region = tankDrive_RIGHT_LEFT_B.getKeyFrame(timer,true);
                break;

            case STANDING_RIGHT_LEFT:
                region = tankStandRight;
                if (howManyStarsWeHave ==1)
                    region = tankStandRight_M;
                if (howManyStarsWeHave ==2)
                    region = tankStandRight_S;
                if (howManyStarsWeHave ==3)
                    region = tankStandRight_B;
                break;

            case DEAD:
                region = tank_ded;
                break;
            default:
                region = tankStandUP;
                if (howManyStarsWeHave ==1)
                    region = tankStandUP_M;
                if (howManyStarsWeHave ==2)
                    region = tankStandUP_S;
                if (howManyStarsWeHave ==3)
                    region = tankStandUP_B;
                break;
        }


        //driving down
        if((body.getLinearVelocity().y <0 || !standingUP) && !region.isFlipY()) {
            region.flip(false, true);
            standingUP = false;

        }
        // driving up
         else if ((body.getLinearVelocity().y >0 || standingUP) && region.isFlipY()){
            region.flip(false,true);
            standingUP = true;
        }
        // driving left
        else if ((body.getLinearVelocity().x < 0 || !standingRight ) && !region.isFlipX()){
            region.flip(true,false);
            standingRight = false;
        }
        // driving right
        else if ((body.getLinearVelocity().x > 0 || standingRight) && region.isFlipX()){
            region.flip(true,false);
            standingRight = true;
        }

        timer = currentState == previousState ? timer + delta : 0;
        previousState = currentState;

        return region;
    }
    public State getState()
    {

        if(body.getLinearVelocity().y != 0  )
        {
            standingRightLeft = false;
            return State.DRIVING_UP_DOWN;
        }
        else if (body.getLinearVelocity().y ==0 && !standingRightLeft && body.getLinearVelocity().x==0)
        {
           // standingRightLeft = false;
            return State.STANDING_UP_DOWN;
        }
        else if(body.getLinearVelocity().x != 0 )
        {
            standingRightLeft = true;
            return State.DRIVING_RIGHT_LEFT;
        }
        else return State.STANDING_RIGHT_LEFT;
    }

    public void up() { body.setLinearVelocity(0,0.2f);}
    public void down() { body.setLinearVelocity(0,-0.2f);}
    public void right()
    {
        body.setLinearVelocity(0.2f,0);
    }
    public void left() { body.setLinearVelocity(-0.2f,0);}
    public void stop()
    {
        body.setLinearVelocity(0,0);
    }


    public void defineOurTank()
    {
        bodyDef = new BodyDef();
        bodyDef.position.set(10 /Tanks.PPM, 10/ Tanks.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();

        square = new PolygonShape();
        square.setAsBox(10/2/ Tanks.PPM,10/2/Tanks.PPM);

        fixtureDef.shape = square;
        fixtureDef.filter.categoryBits = Tanks.OUR_TANK_BIT;
        fixtureDef.filter.maskBits =
                Tanks.Item_BIT|
                Tanks.ENEMY_BIT |
                Tanks.IRON_BIT |
                Tanks.WALL_BIT |
                Tanks.EAGLE_FENCE_BIT|
                Tanks.WATER_BIT |
                Tanks.END_OF_MAP_BIT |
                Tanks.ENEMY_BULLET_BIT |
                Tanks.ICE_BIT;

        body.createFixture(fixtureDef).setUserData(this);

        square.dispose();

    }
    public void redefineOurTank()
    {

        world.destroyBody(body);
        bodyDef = new BodyDef();
        bodyDef.position.set(10 /Tanks.PPM, 10/ Tanks.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();

        square = new PolygonShape();
        square.setAsBox(10/2/ Tanks.PPM,10/2/Tanks.PPM);

        fixtureDef.shape = square;
        fixtureDef.filter.categoryBits = Tanks.OUR_TANK_BIT;
        fixtureDef.filter.maskBits =
                Tanks.ENEMY_BIT |
                        Tanks.IRON_BIT |
                        Tanks.WALL_BIT |
                        Tanks.WATER_BIT |
                        Tanks.END_OF_MAP_BIT |
                        Tanks.EAGLE_FENCE_BIT|
                        Tanks.ENEMY_BULLET_BIT |
                        Tanks.ICE_BIT |
                        Tanks.Item_BIT;

        body.createFixture(fixtureDef).setUserData(this);
        square.dispose();
        ourTankHiT = false;

    }
    public void redefineForItem(){

        Vector2 currentPosition = body.getPosition();
        world.destroyBody(body);
        bodyDef = new BodyDef();
        bodyDef.position.set(currentPosition);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();

        square = new PolygonShape();
        square.setAsBox(10/2/ Tanks.PPM,10/2/Tanks.PPM);

        fixtureDef.shape = square;
        fixtureDef.filter.maskBits =
                Tanks.ENEMY_BIT |
                        Tanks.IRON_BIT |
                        Tanks.WALL_BIT |
                        Tanks.WATER_BIT |
                        Tanks.END_OF_MAP_BIT |
                        Tanks.ENEMY_BULLET_BIT |
                        Tanks.ICE_BIT |
                        Tanks.EAGLE_FENCE_BIT |
                        Tanks.Item_BIT;

        body.createFixture(fixtureDef).setUserData(this);

        square.dispose();
    }
    public void Hit(){

        if (howManyStarsWeHave ==3)
        {
            howManyStarsWeHave--;
        }
        else {
            howManyStarsWeHave =0;
            ourTankHiT = true;
            if (health > 0) {

                health--;
                replacingTheBlock(34, (health), 32, 11);
            }
        }

        Tanks.manager.get("explosion.wav", Sound.class).play();
    }

    public void fire (){

        if ( (bullets.size <1) || (howManyStarsWeHave ==2 && bullets.size<2 && !ourTankHiT))
        {
            Tanks.manager.get("shot.wav", Sound.class).play();
            bullets.add(new Bullet(screen, body.getPosition().x, body.getPosition().y,
                    standingRightLeft ? true : false,
                    standingUP ? true : false,
                    standingRight ? true : false,
                    Tanks.OUR_BULLET_BIT, howManyStarsWeHave));
        }
    }

    public void cereateEnemy()
    {

        if (enemies_1.size == 4 ) // too early
        {
            timerEnemyAppear =0;
        }
        if(enemies_1.size <4 && timerEnemyAppear >1.9 && howManyTanksToDestroyed <20){
            if(playMusic) {
                Tanks.manager.get("stage_start.wav", Music.class).play();
                playMusic = false;
            }

            howManyTanksToDestroyed++;
            timerEnemyAppear =0;
            enemies_1.add(new Enemy_1(screen));
        }
    }

    public void draw (Batch batch){
        
        super.draw(batch);
        for (Bullet bullet : bullets) 
            bullet.draw(batch);
        for (Enemy enemy: enemies_1)
            enemy.draw(batch);
        for (Item item: items)
        {
            item.draw(batch);
        }
    }

    public TiledMapTileLayer.Cell getCell_tanks (int x, int y) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        TiledMapTileLayer.Cell cell = layer.getCell((int) (x), (int) (y));

        // System.out.println(((int) (256/ 8))+ " y" + (int) (136 /8));
        return cell;
    }

    // from z_x z_y to na_x na_y
    public void replacingTheBlock(int z_x, int z_y, int na_x, int na_y )
    {

        TiledMapTileLayer.Cell cell = getCell_tanks(z_x,z_y);
        getCell_tanks(na_x,na_y).setTile(cell.getTile());
    }

    public void itemRaised(){

        switch (itemType){
            case 16:
                helmRaised();
                break;
            case 17:
                clockRaised();
                break;
            case 18:
                shovelRaised();
                break;
            case 19:
                starRaised();
                break;
            case 20:
                PodniesionoGranat();
                break;
            case 21:
                healthRaised();
                break;
            case 22:
                gunRaised();
                break;
            default:
                break;
        }
    }

    public void starRaised(){
        if(howManyStarsWeHave <3) {
            howManyStarsWeHave++;
            starRaised = true;
        }
    }

    public void healthRaised(){

        if(health <9) {
            health++;
            replacingTheBlock(34, (health), 32, 11);
        }
    }
    public void gunRaised(){
        howManyStarsWeHave =3;
    }
    public void helmRaised(){

        helmRaised =true;
    }
    public void clockRaised(){

        clockRaised = true;
    }
    public void shovelRaised(){
        renderFence = true;
        shovelRaised = true;
        replacingTheBlock(34,10,13,0);
        replacingTheBlock(34,10,13,1);
        replacingTheBlock(34,10,13,2);
        replacingTheBlock(34,10,14,2);
        replacingTheBlock(34,10,15,2);
        replacingTheBlock(34,10,16,0);
        replacingTheBlock(34,10,16,1);
        replacingTheBlock(34,10,16,2);
    }

    public void PodniesionoGranat(){
        grenadeRaised = true;
    }
}
