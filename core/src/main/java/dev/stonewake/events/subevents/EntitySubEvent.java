package dev.stonewake.events.models;

import dev.stonewake.entities.Entity;
import dev.stonewake.entities.types.LivingEntityType;
import dev.stonewake.world.GameWorld;

public class EntityEvent<T extends Entity, U extends LivingEntityType> extends WorldEvent {
    public final Object eventSource;
    public final T targettedEntity;
    public final U targettedEntityType;
    public final String[] eventTags;

    public EntityEvent(Object eventSource, T targettedEntity, String[] eventTags, GameWorld world) {
        super(world);
        this.eventSource = eventSource;
        this.targettedEntity = targettedEntity;
        this.targettedEntityType = (U)world.getEntityRegistry().getRegisteredEntityType(targettedEntity.getEntityTypeId());
        this.eventTags = eventTags;
    }
}
