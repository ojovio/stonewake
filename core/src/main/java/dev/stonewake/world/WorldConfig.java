package dev.stonewake.world;

import dev.stonewake.tiles.TileType;
import dev.stonewake.tiletypes.GrassTile;

public class WorldConfig {
    public static final int NUM_LAYERS = 3;
    public static final int WORLD_WIDTH = 512;
    public static final int WORLD_HEIGHT = 256;
    public static final int TILE_SIZE = 8;
    public static final Class<TileType>[] TILE_TYPES = new Class[] {GrassTile.class };
}
