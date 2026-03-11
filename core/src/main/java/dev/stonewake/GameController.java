package dev.stonewake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.stonewake.assets.TextureManager;
import dev.stonewake.screens.GameScreen;
import dev.stonewake.screens.ScreenManager;
import dev.stonewake.screens.ScreenRenderer;

public abstract class GameController {
    protected float fixedDeltaTime;
    protected float deltaTime;
    protected float alpha;

    protected boolean hasSetInitialGameScreen;
    protected ScreenManager screenManager;
    protected ScreenRenderer screenRenderer;
    protected GameScreen currentGameScreen;
    protected SpriteBatch spriteBatch;

    private TextureManager textureManager;

    public GameController(float fixedDeltaTime) {
        this.fixedDeltaTime = fixedDeltaTime;

        screenManager = new ScreenManager();
        screenRenderer = new ScreenRenderer(screenManager);
        spriteBatch = new SpriteBatch();

        textureManager = new TextureManager();
    }

    public abstract void start();

    public abstract void input();

    public abstract void update();

    public abstract void render();

    public abstract void dispose();

    public void resize(int width, int height) {
        screenRenderer.resize(width, height);
    }

    public void setInitialGameScreen(GameScreen screen) {
        if (hasSetInitialGameScreen)
            throw new RuntimeException("Initial game screen can be set just once.");

        hasSetInitialGameScreen = true;
        screenManager.set(this, screen);
    }

    public float getFixedDeltaTime() {
        return fixedDeltaTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void updateDeltaTime(float dt) {
        deltaTime = dt;
    }

    public void updateAlpha(float alpha) {
        this.alpha = alpha;
    }

    public GameScreen getCurrentGameScreen() {
        return currentGameScreen;
    }

    public void setCurrentGameScreen(GameScreen currentGameScreen) {
        this.currentGameScreen = currentGameScreen;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
