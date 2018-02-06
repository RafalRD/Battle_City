package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-06-15.
 */

public class Bush extends InteractiveTileObject {
    public Bush(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Tanks.BUSH_BIT);
    }

    @Override
    public void Hit() {

//        Gdx.app.log("bush","");
    }
}
