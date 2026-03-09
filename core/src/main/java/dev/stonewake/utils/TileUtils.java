package dev.stonewake.utils;

import com.badlogic.gdx.graphics.Texture;
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
        int columns = tileType.getTileTexture().getWidth() / tileSize;
        return spriteY * columns + spriteX;
    }

    public static int codifyTilePosition(TileMap tileMap, int layer, int x, int y) {
        int layerSize = tileMap.getTileMapChunkWidth() * tileMap.getTileMapChunkHeight();
        int layerOffset = layer * layerSize;
        int rowOffset = y * tileMap.getTileMapChunkWidth();

        return x + rowOffset + layerOffset;
    }

    public static int decodifyTileLayer(TileMap tileMap, int index) {
        int layerSize = tileMap.getTileMapChunkWidth() * tileMap.getTileMapChunkHeight();
        return index / layerSize;
    }

    public static int decodifyTileX(TileMap tileMap, int index) {
        int chunkWidth = tileMap.getTileMapChunkWidth();
        int layerSize = chunkWidth * tileMap.getTileMapChunkHeight();

        int indexInLayer = index % layerSize;
        return indexInLayer % chunkWidth;
    }

    public static int decodifyTileY(TileMap tileMap, int index) {
        int chunkWidth = tileMap.getTileMapChunkWidth();
        int layerSize = chunkWidth * tileMap.getTileMapChunkHeight();

        int indexInLayer = index % layerSize;
        return indexInLayer / chunkWidth;
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
}
