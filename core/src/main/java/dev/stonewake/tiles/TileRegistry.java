package dev.stonewake.tiles;

import jdk.vm.ci.meta.Constant;

import java.io.Console;

public class TileRegistry {
    private TileType[] tileTypes;

    public TileRegistry(Class<? extends TileType>[] tileTypes) {
        this.tileTypes = new TileType[tileTypes.length];

        int currentId = -1;
        for (Class<? extends TileType> tileType : tileTypes) {
            try {
                TileType instantiatedTileType =
                    tileType
                        .getDeclaredConstructor(int.class)
                        .newInstance(currentId++);

                this.tileTypes[currentId] = instantiatedTileType;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public TileType getTileType(int tileId) {
        return tileTypes[tileId];
    }

    public TileType[] getTileTypes() {
        return tileTypes;
    }
}
