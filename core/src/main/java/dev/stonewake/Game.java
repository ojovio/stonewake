package dev.stonewake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import dev.stonewake.assets.AssetManager;
import dev.stonewake.assets.TextureManager;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.physics.TileMapPhysicsManager;
import dev.stonewake.rendering.Camera;
import dev.stonewake.rendering.TileMapRenderer;
import dev.stonewake.tiles.TileRegistry;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.world.GameWorld;
import dev.stonewake.world.WorldConfig;
import dev.stonewake.tiles.tiling.BitMask;

public class Game {
    private SpriteBatch spriteBatch;
    private float deltaTime = 0f;
    private float fixedDeltaTime = 0f;
    private float alpha = 0f;

    private Camera camera;
    private TileMapRenderer tileMapRenderer;
    private AssetManager assetManager;
    private TextureManager textureManager;
    private TileAssetManager tileAssetManager;

    private GameWorld world;
    private World physicsWorld;
    private TileMapPhysicsManager tileMapPhysicsManager;
    private BitMask bitMask;

    private Texture ballTexture;
    private Body ballBody;

    public void start() {
        spriteBatch = new SpriteBatch();
        tileMapRenderer = new TileMapRenderer();
        assetManager = new AssetManager();
        textureManager = new TextureManager();
        TileMap tileMap = new TileMap(WorldConfig.NUM_LAYERS, WorldConfig.WORLD_WIDTH, WorldConfig.WORLD_HEIGHT, WorldConfig.TILE_SIZE, WorldConfig.TILE_TYPES);
        world = new GameWorld(tileMap);
        tileAssetManager = new TileAssetManager(tileMap, textureManager);
        camera = new Camera(tileMap, 0.1f, 0.5f);
        bitMask = tileMap.getBitMask();
        tileMapPhysicsManager = new TileMapPhysicsManager();

        assetManager.loadAllAssets(this);

        tileMap.fillTiles(0, 0, WorldConfig.WORLD_WIDTH - 1, 0, 15, 0);
        tileMap.clearTile(0, 5, 5);
        tileMap.clearTile(0, 255, 5);

        camera.update();

        physicsWorld = new World(new Vector2(0f, -9.8f), true);

        ballTexture = new Texture(Gdx.files.internal("ball.png"));
        createBall();
    }

    private void createBall() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(camera.getCameraX(), camera.getCameraY() + 5);

        ballBody = physicsWorld.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;

        ballBody.createFixture(fixtureDef);
        circle.dispose();
    }

    public void input() {
        float cameraSpeed = 3f;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.changeZoom(-0.1f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.changeZoom(0.1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.changePosition(-cameraSpeed, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.changePosition(cameraSpeed, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.changePosition(0f, cameraSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.changePosition(0f, -cameraSpeed);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float screenX = Gdx.input.getX();
            float screenY = Gdx.input.getY();

            Vector3 worldCoords = new Vector3(screenX, screenY, 0);
            camera.getLibGdxCamera().unproject(worldCoords);

            int tileX = (int)Math.floor(worldCoords.x / WorldConfig.TILE_SIZE);
            int tileY = (int)Math.floor(worldCoords.y / WorldConfig.TILE_SIZE);

            tileMapPhysicsManager.recalculatePhysics(camera, world.getTileMap(), bitMask, physicsWorld, 0);
            world.getTileMap().clearTile(0, tileX, tileY);
        }

        camera.update();
    }

    public void update() {
        camera.update();
        float step = (fixedDeltaTime > 0) ? fixedDeltaTime : Gdx.graphics.getDeltaTime();
        tileMapPhysicsManager.update(camera, world.getTileMap(), bitMask, physicsWorld, 0);
        physicsWorld.step(step, 6, 2);
    }

    public void render() {
        camera.renderCamera(spriteBatch);
        tileMapRenderer.renderOnce(this, world.getTileMap(), camera.getViewPortMinXTiles(), camera.getViewPortMaxXTiles(), camera.getViewPortMinYTiles(), camera.getViewPortMaxYTiles());

        spriteBatch.setProjectionMatrix(camera.getCombinedMatrix());

        float size = 2.75f;
        spriteBatch.draw(ballTexture,
                ballBody.getPosition().x - size/2f,
                ballBody.getPosition().y - size/2f,
                size, size);
    }

    public void dispose() {
        textureManager.dispose();
        physicsWorld.dispose();
        ballTexture.dispose();
    }

    public void resize(int width, int height) {
        camera.resizeCamera(width, height);
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