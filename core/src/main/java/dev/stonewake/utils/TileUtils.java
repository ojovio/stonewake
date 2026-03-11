package dev.stonewake.utils;

import com.badlogic.gdx.graphics.Texture;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.TileType;

public class TileUtils {
    public static int decodifyTileSpriteIndexX(int index, Texture tileTexture, int tileSize) {
        return (index % (tileTexture.getWidth() / tileSize)) * tileSize;
    }

    public static int decodifyTileSpriteIndexY(int index, Texture tileTexture, int tileSize) {
        return (index / (tileTexture.getWidth() / tileSize)) * tileSize;
    }

    public static int codifyTileSpriteIndex(int spriteX, int spriteY, TileType tileType, int tileSize) {
        int columns = tileType.getCachedTileTexture().getWidth() / tileSize;
        return spriteY * columns + spriteX;
    }

    public static int codifyTilePosition(TileMap tileMap, int layer, int x, int y) {
        int layerSize = tileMap.getTileMapChunkWidth() * tileMap.getTileMapChunkHeight();
        int layerOffset = layer * layerSize;
        int rowOffset = y * tileMap.getTileMapChunkWidth();

        return x + rowOffset + layerOffset;
    }

    public static int codifyWorldTilePosition(TileMap tileMap, int layer, int x, int y) {
        int worldWidth = tileMap.getTileMapWidth();
        int worldHeight = tileMap.getTileMapHeight();

        int layerSize = worldWidth * worldHeight;
        int layerOffset = layer * layerSize;

        return x + y * worldWidth + layerOffset;
    }

    public static int decodifyWorldTileLayer(TileMap tileMap, long index) {
        int worldWidth = tileMap.getTileMapWidth();
        int worldHeight = tileMap.getTileMapHeight();
        int layerSize = worldWidth * worldHeight;

        return (int)index / layerSize;
    }

    public static int decodifyWorldTileX(TileMap tileMap, long index) {
        int worldWidth = tileMap.getTileMapWidth();
        int worldHeight = tileMap.getTileMapHeight();
        int layerSize = worldWidth * worldHeight;

        long indexInLayer = index % layerSize;
        return (int)indexInLayer % worldWidth;
    }

    public static int decodifyWorldTileY(TileMap tileMap, long index) {
        int worldWidth = tileMap.getTileMapWidth();
        int worldHeight = tileMap.getTileMapHeight();
        int layerSize = worldWidth * worldHeight;

        long indexInLayer = index % layerSize;
        return (int)indexInLayer / worldWidth;
    }

    public static int getTileChunkX(TileMap tileMap, int tileX) {
        return tileX % tileMap.getTileMapChunkWidth();
    }

    public static int getTileChunkY(TileMap tileMap, int tileY) {
        return tileY % tileMap.getTileMapChunkHeight();
    }
}
