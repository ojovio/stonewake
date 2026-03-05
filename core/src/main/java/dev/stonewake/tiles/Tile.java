package dev.stonewake.tiles;

public class Tile {
    private int tileLayer;
    private int tileX;
    private int tileY;
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
}
