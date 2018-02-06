package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-06-15.
 */

public abstract class InteractiveTileObject {

    protected World world;
    private TiledMap map;
    private Rectangle rectangle;
    protected Body body;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, MapObject object)
    {
        this.object = object;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.rectangle = ((RectangleMapObject) object).getRectangle();


        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Tanks.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Tanks.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(rectangle.getWidth() / 2 / Tanks.PPM, rectangle.getHeight() / 2 / Tanks.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    // abstract
    public abstract void Hit();


    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell_eagle(int x, int y)
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        TiledMapTileLayer.Cell cell = layer.getCell((int) (x), (int) (y));
       // System.out.println(((int) (256/ 8))+ " y" + (int) (136 /8));
        return cell;
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
//        TiledMapTileLayer.Cell cell = layer.getCell((int) (256 / 8), (int) (144/ 8));
        TiledMapTileLayer.Cell cell = layer.getCell((int) (body.getPosition().x * Tanks.PPM / 8),(int) (body.getPosition().y * Tanks.PPM / 8));
//        System.out.println((int)(body.getPosition().x * Tanks.PPM / 8) + "  y =" +(int) (body.getPosition().y * Tanks.PPM / 8));
            return cell;
    }

}
