package dev.stonewake.entities;

import dev.stonewake.GameController;
import dev.stonewake.entities.events.EntitySpawnEvent;
import dev.stonewake.entities.listeners.EntitySpawnListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EntityManager {
    private GameController game;
    private HashMap<Integer, Entity> activeEntities;
    private List<Integer> entitiesToRemove;

    public EntityManager(GameController game) {
        activeEntities = new HashMap<>();
        entitiesToRemove = new ArrayList<>();
        this.game = game;
    }

    public void addEntity(Entity entity) {
        activeEntities.put(entity.getUniqueEntityId(), entity);

        for (EntitySpawnListener entitySpawnListener : entity.getEntitySpawnListeners()) {
            entitySpawnListener.entitySpawned(new EntitySpawnEvent(entity, entity.getEntityX(), entity.getEntityY()));
        }

        entity.start(game);
    }

    public Entity getEntity(int uniqueEntityId) {
        return activeEntities.get(uniqueEntityId);
    }

    public void removeEntity(int uniqueEntityId) {
        entitiesToRemove.add(uniqueEntityId);
    }

    public Collection<Entity> getEntities() {
        return activeEntities.values();
    }

    public void updateEntities() {
        for (Entity e : activeEntities.values()) {
            if (e instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)e;
                if (!living.isAlive()) {
                    removeEntity(e.getUniqueEntityId());

                    continue;
                }
            }

            e.update(game);
            e.updateEntityTime(game.getDeltaTime());
        }
        for (Integer id : entitiesToRemove) {
            Entity e = activeEntities.get(id);

            e.dispose();
            activeEntities.remove(id);
        }

        entitiesToRemove.clear();
    }
}
