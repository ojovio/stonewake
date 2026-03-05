package dev.stonewake.assets;

import com.badlogic.gdx.graphics.Texture;
import dev.stonewake.Game;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.TileType;

public class TileAssetManager {
    private TextureManager textureManager;

    public TileAssetManager(TileMap tileMap, TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void loadTileTextures(Game game) {
        String[] tileTextures = new String[game.getTileRegistry().getTileTypes().length];

        for (TileType tileType : game.getTileRegistry().getTileTypes()) {
            tileTextures[tileType.getTileId()] = tileType.getTileSprite();
        }

        game.getTextureManager().loadTextures(tileTextures);

        for (TileType tileType : game.getTileRegistry().getTileTypes()) {
            tileType.loadTileTexture(game.getTextureManager().getTexture(tileType.getTileSprite()));
        }
    }
}
