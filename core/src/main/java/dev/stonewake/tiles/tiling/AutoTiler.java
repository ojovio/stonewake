package dev.stonewake.tiles.tiling;

import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileType;

import java.util.Set;

public abstract class AutoTiler {
    private Set<Integer> connectableTileIds;
    private int numVariants;

    public AutoTiler(Set<Integer> connectableTileIds, int numVariants) {
        this.connectableTileIds = connectableTileIds;
        this.numVariants = numVariants;
    }

    public boolean connectsTo(Tile tile) {
        if (tile.isTileAir()) return false;

        return connectableTileIds.contains(tile.tileType.getTileId());
    }

    public int getDeterministicVariant(Tile tile) {
        return Math.abs(tile.getTileX() * 734287 + tile.getTileY() * 912271) % numVariants;
    }

    public abstract int getTileSpriteIndex(Tile occupiedTile, int tileSize);
}
