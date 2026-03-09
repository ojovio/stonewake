package dev.stonewake.tiles;

import dev.stonewake.tiles.events.TileChangeEvent;
import dev.stonewake.tiles.listeners.TileChangeListener;
import jdk.vm.ci.meta.Constant;

import java.io.Console;

public class TileRegistry {
    private TileType[] tileTypes;

    public TileRegistry(Class<? extends TileType>[] tileTypeClasses) {
        this.tileTypes = new TileType[tileTypeClasses.length];

        for (int i = 0; i < tileTypeClasses.length; i++) {
            try {
                TileType instantiatedTileType = tileTypeClasses[i]
                    .getDeclaredConstructor(int.class)
                    .newInstance(i);

                this.tileTypes[i] = instantiatedTileType;

            } catch (Exception e) {
                throw new RuntimeException("Erro ao instanciar Tile ID " + i, e);
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
