package dev.stonewake.content.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import dev.stonewake.GameController;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.content.tiles.CobblestoneTile;
import dev.stonewake.content.tiles.GrassTile;
import dev.stonewake.physics.TileMapPhysicsSolver;
import dev.stonewake.rendering.Camera;
import dev.stonewake.rendering.TileMapRenderer;
import dev.stonewake.screens.GameScreen;
import dev.stonewake.tiles.TileChunk;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.TileRegistry;
import dev.stonewake.tiles.TileType;

public class WorldScreen extends GameScreen {
    private World physicsWorld;
    private TileMap tileMap;
    private TileMapRenderer tileMapRenderer;
    private TileMapPhysicsSolver tileMapPhysicsSolver;
    private TileRegistry tileRegistry;
    private TileAssetManager tileAssetManager;

    private static final float CAMERA_SPEED = 3f;
    private Camera camera;

    public WorldScreen(GameController game) {
        super(game);
    }

    @Override
    public void load() {
        physicsWorld = new World(new Vector2(0f, -9.8f), false);
        tileRegistry = new TileRegistry();
        tileMap = new TileMap(tileRegistry, 3, (short)32, (short)16, 16, 16, 8);
        tileMapRenderer = new TileMapRenderer();
        tileAssetManager = new TileAssetManager(tileMap, game.getTextureManager());

        tileMapPhysicsSolver = new TileMapPhysicsSolver();

        tileRegistry.registerTileType(new GrassTile());
        tileRegistry.registerTileType(new CobblestoneTile());

        tileAssetManager.loadTileTextures(tileRegistry, game.getTextureManager());

        camera = new Camera(tileMap, 0.1f, 1.5f);
        camera.update();
    }

    @Override
    public void render(float v) {
        camera.renderCamera(game.getSpriteBatch());

        tileMapRenderer.renderOnce(tileAssetManager, game.getTextureManager(), game.getSpriteBatch(), tileMap, 0, 0, 1, 5);
    }

    @Override
    public void resize(int i, int i1) {
        camera.resizeCamera(i, i1);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void start() {
        for (int chunkX = 0; chunkX < 2; chunkX++) {
            for (int chunkY = 0; chunkY < 5; chunkY++) {
                TileChunk chunk = tileMap.getChunk(chunkX, chunkY);
                for (int x = 1; x < tileMap.getTileMapChunkWidth(); x++) {
                    for (int y = 0; y < tileMap.getTileMapChunkHeight(); y++) {
                        int id = (x % 2 == 0 && y % 2 == 0) ? 0 : 1;
                        chunk.setTile(tileMap, 0, x, y, (short)id);
                    }
                }
            }
        }
    }

    @Override
    public void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.changePosition(-CAMERA_SPEED, 0f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.changePosition(CAMERA_SPEED, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.changePosition(0f, CAMERA_SPEED);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.changePosition(0f, -CAMERA_SPEED);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.changeZoom(-0.1f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.changeZoom(0.1f);
        }

        camera.update();
    }

    @Override
    public void update() {
        camera.update();
        physicsWorld.step(game.getFixedDeltaTime(), 6, 2);
        tileMapPhysicsSolver.updatePhysics(camera, tileMap, tileMap.getBitMask(), physicsWorld, 0);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }
}
