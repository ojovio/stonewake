package dev.stonewake.tiles;

import dev.stonewake.tiles.tiling.BitMask;

public class TileMap {
    private int tileMapLayersCount;
    private int tileMapWidth;
    private int tileMapHeight;
    private int tileSize;
    private Tile[] tiles;
    private TileRegistry tileRegistry;
    private BitMask bitMask;

    public TileMap(int tileMapLayersCount, int tileMapWidth, int tileMapHeight, int tileSize, Class<TileType>[] tileTypes) {
        this.tileMapLayersCount = tileMapLayersCount;
        this.tileMapWidth = tileMapWidth;
        this.tileMapHeight = tileMapHeight;
        this.tileSize = tileSize;
        tiles = new Tile[tileMapLayersCount * tileMapWidth * tileMapHeight];

        int layerSize = tileMapWidth * tileMapHeight;
        for (int l = 0; l < tileMapLayersCount; l++) {
            int layerOffset = l * layerSize;
            for (int y = 0; y < tileMapHeight; y++) {
                int rowOffset = y * tileMapWidth;
                for (int x = 0; x < tileMapWidth; x++) {
                    tiles[x + rowOffset + layerOffset] = new Tile(l, x, y);
                }
            }
        }
        tileRegistry = new TileRegistry(tileTypes);
        bitMask = new BitMask(this);
    }

    public boolean isTileOnBounds(int tileLayer, int tileX, int tileY) {
        return (tileLayer >= 0 && tileLayer < tileMapLayersCount) &&
               (tileX >= 0 && tileX < tileMapWidth) &&
               (tileY >= 0 && tileY < tileMapHeight);
    }

    public Tile getTileAt(int tileLayer, int tileX, int tileY) {
        int idx = tileX + tileY * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

        return tiles[idx];
    }

    public void setTile(int tileLayer, int tileX, int tileY, int tileId) {
        if (!isTileOnBounds(tileLayer, tileX, tileY)) return;

        int idx = tileX + tileY * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

        updateTile(tiles[idx]);

        tiles[idx].tileType = tileRegistry.getTileType(tileId);
    }

    public void clearTile(int tileLayer, int tileX, int tileY) {
        if (!isTileOnBounds(tileLayer, tileX, tileY)) return;

        int idx = tileX + tileY * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

        updateTile(tiles[idx]);

        tiles[idx].tileType = null;
    }

    public void fillTiles(int tileLayer, int startX, int endX, int startY, int endY, int tileId) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (!isTileOnBounds(tileLayer, x, y)) continue;

                int idx = x + y * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

                tiles[idx].tileType = tileRegistry.getTileType(tileId);
            }
        }

        updateTiles(tileLayer, startX, startY, endX, endY);
    }

    public void clearTiles(int tileLayer, int startX, int startY, int endX, int endY) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (!isTileOnBounds(tileLayer, x, y)) continue;

                int idx = x + y * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

                tiles[idx].tileType = null;
            }
        }

        updateTiles(tileLayer, startX, startY, endX, endY);
    }

    private void updateTiles(int tileLayer, int startX, int startY, int endX, int endY) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (!isTileOnBounds(tileLayer, x, y)) continue;

                int idx = x + y * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

                updateTile(tiles[idx]);
            }
        }
    }

    public void updateTile(Tile tile) {
        tile.markTileDirty();

        for (int layer = 0; layer < tileMapLayersCount; layer++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0 && layer == 0) continue;

                    int dx = tile.getTileX() + x;
                    int dy = tile.getTileY() - y;

                    if (!isTileOnBounds(layer, dx, dy)) continue;

                    Tile neighbor = tiles[dx + dy * tileMapWidth + layer * tileMapWidth * tileMapHeight];
                    neighbor.markTileDirty();
                }
            }
        }
    }

    public int getTileMapLayersCount() {
        return tileMapLayersCount;
    }

    public int getTileMapWidth() {
        return tileMapWidth;
    }

    public int getTileMapHeight() {
        return tileMapHeight;
    }

    public int getTileSize() {
        return tileSize;
    }

    public TileRegistry getTileRegistry() {
        return tileRegistry;
    }

    public BitMask getBitMask() {
        return bitMask;
    }
}
