package dev.stonewake.tiletypes;

import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileType;
import dev.stonewake.utils.TileUtils;

public class GrassTile extends TileType {

    public GrassTile(int tileId) {
        super(tileId);
    }

    @Override
    public void setDefaults() {
        this.tileSprite = "tiles/grass.png";
    }
}
