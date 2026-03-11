package dev.stonewake.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.stonewake.assets.TextureManager;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.tiles.*;
import dev.stonewake.utils.TileUtils;

public class TileMapRenderer {
    public void renderOnce(TileAssetManager tileAssetManager, TextureManager textureManager, SpriteBatch spriteBatch, TileMap tileMap, int chunkXStart, int chunkYStart, int chunkXEnd, int chunkYEnd) {
        int tileSize = tileMap.getTileSize();

        for (int chunkX = chunkXStart; chunkX <= chunkXEnd; chunkX++) {
            for (int chunkY = chunkYStart; chunkY <= chunkYEnd; chunkY++) {
                if (!tileMap.isChunkOnBounds(chunkX, chunkY)) continue;

                TileChunk chunk = tileMap.getChunk(chunkX, chunkY);

                for (int layer = 0; layer < tileMap.getTileMapLayersCount(); layer++) {
                    for (int x = 0; x < tileMap.getTileMapChunkWidth(); x++) {
                        for (int y = 0; y < tileMap.getTileMapChunkHeight(); y++) {
                            if (!chunk.isTileOnChunkBounds(tileMap, layer, x, y)) continue;

                            Tile tileToBeRendered = chunk.getTile(tileMap, layer, x, y);
                            if (tileToBeRendered.isTileAir()) continue;

                            TileType tileType = tileMap.getTileRegistry().getRegisteredTileType(tileToBeRendered.tileType);
                            Texture tileTexture = tileType.getCachedTileTexture();
                            int tileSpriteIndex = tileType.getTileSpriteIndex(tileMap, tileToBeRendered, tileSize);
                            int tileSpriteX = TileUtils.decodifyTileSpriteIndexX(tileSpriteIndex, tileTexture, tileSize);
                            int tileSpriteY = TileUtils.decodifyTileSpriteIndexY(tileSpriteIndex, tileTexture, tileSize);

                            int worldX = tileToBeRendered.getTileX(tileMap) * tileSize;
                            int worldY = tileToBeRendered.getTileY(tileMap) * tileSize;

                            spriteBatch.draw(
                                    tileTexture,
                                    worldX,
                                    worldY,
                                    tileSize,
                                    tileSize,
                                    tileSpriteX,
                                    tileSpriteY,
                                    tileSize,
                                    tileSize,
                                    false,
                                    false
                            );
                        }
                    }
                }
            }
        }
    }
}
