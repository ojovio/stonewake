package dev.stonewake.physics;

import com.badlogic.gdx.physics.box2d.*;
import dev.stonewake.rendering.Camera;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.tiling.BitMask;
import dev.stonewake.utils.TileUtils;
import dev.stonewake.world.WorldConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileMapPhysicsSolver {

    private Map<Long, Body> tileBodies;

    public TileMapPhysicsSolver() {
        tileBodies = new HashMap<>();
    }

    public void updatePhysics(Camera camera, TileMap tileMap, BitMask bm, World physicsWorld, int layer) {

        int chunkX = camera.getCameraChunkX(tileMap);
        int chunkY = camera.getCameraChunkY(tileMap);

        int minChunkX = chunkX - WorldConfig.CHUNK_SIMULATION_DISTANCE_X;
        int maxChunkX = chunkX + WorldConfig.CHUNK_SIMULATION_DISTANCE_X;
        int minChunkY = chunkY - WorldConfig.CHUNK_SIMULATION_DISTANCE_Y;
        int maxChunkY = chunkY + WorldConfig.CHUNK_SIMULATION_DISTANCE_Y;

        Iterator<Map.Entry<Long, Body>> it = tileBodies.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry<Long, Body> entry = it.next();
            long key = entry.getKey();

            int entryLayer = TileUtils.decodifyWorldTileLayer(tileMap, key);
            int tX = TileUtils.decodifyWorldTileX(tileMap, key);
            int tY = TileUtils.decodifyWorldTileY(tileMap, key);

            int entryChunkX = Math.floorDiv(tX, tileMap.getTileMapChunkWidth());
            int entryChunkY = Math.floorDiv(tY, tileMap.getTileMapChunkHeight());

            if (entryChunkX < minChunkX || entryChunkX > maxChunkX ||
                    entryChunkY < minChunkY || entryChunkY > maxChunkY) {

                physicsWorld.destroyBody(entry.getValue());
                it.remove();
                continue;
            }

            Tile tile = tileMap.getTile(entryLayer, tX, tY);

            if (tile.isDirtyPhysics()) {
                calculateTilePhysics(bm, tileMap, physicsWorld, entryLayer, tX, tY);
            }
        }

        for (int simulateChunkX = minChunkX; simulateChunkX <= maxChunkX; simulateChunkX++) {
            for (int simulateChunkY = minChunkY; simulateChunkY <= maxChunkY; simulateChunkY++) {

                if (!tileMap.isChunkOnBounds(simulateChunkX, simulateChunkY)) continue;

                for (int tileX = 0; tileX < tileMap.getTileMapChunkWidth(); tileX++) {
                    for (int tileY = 0; tileY < tileMap.getTileMapChunkHeight(); tileY++) {

                        int worldTileX = simulateChunkX * tileMap.getTileMapChunkWidth() + tileX;
                        int worldTileY = simulateChunkY * tileMap.getTileMapChunkHeight() + tileY;

                        if (!tileMap.isTileOnBounds(layer, worldTileX, worldTileY)) continue;

                        long key = TileUtils.codifyWorldTilePosition(tileMap, layer, worldTileX, worldTileY);

                        Tile tile = tileMap.getTile(layer, worldTileX, worldTileY);

                        if (tileBodies.containsKey(key) && !tile.isDirtyPhysics()) continue;

                        calculateTilePhysics(bm, tileMap, physicsWorld, layer, worldTileX, worldTileY);
                    }
                }
            }
        }
    }

    public void calculateTilePhysics(BitMask bm, TileMap tileMap, World physicsWorld, int tileLayer, int tileX, int tileY) {

        long key = TileUtils.codifyWorldTilePosition(tileMap, tileLayer, tileX, tileY);
        Tile tile = tileMap.getTile(tileLayer, tileX, tileY);

        if (tile.isTileAir()) {

            Body body = tileBodies.remove(key);
            if (body != null) physicsWorld.destroyBody(body);

            return;
        }

        if (tile.isDirtyPhysics() || !tileBodies.containsKey(key)) {

            int mask = bm.calculateBitMask(tile);

            if (bm.isTileSurrounded(mask)) {

                Body body = tileBodies.remove(key);
                if (body != null) physicsWorld.destroyBody(body);

                tile.clearDirtyTilePhysics();
                return;
            }

            if (tileBodies.containsKey(key)) {

                Body body = tileBodies.remove(key);
                physicsWorld.destroyBody(body);
            }

            Body tileBody = mapTilePhysics(tileMap, tile, physicsWorld, mask);
            tileBodies.put(key, tileBody);
        }

        tile.clearDirtyTilePhysics();
    }

    public Body mapTilePhysics(TileMap tileMap, Tile tile, World physicsWorld, int mask) {
        int x = tile.getTileX(tileMap);
        int y = tile.getTileY(tileMap);

        float tileSize = tileMap.getTileSize();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        Body body = physicsWorld.createBody(bodyDef);

        float left = x * tileSize;
        float right = (x + 1) * tileSize;
        float top = (y + 1) * tileSize;
        float bottom = y * tileSize;

        if ((mask & BitMask.N) == 0) {
            createEdge(body, left, top, right, top);
        }

        if ((mask & BitMask.S) == 0) {
            createEdge(body, left, bottom, right, bottom);
        }

        if ((mask & BitMask.W) == 0) {
            createEdge(body, left, bottom, left, top);
        }

        if ((mask & BitMask.E) == 0) {
            createEdge(body, right, bottom, right, top);
        }

        return body;
    }

    private void createEdge(Body body, float x1, float y1, float x2, float y2) {

        EdgeShape edge = new EdgeShape();
        edge.set(x1, y1, x2, y2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = edge;
        fdef.friction = 0.5f;

        body.createFixture(fdef);

        edge.dispose();
    }
}