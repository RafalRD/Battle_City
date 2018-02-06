package com.rafdom.battle_city.Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Sprites.*;
import com.rafdom.battle_city.Sprites.Ice;

public class B2WorldCreator
{

    public B2WorldCreator (PlayScreen screen)
    {

        TiledMap map = screen.getMap();

        // end map wall
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            new EndOfMap_Wall(screen,object);
        }

        // iron
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            new Iron(screen,object);
        }
        // wall
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            new Wall(screen,object);
        }

        //bush
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            new Bush(screen,object);
        }
        // water
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            new Water(screen,object);
        }
        // ice
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            new Ice(screen, object);
        }

        // eagle
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            new Eagle(screen, object);
        }
        // eagle fence
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            new EagleFence(screen,object);
        }
    }
}

