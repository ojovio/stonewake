package dev.stonewake.assets;

import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.TileRegistry;
import dev.stonewake.tiles.TileType;

public class TileAssetManager {
    private TextureManager textureManager;

    public TileAssetManager(TileMap tileMap, TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void loadTileTextures(TileRegistry tileRegistry, TextureManager textureManager) {
        String[] tileSprites = new String[tileRegistry.getRegisteredTileTypes().size()];

        for (TileType tileType : tileRegistry.getRegisteredTileTypes()) {
            tileSprites[tileType.getTileId()] = tileType.getTileSprite();
        }

        textureManager.loadTextures(tileSprites);

        for (TileType tileType : tileRegistry.getRegisteredTileTypes()) {
            tileType.loadTileTexture(textureManager.getTexture(tileType.getTileSprite()));
        }
    }
}
