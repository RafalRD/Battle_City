package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-06-15.
 */

public class Wall extends InteractiveTileObject {

    public Wall(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Tanks.WALL_BIT);
    }

    @Override
    public void Hit() {

        setCategoryFilter(Tanks.NOTHING_BIT);
        getCell().setTile(null);
    }

}
