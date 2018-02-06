package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-10-24.
 */

public class EndOfMap_Wall extends InteractiveTileObject {

    public EndOfMap_Wall(PlayScreen screen, MapObject object) {

        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Tanks.END_OF_MAP_BIT);
    }

    @Override
    public void Hit() {

    }
}
