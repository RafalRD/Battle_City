package com.rafdom.battle_city.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rafdom.battle_city.Tanks;

public class LevelComplete implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;

    public LevelComplete(Game game) {

        this.game=game;
        viewport = new FitViewport(Tanks.wight, Tanks.height, new OrthographicCamera());
        stage = new Stage(viewport, ((Tanks) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();

        table.setFillParent(true);

        Label gameOverLabel = new Label("LEVEL COMPLETE ", font);

        table.add(gameOverLabel).expandX();
        table.row();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen((Tanks) game));
            dispose();
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
