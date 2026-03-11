package dev.stonewake.tiles.tiling;

import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;
import dev.stonewake.tiles.TileType;

import java.util.Set;

public abstract class AutoTiler {
    private Set<Short> connectableTileIds;
    private BitMaskComparer[] comparers;
    private int numVariants;

    public AutoTiler(Set<Short> connectableTileIds, int numVariants, int numComparers) {
        this.connectableTileIds = connectableTileIds;
        this.numVariants = numVariants;
        this.comparers = new BitMaskComparer[numComparers];
    }

    public boolean connectsTo(Tile tile) {
        if (tile.isTileAir()) return false;

        return connectableTileIds.contains(tile.tileType);
    }

    public int getDeterministicVariant(TileMap tileMap, Tile tile) {
        int hash = tile.getTileX(tileMap) * 734287 ^ tile.getTileY(tileMap) * 912271;
        return Math.floorMod(hash, numVariants);
    }

    public abstract int getTileSpriteIndex(TileMap tileMap, Tile occupiedTile, int tileSize);

    public void addBitMaskComparer(int id, BitMaskComparer bitMaskComparer) {
        comparers[id] = bitMaskComparer;
    }

    public BitMaskComparer getBitMaskComparer(int id) {
        return comparers[id];
    }
}
