package dev.stonewake.world;

import dev.stonewake.tiles.TileMap;

public class GameWorld {
    private TileMap tileMap;

    public GameWorld(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void update(float fixedDeltaTime) {

    }

    public TileMap getTileMap() {
        return tileMap;
    }
}
