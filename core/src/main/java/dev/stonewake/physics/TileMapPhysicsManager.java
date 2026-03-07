package dev.stonewake.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dev.stonewake.rendering.Camera;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.tiling.BitMask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileMapPhysicsManager {
    public static int TILES_PHYSICS_MARGIN = 5;
    private Map<Long, Body> tileBodies = new HashMap<>();

    public void update(Camera camera, TileMap tileMap, BitMask bm, World physicsWorld, int layer) {
        int minX = camera.getViewPortMinXTiles() - TILES_PHYSICS_MARGIN;
        int maxX = camera.getViewPortMaxXTiles() + TILES_PHYSICS_MARGIN;
        int minY = camera.getViewPortMinYTiles() - TILES_PHYSICS_MARGIN;
        int maxY = camera.getViewPortMaxYTiles() + TILES_PHYSICS_MARGIN;

        Iterator<Map.Entry<Long, Body>> it = tileBodies.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Body> entry = it.next();
            long key = entry.getKey();

            int tx = (int) (key >> 32);
            int ty = (int) (key & 0xFFFFFFFFL);

            if (tx < minX || tx > maxX || ty < minY || ty > maxY) {
                physicsWorld.destroyBody(entry.getValue());
                it.remove();
            }
        }

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (!tileMap.isTileOnBounds(layer, x, y)) continue;

                long key = (((long) x) << 32) | (y & 0xFFFFFFFFL);
                if (tileBodies.containsKey(key)) continue;

                Tile tile = tileMap.getTileAt(layer, x, y);
                if (tile.isTileAir()) continue;

                int mask = bm.calculateBitMask(tile);
                if (bm.isTileSurrounded(mask)) continue;

                Body tileBody = mapTilePhysics(bm, tileMap, tile, physicsWorld, mask);
                tileBodies.put(key, tileBody);
            }
        }
    }

    public void recalculatePhysics(Camera camera, TileMap tileMap, BitMask bm, World physicsWorld, int layer) {
        for (Body body : tileBodies.values()) {
            physicsWorld.destroyBody(body);
        }

        tileBodies.clear();

        update(camera, tileMap, bm, physicsWorld, layer);
    }

    public Body mapTilePhysics(BitMask bm, TileMap tileMap, Tile tile, World physicsWorld, int mask) {
        int x = tile.getTileX();
        int y = tile.getTileY();
        float tileSize = tileMap.getTileSize();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(0, 0);

        Body body = physicsWorld.createBody(bodyDef);

        float left   = x * tileSize;
        float right  = (x + 1) * tileSize;
        float top    = (y + 1) * tileSize;
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