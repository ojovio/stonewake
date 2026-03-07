package dev.stonewake.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.stonewake.tiles.TileMap;

public class Camera {
    private OrthographicCamera camera;
    private Viewport viewport;
    private float targetX;
    private float targetY;
    private int tileSize;
    private int tilesEndX;
    private int tilesEndY;

    private float minZoom;
    private float maxZoom;

    public Camera(TileMap tileMap, float minZoom, float maxZoom) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);

        tileSize = tileMap.getTileSize();
        this.tilesEndX = tileMap.getTileMapWidth() * tileSize;
        this.tilesEndY = tileMap.getTileMapHeight() * tileSize;

        this.minZoom = minZoom;
        this.maxZoom = maxZoom;

        camera.update();
    }

    public void update() {
        camera.zoom = MathUtils.clamp(camera.zoom, minZoom, maxZoom);

        float halfScreenWidth = getHalfViewPortWidth();
        float halfScreenHeight = getHalfViewPortHeight();

        targetX = MathUtils.clamp(targetX, halfScreenWidth, tilesEndX - halfScreenWidth);
        targetY = MathUtils.clamp(targetY, halfScreenHeight, tilesEndY - halfScreenHeight);

        camera.position.x = targetX;
        camera.position.y = targetY;

        camera.update();
    }

    public float getHalfViewPortWidth() {
        return (viewport.getWorldWidth() * camera.zoom) / 2f;
    }

    public float getHalfViewPortHeight() {
        return (viewport.getWorldHeight() * camera.zoom) / 2f;
    }

    public float getViewPortMinX() {
        return camera.position.x - getHalfViewPortWidth();
    }

    public float getViewPortMaxX() {
        return camera.position.x + getHalfViewPortWidth();
    }

    public float getViewPortMinY() {
        return camera.position.y - getHalfViewPortHeight();
    }

    public float getViewPortMaxY() {
        return camera.position.y + getHalfViewPortHeight();
    }

    public int getViewPortMinXTiles() {
        return (int)Math.floor(getViewPortMinX() / tileSize);
    }

    public int getViewPortMaxXTiles() {
        return (int)Math.ceil(getViewPortMaxX() / tileSize);
    }

    public int getViewPortMinYTiles() {
        return (int)Math.floor(getViewPortMinY() / tileSize);
    }

    public int getViewPortMaxYTiles() {
        return (int)Math.ceil(getViewPortMaxY() / tileSize);
    }

    public void renderCamera(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void resizeCamera(int width, int height) {
        viewport.update(width, height, false);
    }

    public void changeZoom(float delta) {
        camera.zoom += delta;
    }

    public void changePosition(float dx, float dy) {
        targetX += dx;
        targetY += dy;
    }

    public void setZoom(float zoom) {
        camera.zoom = zoom;
    }

    public void setPosition(float x, float y) {
        targetX = x;
        targetY = y;
    }

    public Matrix4 getCombinedMatrix() {
        return camera.combined;
    }

    public float getCameraX() {
        return camera.position.x;
    }

    public float getCameraY() {
        return camera.position.y;
    }

    public OrthographicCamera getLibGdxCamera() {
        return camera;
    }
}
