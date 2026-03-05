package dev.stonewake.tiletypes;

import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileType;

public class GrassTile extends TileType {

    public GrassTile(int tileId) {
        super(tileId);
    }

    @Override
    public void setDefaults() {
        this.tileSprite = "tiles/grass.png";
    }

    @Override
    public int getTileSpriteIndex(TileAssetManager tileAssetManager, Tile occupiedTile) {
        return tileAssetManager.codifyTileSpriteIndex(2, 1, this);
    }
}
