package dev.stonewake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.stonewake.tiletypes.GrassTile;
import dev.stonewake.tiles.TileMap;

public class Game {
    private SpriteBatch spriteBatch;
    private float deltaTime = 0f;
    private float fixedDeltaTime = 0f;
    private float alpha = 0f;

    public void start() {
        TileMap tileMap = new TileMap(2, 100, 100, new Class[]{GrassTile.class});
    }

    public void input() {

    }

    public void update() {

    }

    public void render() {

    }

    public void updateDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void updateAlpha(float alpha) {
        this.alpha = alpha;
    }

    public Game(SpriteBatch spriteBatch, float fixedDeltaTime) {
        this.spriteBatch = spriteBatch;
        this.fixedDeltaTime = fixedDeltaTime;
    }
}
