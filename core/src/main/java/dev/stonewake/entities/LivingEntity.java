package dev.stonewake.entities;

public abstract class LivingEntity extends Entity {
    protected int entityHealth;
    protected int entityMaxHealth;
    protected int entityDefense;
    protected int entityInvulnerabilityTime;
    protected int entityMaxInvulnerabilityTime;

    public LivingEntity(float entityX, float entityY) {
        super(entityX, entityY);
    }

    public void damage(int amount) {
        if (entityInvulnerabilityTime > 0) return;

        amount -= entityDefense;
        amount = Math.max(amount, 0);

        entityHealth -= amount;
        entityHealth = Math.max(0, entityHealth);

        entityInvulnerabilityTime = entityMaxInvulnerabilityTime;

        if (entityHealth < 1) {
            die();
        }
    }

    public void heal(int amount) {
        entityHealth += amount;
        entityHealth = Math.min(entityMaxHealth, entityHealth);
    }

    public void die() {
        dispose();
    }

    public boolean isAlive() {
        return entityHealth > 0;
    }
}
