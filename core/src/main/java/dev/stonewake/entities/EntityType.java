package dev.stonewake.entities;

import com.badlogic.gdx.graphics.Texture;

public class EntityType {
    public final int entityId;
    public final String[] entitySprites;
    public final Class<? extends Entity> entityClass;
    public Texture[] cachedEntitySprites;

    public EntityType(int entityId, String[] entitySprites, Class<? extends Entity> entityClass) {
        this.entityId = entityId;
        this.entitySprites = entitySprites;
        this.entityClass = entityClass;

        cachedEntitySprites = new Texture[entitySprites.length];
    }
}
