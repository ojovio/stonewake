package dev.stonewake.events.models;

import dev.stonewake.world.GameWorld;

public class WorldEvent {
    public final GameWorld world;

    public WorldEvent(GameWorld world) {
        this.world = world;
    }
}
