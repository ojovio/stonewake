package dev.stonewake.entities.events;

import dev.stonewake.entities.types.LivingEntityType;
import dev.stonewake.events.Event;
import dev.stonewake.events.subevents.EntitySubEvent;
import dev.stonewake.entities.LivingEntity;
import dev.stonewake.world.GameWorld;

public class EntityTakeDamageEvent extends Event {
    public static class Pre extends EntitySubEvent {
        public int entityDefense;
        public int damageAmount;

        public Pre(Object damageSource, LivingEntity damagedEntity, int damageAmount, String[] damageTags, GameWorld world) {
            super(damageSource, damagedEntity, damageTags, world);

            this.entityDefense = ((LivingEntityType)world.getEntityRegistry().getRegisteredEntityType(damagedEntity.getEntityId())).getEntityDefense();
            this.damageAmount = damageAmount;
        }
    }

    public static final class Post extends EntitySubEvent {
        public final int entityDefense;
        public final int realEntityDefense;
        public final int damageAmount;
        public final int realDamageAmount;

        public Post(Object damageSource, LivingEntity damagedEntity, int damageAmount, Pre preEvent, String[] damageTags, GameWorld world) {
            super(damageSource, damagedEntity, damageTags, world);

            this.entityDefense = ((LivingEntityType)world.getEntityRegistry().getRegisteredEntityType(damagedEntity.getEntityId())).getEntityDefense();
            this.realEntityDefense = preEvent.entityDefense;
            this.damageAmount = damageAmount;
            this.realDamageAmount = preEvent.damageAmount;
        }
    }

    public EntityTakeDamageEvent(Pre pre) {
        super(pre);
    }

    @Override
    public Class<? extends Event> getEventClass() {
        return EntityTakeDamageEvent.class;
    }
}
