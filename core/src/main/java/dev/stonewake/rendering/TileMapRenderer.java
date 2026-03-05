package dev.stonewake.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.stonewake.Game;
import dev.stonewake.assets.TextureManager;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.utils.TileUtils;

public class TileMapRenderer {
    public void renderOnce(Game game, TileMap tileMap, int startX, int endX, int startY, int endY) {
        TileAssetManager tileAssetManager = game.getTileAssetManager();
        TextureManager textureManager = game.getTextureManager();
        SpriteBatch spriteBatch = game.getSpriteBatch();

        for (int layer = 0; layer < tileMap.getTileMapLayersCount(); layer++) {
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
                    Texture tileTexture = tile.tileType.getTileTexture();
                    int tileSpriteIndex = tile.tileType.getTileSpriteIndex(tile, tileSize);
                    int tileSpriteX = TileUtils.decodifyTileSpriteIndexX(tileSpriteIndex, tileTexture, tileSize);
                    int tileSpriteY = TileUtils.decodifyTileSpriteIndexY(tileSpriteIndex, tileTexture, tileSize);
                    spriteBatch.draw(
                        tileTexture,
                        x * tileSize,
                        y * tileSize,
                        (float) tileSize,
                        (float) tileSize,
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
