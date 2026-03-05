package dev.stonewake.utils;

import com.badlogic.gdx.graphics.Texture;
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
}
