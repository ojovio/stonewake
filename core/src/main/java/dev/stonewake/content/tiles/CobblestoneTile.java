package dev.stonewake.content.tiles;

import dev.stonewake.tiles.TileType;
import dev.stonewake.content.autotilers.SimpleAutoTiler;

import java.util.HashSet;

public class CobblestoneTile extends TileType {

    public CobblestoneTile() {
        super((short) 1);
    }

    @Override
    public void setDefaults() {
        this.tileSprite = "tiles/cobblestone.png";
        this.autoTiler = new SimpleAutoTiler(new HashSet<>(), 2);
    }
}
