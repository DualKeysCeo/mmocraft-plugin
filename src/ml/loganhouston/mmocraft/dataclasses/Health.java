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

    public Health() {}

    public void regenerate(int health) {
        regenerate(health, this.regenInterval);
    }


    public void regenerate(int health, int regenInterval) {
        while (this.health < maxHealth) {
            regenTimer += System.currentTimeMillis() - millis;
            millis = regenTimer + millis;

            if (regenTimer > regenInterval) {
                this.health += health;
                regenTimer = 0;
            }
        }
        if (this.health > maxHealth) this.health = maxHealth;
    }

    public void damage(int damage) {
        this.health -= damage;
    }

    public void heal(int health) {
        this.health += health;
    }

    public void addMaxHealth(int health) {
        this.maxHealth += health;
    }

    public void damageMaxHealth(int damage) {
        this.maxHealth -= damage;
    }


}
