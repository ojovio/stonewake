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

    public TileRegistry getTileRegistry() {
        return tileRegistry;
    }
}
