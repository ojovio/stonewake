package dev.stonewake.tiles.tiling;

import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileMap;

import java.util.function.Predicate;

public class BitMask {
    TileMap tileMap;

    public static final int NW = 1 << 0;
    public static final int N  = 1 << 1;
    public static final int NE = 1 << 2;
    public static final int W  = 1 << 3;
    public static final int E  = 1 << 4;
    public static final int SW = 1 << 5;
    public static final int S  = 1 << 6;
    public static final int SE = 1 << 7;

    public BitMask(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public int calculateBitMask(Tile tile, int layer, boolean considerWorldBorders, Predicate<Tile> condition) {
        int mask = 0;

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {

                if (x == 0 && y == 0) continue;

                int dx = tile.getTileX() + x;
                int dy = tile.getTileY() - y;

                int bit = getBit(x, y);

                if (!tileMap.isTileOnBounds(layer, dx, dy)) {
                    if (considerWorldBorders) {
                        mask |= bit;
                    }
                    continue;
                }

                Tile neighbor = tileMap.getTileAt(layer, dx, dy);

                if (condition.test(neighbor)) {
                    mask |= bit;
                }
            }
        }

        return mask;
    }

    public int calculateBitMask(Tile tile, boolean considerWorldBorders) {
        return calculateBitMask(tile, tile.getTileLayer(), considerWorldBorders, Tile::isTileOccupied);
    }

    public int calculateBitMask(Tile tile) {
        return calculateBitMask(tile, tile.getTileLayer(), true, Tile::isTileOccupied);
    }

    public int calculateBitMask(Tile tile, int layer) {
        return calculateBitMask(tile, layer, true, Tile::isTileOccupied);
    }

    public int calculateBitMask(Tile tile, boolean considerWorldBorders, Predicate<Tile> condition) {
        return calculateBitMask(tile, tile.getTileLayer(), considerWorldBorders, condition);
    }

    public int calculateBitMask(Tile tile, Predicate<Tile> condition) {
        return calculateBitMask(tile, tile.getTileLayer(), true, condition);
    }

    public int calculateBitMask(Tile tile, int layer, Predicate<Tile> condition) {
        return calculateBitMask(tile, layer, true, condition);
    }

    public int filterBitMask(int firstMask, int secondMask) {
        return firstMask & secondMask;
    }

    public int unifyBitMask(int firstMask, int secondMask) {
        return firstMask | secondMask;
    }

    public int alternateBitMask(int firstMask, int secondMask) {
        return firstMask ^ secondMask;
    }

    public int negateBitMask(int mask) {
        return ~mask;
    }

    public int getBit(int dx, int dy) {

        if (dx == -1 && dy == -1) return NW;
        if (dx ==  0 && dy == -1) return N;
        if (dx ==  1 && dy == -1) return NE;

        if (dx == -1 && dy ==  0) return W;
        if (dx ==  1 && dy ==  0) return E;

        if (dx == -1 && dy ==  1) return SW;
        if (dx ==  0 && dy ==  1) return S;
        if (dx ==  1 && dy ==  1) return SE;

        return 0;
    }
}
