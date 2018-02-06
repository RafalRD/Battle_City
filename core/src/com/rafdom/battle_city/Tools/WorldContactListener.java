package com.rafdom.battle_city.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rafdom.battle_city.Sprites.*;
import com.rafdom.battle_city.Sprites.Enemies.Enemy;
import com.rafdom.battle_city.Sprites.EagleFence;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-06-15.
 */

public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        int  cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits  ;

        switch (cDef) {

            case Tanks.OUR_TANK_BIT | Tanks.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.ENEMY_BIT)
                {
                    ((Enemy) fixA.getUserData()).hit();
                }
                else {
                    ((Enemy) fixB.getUserData()).hit();
                }
                break;

            case Tanks.OUR_TANK_BIT | Tanks.ENEMY_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_TANK_BIT)
                {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                    ((Yellow_Tank) fixA.getUserData()).Hit();
                }
                else {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((Yellow_Tank) fixB.getUserData()).Hit();
                }
                break;

            case Tanks.OUR_BULLET_BIT | Tanks.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((InteractiveTileObject) fixB.getUserData()).Hit();
                }
                else {
                    ((InteractiveTileObject) fixA.getUserData()).Hit();
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.ENEMY_BULLET_BIT | Tanks.WALL_BIT:                             // pocisk vs mur
                if (fixA.getFilterData().categoryBits == Tanks.ENEMY_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((InteractiveTileObject) fixB.getUserData()).Hit();
                }
                else {
                    ((InteractiveTileObject) fixA.getUserData()).Hit();
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.OUR_BULLET_BIT | Tanks.IRON_BIT:
                if(fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((Iron) fixB.getUserData()).Hit_2((Bullet) fixA.getUserData());
                }
                else {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                    ((Iron) fixA.getUserData()).Hit_2((Bullet) fixB.getUserData());
                }

                break;

            case Tanks.ENEMY_BULLET_BIT | Tanks.IRON_BIT:
                if(fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                else ((Bullet) fixB.getUserData()).setToDestroyed();
                break;

            case Tanks.OUR_BULLET_BIT | Tanks.END_OF_MAP_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                else {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.OUR_TANK_BIT | Tanks.ICE_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_TANK_BIT)
                {
                    ((InteractiveTileObject) fixB.getUserData()).Hit();
                }
                else ((InteractiveTileObject) fixA.getUserData()).Hit();
                break;

            case Tanks.ENEMY_BIT | Tanks.WALL_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.ENEMY_BIT)
                {
                    ((Enemy) fixA.getUserData()).hit();
                }
                else ((Enemy) fixB.getUserData()).hit();
                break;

            case Tanks.ENEMY_BIT | Tanks.OUR_BULLET_BIT:
                if(fixA.getFilterData().categoryBits == Tanks.ENEMY_BIT)
                {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                    ((Enemy) fixA.getUserData()).setToDestroyed();
                }
                else
                {
                    ((Enemy) fixB.getUserData()).setToDestroyed();
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.ENEMY_BIT | Tanks.END_OF_MAP_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.ENEMY_BIT)
                {
                    ((Enemy) fixA.getUserData()).hit();
                }
                else ((Enemy) fixB.getUserData()).hit();
                break;

            case Tanks.ENEMY_BIT | Tanks.IRON_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.ENEMY_BIT)
                {
                    ((Enemy) fixA.getUserData()).hit();
                }
                else ((Enemy) fixB.getUserData()).hit();
                break;

            case Tanks.ENEMY_BIT | Tanks.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.ENEMY_BIT)
                {
                    ((Enemy) fixA.getUserData()).hit();
                    ((Enemy) fixB.getUserData()).hit();
                }
                else
                    {
                    ((Enemy) fixB.getUserData()).hit();
                    ((Enemy) fixA.getUserData()).hit();
                }
                break;
            case Tanks.OUR_BULLET_BIT | Tanks.ENEMY_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                }
                else {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.OUR_BULLET_BIT | Tanks.EAGLE_BIT:
                if (fixB.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT )
                {
                    ((Eagle) fixA.getUserData()).Hit();
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                }
                else {
                    ((Eagle) fixB.getUserData()).Hit();
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.ENEMY_BULLET_BIT | Tanks.EAGLE_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.EAGLE_BIT)
                {
                    ((Eagle) fixA.getUserData()).Hit();
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                }
                else {
                    ((Eagle) fixB.getUserData()).Hit();
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                break;

            case Tanks.OUR_TANK_BIT | Tanks.Item_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_TANK_BIT)
                {
                    ((Item) fixB.getUserData()).setToDestroyed();
                    ((Yellow_Tank) fixA.getUserData()).itemRaised();
                }
                else {
                    ((Item) fixA.getUserData()).setToDestroyed();
                    ((Yellow_Tank) fixB.getUserData()).itemRaised();
                }
                break;

            case Tanks.END_OF_MAP_BIT | Tanks.ENEMY_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                }
                else ((Bullet) fixB.getUserData()).setToDestroyed();
                break;

            case Tanks.OUR_BULLET_BIT | Tanks.EAGLE_FENCE_BIT:
                if (fixA.getFilterData().categoryBits == Tanks.OUR_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((EagleFence) fixB.getUserData()).Hit_2((Bullet) fixA.getUserData());
                }
                else {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                    ((EagleFence) fixA.getUserData()).Hit_2((Bullet) fixB.getUserData());
                }

                break;
            case Tanks.ENEMY_BULLET_BIT | Tanks.EAGLE_FENCE_BIT:
                if(fixA.getFilterData().categoryBits== Tanks.ENEMY_BULLET_BIT)
                {
                    ((Bullet) fixA.getUserData()).setToDestroyed();
                    ((EagleFence) fixB.getUserData()).Hit_2((Bullet) fixA.getUserData());
                }
                else {
                    ((Bullet) fixB.getUserData()).setToDestroyed();
                    ((EagleFence) fixA.getUserData()).Hit_2((Bullet) fixB.getUserData());
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
