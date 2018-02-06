package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;

/**
 * Created by Rafał on 2017-06-16.
 */

public class Ice extends InteractiveTileObject{

    public Ice(PlayScreen screen, MapObject object) {
        super(screen, object);

    }

    @Override
    public void Hit() {

//        Gdx.app.log("Ice", "");
    }
}

