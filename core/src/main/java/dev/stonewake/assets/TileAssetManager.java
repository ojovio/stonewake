package dev.stonewake.assets;

import dev.stonewake.rendering.TextureManager;
import dev.stonewake.tiles.TileRegistry;
import dev.stonewake.tiles.TileType;

public class TileAssetManager {
    public void loadTileTextures(TileRegistry tileRegistry, TextureManager textureManager) {
        String[] tileTextures = new String[tileRegistry.getTileTypes().length];

        for (TileType tileType : tileRegistry.getTileTypes()) {
            tileTextures[tileType.getTileId()] = tileType.getTileSprite();
        }

        textureManager.loadTextures(tileTextures);
    }
}
