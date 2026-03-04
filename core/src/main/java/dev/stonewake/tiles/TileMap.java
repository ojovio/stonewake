package dev.stonewake.tiles;

public class TileMap {
    private int tileMapLayersCount;
    private int tileMapWidth;
    private int tileMapHeight;
    private int tileSize;
    private Tile[] tiles;
    private TileRegistry tileRegistry;

    public TileMap(int tileMapLayersCount, int tileMapWidth, int tileMapHeight, int tileSize, Class<TileType>[] tileTypes) {
        this.tileMapLayersCount = tileMapLayersCount;
        this.tileMapWidth = tileMapWidth;
        this.tileMapHeight = tileMapHeight;
        this.tileSize = tileSize;
        tiles = new Tile[tileMapLayersCount * tileMapWidth * tileMapHeight];

        for (int i = 0; i < tileMapLayersCount; i++) {
            for (int j = 0; j < tileMapHeight; j++) {
                for (int k = 0; k < tileMapWidth; k++) {
                    int idx = j + k * tileMapWidth + i * tileMapWidth * tileMapHeight;
                    tiles[idx] = new Tile(i, j, k);
                }
            }
        }

        tileRegistry = new TileRegistry(tileTypes);
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

    public void setTileAt(int tileLayer, int tileX, int tileY, int tileId) {
        int idx = tileX + tileY * tileMapWidth + tileLayer * tileMapWidth * tileMapHeight;

        tiles[idx].tileType = tileRegistry.getTileType(tileId);
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
}
