package ml.loganhouston.mmocraft.dataclasses;

/**
* Class for storing and modifying health easily
 * regeneration classes etc
*
**/

public final class Health {

    private int health = 100,
                maxHealth = 100,
                regenInterval = 1000,
                regenTimer = 0;
    private long millis;

    public Health(int health, int maxHealth) {
        if (health > maxHealth) health = maxHealth;
        this.health = health;
        this.maxHealth = maxHealth;
        millis = System.currentTimeMillis();
    }

    public Health(int health) {
        this.health = health;
    }

    public void regenerate(int health) {
        regenerate(health, this.regenInterval);
    }

    public void regenerate(int health, int regenInterval) {
        while (health < maxHealth) {
            regenTimer += System.currentTimeMillis() - millis;
            millis = regenTimer + millis;

            if (regenTimer > regenInterval) {
                this.health += health;
                regenTimer = 0;
            }
        }
        if (health > maxHealth) health = maxHealth;
    }

    public void damage(int damage) {
        health -= damage;
    }

}
