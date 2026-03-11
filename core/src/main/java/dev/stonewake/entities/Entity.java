package dev.stonewake.entities;

import com.badlogic.gdx.physics.box2d.Body;
import dev.stonewake.GameController;
import dev.stonewake.entities.listeners.EntitySpawnListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private static int nextUniqueId = 0;

    private int uniqueEntityId;
    protected Body entityBody;
    protected float entityTime;

    private List<EntitySpawnListener> entitySpawnListeners;

    public Entity(float entityX, float entityY) {
        entitySpawnListeners = new ArrayList<>();
        setDefaults();

        entityBody.setTransform(entityX, entityY, 0);

        this.uniqueEntityId = nextUniqueId++;
    }

    public abstract void setDefaults();

    public abstract void start(GameController game);

    public abstract void update(GameController game);

    public abstract void dispose();

    public void updateEntityTime(float deltaTime) {
        entityTime += deltaTime;
    }

    public void applyLinearVelocity(float vx, float vy) {
        entityBody.setLinearVelocity(vx, vy);
    }

    public void applyAngularVelocity(float omega) {
        entityBody.setAngularVelocity(omega);
    }

    public float getEntityX() {
        return entityBody.getPosition().x;
    }

    public float getEntityY() {
        return entityBody.getPosition().y;
    }

    public int getUniqueEntityId() {
        return uniqueEntityId;
    }

    public List<EntitySpawnListener> getEntitySpawnListeners() {
        return entitySpawnListeners;
    }
}
