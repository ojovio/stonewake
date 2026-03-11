package dev.stonewake.tiles;

import java.util.Collection;
import java.util.HashMap;

public class TileRegistry {
    private HashMap<Short, TileType> tileTypes;

    public TileRegistry() {
        this.tileTypes = new HashMap<>();
    }

    public void registerTileType(TileType tileType) {
        tileTypes.put(tileType.getTileId(), tileType);
    }

    public TileType getRegisteredTileType(short tileId) {
        return tileTypes.get(tileId);
    }

    public Collection<TileType> getRegisteredTileTypes() {
        return tileTypes.values();
    }
}
