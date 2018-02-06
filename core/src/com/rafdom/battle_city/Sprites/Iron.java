package com.rafdom.battle_city.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.rafdom.battle_city.Screens.PlayScreen;
import com.rafdom.battle_city.Tanks;

/**
 * Created by Rafał on 2017-06-15.
 */

public class Iron extends InteractiveTileObject {

    private int stars;
    public Iron(PlayScreen screen, MapObject object)
    {
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Tanks.IRON_BIT);
        stars =0;
    }

    @Override
    public void Hit() {

    }
    public void Hit_2 (Bullet stars){

        this.stars =stars.getStars();
        if(this.stars ==3) {
            setCategoryFilter(Tanks.NOTHING_BIT);
            getCell().setTile(null);
        }
    }

}
