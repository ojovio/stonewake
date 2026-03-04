package dev.stonewake.tiles;

public abstract class TileType {
    private int tileId;
    private String tileSprite;

    public TileType(int tileId) {
        this.tileId = tileId;
    }

    public void setDefaults() {

    }

    public int getTileId() {
        return tileId;
    }

    public String getTileSprite() {
        return tileSprite;
    }

    // must be y * tileSize + x;
    public int getTileSpriteIndex(int tileSize, Tile occupiedTile) {
        return 0;
    }
}
