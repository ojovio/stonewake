package dev.stonewake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import dev.stonewake.assets.AssetManager;
import dev.stonewake.assets.TextureManager;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.rendering.TileMapRenderer;
import dev.stonewake.tiles.TileRegistry;
import dev.stonewake.tiles.TileType;
import dev.stonewake.tiletypes.GrassTile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.world.GameWorld;
import dev.stonewake.world.WorldConfig;

public class Game {
    private SpriteBatch spriteBatch;
    private float deltaTime = 0f;
    private float fixedDeltaTime = 0f;
    private float alpha = 0f;

    private TileMapRenderer tileMapRenderer;
    private AssetManager assetManager;
    private TextureManager textureManager;
    private TileAssetManager tileAssetManager;

    private GameWorld world;

    public void start() {
        spriteBatch = new SpriteBatch();
        tileMapRenderer = new TileMapRenderer();
        assetManager = new AssetManager();
        textureManager = new TextureManager();
        TileMap tileMap = new TileMap(WorldConfig.NUM_LAYERS, WorldConfig.WORLD_WIDTH, WorldConfig.WORLD_HEIGHT, WorldConfig.TILE_SIZE, WorldConfig.TILE_TYPES);
        world = new GameWorld(tileMap);
        tileAssetManager = new TileAssetManager(tileMap, textureManager);

        assetManager.loadAllAssets(this);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tileMap.setTileAt(0, i, j, 0);
            }
        }
    }

    public void input() {

    }

    public void update() {

    }

    public void render() {
        tileMapRenderer.renderOnce(this, world.getTileMap(), 0, 5, 0, 5);
    }

    public void dispose() {
        textureManager.dispose();
    }

    public void updateDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void updateAlpha(float alpha) {
        this.alpha = alpha;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public TileAssetManager getTileAssetManager() {
        return tileAssetManager;
    }

    public TileRegistry getTileRegistry() {
        return world.getTileMap().getTileRegistry();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Game(float fixedDeltaTime) {
        this.fixedDeltaTime = fixedDeltaTime;
    }
}
