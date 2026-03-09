package dev.stonewake.tiles;

import dev.stonewake.tiles.tiling.BitMask;

public class TileMap {
    private final int tileMapLayersCount;
    private final int tileMapWidthInChunks;
    private final int tileMapHeightInChunks;
    private final int tileMapChunkWidth;
    private final int tileMapChunkHeight;
    private final int tileSize;
    private final TileRegistry tileRegistry;
    private final BitMask bitMask;
    private final TileChunk[][] chunks;

    public TileMap(int tileMapLayersCount, int tileMapWidthInChunks, int tileMapHeightInChunks, int tileMapChunkWidth, int tileMapChunkHeight, int tileSize, Class<TileType>[] tileTypes) {
        this.tileMapLayersCount = tileMapLayersCount;
        this.tileMapWidthInChunks = tileMapWidthInChunks;
        this.tileMapHeightInChunks = tileMapHeightInChunks;
        this.tileMapChunkWidth = tileMapChunkWidth;
        this.tileMapChunkHeight = tileMapChunkHeight;
        this.tileSize = tileSize;

        tileRegistry = new TileRegistry(tileTypes);
        bitMask = new BitMask(this);
        chunks = new TileChunk[tileMapWidthInChunks][tileMapHeightInChunks];

        for (int x = 0; x < tileMapWidthInChunks; x++) {
            for (int y = 0; y < tileMapHeightInChunks; y++) {
                chunks[x][y] = new TileChunk(this, x, y);
            }
        }
    }

    public boolean isChunkOnBounds(int chunkX, int chunkY) {
        return (chunkX >= 0 && chunkX < tileMapWidthInChunks) &&
                (chunkY >= 0 && chunkY < tileMapHeightInChunks);
    }

    public boolean isTileOnBounds(int tileLayer, int tileX, int tileY) {
        return (tileLayer >= 0 && tileLayer < tileMapLayersCount) &&
                (tileX >= 0 && tileX < getTileMapWidth()) &&
                (tileY >= 0 && tileY < getTileMapHeight());
    }

    public boolean isTileFromChunkOnBounds(int tileLayer, int tileChunkX, int tileChunkY) {
        int tileX = tileChunkX * tileMapChunkWidth;
        int tileY = tileChunkY * tileMapChunkHeight;

        return isTileOnBounds(tileLayer, tileX, tileY);
    }

    public TileChunk getChunk(int chunkX, int chunkY) {
        return chunks[chunkX][chunkY];
    }

    public Tile getTile(int chunkX, int chunkY, int tileLayer, int tileChunkX, int tileChunkY) {
        if (!isChunkOnBounds(chunkX, chunkY))
            throw new RuntimeException(String.format("Chunk (%d, %d) is out of world boundaries.", chunkX, chunkY));

        return chunks[chunkX][chunkY].getTile(this, tileLayer, tileChunkX, tileChunkY);
    }

    public Tile getTile(int tileLayer, int tileX, int tileY) {
        if (!isTileOnBounds(tileLayer, tileX, tileY))
            throw new RuntimeException(String.format("Tile (%d, %d, %d) is out of world boundaries.", tileLayer, tileX, tileY));

        int chunkX = Math.floorDiv(tileX, tileMapChunkWidth);
        int chunkY = Math.floorDiv(tileY, tileMapChunkHeight);

        return chunks[chunkX][chunkY].getTileFromWorld(this, tileLayer, tileX, tileY);
    }

    public void setTile(int chunkX, int chunkY, int tileLayer, int tileChunkX, int tileChunkY, int tileId) {
        getTile(chunkX, chunkY, tileLayer, tileChunkX, tileChunkY).tileType = tileRegistry.getTileType(tileId);
    }

    public void setTile(int tileLayer, int tileX, int tileY, int tileId) {
        getTile(tileLayer, tileX, tileY).tileType = tileRegistry.getTileType(tileId);
    }

    public int getChunkX(int tileX) {
        return Math.floorDiv(tileX, tileMapChunkWidth);
    }

    public int getChunkY(int tileY) {
        return Math.floorDiv(tileY, tileMapChunkHeight);
    }

    public int getTileMapLayersCount() {
        return tileMapLayersCount;
    }

    public int getTileMapWidth() {
        return tileMapWidthInChunks * tileMapChunkWidth;
    }

    public int getTileMapHeight() {
        return tileMapHeightInChunks * tileMapChunkHeight;
    }

    public int getTileMapChunkWidth() {
        return tileMapChunkWidth;
    }

    public int getTileMapChunkHeight() {
        return tileMapChunkHeight;
    }

    public int getTileMapWidthInChunks() {
        return tileMapWidthInChunks;
    }

    public int getTileMapHeightInChunks() {
        return tileMapHeightInChunks;
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
