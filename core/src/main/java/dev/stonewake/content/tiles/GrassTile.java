package dev.stonewake.content.tiles;

import dev.stonewake.tiles.TileType;
import dev.stonewake.content.autotilers.SimpleAutoTiler;
import dev.stonewake.tiles.events.TileChangeEvent;
import dev.stonewake.tiles.listeners.TileChangeListener;

import java.util.HashSet;

public class GrassTile extends TileType {

    public GrassTile() {
        super((short) 0);
    }

    @Override
    public void setDefaults() {
        this.tileSprite = "tiles/grass.png";
        this.autoTiler = new SimpleAutoTiler(new HashSet<>(), 2);
    }
}
