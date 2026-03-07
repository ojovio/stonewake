package dev.stonewake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.stonewake.assets.AssetManager;
import dev.stonewake.assets.TextureManager;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.rendering.TileMapRenderer;
import dev.stonewake.tiles.TileRegistry;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.world.GameWorld;
import dev.stonewake.world.WorldConfig;

public class Game {
    private SpriteBatch spriteBatch;
    private float deltaTime = 0f;
    private float fixedDeltaTime = 0f;
    private float alpha = 0f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private TileMapRenderer tileMapRenderer;
    private AssetManager assetManager;
    private TextureManager textureManager;
    private TileAssetManager tileAssetManager;

    private GameWorld world;

    public void start() {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        spriteBatch = new SpriteBatch();
        tileMapRenderer = new TileMapRenderer();
        assetManager = new AssetManager();
        textureManager = new TextureManager();
        TileMap tileMap = new TileMap(WorldConfig.NUM_LAYERS, WorldConfig.WORLD_WIDTH, WorldConfig.WORLD_HEIGHT, WorldConfig.TILE_SIZE, WorldConfig.TILE_TYPES);
        world = new GameWorld(tileMap);
        tileAssetManager = new TileAssetManager(tileMap, textureManager);

        assetManager.loadAllAssets(this);

        tileMap.fillTiles(0, 0, 15, 0, 15, 0);
        tileMap.clearTile(0, 5, 5);

        camera.update();
    }

    public void input() {

        float cameraSpeed = 3f;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.1f;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom += 0.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= cameraSpeed;
        }

        camera.update();
    }

    public void update() {

    }

    public void render() {
        spriteBatch.begin();
        tileMapRenderer.renderOnce(this, world.getTileMap(), 0, 100, 0, 100);
    }

    public void dispose() {
        textureManager.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
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

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Game(float fixedDeltaTime) {
        this.fixedDeltaTime = fixedDeltaTime;
    }
}
