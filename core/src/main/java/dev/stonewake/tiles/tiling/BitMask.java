package dev.stonewake.tiles.tiling;

import dev.stonewake.tiles.Tile;
import dev.stonewake.tiles.TileChunk;
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

        int tileX = tile.getTileX(tileMap);
        int tileY = tile.getTileY(tileMap);

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {

                if (dx == 0 && dy == 0) continue;

                int worldX = tileX + dx;
                int worldY = tileY + dy;

                if (!tileMap.isTileOnBounds(layer, worldX, worldY)) {
                    if (considerWorldBorders) {
                        mask |= getBit(dx, dy);
                    }
                    continue;
                }

                Tile neighbor = tileMap.getTile(layer, worldX, worldY);

                if (condition.test(neighbor)) {
                    mask |= getBit(dx, dy);
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

        if (dx == -1 && dy == 1) return NW;
        if (dx ==  0 && dy == 1) return N;
        if (dx ==  1 && dy == 1) return NE;

        if (dx == -1 && dy ==  0) return W;
        if (dx ==  1 && dy ==  0) return E;

        if (dx == -1 && dy == -1) return SW;
        if (dx ==  0 && dy == -1) return S;
        if (dx ==  1 && dy == -1) return SE;

        return 0;
    }

    public boolean isTileSurrounded(int mask) {
        return mask == 0xFF;
    }

    public boolean isTileOnAir(int mask) {
        return mask == 0;
    }
}