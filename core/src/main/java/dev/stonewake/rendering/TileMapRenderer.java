package dev.stonewake.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;

public class TileMapRenderer {
    public void renderOnce(SpriteBatch spriteBatch, TextureManager textureManager, TileMap tileMap, int[] layers, int startX, int endX, int startY, int endY) {
        for (int layer : layers) {
            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    if (!tileMap.isTileOnBounds(layer, x, y)) {
                        continue;
                    }

                    Tile tile = tileMap.getTileAt(layer, x, y);

                    if (tile.isTileAir()) {
                        continue;
                    }

                    int tileSize = tileMap.getTileSize();
                    Texture tileTexture = textureManager.getTexture(tile.tileType.getTileSprite());
                    int tileSpriteIndex = tile.tileType.getTileSpriteIndex(tileSize, tile);
                    int tileSpriteX = textureManager.decodifyTileIndexX(tileSpriteIndex, tileSize);
                    int tileSpriteY = textureManager.decodifyTileIndexY(tileSpriteIndex, tileSize);

                    spriteBatch.draw(tileTexture, x, y, tileSpriteX, tileSpriteY, tileSize, tileSize);
                }
            }
        }
    }
}
