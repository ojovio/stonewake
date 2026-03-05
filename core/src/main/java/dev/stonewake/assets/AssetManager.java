package dev.stonewake.assets;

import dev.stonewake.Game;
import dev.stonewake.tiles.TileRegistry;

public class AssetManager {
    public void loadAllAssets(Game game) {
        game.getTileAssetManager().loadTileTextures(game);
    }
}
