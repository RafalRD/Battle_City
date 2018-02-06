package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-08-14.
 */


public class Eagle extends InteractiveTileObject {

    private PlayScreen screen;
    public Eagle(PlayScreen screen, MapObject object) {

        super(screen, object);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(Tanks.EAGLE_BIT);
    }

    @Override
    public void Hit() {

        getCell_eagle(14,0).setTile(null);
        getCell_eagle(14,1).setTile(null);
        getCell_eagle(15,0).setTile(null);
        getCell_eagle(15,1).setTile(null);
        screen.gameOver();
        Tanks.manager.get("explosion.wav", Sound.class).play();

    }
}
