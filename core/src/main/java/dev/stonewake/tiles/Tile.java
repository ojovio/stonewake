package dev.stonewake.tiles;

public class Tile {
    private int tileLayer;
    private int tileX;
    private int tileY;
    private boolean dirtyBitMask;
    private boolean dirtySpriteIndex;
    private int cachedSpriteIndex = 0;
    private int cachedBitMask = 0;
    public TileType tileType;

    public Tile(int tileLayer, int tileX, int tileY) {
        this.tileLayer = tileLayer;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileLayer() {
        return tileLayer;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public boolean isTileOccupied() {
        return tileType != null;
    }

    public boolean isTileAir() {
        return tileType == null;
    }

    public boolean isBitMaskDirty() {
        return dirtyBitMask;
    }

    public boolean isSpriteIndexDirty() {
        return dirtySpriteIndex;
    }

    public void markDirtyBitMask() {
        dirtyBitMask = true;
    }

    public void markDirtySpriteIndex() {
        dirtySpriteIndex = true;
    }

    public void clearDirtyBitMask() {
        dirtyBitMask = false;
    }

    public void clearDirtySpriteIndex() {
        dirtySpriteIndex = false;
    }

    public int getCachedBitMask() {
        return cachedBitMask;
    }

    public int getCachedSpriteIndex() {
        return cachedSpriteIndex;
    }

    public void cacheBitMask(int bitMask) {
        cachedBitMask = bitMask;
    }

    public void cacheSpriteIndex(int spriteIndex) {
        cachedSpriteIndex = spriteIndex;
    }
}
