package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-11-15.
 */

public class EagleFence extends InteractiveTileObject {


    public EagleFence(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Tanks.EAGLE_FENCE_BIT);
    }

    @Override
    public void Hit() {


    }
    public void Hit_2 (Bullet b){

            setCategoryFilter(Tanks.NOTHING_BIT);
            getCell().setTile(null);
    }

}
