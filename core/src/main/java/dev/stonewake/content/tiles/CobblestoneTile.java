package dev.stonewake.content.tiles;

import dev.stonewake.tiles.TileType;
import dev.stonewake.tiles.tiling.autotilers.SimpleAutoTiler;

import java.util.HashSet;

public class CobblestoneTile extends TileType {

    public CobblestoneTile(int tileId) {
        super(tileId);
    }

    @Override
    public void setDefaults() {
        this.tileSprite = "tiles/grass.png";
        this.autoTiler = new SimpleAutoTiler(new HashSet<>(), 2);
    }
}
