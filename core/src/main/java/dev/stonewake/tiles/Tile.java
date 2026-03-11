package dev.stonewake.tiles;

public class Tile {
    private final byte tileChunkX;
    private final byte tileChunkY;
    private final short parentChunk;
    private byte flags;
    private byte cachedSpriteIndex = 0;
    public short tileType;

    public Tile(int tileLayer, int tileChunkX, int tileChunkY, short parentChunkX, short parentChunkY) {
        this.tileType = -1;
        this.flags = (byte)((0) | ((tileLayer & 0b00001111) << 2));
        this.tileChunkX = (byte)tileChunkX;
        this.tileChunkY = (byte)tileChunkY;
        this.parentChunk = (short)((parentChunkX & 0xFF) << 8 | (parentChunkY & 0xFF));
    }

    public boolean isTileOccupied() {
        return tileType != -1;
    }

    public boolean isTileAir() {
        return tileType == -1;
    }

    public boolean isTileSpriteIndexDirty() {
        return (flags & 0b00000001) != 0;
    }

    public void markTileSpriteIndexDirty() {
        flags |= 0b00000001;
    }

    public void clearDirtyTileSpriteIndex() {
        flags &= ~0b00000001;
    }

    public boolean isDirtyPhysics() {
        return (flags & 0b00000010) != 0;
    }

    public void markTilePhysicsDirty() {
        flags |= 0b00000010;
    }

    public void clearDirtyTilePhysics() {
        flags &= ~0b00000010;
    }

    public int getCachedSpriteIndex() {
        return cachedSpriteIndex & 0xFF;
    }

    public void cacheSpriteIndex(int spriteIndex) {
        cachedSpriteIndex = (byte)spriteIndex;
    }

    public int getTileLayer() {
        return (flags >> 2) & 0b00001111;
    }

    public int getTileChunkX() {
        return tileChunkX & 0xFF;
    }

    public int getTileChunkY() {
        return tileChunkY & 0xFF;
    }

    public short getParentChunkX() {
        return (short)((parentChunk >> 8) & 0xFF);
    }

    public short getParentChunkY() {
        return (short)(parentChunk & 0xFF);
    }

    public int getTileX(TileMap tileMap) {
        return getParentChunkX() * tileMap.getTileMapChunkWidth() + tileChunkX;
    }

    public int getTileY(TileMap tileMap) {
        return getParentChunkY() * tileMap.getTileMapChunkHeight() + tileChunkY;
    }
}
