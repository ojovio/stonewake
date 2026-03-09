package dev.stonewake.screens;

import com.badlogic.gdx.Screen;
import dev.stonewake.Game;

public abstract class GameScreen implements Screen {
    protected Game game;
    protected boolean active;

    public GameScreen(Game game) {
        this.game = game;
    }


    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public void show() {

    }

    public abstract void load();

    public abstract  void start();

    public abstract void input();

    public abstract  void update();

    public abstract boolean isTransparent();

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
