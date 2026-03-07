package dev.stonewake.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class Camera {
    private OrthographicCamera camera;
    private float worldWidth;
    private float worldHeight;
    private float tileSize;
    private float minZoom;
    private float maxZoom;

    public Camera(float viewportTilesX, float viewportTilesY, float tileSize, float minZoom, float maxZoom, float worldWidth, float worldHeight) {
        this.tileSize = tileSize;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        camera = new OrthographicCamera(viewportTilesX * tileSize, viewportTilesY * tileSize);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void update() {
        clampPosition();
        roundToPixel();
        camera.update();
    }

    public void resize(int width, int height) {
        float aspect = (float) height / width;
        camera.viewportHeight = camera.viewportWidth * aspect;
        update();
    }

    public void changePositionBy(float dx, float dy) {
        camera.translate(dx, dy);
    }

    public void changeZoomBy(float delta) {
        camera.zoom = MathUtils.clamp(camera.zoom + delta, minZoom, maxZoom);
    }

    private void clampPosition() {
        float halfWidth = camera.viewportWidth * camera.zoom / 2f;
        float halfHeight = camera.viewportHeight * camera.zoom / 2f;

        camera.position.x = MathUtils.clamp(camera.position.x, halfWidth, worldWidth - halfWidth);
        camera.position.y = MathUtils.clamp(camera.position.y, halfHeight, worldHeight - halfHeight);
    }

    private void roundToPixel() {
        float pixelsPerUnit = tileSize / camera.zoom;
        camera.position.x = Math.round(camera.position.x * pixelsPerUnit) / pixelsPerUnit;
        camera.position.y = Math.round(camera.position.y * pixelsPerUnit) / pixelsPerUnit;
    }

    public float getZoom() {
        return camera.zoom;
    }

    public void setZoom(float zoom) {
        camera.zoom = MathUtils.clamp(zoom, minZoom, maxZoom);
    }
}